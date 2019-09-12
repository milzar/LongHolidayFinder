package com.longholidayfinder;

import java.util.Date;

public class LongHoliday {
    private Date startDate;
    private Date endDate;

    public LongHoliday(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int duration(){
        return (int) ((this.endDate.getTime() - this.startDate.getTime()) / (1000 * 60 * 60 * 24)) + 1;
    }
}
