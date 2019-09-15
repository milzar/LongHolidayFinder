package com.longholidayfinder;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import java.util.ArrayList;
import java.util.List;

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

    public Event getEvent() {
        Event myEvent = new Event();
        myEvent.setStart(new EventDateTime().setDateTime(this.startDate));
        myEvent.setEnd(new EventDateTime().setDateTime(this.endDate));
        myEvent.setSummary(this.holidaysList());

        return myEvent;
    }

    @Override
    public String toString() {
        String holidayList = holidaysList();

        return String.format("%-40s\t%-15s\t%-15s\tfor %d days", holidayList, trimTime(startDate), trimTime(endDate), this.duration());
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
