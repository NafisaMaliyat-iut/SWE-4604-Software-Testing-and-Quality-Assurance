package code;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DateTimeTest {
    private static DateTime dateTime;

    @Test
    @Order(1)
    public void initializingWithSystemTime(){
        long currentTime = System.currentTimeMillis();
        DateTime dateTime = new DateTime();
        assertTrue(currentTime - dateTime.time <= 2000L);
    }

    @Test
    @Order(1)
    public void initializingWithDayMonthYear(){
        int day = 13;
        int month = 3;
        int year = 2024;

        DateTime dateTime = new DateTime(day, month, year);
        assertTrue(Math.abs(1710266500000L - dateTime.time)<=100000L);
    }

    @Test
    @Order(1)
    public void initializingWithSettingTimeForward(){
        int daysToAdvance = 5;

        long expectedTime = System.currentTimeMillis() + daysToAdvance * 24L * 60L * 60000L;
        DateTime dateTime = new DateTime(5);

        assertTrue(Math.abs(expectedTime- dateTime.time)<=1000L);
    }

    @Test
    @Order(2)
    public void initializingWithAddingToADate(){
        int daysToAdvance = 5;
        long expectedTime = 1710266500000L + daysToAdvance * 24L * 60L * 60000L;
        DateTime dateTime = new DateTime(new DateTime(13,3,2024),daysToAdvance);

        assertTrue(Math.abs(expectedTime - dateTime.time)<=100000L);
    }

    @Test
    @Order(4)
    public void timeGetterWorks(){
        dateTime = new DateTime(2,4,2024);
        assertEquals(dateTime.time, dateTime.getTime());
    }

    @Test
    @Order(3)
    public void toStringWorks(){
        dateTime = new DateTime(2,4,2024);
        assertEquals("02/04/2024", dateTime.toString());
    }

    @Test
    @Order(3)
    public void returnsDayNameCorrectly(){
        dateTime = new DateTime(13,3,2024);
        assertEquals("Wednesday", dateTime.getNameOfDay());
    }

    @Test
    @Order(3)
    public void returnsFormattedDateCorrectly(){
        dateTime = new DateTime(13,3,2024);
        assertEquals("13/03/2024", dateTime.getFormattedDate());
    }

    @Test
    @Order(3)
    public void returnsEightDigitDateCorrectly(){
        dateTime = new DateTime(13,3,2024);
        assertEquals("13032024", dateTime.getEightDigitDate());
    }

    @Test
    @Order(3)
    public void currentTimeIsReturnedCorrectly(){
        String expected = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        assertEquals(expected,DateTime.getCurrentTime());
    }

    @ParameterizedTest
    @Order(3)
    @CsvSource({
            "15, 3, 2024, 10, 3, 2024, -5",
            "15, 3, 2024, 10, 2, 2024, -34",
            "10, 3, 2022, 15, 3, 2024, 736",
            "15, 3, 2024, 20, 3, 2024, 5"
    })
    public void returnsDifferenceInDatesCorrectly(int startDay, int startMonth, int startYear, int endDay, int endMonth, int endYear, int expectedDiff){
        assertEquals(expectedDiff,DateTime.diffDays(new DateTime(endDay, endMonth, endYear),new DateTime(startDay, startMonth, startYear)));
    }

    @Test
    @Order(3)
    public void dateIsSetCorrectly(){
        dateTime = new DateTime(1,1,2001);
        dateTime.setDate(13,3,2024);
        assertTrue(Math.abs(1710266500000L - dateTime.time)<=100000L);
    }

    @Test
    @Order(3)
    public void advanceSetterWorks(){
        dateTime = new DateTime(13,3,2024);
        dateTime.setAdvance(13,23,200);
        assertEquals((13*24L+23)*60L*60000L, dateTime.advance);
    }


}
