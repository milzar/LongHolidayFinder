package com.longholidayfinder;

import com.google.api.client.util.DateTime;
import org.junit.jupiter.api.Test;

import static com.longholidayfinder.DateCalculation.yearsFrom;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class DateCalculationTest {
    @Test
    void expectEqualWhenComparingCurrentDateWithDate0YearsFromNow() {
        DateTime now = new DateTime(System.currentTimeMillis());

        assertEquals(now, yearsFrom(0,now));
    }

    @Test
    void expectEqualWhenComparingCurrentDateWithDate1YearsFromNow() {
        DateTime now = new DateTime(System.currentTimeMillis());

        assertNotEquals(now, yearsFrom(1,now));
    }
}
