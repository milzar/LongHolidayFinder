package com.longholidayfinder;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LongHolidayTest {

    @Test
    void expect1WhenStartDateAndEndDateAreTheSame(){
        LongHoliday some = new LongHoliday(new Date(System.currentTimeMillis()) ,new Date(System.currentTimeMillis()) );
        assertEquals(some.duration(),1);
    }


    @Test
    void expect2WhenStartDateAndEndDateAreConsecutive(){
        LongHoliday some = new LongHoliday(new Date(System.currentTimeMillis()) ,new Date(System.currentTimeMillis() + 86400000 ) );
        assertEquals(some.duration(),2);
    }
}
