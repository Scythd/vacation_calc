package com.moklyak.vacation_calc.controllers;


import com.moklyak.vacation_calc.exceptions.ValidationException;
import com.moklyak.vacation_calc.services.CalculateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/calculate")
public class CalculateController {

    private final CalculateService calculateService;

    @Autowired
    public CalculateController(CalculateService calculateService) {
        this.calculateService = calculateService;
    }

    /**
     * Returns the vacation payment for specified average month salary and count of vacations days or
     * if specified dates of actual vacation dates returns the vacation payment without including
     * holidays and weekends (why without weekends idk but saying me that I should take into account the given condition
     * but the law says that it should count as workdays, so I shouldn't) <br>
     * if actual vacation days specified the @param vacDaysCount ignored :(
     * @param avgMonthSalary - average salary for month in kopecks
     * @param vacDaysCount - count of vacation days
     * @param vacDays - string array of actual vacation days, date (day) must be in format dd.MM.yyyy
     *                where dd is day of the month, MM is month of the year and yyyy is year ща Anno Domini
     * @return - calculated vacation pay
     */
    @GetMapping()
    Object calculate(@RequestParam int avgMonthSalary,
                   @RequestParam int vacDaysCount,
                   @Nullable @RequestParam Set<String> vacDays) {
        try {
            calculateService.validate(avgMonthSalary, vacDaysCount, vacDays);
            return calculateService.calculate(avgMonthSalary, vacDaysCount, vacDays);
        } catch (ValidationException ex){
            return ex;
        }
    }
}
