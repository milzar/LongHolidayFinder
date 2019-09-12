package com.longholidayfinder;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import java.util.Date;

class DateCalculation {
    static DateTime yearsFromNow(int years) {
        return new DateTime(System.currentTimeMillis() + years * (31536L * 1000000));
    }

    static DateTime daysFromDate(int days, DateTime some) {
        return new DateTime(some.getValue() + days * (1000 * 60 * 60 * 24));
    }

    static boolean isFriday(Event holiday) {
        java.util.Calendar someCalendar = getCalendarWithDateAs(holiday);
        return someCalendar.get(java.util.Calendar.DAY_OF_WEEK) == java.util.Calendar.FRIDAY;
    }

    static boolean isMonday(Event holiday) {
        java.util.Calendar someCalendar = getCalendarWithDateAs(holiday);
        return someCalendar.get(java.util.Calendar.DAY_OF_WEEK) == java.util.Calendar.MONDAY;
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
