package com.longholidayfinder;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;

import java.util.Calendar;
import java.util.Date;

import static com.google.common.primitives.Ints.min;
import static com.longholidayfinder.DateCalculation.daysAfterDate;
import static com.longholidayfinder.DateCalculation.daysBeforeDate;
import static java.lang.Math.abs;

class WeekendCalculation {

    private static Calendar getCalendarWithDateAs(Event holiday) {
        DateTime holidayDate = holiday.getStart().getDate();

        Date holidayDateInJavaFormat = new Date(holidayDate.getValue());

        Calendar someCalendar = Calendar.getInstance();
        someCalendar.setTime(holidayDateInJavaFormat);
        return someCalendar;
    }

    static LongHoliday processHoliday(Event holiday) {
        int dayOfTheWeekOfMyHoliday = getCalendarWithDateAs(holiday).get(Calendar.DAY_OF_WEEK);

        int distanceFromSaturday = dayOfTheWeekOfMyHoliday - Calendar.SATURDAY;
        int distanceFromSunday = dayOfTheWeekOfMyHoliday - Calendar.SUNDAY;

        int distanceFromWeekend = min(abs(distanceFromSaturday), abs(distanceFromSunday));

        if (distanceFromWeekend > 2 || distanceFromWeekend == 0) {
            return null;
        }
        DateTime holidayStartDate = holiday.getStart().getDate();
        LongHoliday newHoliday;
        if (abs(distanceFromSaturday) < abs(distanceFromSunday)) {
            newHoliday = new LongHoliday(holidayStartDate
                    , daysAfterDate(holidayStartDate, abs(distanceFromSaturday) +1 ));
        } else {
            newHoliday = new LongHoliday(daysBeforeDate(holidayStartDate, distanceFromSunday + 1), holidayStartDate);
        }
        newHoliday.addHoliday(holiday.getSummary());

        return newHoliday;
    }
}
