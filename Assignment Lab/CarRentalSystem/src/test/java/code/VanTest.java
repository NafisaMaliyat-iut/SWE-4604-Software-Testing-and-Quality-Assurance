package code;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class VanTest {
    private static Van van;
    private static RentalRecord[] records;
    @BeforeAll
    public static void init(){
        RentalRecord record1 = new RentalRecord("01", new DateTime(5, 3, 2013), new DateTime(10, 3, 2013));
        RentalRecord record2 = new RentalRecord("02", new DateTime(15, 3, 2013), new DateTime(20, 3, 2013));
        RentalRecord record3 = new RentalRecord("03", new DateTime(25, 3, 2013), new DateTime(30, 3, 2013));
        RentalRecord record4 = new RentalRecord("04", new DateTime(5, 4, 2013), new DateTime(10, 4, 2013));
        RentalRecord record5 = new RentalRecord("05", new DateTime(15, 4, 2013), new DateTime(20, 4, 2013));

        //add records
        records = new RentalRecord[]{record1, record2, record3, record4, record5};
    }

    @ParameterizedTest
    @CsvSource({
            "V_001,2020,Ford,Transit,0,7,20,3,2024,'V_001:2020:Ford:Transit:15:Available:20/03/2024'",
            "V_002,2015,Chevrolet,Express,0,15,10,6,2023,'V_002:2015:Chevrolet:Express:15:Available:10/06/2023'",
            "V_003,2018,Mercedes-Benz,Sprinter,1,12,15,9,2022,'V_003:2018:Mercedes-Benz:Sprinter:15:Rented:15/09/2022'",
            "V_004,2016,Dodge,Grand Caravan,1,7,5,11,2023,'V_004:2016:Dodge:Grand Caravan:15:Rented:05/11/2023'",
            "V_005,2019,Nissan,NV Passenger,2,12,25,4,2022,'V_005:2019:Nissan:NV Passenger:15:Maintenance:25/04/2022'",
            "V_006,2017,Toyota,Sienna,2,8,12,7,2022,'V_006:2017:Toyota:Sienna:15:Maintenance:12/07/2022'"
    })
    public void returnsCorrectToString(String VehicleId, int year, String make, String model, int status, int seats, int maintenanceDay, int maintenanceMonth,int maintenanceYear, String expected){
        van = new Van(VehicleId, year, make, model, status, new VehicleType(seats, new DateTime(maintenanceDay,maintenanceMonth,maintenanceYear)));
        assertEquals(expected,van.toString());
    }

    @ParameterizedTest
    @CsvSource({
            "V_001,2020,Ford,Transit,0,7,20,3,2024,0,'Vehicle ID:\tV_001\n Year:\t 2020\n Make:\tFord\n Model:\tTransit\n Number of Seats:\t7\n Status:\tAvailable\nLast maintenance date:\t20/03/2024\nRENTAL RECORD:\tempty'",
            "V_002,2015,Chevrolet,Express,0,15,10,6,2023,1,'Vehicle ID:\tV_002\n Year:\t 2015\n Make:\tChevrolet\n Model:\tExpress\n Number of Seats:\t15\n Status:\tAvailable\nLast maintenance date:\t10/06/2023\nRENTAL RECORD:\nRecord ID:             01\nRent Date:             05/03/2013\nEstimated Return Date: 10/03/2013                     \n----------                     \n'",
            "V_003,2018,Mercedes-Benz,Sprinter,1,12,15,9,2022,2,'Vehicle ID:\tV_003\n Year:\t 2018\n Make:\tMercedes-Benz\n Model:\tSprinter\n Number of Seats:\t12\n Status:\tRented\nLast maintenance date:\t15/09/2022\nRENTAL RECORD:\nRecord ID:             02\nRent Date:             15/03/2013\nEstimated Return Date: 20/03/2013                     \n----------                     \nRecord ID:             01\nRent Date:             05/03/2013\nEstimated Return Date: 10/03/2013                     \n----------                     \n'",
            "V_004,2016,Dodge,Grand Caravan,1,7,5,11,2023,3,'Vehicle ID:\tV_004\n Year:\t 2016\n Make:\tDodge\n Model:\tGrand Caravan\n Number of Seats:\t7\n Status:\tRented\nLast maintenance date:\t05/11/2023\nRENTAL RECORD:\nRecord ID:             03\nRent Date:             25/03/2013\nEstimated Return Date: 30/03/2013                     \n----------                     \nRecord ID:             02\nRent Date:             15/03/2013\nEstimated Return Date: 20/03/2013                     \n----------                     \nRecord ID:             01\nRent Date:             05/03/2013\nEstimated Return Date: 10/03/2013                     \n----------                     \n'",
            "V_005,2019,Nissan,NV Passenger,2,12,25,4,2022,4,'Vehicle ID:\tV_005\n Year:\t 2019\n Make:\tNissan\n Model:\tNV Passenger\n Number of Seats:\t12\n Status:\tMaintenance\nLast maintenance date:\t25/04/2022\nRENTAL RECORD:\nRecord ID:             04\nRent Date:             05/04/2013\nEstimated Return Date: 10/04/2013                     \n----------                     \nRecord ID:             03\nRent Date:             25/03/2013\nEstimated Return Date: 30/03/2013                     \n----------                     \nRecord ID:             02\nRent Date:             15/03/2013\nEstimated Return Date: 20/03/2013                     \n----------                     \nRecord ID:             01\nRent Date:             05/03/2013\nEstimated Return Date: 10/03/2013                     \n----------                     \n'",
            "V_006,2017,Toyota,Sienna,2,8,12,7,2022,5,'Vehicle ID:\tV_006\n Year:\t 2017\n Make:\tToyota\n Model:\tSienna\n Number of Seats:\t8\n Status:\tMaintenance\nLast maintenance date:\t12/07/2022\nRENTAL RECORD:\nRecord ID:             05\nRent Date:             15/04/2013\nEstimated Return Date: 20/04/2013                     \n----------                     \nRecord ID:             04\nRent Date:             05/04/2013\nEstimated Return Date: 10/04/2013                     \n----------                     \nRecord ID:             03\nRent Date:             25/03/2013\nEstimated Return Date: 30/03/2013                     \n----------                     \nRecord ID:             02\nRent Date:             15/03/2013\nEstimated Return Date: 20/03/2013                     \n----------                     \nRecord ID:             01\nRent Date:             05/03/2013\nEstimated Return Date: 10/03/2013                     \n----------                     \n'"
    })
    public void returnsCorrectDetailsWithRentalHistory(String VehicleId, int year, String make, String model, int status, int seats, int maintenanceDay, int maintenanceMonth,int maintenanceYear, int recordsLength, String expected){
        van = new Van(VehicleId, year, make, model, status, new VehicleType(seats, new DateTime(maintenanceDay,maintenanceMonth,maintenanceYear)));
        if (recordsLength >= 0) System.arraycopy(records, 0, van.records, 0, recordsLength);
        assertEquals(expected,van.getDetails());
    }

    @ParameterizedTest
    @CsvSource({
            "15, 3, 2024, 10, 3, 2024, 1495.0",
            "10, 3, 2024, 10, 3, 2024, 0.0",
            "5, 3, 2024, 10, 3, 2024, 0.0",
            "12, 4, 2024, 15, 1, 2024, 26312.0",
            "2, 5, 2024, 5, 4, 2024, 8073.0",
            "30, 5, 2024, 20, 5, 2024, 2990.0"
    })
    public void returnsCorrectLateFee(int endDay, int endMonth, int endYear, int startDay, int startMonth, int startYear, double expectedFee){
        van = new Van("V_001", 2020, "Ford", "Transit", 0, new VehicleType(7, new DateTime(20,3,2024)));
        assertEquals(expectedFee,van.getLateFee(new DateTime(endDay,endMonth,endYear), new DateTime(startDay,startMonth,startYear)));
    }

    @ParameterizedTest
    @CsvSource({"0,12,3,2013","1,19,3,2013","2,2,4,2013", "3,19,4,2013", "4,30,4,2013"})
    public void returnsVanSuccessfully(int recordIndex, int day, int month, int year){
        van = new Van("V_001", 2020, "Ford", "Transit", 1, new VehicleType(7, new DateTime(20,3,2024)));
        van.records[0]=records[recordIndex];
        assertTrue(van.returnVehicle(new DateTime(day,month,year)));
    }

    @ParameterizedTest
    @CsvSource({"0,0,12,3,2013","0,3,19,3,2013"})
    public void returnVanWithIncorrectStatus(int status,int recordIndex, int day, int month, int year){
        van = new Van("V_001", 2020, "Ford", "Transit", status, new VehicleType(7, new DateTime(20,3,2024)));
        van.records[0]=records[recordIndex];
        assertFalse(van.returnVehicle(new DateTime(day,month,year)));
    }

    @ParameterizedTest
    @CsvSource({"0,5,3,2013","1,15,3,2013","2,25,3,2013", "3,1,4,2013", "4,10,4,2013"})
    public void returnVanWithIncorrectReturnDate(int recordIndex, int day, int month, int year){
        van = new Van("V_001", 2020, "Ford", "Transit", 1, new VehicleType(7, new DateTime(20,3,2024)));
        van.records[0]=records[recordIndex];
        assertFalse(van.returnVehicle(new DateTime(day,month,year)));
    }

    @ParameterizedTest
    @CsvSource({"5,4,2024,2,5,2024","2,4,2024,15,4,2024"})
    public void completeMaintenanceCorrectly(int maintenanceDay, int maintenanceMonth, int maintenanceYear, int returnDay, int returnMonth, int returnYear){
        van = new Van("V_001", 2020, "Ford", "Transit", 2, new VehicleType(7, new DateTime(maintenanceDay, maintenanceMonth, maintenanceYear)));
        assertTrue(van.completeMaintenance(new DateTime(returnDay, returnMonth, returnYear)));
    }
    @ParameterizedTest
    @CsvSource({"0,2,4,2024,2,4,2024","1,2,4,2024,2,4,2024","0,12,6,2024,6,1,2024","1,12,6,2024,6,1,2024",
                "0,6,6,2018,12,6,2018", "1,6,6,2018,12,6,2018"})
    public void completeMaintenanceWithIncorrectDateAndStatus(int status, int maintenanceDay, int maintenanceMonth, int maintenanceYear, int returnDay, int returnMonth, int returnYear){
        van = new Van("V_001", 2020, "Ford", "Transit", status, new VehicleType(7, new DateTime(maintenanceDay, maintenanceMonth, maintenanceYear)));
        assertFalse(van.completeMaintenance(new DateTime(returnDay, returnMonth, returnYear)));
    }

}
