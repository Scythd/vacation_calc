package com.moklyak.vacation_calc.services;

import com.moklyak.vacation_calc.exceptions.ValidationException;

import java.util.Set;

public interface CalculateService {
    boolean validate(int avgMonthSalary, int vacDaysCount, Set<String> vacDays) throws ValidationException;
    long calculate(int avgMonthSalary, int vacDaysCount, Set<String> vacDays);
}
