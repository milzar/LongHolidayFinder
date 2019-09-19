package com.longholidayfinder;

import com.google.api.client.util.DateTime;

import static com.longholidayfinder.Options.*;

class DateCalculation {

    static DateTime yearsAfter(DateTime some, int years) {
        return daysAfterDate(some, DAYS_IN_A_YEAR * years);
    }

    static DateTime daysAfterDate(DateTime some, int days) {
        return new DateTime(some.getValue() + (long) days * (SECONDS_TO_MILLISECONDS * SECONDS_IN_A_MINUTE * MINUTES_IN_AN_HOUR * HOURS_IN_A_DAY));
    }

    static  DateTime daysBeforeDate(DateTime some, int days){
        return daysAfterDate(some,-days);
    }
}
