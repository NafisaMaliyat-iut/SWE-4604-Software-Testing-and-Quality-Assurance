package code;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VehicleTypeTest {
    private static VehicleType vehicleType;

    @ParameterizedTest
    @ValueSource(ints= {3,6,9,15})
    public void returnsCarSeatCorrectly(int seats){
        vehicleType=new VehicleType(seats);
        assertEquals(vehicleType.getSeats("car"), seats);
    }

    @ParameterizedTest
    @CsvSource({"4,20,2,2023", "12,12,12,2012", "16,12,12,2023"})
    public void returnsVanSeatCorrectly(int seats, int day, int month, int year){
        vehicleType=new VehicleType(seats, new DateTime(day,month,year));
        assertEquals(vehicleType.getSeats("van"), seats);
    }

    @ParameterizedTest
    @ValueSource(ints= {3,6,9,15})
    public void carSeatGetterWorks(int seats){
        vehicleType=new VehicleType(seats);
        assertEquals(vehicleType.getCarSeats(), seats);
    }

    @ParameterizedTest
    @CsvSource({"3,5", "5,12", "12,9"})
    public void carSeatSetterWorks(int seatsInit, int seatsSet){
        vehicleType=new VehicleType(seatsInit);
        vehicleType.setCarSeats(seatsSet);
        assertEquals(vehicleType.carSeats, seatsSet);
    }

    @ParameterizedTest
    @CsvSource({
            "Sunday,0",
            "Monday,1",
            "Tuesday,2",
            "Wednesday,3",
            "Thursday,4",
            "Friday,5",
            "Saturday,6",
            "Invalid,-1"})
    public void indexOfDaysReturnedCorrectly(String inputDay, int expectedIndex){
        final String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        vehicleType=new VehicleType(12);
        assertEquals(expectedIndex, vehicleType.indexOf(inputDay));
    }

    @ParameterizedTest
    @CsvSource({"4,20,2,2023", "12,12,12,2012", "16,12,12,2023"})
    public void lastMaintenanceDateGetter(int seats, int day, int month, int year){
        vehicleType=new VehicleType(seats, new DateTime(day,month,year));
        assertEquals(new DateTime(day,month,year).toString(), vehicleType.getLastMaintenance().toString());
    }

    @ParameterizedTest
    @CsvSource({"4,20,2,2023", "12,12,12,2012", "16,12,12,2023"})
    public void lastMaintenanceDateSetter(int seats, int day, int month, int year){
        vehicleType=new VehicleType(seats, new DateTime(day,month,year));
        assertEquals(new DateTime(day,month,year).toString(), vehicleType.LastMaintenance.toString());
    }

    @ParameterizedTest
    @CsvSource({"13,3,2024,car,2", "15,3,2024,car,3", "13,3,2024,van,1", "15,3,2024,van,1"})
    public void returnsCorrectMinimumRentDuration( int day, int month, int year, String type, int expectedDuration){
        vehicleType = new VehicleType(5);
        assertEquals(expectedDuration, vehicleType.canBeRentedForMinimumDays(new DateTime(day,month,year), type));
    }

    @ParameterizedTest
    @CsvSource({
            "2,van,2,false",
            "2,car,2,false",
            "2,van,16,true",
            "2,car,16,false",
            "14,van,2,true",
            "14,car,2,false"
    })
    public void checksIfUnderMaintenanceCorrectly(int days, String type, int numOfRentDays, boolean expected){
        DateTime lastMaintenance = new DateTime(12,2,2024);
        vehicleType = new VehicleType(5, lastMaintenance);
        boolean actualMaintenanceStatus = vehicleType.IsUnderMaintenance(new DateTime(lastMaintenance,days), type, numOfRentDays);
        assertEquals(expected, actualMaintenanceStatus);
    }

}
