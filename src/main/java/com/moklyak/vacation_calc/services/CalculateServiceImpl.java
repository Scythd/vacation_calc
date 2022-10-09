package com.moklyak.vacation_calc.services;

import com.moklyak.vacation_calc.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Component
public class CalculateServiceImpl implements CalculateService {

    // it may be minimal salary from law
    private final static int MIN_AVG_MONTH_SALARY = 1;

    private final static DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private final static double AVG_DAY_IN_MONTH_COUNT = 29.3;

    private final List<Date> holidays;
    private final Calendar calendar = Calendar.getInstance();


    @Autowired
    public CalculateServiceImpl(@Qualifier("holidays") List<Date> holidays) {
        this.holidays = holidays;
    }

    @Override
    public boolean validate(int avgMonthSalary, int vacDaysCount, Set<String> vacDays) throws ValidationException{
        // counting payment employee to pay? no
        if (avgMonthSalary < MIN_AVG_MONTH_SALARY){
            throw new ValidationException(new IllegalArgumentException("Average month salary shouldn't be less then allowed"));
        }
        // i shouldn't count for 0 and negative numbers because they have no vacations
        if (vacDaysCount < 1){
            throw new ValidationException(new IllegalArgumentException("Vacation must be (vacation days count 0 or less)"));
        }
        return true;
    }

    @Override
    public long calculate(int avgMonthSalary, int vacDaysCount, Set<String> vacDays) {
        if (vacDays == null) {
            // first case when only get avg salary and days count
            return Math.round(avgMonthSalary / AVG_DAY_IN_MONTH_COUNT * vacDaysCount);
        } else {
            // second case when get avg salary, days count and actual vacation days
            //filtering days dropped on weekend and holidays
            long actualCount = vacDays.stream()
                                        //transform strings from input into dates by format
                                        .map(source -> {
                                                    try {
                                                        return dateFormat.parse(source);
                                                    } catch (ParseException ex) {
                                                        throw new RuntimeException(ex);
                                                    }
                                                }
                                        )
                                        // actual filtering
                                        .filter(x -> holidays.stream().noneMatch(y -> {
                                            int holDay, holMon, vacDay, vacMon, vacDayOfWeek;
                                            calendar.setTime(y);
                                            holDay = calendar.get(Calendar.MONTH);
                                            holMon = calendar.get(Calendar.DAY_OF_MONTH);
                                            calendar.setTime(x);
                                            vacDay = calendar.get(Calendar.MONTH);
                                            vacMon = calendar.get(Calendar.DAY_OF_MONTH);
                                            vacDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                                            return holDay != vacDay &&
                                                    holMon != vacMon ||
                                                    vacDayOfWeek != Calendar.SATURDAY &&
                                                            vacDayOfWeek != Calendar.SUNDAY;
                                        }))
                                        //count remains days - we should pay for these (as i understand)
                                        .count();
            return Math.round(avgMonthSalary / AVG_DAY_IN_MONTH_COUNT * actualCount);
        }
    }
}
