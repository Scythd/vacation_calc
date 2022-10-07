package com.moklyak.vacation_calc.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class HolidaysConfiguration {

    @Bean
    @Qualifier("holidays")
    public static SimpleDateFormat dateFormat(){
        return new SimpleDateFormat("dd.MM");
    }

    @Bean
    @Qualifier("holidays")
    public static Date holiday1(DateFormat dateFormat) throws ParseException {
        return dateFormat.parse("01.01");
    }

    @Bean
    @Qualifier("holidays")
    public static Date holiday2(DateFormat dateFormat) throws ParseException {
        return dateFormat.parse("02.01");
    }

    @Bean
    @Qualifier("holidays")
    public static Date holiday3(DateFormat dateFormat) throws ParseException {
        return dateFormat.parse("03.01");
    }

    @Bean
    @Qualifier("holidays")
    public static Date holiday4(DateFormat dateFormat) throws ParseException {
        return dateFormat.parse("04.01");
    }

    @Bean
    @Qualifier("holidays")
    public static Date holiday5(DateFormat dateFormat) throws ParseException {
        return dateFormat.parse("05.01");
    }

    @Bean
    @Qualifier("holidays")
    public static Date holiday6(DateFormat dateFormat) throws ParseException {
        return dateFormat.parse("06.01");
    }

    @Bean
    @Qualifier("holidays")
    public static Date holiday7(DateFormat dateFormat) throws ParseException {
        return dateFormat.parse("07.01");
    }

    @Bean
    @Qualifier("holidays")
    public static Date holiday8(DateFormat dateFormat) throws ParseException {
        return dateFormat.parse("08.01");
    }

    @Bean
    @Qualifier("holidays")
    public static Date holiday9(DateFormat dateFormat) throws ParseException {
        return dateFormat.parse("23.02");
    }

    @Bean
    @Qualifier("holidays")
    public static Date holiday10(DateFormat dateFormat) throws ParseException {
        return dateFormat.parse("08.03");
    }

    @Bean
    @Qualifier("holidays")
    public static Date holiday11(DateFormat dateFormat) throws ParseException {
        return dateFormat.parse("01.05");
    }

    @Bean
    @Qualifier("holidays")
    public static Date holiday12(DateFormat dateFormat) throws ParseException {
        return dateFormat.parse("09.05");
    }

    @Bean
    @Qualifier("holidays")
    public static Date holiday13(DateFormat dateFormat) throws ParseException {
        return dateFormat.parse("12.06");
    }

    @Bean
    @Qualifier("holidays")
    public static Date holiday14(DateFormat dateFormat) throws ParseException {
        return dateFormat.parse("04.11");
    }

}
