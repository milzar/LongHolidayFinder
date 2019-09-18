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
    static boolean onAFriday(Event holiday) {
        java.util.Calendar someCalendar = getCalendarWithDateAs(holiday);
        return someCalendar.get(java.util.Calendar.DAY_OF_WEEK) == java.util.Calendar.FRIDAY;
    }

    static boolean onAMonday(Event holiday) {
        java.util.Calendar someCalendar = getCalendarWithDateAs(holiday);
        return someCalendar.get(java.util.Calendar.DAY_OF_WEEK) == java.util.Calendar.MONDAY;
    }

    static boolean onAThursday(Event holiday) {
        java.util.Calendar someCalendar = getCalendarWithDateAs(holiday);
        return someCalendar.get(java.util.Calendar.DAY_OF_WEEK) == Calendar.THURSDAY;
    }


    static boolean onATuesday(Event holiday) {
        java.util.Calendar someCalendar = getCalendarWithDateAs(holiday);
        return someCalendar.get(java.util.Calendar.DAY_OF_WEEK) == Calendar.TUESDAY;
    }


    static boolean isFridayOrMonday(Event holiday) {
        java.util.Calendar someCalendar = getCalendarWithDateAs(holiday);

        return someCalendar.get(java.util.Calendar.DAY_OF_WEEK) == java.util.Calendar.FRIDAY ||
                someCalendar.get(java.util.Calendar.DAY_OF_WEEK) == java.util.Calendar.MONDAY;
    }

    static boolean isOneDayAwayFromWeekend(Event holiday) {
        java.util.Calendar someCalendar = getCalendarWithDateAs(holiday);

        return someCalendar.get(java.util.Calendar.DAY_OF_WEEK) == java.util.Calendar.THURSDAY ||
                someCalendar.get(java.util.Calendar.DAY_OF_WEEK) == java.util.Calendar.TUESDAY;
    }

    private static java.util.Calendar getCalendarWithDateAs(Event holiday) {
        DateTime holidayDate = holiday.getStart().getDate();

        Date holidayDateInJavaFormat = new Date(holidayDate.getValue());

        java.util.Calendar someCalendar = java.util.Calendar.getInstance();
        someCalendar.setTime(holidayDateInJavaFormat);
        return someCalendar;
    }
}
