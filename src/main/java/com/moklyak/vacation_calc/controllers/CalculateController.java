package com.moklyak.vacation_calc.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/calculate")
public class CalculateController {
    private final DecimalFormat df = new DecimalFormat(".##");
    private final DateFormat holidayDateFormat;
    private final static DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private final List<Date> holidays;
    private final Calendar calendar = Calendar.getInstance();

    @Autowired
    public CalculateController(@Qualifier("holidays") DateFormat holidayDateFormat,
                               @Qualifier("holidays") List<Date> holidays) {
        this.holidayDateFormat = holidayDateFormat;
        this.holidays = holidays;
    }

    private static final double AVG_DAY_IN_MONTH_COUNT = 29.3;

    /**
     * Returns the vacation payment for specified average month salary and count of vacations days or
     * if specified dates of actual vacation dates returns the vacation payment without including
     * holidays and weekends (why without weekends idk but saying me that I should take into account the given condition
     * but the law says that it should count as workdays so i should'nt) <br>
     * if actual vacation days specified the @param vacDaysCount ignored :(
     * @param avgMonthSalary - average salary for month
     * @param vacDaysCount - count of vacation days
     * @param vacDays - string array of actual vacation days, date (day) must be in format dd.MM.yyyy
     *                where dd is day of the month, MM is month of the year and yyyy is year ща Anno Domini
     * @return - calculated vacation pay
     */
    @GetMapping()
    Object calculate(@RequestParam int avgMonthSalary,
                     @RequestParam int vacDaysCount,
                     @Nullable @RequestParam Set<String> vacDays) {
        if (vacDays == null) {
            return Math.round(avgMonthSalary / AVG_DAY_IN_MONTH_COUNT * vacDaysCount);
        } else {
            long actualCount = vacDays.stream()
                    .map(source -> {
                                try {
                                    return dateFormat.parse(source);
                                } catch (ParseException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                    )
                    .filter(x -> {
                        return holidays.stream().noneMatch(y -> {
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
                                    vacDayOfWeek != Calendar.SATURDAY ||
                                    vacDayOfWeek != Calendar.SUNDAY;
                        });
                    }).count();
            return Math.round(avgMonthSalary / AVG_DAY_IN_MONTH_COUNT * actualCount);
        }
    }
}
