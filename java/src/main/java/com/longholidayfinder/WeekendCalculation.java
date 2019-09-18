package com.longholidayfinder;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;

import java.util.Calendar;
import java.util.Date;

class WeekendCalculation {
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
        Calendar someCalendar = getCalendarWithDateAs(holiday);
        return someCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY;
    }

    static boolean isFridayOrMonday(Event holiday) {
        Calendar someCalendar = getCalendarWithDateAs(holiday);

        return someCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY ||
                someCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY;
    }

    static boolean isOneDayAwayFromWeekend(Event holiday) {
        Calendar someCalendar = getCalendarWithDateAs(holiday);

        return someCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY ||
                someCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY;
    }

    private static Calendar getCalendarWithDateAs(Event holiday) {
        DateTime holidayDate = holiday.getStart().getDate();

        Date holidayDateInJavaFormat = new Date(holidayDate.getValue());

        Calendar someCalendar = Calendar.getInstance();
        someCalendar.setTime(holidayDateInJavaFormat);
        return someCalendar;
    }
}
