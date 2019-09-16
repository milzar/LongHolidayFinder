package com.longholidayfinder;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import java.util.ArrayList;
import java.util.List;

import static com.longholidayfinder.DateCalculation.daysBeforeDate;

public class LongHoliday {
    private DateTime startDate;
    private DateTime endDate;
    private List<String> holidays;

    public LongHoliday(DateTime startDate, DateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        holidays = new ArrayList<>();
    }

    public int duration() {
        return (int) ((this.endDate.getValue() - this.startDate.getValue()) / (1000 * 60 * 60 * 24)) + 1;
    }

    void addHoliday(String holiday) {
        this.holidays.add(holiday);
    }

    public Event getEventReminder() {
        Event myEventReminder = new Event();


        DateTime eventReminderDate = daysBeforeDate(115,this.startDate).getValue() < System.currentTimeMillis()?
                new DateTime(System.currentTimeMillis()) : daysBeforeDate(115,this.startDate) ;

        myEventReminder.setStart(new EventDateTime().setDateTime(eventReminderDate));
        myEventReminder.setEnd(new EventDateTime().setDateTime(eventReminderDate));
        myEventReminder.setSummary(this.holidaysList());
        myEventReminder.setDescription(this.toString());
        myEventReminder.setColorId("11");

        return myEventReminder;
    }

    @Override
    public String toString() {
        return String.format("%-40s\n%s - %s\nfor %d days", this.holidaysList(), trimTime(startDate), trimTime(endDate), this.duration());
    }

    private String holidaysList() {
        StringBuilder holidayList = new StringBuilder();
        for (String holiday : this.holidays) {
            holidayList.append(String.format("%s ", holiday));
        }
        return holidayList.toString();
    }

    private String trimTime(DateTime some) {
        int endIndex = !some.toString().contains("T") ? some.toString().length() : some.toString().indexOf("T");
        return some.toString().substring(0, endIndex);
    }

}
