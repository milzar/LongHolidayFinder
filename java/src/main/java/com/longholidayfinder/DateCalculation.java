package com.longholidayfinder;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;

import java.util.Calendar;
import java.util.Date;

class DateCalculation {
    static DateTime yearsAfter(DateTime some, int years) {
        return daysAfterDate(some,365 * years);
    }

    static DateTime daysAfterDate(DateTime some, int days) {
        return new DateTime(some.getValue() + (long) days * (1000 * 60 * 60 * 24));
    }

    static  DateTime daysBeforeDate(DateTime some, int days){
        return daysAfterDate(some,-days);
    }


}
