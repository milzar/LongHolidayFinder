package com.longholidayfinder;

import com.google.api.client.util.DateTime;

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
