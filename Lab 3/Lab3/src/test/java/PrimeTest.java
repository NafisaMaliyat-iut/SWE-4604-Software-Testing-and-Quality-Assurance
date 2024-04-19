import org.example.PrimeCheck;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class PrimeTest {
    private static PrimeCheck primeCheck;

    @BeforeClass
    public static void SetUpPrimeCheck(){
        primeCheck = new PrimeCheck();
    }

    @Test
    public void NotPrimeNumberTest1(){
        assertFalse(primeCheck.isPrime(20L));
    }

    @Test
    public void NotPrimeNumberTest2(){
        assertFalse(primeCheck.isPrime(933L));
    }

    @Test
    public void NotPrimeNumberTest3(){
        assertFalse(primeCheck.isPrime(1000000000000L));
    }

    @Test
    public void PrimeNumberTest1(){
        assertTrue(primeCheck.isPrime(2L));
    }

    @Test
    public void PrimeNumberTest2(){
        assertTrue(primeCheck.isPrime(47L));
    }

    @Ignore
    @Test(expected = RuntimeException.class)
    public void FloatParameterTest(){
        primeCheck.isPrime((long)2.5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void SmallParameterTest(){
        primeCheck.isPrime(1L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void NegativeParameterTest(){
        primeCheck.isPrime(-14L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void LargeParameterTest(){
        primeCheck.isPrime(1000000000001L);
    }
}
