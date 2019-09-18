package com.longholidayfinder;

import com.google.api.client.util.DateTime;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.longholidayfinder.DateCalculation.daysAfterDate;
import static com.longholidayfinder.DateCalculation.yearsFrom;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class DateCalculationTest {
    @Nested
    class YearCalculationTest{

        @Test
        void expectEqualWhenComparingCurrentDateWithDate0YearsFromNow() {
            DateTime now = new DateTime(System.currentTimeMillis());

            assertEquals(now, yearsFrom(0, now));
        }

        @Test
        void expectNotEqualWhenComparingCurrentDateWithDate1YearsFromNow() {
            DateTime now = new DateTime(System.currentTimeMillis());

            assertNotEquals(now, yearsFrom(1, now));
        }

        @Test
        void expectEqualWhenComparingSameDate() {
            DateTime now = new DateTime("1984-04-12T05:30:00.000+05:30");
            DateTime yearFromNow = new DateTime("1985-04-12T05:30:00.000+05:30");

            assertEquals(yearFromNow, yearsFrom(1, now));
        }
    }
}
