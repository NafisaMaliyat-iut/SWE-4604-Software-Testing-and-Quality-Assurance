package code;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class StartTest {
    @Test
    public void testMainMethod() {
        String input = "7\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Start.main(new String[]{});

        System.setIn(System.in);
        System.setOut(System.out);

        String output = outputStream.toString().trim().replace("\r","");
        String expectedOutput =
                "**** RENT SYSTEM MENU ****\n" +
                "\n" +
                "Add vehicle:            1\n" +
                "Rent vehicle:           2\n" +
                "Return vehicle:         3\n" +
                "Vehicle Maintenance:    4\n" +
                "Complete Maintenance:   5\n" +
                "Display All Vehicles:   6\n" +
                "Exit Program:           7\n" +
                "Enter your choice:";

        assertEquals(expectedOutput, output);
    }
}
