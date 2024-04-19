package org.example;

public class PrimeCheck {
    public boolean isPrime(Long a){
        if(a<2L || a>1000000000000L){
            throw new IllegalArgumentException("Argument must be within range!");
        }
        boolean isItPrime = true;
        if(a <= 1)
        {
            isItPrime = false;
            return isItPrime;
        }
        else
        {
            for (long i = 2L; i<= a/2; i++)
            {
                if ((a % i) == 0)
                {
                    isItPrime = false;
                    break;
                }
            }
            return isItPrime;
        }
    }
}
