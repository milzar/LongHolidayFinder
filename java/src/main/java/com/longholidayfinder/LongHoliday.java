package com.longholidayfinder;

import com.google.api.client.util.DateTime;

public class LongHoliday {
    private DateTime startDate;
    private DateTime endDate;

    public LongHoliday(DateTime startDate, DateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int duration() {
        return (int) ((this.endDate.getValue() - this.startDate.getValue()) / (1000 * 60 * 60 * 24)) + 1;
    }

    @Override
    public String toString() {

        return String.format("%s\t-\t%s\tfor %d days", trimTime(startDate), trimTime(endDate), this.duration());
    }

    private String trimTime(DateTime some){
        int endIndex = !some.toString().contains("T") ? some.toString().length() :some.toString().indexOf("T");
        return some.toString().substring(0,endIndex);
    }
}
