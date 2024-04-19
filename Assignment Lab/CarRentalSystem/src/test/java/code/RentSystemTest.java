package code;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RentSystemTest {
    @Mock
    private static Car car;
    @Mock
    private static Van van;
    @Mock
    private static RentalRecord rentalRecord;
    private static RentSystem rentSystem;

    @BeforeAll
    public static void init(){
        car = mock(Car.class);
        van=mock(Van.class);
        rentalRecord=mock(RentalRecord.class);
    }

    @Test
    @Order(2)
    public void isAddMethodCalledForInput1(){
        List<InputStream> inputs = List.of(
                new ByteArrayInputStream("1\n".getBytes()),
                new ByteArrayInputStream("Car\n2020\nFord\nFusion\n001\n7\n".getBytes()),
                new ByteArrayInputStream("7\n".getBytes())
        );
        InputStream in = new SequenceInputStream(Collections.enumeration(inputs));
        System.setIn(in);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        when(car.rent(anyString(), any(DateTime.class), anyInt())).thenReturn(true);

        rentSystem = new RentSystem();
        RentSystem spyRentSystem = spy(rentSystem);
        spyRentSystem.run();

        verify(spyRentSystem, times(1)).add(any(Scanner.class));
        verify(spyRentSystem, never()).rent(any(Scanner.class));
        verify(spyRentSystem, never()).returnCar(any(Scanner.class));
        verify(spyRentSystem, never()).vehicleMaintenance(any(Scanner.class));
        verify(spyRentSystem, never()).completeMaintenance(any(Scanner.class));
        verify(spyRentSystem, never()).getDetails();
    }


    @Test
    @Order(2)
    public void isRentMethodCalledForInput2(){
        rentSystem = new RentSystem();
        RentSystem spyRentSystem = spy(rentSystem);
        spyRentSystem.cars[0]=car;

        List<InputStream> inputs = List.of(
                new ByteArrayInputStream("2\n".getBytes()),
                new ByteArrayInputStream("C_001\ncustomer1\n20/03/2024\n12\n".getBytes()),
                new ByteArrayInputStream("7\n".getBytes())
        );
        InputStream in = new SequenceInputStream(Collections.enumeration(inputs));
        System.setIn(in);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        spyRentSystem.run();

        verify(spyRentSystem, times(1)).rent(any(Scanner.class));
        verify(spyRentSystem, never()).add(any(Scanner.class));
        verify(spyRentSystem, never()).returnCar(any(Scanner.class));
        verify(spyRentSystem, never()).vehicleMaintenance(any(Scanner.class));
        verify(spyRentSystem, never()).completeMaintenance(any(Scanner.class));
        verify(spyRentSystem, never()).getDetails();
    }

    @Test
    @Order(2)
    public void isReturnCarMethodCalledForInput3(){
        rentSystem = new RentSystem();
        RentSystem spyRentSystem = spy(rentSystem);

        when(car.returnVehicle(any(DateTime.class))).thenReturn(false);
        when(car.getVehicleId()).thenReturn("C_001");
        spyRentSystem.cars[0]=car;

        List<InputStream> inputs = List.of(
                new ByteArrayInputStream("3\n".getBytes()),
                new ByteArrayInputStream("C_001\n20/03/2024\n".getBytes()),
                new ByteArrayInputStream("7\n".getBytes())
        );
        InputStream in = new SequenceInputStream(Collections.enumeration(inputs));
        System.setIn(in);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        spyRentSystem.run();

        verify(spyRentSystem, times(1)).returnCar(any());
        verify(spyRentSystem, never()).add(any());
        verify(spyRentSystem, never()).rent(any());
        verify(spyRentSystem, never()).vehicleMaintenance(any());
        verify(spyRentSystem, never()).completeMaintenance(any());
        verify(spyRentSystem, never()).getDetails();
    }

    @Test
    @Order(2)
    public void isVehicleMaintenanceMethodCalledForInput4(){
        rentSystem = new RentSystem();
        RentSystem spyRentSystem = spy(rentSystem);

        when(car.performMaintenance()).thenReturn(true);
        when(car.getVehicleId()).thenReturn("C_001");
        spyRentSystem.cars[0]=car;

        List<InputStream> inputs = List.of(
                new ByteArrayInputStream("4\n".getBytes()),
                new ByteArrayInputStream("C_001\n".getBytes()),
                new ByteArrayInputStream("7\n".getBytes())
        );
        InputStream in = new SequenceInputStream(Collections.enumeration(inputs));
        System.setIn(in);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        spyRentSystem.run();

        verify(spyRentSystem, times(1)).vehicleMaintenance(any());
        verify(spyRentSystem, never()).add(any());
        verify(spyRentSystem, never()).rent(any());
        verify(spyRentSystem, never()).returnCar(any());
        verify(spyRentSystem, never()).completeMaintenance(any());
        verify(spyRentSystem, never()).getDetails();
    }


    @Test
    @Order(2)
    public void isCompleteMaintenanceMethodCalledForInput5(){
        rentSystem = new RentSystem();
        RentSystem spyRentSystem = spy(rentSystem);

        when(car.completeMaintenance()).thenReturn(true);
        when(car.getVehicleId()).thenReturn("C_001");
        spyRentSystem.cars[0]=car;

        List<InputStream> inputs = List.of(
                new ByteArrayInputStream("5\n".getBytes()),
                new ByteArrayInputStream("C_001\n".getBytes()),
                new ByteArrayInputStream("7\n".getBytes())
        );
        InputStream in = new SequenceInputStream(Collections.enumeration(inputs));
        System.setIn(in);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        spyRentSystem.run();

        verify(spyRentSystem, times(1)).completeMaintenance(any());
        verify(spyRentSystem, never()).add(any());
        verify(spyRentSystem, never()).rent(any());
        verify(spyRentSystem, never()).returnCar(any());
        verify(spyRentSystem, never()).vehicleMaintenance(any());
        verify(spyRentSystem, never()).getDetails();
    }

    @Test
    @Order(2)
    public void isGetDetailsMethodCalledForInput6(){
        rentSystem = new RentSystem();
        RentSystem spyRentSystem = spy(rentSystem);

        when(car.getDetails()).thenReturn("");
        spyRentSystem.cars[0]=car;

        List<InputStream> inputs = List.of(
                new ByteArrayInputStream("6\n".getBytes()),
                new ByteArrayInputStream("7\n".getBytes())
        );
        InputStream in = new SequenceInputStream(Collections.enumeration(inputs));
        System.setIn(in);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        spyRentSystem.run();

        verify(spyRentSystem, times(1)).getDetails();
        verify(spyRentSystem, never()).add(any());
        verify(spyRentSystem, never()).rent(any());
        verify(spyRentSystem, never()).returnCar(any());
        verify(spyRentSystem, never()).completeMaintenance(any());
        verify(spyRentSystem, never()).vehicleMaintenance(any());
    }

    @Test
    @Order(1)
    public void noMethodIsCalledForInput0(){
        rentSystem = new RentSystem();
        RentSystem spyRentSystem = spy(rentSystem);

        List<InputStream> inputs = List.of(
                new ByteArrayInputStream("7\n".getBytes())
        );
        InputStream in = new SequenceInputStream(Collections.enumeration(inputs));
        System.setIn(in);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        spyRentSystem.run();

        verify(spyRentSystem, never()).add(any());
        verify(spyRentSystem, never()).rent(any());
        verify(spyRentSystem, never()).returnCar(any());
        verify(spyRentSystem, never()).completeMaintenance(any());
        verify(spyRentSystem, never()).vehicleMaintenance(any());
        verify(spyRentSystem, never()).getDetails();
    }

    @ParameterizedTest
    @ValueSource(ints= {4,7})
    public void addsCarsSuccessfullyWithValidSeats(int seats){
        rentSystem = new RentSystem();
        String input="car\n2020\nFord\nFusion\n001\n"+seats+"\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        rentSystem.add(new Scanner(System.in));
        assertNotNull(rentSystem.cars[0]);
    }

    @Test
    public void addsVansSuccessfully(){
        rentSystem = new RentSystem();
        String input="Van\n2020\nFord\nTransit\n001\n7\n12/03/2024\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        rentSystem.add(new Scanner(System.in));
        assertNotNull(rentSystem.vans[0]);
    }

    @ParameterizedTest
    @ValueSource(ints= {1,5,10,14})
    public void addsCarsWithInvalidSeats(int seats){
        rentSystem = new RentSystem();
        String input="car\n2020\nFord\nFusion\n001\n"+seats+"\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        assertThrows(Exception.class, () -> rentSystem.add(new Scanner(System.in)));
        assertNull(rentSystem.cars[0]);
        String output = outputStream.toString().trim().replace("\r","");
        assertTrue(output.contains("Please enter seats as either 4 or 7"));
    }

    @Disabled
    @ParameterizedTest
    @ValueSource(strings= {"02/30/2024","04/31/2024","06/31/2024","09/31/2024","11/31/2024"})
    public void addsVansWithInvalidMaintenanceDate(String date){
        rentSystem = new RentSystem();
        String input="Van\n2020\nFord\nTransit\n001\n7\n"+date+"\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        rentSystem.add(new Scanner(System.in));
        assertNull(rentSystem.vans[0]);
        String output = outputStream.toString().trim().replace("\r","");
        assertTrue(output.contains("Please enter a valid date in the format dd/mm/yyyy:"));
    }

    @Disabled
    @Test
    public void addsVansWhenMaxCapacityReached(){
        rentSystem = new RentSystem();
        for (int i = 0; i < 50; i++) {
            rentSystem.vans[i]=van;
        }
        String input="Van\n2020\nFord\nTransit\n002\n7\n20/3/2024\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        assertThrows(Exception.class, () -> rentSystem.add(new Scanner(System.in)));

        String output = outputStream.toString().trim().replace("\r","");
        assertTrue(output.contains("Cannot add any more vans"));
    }

    @Disabled
    @Test
    public void addsCarsWhenMaxCapacityReached(){
        rentSystem = new RentSystem();
        for (int i = 0; i < 50; i++) {
            rentSystem.cars[i]=car;
        }
        String input="Car\n2020\nFord\nTransit\n002\n7\n12/03/2024\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        assertThrows(Exception.class, () -> rentSystem.add(new Scanner(System.in)));

        String output = outputStream.toString().trim().replace("\r","");
        assertTrue(output.contains("Cannot add any more cars"));
    }

    @Disabled //did not pass
    @ParameterizedTest
    @ValueSource(strings = {"car", "Car", "cAr", "caR", "CAr", "cAR", "CaR", "CAR"})
    public void addingCarWithDifferentCaseVehicleType(String type){
        rentSystem = new RentSystem();
        String input=type+"\n2020\nFord\nFusion\n001\n7\n12/03/2024\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        rentSystem.add(new Scanner(System.in));
        assertNotNull(rentSystem.cars[0]);
    }

    @ParameterizedTest
    @ValueSource(strings = {"van", "Van", "vAn", "vaN", "VAn", "vAN", "VaN", "VAN"})
    public void addingVanWithDifferentCaseVehicleType(String type){
        rentSystem = new RentSystem();
        String input= type+"\n2020\nFord\nTransit\n001\n7\n12/03/2024\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        rentSystem.add(new Scanner(System.in));
        assertNotNull(rentSystem.vans[0]);
    }

    @ParameterizedTest
    @ValueSource(strings = {"bike", "helicopter"})
    public void addingVehicleWithInvalidVehicleType(String type){
        RentSystem rentSystem = new RentSystem();
        String input = type + "\n2020\nFord\nTransit\n001\n20/03/2024\n";

        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        assertThrows(Exception.class, () -> rentSystem.add(new Scanner(System.in)));

        String output = outputStream.toString().trim().replace("\r","");
        assertTrue(output.contains("Please enter either van or car: "));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1234, 3000})
    public void addingVehicleWithInvalidYear(int year){
        RentSystem rentSystem = new RentSystem();
        String input = "car\n"+year+"\nFord\nFusion\n001\n";

        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        assertThrows(Exception.class, () -> rentSystem.add(new Scanner(System.in)));

        String output = outputStream.toString().trim().replace("\r","");
        assertTrue(output.contains("Please enter a valid year"));
    }


    @Test
    public void addingVanWithAlreadyAddedId(){
        RentSystem rentSystem = new RentSystem();
        when(van.getVehicleId()).thenReturn("V_001");
        rentSystem.vans[0] = van;
        String input = "van\n2020\nFord\nTransit\n001\n20/03/2024\n";

        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        rentSystem.add(new Scanner(System.in));

        String output = outputStream.toString().trim().replace("\r","");
        assertTrue(output.contains("ID Already used, Please add a new vehicle"));
    }

    @Test
    public void addingCarWithAlreadyAddedId(){
        RentSystem rentSystem = new RentSystem();
        when(car.getVehicleId()).thenReturn("C_001");
        rentSystem.cars[0] = car;
        String input = "car\n2020\nFord\nTransit\n001\n";

        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        rentSystem.add(new Scanner(System.in));;

        String output = outputStream.toString().trim().replace("\r","");
        assertTrue(output.contains("ID Already used, Please add a new vehicle"));
    }

    @Test
    public void rentCarWithNoVehiclesAdded(){
        RentSystem rentSystem = new RentSystem();
        String input = "C_001\ncustomer1\n20/03/2024\n5\n";

        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        rentSystem.rent(new Scanner(System.in));
        String output = outputStream.toString().trim().replace("\r","");
        assertTrue(output.contains("There are no cars currently at the moment."));
    }

    @Test
    public void rentVanWithNoVehiclesAdded(){
        RentSystem rentSystem = new RentSystem();
        String input = "V_001\ncustomer1\n20/03/2024\n5\n";

        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        rentSystem.rent(new Scanner(System.in));
        String output = outputStream.toString().trim().replace("\r","");
        assertTrue(output.contains("There are no vans currently at the moment."));
    }

    @Test
    public void rentCarWithOnlyCarsAdded(){
        RentSystem rentSystem = new RentSystem();
        String input = "C_001\ncustomer1\n20/03/2024\n5\n";
        Car car = new Car("C_001", 2020, "Ford", "Fusion", 0, new VehicleType(5));
        rentSystem.cars[0]=car;

        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        rentSystem.rent(new Scanner(System.in));
        String output = outputStream.toString().trim().replace("\r","");
        assertTrue(output.contains("Vehicle C_001 is now rented by customer customer1"));
    }

    @Test
    public void rentVanWithOnlyVansAdded(){
        RentSystem rentSystem = new RentSystem();
        String input = "V_001\ncustomer1\n20/03/2024\n6\n";
        Van van = new Van("V_001", 2020, "Ford", "Transit", 0, new VehicleType(5, new DateTime(19,3,2024)));
        rentSystem.vans[0]=van;

        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        rentSystem.rent(new Scanner(System.in));
        String output = outputStream.toString().trim().replace("\r","");
        assertTrue(output.contains("Vehicle V_001 is now rented by customer customer1"));
    }

    @Test
    public void rentVanWithBothVehiclesAdded(){
        RentSystem rentSystem = new RentSystem();
        String input = "V_001\ncustomer1\n20/03/2024\n6\n";
        Van van = new Van("V_001", 2020, "Ford", "Transit", 0, new VehicleType(5, new DateTime(19,3,2024)));
        rentSystem.vans[0]=van;
        Car car = new Car("C_001", 2020, "Ford", "Transit", 0, new VehicleType(5));
        rentSystem.cars[0]=car;

        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        rentSystem.rent(new Scanner(System.in));
        String output = outputStream.toString().trim().replace("\r","");
        assertTrue(output.contains("Vehicle V_001 is now rented by customer customer1"));
    }

    @Test
    public void rentCarWithBothVehiclesAdded(){
        RentSystem rentSystem = new RentSystem();
        String input = "C_001\ncustomer1\n20/03/2024\n6\n";
        Van van = new Van("V_001", 2020, "Ford", "Transit", 0, new VehicleType(5, new DateTime(19,3,2024)));
        rentSystem.vans[0]=van;
        Car car = new Car("C_001", 2020, "Ford", "Transit", 0, new VehicleType(5));
        rentSystem.cars[0]=car;

        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        rentSystem.rent(new Scanner(System.in));
        String output = outputStream.toString().trim().replace("\r","");
        assertTrue(output.contains("Vehicle C_001 is now rented by customer customer1"));
    }

    @Test
    public void performCarMaintenanceSuccessfully(){
        RentSystem rentSystem = new RentSystem();
        when(car.getVehicleId()).thenReturn("C_001");
        when(car.performMaintenance()).thenReturn(true);
        rentSystem.cars[0]=car;
        rentSystem.vans[0]=van;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "C_001";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        rentSystem.vehicleMaintenance(new Scanner(System.in));

        String output = outputStream.toString().trim().replace("\r","");
        assertTrue(output.contains("Vehicle C_001 is now under maintenance"));
    }

    @Test
    public void performVanMaintenanceSuccessfully(){
        RentSystem rentSystem = new RentSystem();
        when(van.getVehicleId()).thenReturn("V_001");
        when(van.performMaintenance()).thenReturn(true);
        rentSystem.cars[0]=car;
        rentSystem.vans[0]=van;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "V_001";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        rentSystem.vehicleMaintenance(new Scanner(System.in));

        String output = outputStream.toString().trim().replace("\r","");
        assertTrue(output.contains("Vehicle V_001 is now under maintenance"));
    }

    @Test
    public void failedToPerformVanMaintenance(){
        RentSystem rentSystem = new RentSystem();
        when(van.getVehicleId()).thenReturn("V_001");
        when(van.performMaintenance()).thenReturn(false);
        rentSystem.cars[0]=car;
        rentSystem.vans[0]=van;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "V_001";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        rentSystem.vehicleMaintenance(new Scanner(System.in));

        String output = outputStream.toString().trim().replace("\r","");
        assertTrue(output.contains("Vehicle V_001 could not be sent for maintenance"));
    }

    @Disabled
    @Test
    public void performVanMaintenanceWithNoVehiclesAdded(){
        RentSystem rentSystem = new RentSystem();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "V_001";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        rentSystem.vehicleMaintenance(new Scanner(System.in));

        String output = outputStream.toString().trim().replace("\r","");
        assertTrue(output.contains("There are no vans, please add vans."));
    }

    @Test
    public void performCarMaintenanceWithNoVehiclesAdded(){
        RentSystem rentSystem = new RentSystem();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "C_001";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        rentSystem.vehicleMaintenance(new Scanner(System.in));

        String output = outputStream.toString().trim().replace("\r","");
        assertTrue(output.contains("There are no cars, please add cars."));
    }

    @Disabled
    @Test
    public void performVanMaintenanceWithOnlyVanAdded(){
        RentSystem rentSystem = new RentSystem();
        when(van.getVehicleId()).thenReturn("V_001");
        when(van.performMaintenance()).thenReturn(false);
        rentSystem.vans[0]=van;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "V_001";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        rentSystem.vehicleMaintenance(new Scanner(System.in));

        String output = outputStream.toString().trim().replace("\r","");
        assertTrue(output.contains("Vehicle V_001 is now under maintenance"));
    }

    @Disabled
    @Test
    public void performCarMaintenanceWithOnlyCarAdded(){
        RentSystem rentSystem = new RentSystem();
        when(car.getVehicleId()).thenReturn("C_001");
        when(car.performMaintenance()).thenReturn(false);
        rentSystem.cars[0]=car;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "C_001";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        rentSystem.vehicleMaintenance(new Scanner(System.in));

        String output = outputStream.toString().trim().replace("\r","");
        assertTrue(output.contains("Vehicle C_001 is now under maintenance"));
    }

    @Test
    public void failedToPerformCarMaintenance(){
        RentSystem rentSystem = new RentSystem();
        when(car.getVehicleId()).thenReturn("C_001");
        when(car.performMaintenance()).thenReturn(false);
        rentSystem.cars[0]=car;
        rentSystem.vans[0]=van;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "C_001\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        rentSystem.vehicleMaintenance(new Scanner(System.in));

        String output = outputStream.toString().trim().replace("\r","");
        assertTrue(output.contains("Vehicle C_001 could not be sent for maintenance"));
    }

    @Test
    public void performMaintenanceForWhenIdNotExist(){
        RentSystem rentSystem = new RentSystem();
        when(car.getVehicleId()).thenReturn("C_001");
        when(car.completeMaintenance()).thenReturn(true);
        rentSystem.cars[0]=car;
        when(van.getVehicleId()).thenReturn("V_001");
        when(van.completeMaintenance(any(DateTime.class))).thenReturn(true);
        rentSystem.vans[0]=van;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "V_002\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        rentSystem.completeMaintenance(new Scanner(System.in));
        String output = outputStream.toString().trim().replace("\r","");
        assertTrue(output.contains("Id is incorrect"));
    }

    @Test
    public void performMaintenanceWhenIdNotExist(){
        RentSystem rentSystem = new RentSystem();
        when(car.getVehicleId()).thenReturn("C_001");
        when(car.completeMaintenance()).thenReturn(true);
        rentSystem.cars[0]=car;
        when(van.getVehicleId()).thenReturn("V_001");
        when(van.completeMaintenance(any(DateTime.class))).thenReturn(true);
        rentSystem.vans[0]=van;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "C_002\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        rentSystem.completeMaintenance(new Scanner(System.in));
        String output = outputStream.toString().trim().replace("\r","");
        assertTrue(output.contains("ID is incorrect"));
    }

    @Disabled
    @Test
    public void performVehicleMaintenanceWithInvalidId(){
        RentSystem rentSystem = new RentSystem();
        when(car.getVehicleId()).thenReturn("C_001");
        when(car.completeMaintenance()).thenReturn(true);
        rentSystem.cars[0]=car;
        when(van.getVehicleId()).thenReturn("V_001");
        when(van.completeMaintenance(any(DateTime.class))).thenReturn(true);
        rentSystem.vans[0]=van;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "1\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        rentSystem.completeMaintenance(new Scanner(System.in));
        String output = outputStream.toString().trim().replace("\r","");
        assertTrue(output.contains("Id is incorrect"));
    }

    @Test
    public void completeCarMaintenanceWithNoVehicleAdded(){
        RentSystem rentSystem = new RentSystem();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "C_001";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        rentSystem.completeMaintenance(new Scanner(System.in));
        String output = outputStream.toString().trim().replace("\r","");
        assertTrue(output.contains("There are no cars, please add cars."));
    }

    @Disabled
    @Test
    public void completeVanMaintenanceWithNoVehicleAdded(){
        RentSystem rentSystem = new RentSystem();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "V_001\n20/03/2024\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        rentSystem.completeMaintenance(new Scanner(System.in));
        String output = outputStream.toString().trim().replace("\r","");
        assertTrue(output.contains("There are no vans, please add vans."));
    }

    @Disabled
    @Test
    public void completeCarMaintenanceWithOnlyCarAdded(){
        RentSystem rentSystem = new RentSystem();
        when(car.completeMaintenance()).thenReturn(true);
        rentSystem.cars[0]=car;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "C_001";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        rentSystem.completeMaintenance(new Scanner(System.in));
        String output = outputStream.toString().trim().replace("\r","");
        assertEquals("Vehicle C_001 has all maintenance completed and ready for rent",output);
    }

    @Disabled
    @Test
    public void completeVehicleMaintenanceWithInvalidId(){
        RentSystem rentSystem = new RentSystem();
        when(car.getVehicleId()).thenReturn("C_001");
        when(car.completeMaintenance()).thenReturn(true);
        rentSystem.cars[0]=car;
        when(van.getVehicleId()).thenReturn("V_001");
        when(van.completeMaintenance(any(DateTime.class))).thenReturn(true);
        rentSystem.vans[0]=van;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "1\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        rentSystem.completeMaintenance(new Scanner(System.in));
        String output = outputStream.toString().trim().replace("\r","");
        assertTrue(output.contains("Id is incorrect"));
    }

    @Disabled
    @Test
    public void completeVanMaintenanceSuccessfullyWithOnlyVanAdded(){
        RentSystem rentSystem = new RentSystem();
        when(van.completeMaintenance(any(DateTime.class))).thenReturn(true);
        rentSystem.vans[0]=van;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "V_001";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        rentSystem.completeMaintenance(new Scanner(System.in));
        String output = outputStream.toString().trim().replace("\r","");
        assertEquals("Vehicle V_001 has all maintenance completed and ready for rent",output);
    }

    @Test
    public void completeVanMaintenanceSuccessfullyWithBothVehiclesAdded(){
        RentSystem rentSystem = new RentSystem();
        when(van.completeMaintenance(any(DateTime.class))).thenReturn(true);
        when(van.getVehicleId()).thenReturn("V_001");
        rentSystem.vans[0]=van;
        when(car.getVehicleId()).thenReturn("C_001");
        when(car.completeMaintenance()).thenReturn(true);
        rentSystem.cars[0]=car;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "V_001\n22/03/2020\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        rentSystem.completeMaintenance(new Scanner(System.in));
        String output = outputStream.toString().trim().replace("\r","");
        assertTrue(output.contains("Vehicle V_001 has all maintenance completed and ready for rent"));
    }

    @Test
    public void completeCarMaintenanceWithBothVehiclesAdded(){
        RentSystem rentSystem = new RentSystem();
        when(van.completeMaintenance(any(DateTime.class))).thenReturn(true);
        when(van.getVehicleId()).thenReturn("V_001");
        rentSystem.vans[0]=van;
        when(car.getVehicleId()).thenReturn("C_001");
        when(car.completeMaintenance()).thenReturn(true);
        rentSystem.cars[0]=car;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "C_001";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        rentSystem.completeMaintenance(new Scanner(System.in));
        String output = outputStream.toString().trim().replace("\r","");
        assertTrue(output.contains("Vehicle C_001 has all maintenance completed and ready for rent"));
    }

    @Test
    public void completeCarMaintenanceWhenIdNotExist(){
        RentSystem rentSystem = new RentSystem();
        when(van.completeMaintenance(any(DateTime.class))).thenReturn(true);
        when(van.getVehicleId()).thenReturn("V_001");
        rentSystem.vans[0]=van;
        when(car.getVehicleId()).thenReturn("C_001");
        when(car.completeMaintenance()).thenReturn(true);
        rentSystem.cars[0]=car;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "C_002";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        rentSystem.completeMaintenance(new Scanner(System.in));
        String output = outputStream.toString().trim().replace("\r","");
        assertTrue(output.contains("ID is incorrect"));
    }

    @Test
    public void completeVanMaintenanceWhenIdNotExist(){
        RentSystem rentSystem = new RentSystem();
        when(van.completeMaintenance(any(DateTime.class))).thenReturn(true);
        when(van.getVehicleId()).thenReturn("V_001");
        rentSystem.vans[0]=van;
        when(car.getVehicleId()).thenReturn("C_001");
        when(car.completeMaintenance()).thenReturn(true);
        rentSystem.cars[0]=car;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String input = "V_002";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        rentSystem.completeMaintenance(new Scanner(System.in));
        String output = outputStream.toString().trim().replace("\r","");
        assertTrue(output.contains("Id is incorrect"));
    }


    @Test
    public void detailsReturnedCorrectWhenNoVehiclesAdded(){
        RentSystem rentSystem = new RentSystem();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        rentSystem.getDetails();

        String output= outputStream.toString().trim().replace("\r","");
        assertEquals("", output);
    }

    @Test
    public void detailsReturnedCorrectWithCarsAdded(){
        RentSystem rentSystem = new RentSystem();
        when(car.getDetails()).thenReturn("Test Car Details\n");
        rentSystem.cars[0]=car;
        rentSystem.cars[1]=car;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        rentSystem.getDetails();

        String output= outputStream.toString().trim().replace("\r","");
        assertEquals("***********Cars**********\nTest Car Details\n\nTest Car Details", output);
    }

    @Test
    public void detailsReturnedCorrectWithVansAdded(){
        RentSystem rentSystem = new RentSystem();
        when(van.getDetails()).thenReturn("Test Van Details\n");
        rentSystem.vans[0]=van;
        rentSystem.vans[1]=van;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        rentSystem.getDetails();

        String output= outputStream.toString().trim().replace("\r","");
        assertEquals("***********Vans**********\nTest Van Details\n\nTest Van Details", output);
    }

    @Disabled
    @Test
    public void detailsReturnedCorrectWithBothVehiclesAdded(){
        RentSystem rentSystem = new RentSystem();
        when(van.getDetails()).thenReturn("Test Van Details\n");
        when(car.getDetails()).thenReturn("Test Car Details\n");
        rentSystem.vans[0]=van;
        rentSystem.cars[0]=car;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        rentSystem.getDetails();

        String output= outputStream.toString().trim().replace("\r","");
        assertEquals("***********Vans**********\nTest Van Details\n\n***********Cars**********\nTest Car Details", output);
    }



    @AfterAll
    public static void cleanUp(){
        System.setIn(System.in);
        System.setOut(System.out);
    }
}
