package com.longholidayfinder;

import com.google.api.client.util.DateTime;

class DateCalculation {

    private static final int DAYS_IN_A_YEAR = 365;
    private static final int SECONDS_TO_MILLISECONDS = 1000;
    private static final int SECONDS_IN_A_MINUTE = 60;
    private static final int MINUTES_IN_AN_HOUR = 60;
    private static final int HOURS_IN_A_DAY = 24;

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
