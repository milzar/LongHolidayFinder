package com.longholidayfinder;

import com.google.api.client.util.DateTime;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LongHolidayTest {

    private static final int SECONDS_IN_A_DAY = 86400000;

    @Test
    void expectDuration1WhenStartDateAndEndDateAreTheSame(){
        LongHoliday some = new LongHoliday(new DateTime(System.currentTimeMillis()) ,new DateTime(System.currentTimeMillis()) );
        assertEquals(some.duration(),1);
    }


    @Test
    void expectDuration2WhenStartDateAndEndDateAreConsecutive(){
        LongHoliday some = new LongHoliday(new DateTime(System.currentTimeMillis()) ,new DateTime(System.currentTimeMillis() + SECONDS_IN_A_DAY) );
        assertEquals(some.duration(),2);
    }
}
