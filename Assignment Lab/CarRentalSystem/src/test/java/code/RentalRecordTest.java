package code;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RentalRecordTest {
    private static DateTime defaultRentDate = new DateTime(12,3,2024);
    private static DateTime defaultReturnDate = new DateTime(20,3,2024);
    private static RentalRecord rentalRecord;


    @Test
    public void dataIsSetCorrectly(){
        rentalRecord = new RentalRecord("001", defaultRentDate, defaultReturnDate);
        DateTime actualReturnDate = new DateTime(24,4,2015);
        double rentalFee = 60.50;
        double lateFee = 330.30;
        rentalRecord.setData(actualReturnDate,rentalFee, lateFee);
        assertEquals(actualReturnDate.toString(), rentalRecord.ActualReturnDate.toString());
        assertEquals(rentalFee, rentalRecord.RentalFee);
        assertEquals(lateFee, rentalRecord.LateFee);
    }

    @Test
    public void estimatedDateGetterWorks(){
        rentalRecord = new RentalRecord("001", defaultRentDate, defaultReturnDate);
        assertEquals(defaultReturnDate.toString() ,rentalRecord.getEstimatedReturnDate().toString());
    }

    @Test
    public void rentDateGetterWorks(){
        rentalRecord = new RentalRecord("001", defaultRentDate, defaultReturnDate);
        assertEquals(defaultRentDate.toString() ,rentalRecord.getRentDate().toString());
    }

    @Test
    public void returnsCorrectDetailsInToString1(){
        rentalRecord = new RentalRecord("001", defaultRentDate, defaultReturnDate);
        rentalRecord.ActualReturnDate=new DateTime(4,5,2023);
        rentalRecord.LateFee=223.23;
        rentalRecord.RentalFee = 656.343;

        assertEquals("001:12/03/2024:20/03/2024:04/05/2023:656.343:223.23", rentalRecord.toString());
    }

    @Disabled
    @Test
    public void returnsCorrectDetailsInToString2(){
        rentalRecord = new RentalRecord("001", defaultRentDate, defaultReturnDate);
        rentalRecord.ActualReturnDate=new DateTime(4,5,2023);
        rentalRecord.RentalFee = 656.343;

        assertEquals("001:12/03/2024:20/03/2024:04/05/2023:656.343:223.23", rentalRecord.toString());
    }


    @Disabled
    @Test
    public void returnsCorrectDetailsInToString3(){
        rentalRecord = new RentalRecord("001", defaultRentDate, defaultReturnDate);
        rentalRecord.ActualReturnDate=new DateTime(4,5,2023);
        rentalRecord.LateFee=223.23;

        assertEquals("001:12/03/2024:20/03/2024:04/05/2023:656.343:223.23", rentalRecord.toString());
    }

    @Disabled
    @Test
    public void returnsCorrectDetailsInToString4(){
        rentalRecord = new RentalRecord("001", defaultRentDate, defaultReturnDate);
        rentalRecord.LateFee=223.23;
        rentalRecord.RentalFee = 656.343;

        assertEquals("001:12/03/2024:20/03/2024:04/05/2023:656.343:223.23", rentalRecord.toString());
    }

    @Test
    public void returnsCorrectDetailsInToString5(){
        rentalRecord = new RentalRecord("001", defaultRentDate, defaultReturnDate);
        assertEquals("001:12/03/2024:20/03/2024:none:none:none", rentalRecord.toString());
    }
}
