import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import demo.PrimeNumberChecker;

public class PrimeNumberChekerTestJUNIT5 {
    private static PrimeNumberChecker primeNumberChecker;
    @BeforeAll
    public static void initialize() {
        primeNumberChecker = new PrimeNumberChecker();
    }
    @ParameterizedTest
//	@ValueSource(ints= {3,17,19,43})
	@MethodSource("testPrimeNumberCheckerArgs")
//	@CsvFileSource(files = "src/test/resources/csv-file-source.csv", numLinesToSkip = 1)
//    @CsvSource({ "2, true", "6, false"})
    public void testPrimeNumberChecker(int inputNumber, Boolean expectedResult) {
        System.out.println("Parameterized Number is : " + inputNumber);
//	      assertTrue(primeNumberChecker.validate(inputNumber));
        assertEquals(expectedResult,
                primeNumberChecker.validate(inputNumber));
    }
    static Stream<Arguments> testPrimeNumberCheckerArgs(){
        return  Stream.of(Arguments.of( 2, true),
                Arguments.of(6, false ),
                Arguments.of(19, true),
                Arguments.of(22, false ),
                Arguments.of(23, true ));
    }

}