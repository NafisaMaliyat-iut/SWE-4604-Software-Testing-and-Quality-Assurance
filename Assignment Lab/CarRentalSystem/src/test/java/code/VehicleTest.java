package code;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleTest {
    private static Vehicle vehicle;
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
    @CsvSource({"002", "001", "003"})
    public void correctVehicleIdIsReturned(String VehicleId){
        vehicle = new Vehicle(VehicleId, 2020, "Ford", "Transit", 0, new VehicleType(7));
        assertEquals(vehicle.getVehicleId(), VehicleId);
    }

    @Test
    public void maintenanceWithCorrectStatus(){
        vehicle = new Vehicle("001", 2020, "Ford", "Transit", 0, new VehicleType(7));
        assertTrue(vehicle.performMaintenance());
    }

    @ParameterizedTest
    @CsvSource({"1", "2"})
    public void maintenanceWithIncorrectStatus(int vehicleStatus){
        vehicle = new Vehicle("001", 2020, "Ford", "Transit", vehicleStatus, new VehicleType(7));
        assertFalse(vehicle.performMaintenance());
    }

    @ParameterizedTest
    @CsvSource({
            "customer1,12,3,2024,10",
            "customer1,15,3,2024,5",
            "customer1, 25,3,2024,2"
    })
    public void rentedVanWithCorrectDetails(String customerId, int day, int month, int year, int numOfRentDay) {
        vehicle = new Vehicle("V_001", 2020, "Ford", "Transit", 0, new VehicleType(7, new DateTime(20,3,2024)));
        assertTrue(vehicle.rent(customerId, new DateTime(day,month,year), numOfRentDay));
    }

    @ParameterizedTest
    @CsvSource({"1,2"
    })
    public void rentedVanWithIncorrectStatus(int status){
        vehicle = new Vehicle("V_001", 2020, "Ford", "Transit", status, new VehicleType(7, new DateTime(20,3,2024)));
        assertFalse(vehicle.rent("customer1", new DateTime(12,3,2024), 10));
    }

    @ParameterizedTest
    @CsvSource({
            "customer1,12,3,2024,15",
            "customer1,15,3,2024,-5",
            "customer1,9,3,2024,52",
            "customer1,25,4,2024,0",
            "customer1,12,6,2024,1"
    })
    public void rentedVanWithInvalidRentDays(String customerId, int day, int month, int year, int numOfRentDay){
        vehicle = new Vehicle("V_001", 2020, "Ford", "Transit", 0, new VehicleType(7, new DateTime(20,3,2024)));
        assertFalse(vehicle.rent(customerId, new DateTime(day,month,year), numOfRentDay));
    }

    @ParameterizedTest
    @CsvSource({
            "customer1,12,3,2024,1,10",
            "customer2,15,3,2024,2,5",
            "customer3, 25,3,2024,1,2"
    })
    public void rentedVanWhileInMaintenance(String customerId, int day, int month, int year, int numOfRentDay){
        vehicle = new Vehicle("V_001", 2020, "Ford", "Transit", 2, new VehicleType(7, new DateTime(20,3,2024)));
        assertFalse(vehicle.rent(customerId, new DateTime(day,month,year), numOfRentDay));
    }

    @ParameterizedTest
    @CsvSource({
            "customer1,12,3,2024,10",
            "customer2,15,3,2024,5",
            "customer3, 25,3,2024,2"
    })
    public void rentedCarWithCorrectDetails(String customerId, int day, int month, int year, int numOfRentDay) {
        vehicle = new Vehicle("C_001", 2020, "Ford", "Fusion", 0, new VehicleType(7, new DateTime(20,3,2024)));
        assertTrue(vehicle.rent(customerId, new DateTime(day,month,year), numOfRentDay));
    }

    @ParameterizedTest
    @CsvSource({
            "customer1,12,3,2024,15",
            "customer2,15,3,2024,-5",
            "customer3,9,3,2024,52",
            "customer4,25,4,2024,0",
            "customer5,12,6,2024,1"
    })
    public void rentedCarWithInvalidRentDays(String customerId, int day, int month, int year, int numOfRentDay){
        vehicle = new Vehicle("C_001", 2020, "Ford", "Fusion", 0, new VehicleType(7, new DateTime(20,3,2024)));
        assertFalse(vehicle.rent(customerId, new DateTime(day,month,year),numOfRentDay));
    }

    @ParameterizedTest
    @CsvSource({
            "customer1,12,3,2024,1,10",
            "customer2,15,3,2024,2,5",
            "customer3, 25,3,2024,1,2"
    })
    public void rentedCarWhileInMaintenance(String customerId, int day, int month, int year, int numOfRentDay){
        vehicle = new Vehicle("C_001", 2020, "Ford", "Fusion", 2, new VehicleType(7, new DateTime(20,3,2024)));
        assertFalse(vehicle.rent(customerId, new DateTime(day,month,year),numOfRentDay));
    }

    @ParameterizedTest
    @CsvSource({
            "V_001,2020,Ford,Transit,0,7,20,3,2024,V_001:2020:Ford:Transit:15:Available",
            "V_002,2015,Chevrolet,Express,0,15,10,6,2023,V_002:2015:Chevrolet:Express:15:Available",
            "V_003,2018,Mercedes-Benz,Sprinter,1,12,15,9,2022,V_003:2018:Mercedes-Benz:Sprinter:15:Rented",
            "V_004,2016,Dodge,Grand Caravan,1,7,5,11,2023,V_004:2016:Dodge:Grand Caravan:15:Rented",
            "V_005,2019,Nissan,NV Passenger,2,12,25,4,2022,V_005:2019:Nissan:NV Passenger:15:Maintenance",
            "V_006,2017,Toyota,Sienna,2,8,12,7,2022,V_006:2017:Toyota:Sienna:15:Maintenance"
    })
    public void returnsCorrectToStringForVan(String VehicleId, int year, String make, String model, int status, int seats, int maintenanceDay, int maintenanceMonth,int maintenanceYear, String expected){
        vehicle = new Vehicle(VehicleId, year, make, model, status, new VehicleType(seats, new DateTime(maintenanceDay,maintenanceMonth,maintenanceYear)));
        assertEquals(expected,vehicle.toString());
    }

    @ParameterizedTest
    @CsvSource({
            "C_001,2020,Ford,Focus,0,7,20,3,2024,C_001:2020:Ford:Focus:0:Available",
            "C_002,2015,Chevrolet,Impala,0,15,10,6,2023,C_002:2015:Chevrolet:Impala:0:Available",
            "C_003,2018,Mercedes-Benz,C-Class,1,12,15,9,2022,C_003:2018:Mercedes-Benz:C-Class:0:Rented",
            "C_004,2016,Dodge,Charger,1,7,5,11,2023,C_004:2016:Dodge:Charger:0:Rented",
            "C_005,2019,Nissan,Maxima,2,12,25,4,2022,C_005:2019:Nissan:Maxima:0:Maintenance",
            "C_006,2017,Toyota,Camry,2,8,12,7,2022,C_006:2017:Toyota:Camry:0:Maintenance"
    })
    public void returnsCorrectToStringForCar(String VehicleId, int year, String make, String model, int status, int seats, int maintenanceDay, int maintenanceMonth,int maintenanceYear, String expected){
        vehicle = new Vehicle(VehicleId, year, make, model, status, new VehicleType(seats, new DateTime(maintenanceDay,maintenanceMonth,maintenanceYear)));
        assertEquals(expected,vehicle.toString());
    }

    @ParameterizedTest
    @ValueSource(ints= {1,2,3,4,5})
    public void returnsLastRentalIndexCorrectly(int recordsLength){
        vehicle = new Vehicle("001", 2020, "Ford", "Transit", 0, new VehicleType(7, new DateTime(20,3,2024)));
        if (recordsLength >= 0) System.arraycopy(records, 0, vehicle.records, 0, recordsLength);
        assertEquals(recordsLength-1,vehicle.getLastElementIndex());
    }

}
