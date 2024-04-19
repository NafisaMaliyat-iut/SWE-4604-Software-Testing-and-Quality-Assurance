package code;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class CarTest {
    private static Car car;
    private static RentalRecord[] records;
    @BeforeAll
    public static void init(){
        RentalRecord record1 = new RentalRecord("01", new DateTime(5, 3, 2013), new DateTime(10, 3, 2013)); //tuesday
        RentalRecord record2 = new RentalRecord("02", new DateTime(17, 3, 2013), new DateTime(21, 3, 2013)); //saturday
        RentalRecord record3 = new RentalRecord("03", new DateTime(25, 3, 2013), new DateTime(30, 3, 2013)); //monday
        RentalRecord record4 = new RentalRecord("04", new DateTime(5, 4, 2013), new DateTime(10, 4, 2013)); //friday
        RentalRecord record5 = new RentalRecord("05", new DateTime(14, 4, 2013), new DateTime(20, 4, 2013)); //saturday

        //add records
        records = new RentalRecord[]{record1, record2, record3, record4, record5};
    }

    @ParameterizedTest
    @CsvSource({
            "C_001,2020,Toyota,Corolla,0,6,2,'Vehicle ID:\tC_001\n Year:\t 2020\n Make:\tToyota\n Model:\tCorolla\n Number of Seats:\t6\n Status:\tAvailable\nRENTAL RECORD\nRecord ID:             02\nRent Date:             17/03/2013\nEstimated Return Date: 21/03/2013\nActual Return Date:    19/03/2013\nRental Fee:            226.00\nLate Fee:              0.00                     \n----------                     \nRecord ID:             01\nRent Date:             05/03/2013\nEstimated Return Date: 10/03/2013\nActual Return Date:    12/03/2013\nRental Fee:            791.00\nLate Fee:              282.50                     \n----------                     \n'",
            "C_002,2015,Honda,Civic,0,23,1,'Vehicle ID:\tC_002\n Year:\t 2015\n Make:\tHonda\n Model:\tCivic\n Number of Seats:\t23\n Status:\tAvailable\nRENTAL RECORD\nRecord ID:             01\nRent Date:             05/03/2013\nEstimated Return Date: 10/03/2013\nActual Return Date:    12/03/2013\nRental Fee:            791.00\nLate Fee:              282.50                     \n----------                     \n'",
            "C_003,2018,Ford,Fusion,1,12,4,'Vehicle ID:\tC_003\n Year:\t 2018\n Make:\tFord\n Model:\tFusion\n Number of Seats:\t12\n Status:\tRented\nRENTAL RECORD\nRecord ID:             04\nRent Date:             05/04/2013\nEstimated Return Date: 10/04/2013\nActual Return Date:    19/04/2013\nRental Fee:            1582.00\nLate Fee:              1271.25                     \n----------                     \nRecord ID:             03\nRent Date:             25/03/2013\nEstimated Return Date: 30/03/2013\nActual Return Date:    02/04/2013\nRental Fee:            904.00\nLate Fee:              423.75                     \n----------                     \nRecord ID:             02\nRent Date:             17/03/2013\nEstimated Return Date: 21/03/2013\nActual Return Date:    19/03/2013\nRental Fee:            226.00\nLate Fee:              0.00                     \n----------                     \nRecord ID:             01\nRent Date:             05/03/2013\nEstimated Return Date: 10/03/2013\nActual Return Date:    12/03/2013\nRental Fee:            791.00\nLate Fee:              282.50                     \n----------                     \n'",
            "C_004,2016,Chevrolet,Malibu,1,5,5,'Vehicle ID:\tC_004\n Year:\t 2016\n Make:\tChevrolet\n Model:\tMalibu\n Number of Seats:\t5\n Status:\tRented\nRENTAL RECORD\nRecord ID:             05\nRent Date:             14/04/2013\nEstimated Return Date: 20/04/2013\nActual Return Date:    30/04/2013\nRental Fee:            1808.00\nLate Fee:              1412.50                     \n----------                     \nRecord ID:             04\nRent Date:             05/04/2013\nEstimated Return Date: 10/04/2013\nActual Return Date:    19/04/2013\nRental Fee:            1582.00\nLate Fee:              1271.25                     \n----------                     \nRecord ID:             03\nRent Date:             25/03/2013\nEstimated Return Date: 30/03/2013\nActual Return Date:    02/04/2013\nRental Fee:            904.00\nLate Fee:              423.75                     \n----------                     \nRecord ID:             02\nRent Date:             17/03/2013\nEstimated Return Date: 21/03/2013\nActual Return Date:    19/03/2013\nRental Fee:            226.00\nLate Fee:              0.00                     \n----------                     \nRecord ID:             01\nRent Date:             05/03/2013\nEstimated Return Date: 10/03/2013\nActual Return Date:    12/03/2013\nRental Fee:            791.00\nLate Fee:              282.50                     \n----------                     \n'",
            "C_005,2019,Nissan,Ultima,2,9,0,'Vehicle ID:\tC_005\n Year:\t 2019\n Make:\tNissan\n Model:\tUltima\n Number of Seats:\t9\n Status:\tMaintenance\nRENTAL RECORD:\tempty'",
            "C_006,2017,Hyundai,intra,2,2,3,'Vehicle ID:\tC_006\n Year:\t 2017\n Make:\tHyundai\n Model:\tintra\n Number of Seats:\t2\n Status:\tMaintenance\nRENTAL RECORD\nRecord ID:             03\nRent Date:             25/03/2013\nEstimated Return Date: 30/03/2013\nActual Return Date:    02/04/2013\nRental Fee:            904.00\nLate Fee:              423.75                     \n----------                     \nRecord ID:             02\nRent Date:             17/03/2013\nEstimated Return Date: 21/03/2013\nActual Return Date:    19/03/2013\nRental Fee:            226.00\nLate Fee:              0.00                     \n----------                     \nRecord ID:             01\nRent Date:             05/03/2013\nEstimated Return Date: 10/03/2013\nActual Return Date:    12/03/2013\nRental Fee:            791.00\nLate Fee:              282.50                     \n----------                     \n'"
    })
    public void returnsCorrectDetailsWithRentalHistory(String vehicleId, int year, String make, String model, int status, int seats, int recordsLength, String expected) {
        car = new Car(vehicleId, year, make, model, status, new VehicleType(seats));
        if (recordsLength >= 0) System.arraycopy(records, 0, car.records, 0, recordsLength);
        assertEquals(expected, car.getDetails());
    }

    @ParameterizedTest
    @CsvSource({
            "C_001,2020,Toyota,Corolla,0,6,'C_001:2020:Toyota:Corolla:6:Available'",
            "C_002,2015,Honda,Civic,0,23,'C_002:2015:Honda:Civic:23:Available'",
            "C_003,2018,Ford,Fusion,1,12,'C_003:2018:Ford:Fusion:12:Rented'",
            "C_004,2016,Chevrolet,Malibu,1,5,'C_004:2016:Chevrolet:Malibu:5:Rented'",
            "C_005,2019,Nissan,Ultima,2,9,'C_005:2019:Nissan:Ultima:9:Maintenance'",
            "C_006,2017,Hyundai,intra,2,2,'C_006:2017:Hyundai:intra:2:Maintenance'"
    })
    public void returnsCorrectToString(String VehicleId, int year, String make, String model, int status, int seats, String expected){
        car = new Car(VehicleId, year, make, model, status, new VehicleType(seats));
        assertEquals(expected,car.toString());
    }


    @ParameterizedTest
    @CsvSource({
            "15, 3, 2024, 10, 3, 2024, 706.25",
            "10, 3, 2024, 10, 3, 2024, 0.0",
            "5, 3, 2024, 10, 3, 2024, 0.0",
            "12, 4, 2024, 15, 1, 2024, 12430.0",
            "2, 5, 2024, 5, 4, 2024, 3813.75",
            "30, 5, 2024, 20, 5, 2024, 1412.5"
    })
    public void returnsCorrectLateFee(int endDay, int endMonth, int endYear, int startDay, int startMonth, int startYear, double expected){
        car = new Car("C_001", 2020, "Ford", "Fusion", 0, new VehicleType(7));
        assertEquals(expected,car.getLateFee(new DateTime(endDay,endMonth,endYear), new DateTime(startDay,startMonth,startYear)));
    }

    @ParameterizedTest
    @CsvSource({"0,12,3,2013","1,19,3,2013","2,2,4,2013", "3,19,4,2013", "4,30,4,2013"})
    public void returnsCarSuccessfully(int recordIndex, int day, int month, int year){
        car = new Car("C_001", 2020, "Ford", "Fusion", 1, new VehicleType(7));
        car.records[0]=records[recordIndex];
        assertTrue(car.returnVehicle(new DateTime(day,month,year)));
    }

    @ParameterizedTest
    @CsvSource({"0,0,12,3,2013","0,3,19,3,2013"})
    public void returnCarWithIncorrectStatus(int status,int recordIndex, int day, int month, int year){
        car = new Car("C_001", 2020, "Ford", "Fusion", status, new VehicleType(7));
        car.records[0]=records[recordIndex];
        assertFalse(car.returnVehicle(new DateTime(day,month,year)));
    }

    @ParameterizedTest
    @CsvSource({
            "0, 5, 3, 2013",
            "1, 19, 3, 2013",
            "2, 26, 3, 2013",
            "3, 12, 4, 2013",
            "4, 29, 4, 2020"
    })
    //cannot be done since the condition for false will never come true
    @Disabled
    public void returnCarWithInvalidRentDuration(int recordIndex, int day, int month, int year) {
        car = new Car("C_001", 2020, "Ford", "Fusion", 1, new VehicleType(7, new DateTime(20, 3, 2024)));
        car.records[0] = records[recordIndex];
        assertFalse(car.returnVehicle(new DateTime(day, month, year)));
    }
    
    @Test
    public void completeMaintenanceCorrectly(){
        car = new Car("C_001", 2020, "Ford", "Fusion", 2, new VehicleType(7));
        assertTrue(car.completeMaintenance());
    }
    @ParameterizedTest
    @CsvSource({"0", "1"})
    public void completeMaintenanceWithIncorrectStatus(int status){
        car = new Car("C_001", 2020, "Ford", "Fusion", status, new VehicleType(7));
        assertFalse(car.completeMaintenance());
    }
}
