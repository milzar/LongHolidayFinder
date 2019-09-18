package com.longholidayfinder;

import com.google.api.client.util.DateTime;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.longholidayfinder.DateCalculation.daysAfterDate;
import static com.longholidayfinder.DateCalculation.yearsAfter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class DateCalculationTest {
    @Nested
    class YearCalculationTest{

        @Test
        void expectEqualWhenComparingCurrentDateWithDate0YearsFromNow() {
            DateTime now = new DateTime(System.currentTimeMillis());

            assertEquals(now, yearsAfter(now, 0));
        }

        @Test
        void expectNotEqualWhenComparingCurrentDateWithDate1YearsFromNow() {
            DateTime now = new DateTime(System.currentTimeMillis());

            assertNotEquals(now, yearsAfter(now, 1));
        }

        @Test
        void expectEqualWhenComparingSameDate() {
            DateTime now = new DateTime("1984-04-12T05:30:00.000+05:30");
            DateTime yearFromNow = new DateTime("1985-04-12T05:30:00.000+05:30");

            assertEquals(yearFromNow, yearsAfter(now, 1));
        }
    }

    @Nested
    class DayCalculationTest{
        @Test
        void expectEqualWhenComparingSameDate() {
            DateTime now = new DateTime("1984-04-12T05:30:00.000+05:30");

            assertEquals(now, daysAfterDate(now, 0));
        }

        @Test
        void expectEqualWhenComparingSameDateAfter1Day() {
            DateTime now = new DateTime("1984-04-12T05:30:00.000+05:30");
            DateTime afterOneDay = new DateTime("1984-04-13T05:30:00.000+05:30");
            assertEquals(afterOneDay, daysAfterDate(now, 1));
        }

        @Test
        void expectNotEqualWhenComparingADateWithAnotherDate1DayAfter() {
            DateTime now = new DateTime("1984-04-12T05:30:00.000+05:30");

            assertNotEquals(now, daysAfterDate(now, 1));
        }
    }
}
