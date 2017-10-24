import java.math.BigInteger;
import java.util.Random;

public class MathUtils {

    public static String calculateSecret(long s, long p, long g){
        BigInteger s1 = BigInteger.valueOf(s);
        BigInteger p1 = BigInteger.valueOf(p);
        BigInteger g1 = BigInteger.valueOf(g);
        return g1.modPow(s1, p1).toString();
    }

    public static long generateRandomNumber(){
        return new Random().nextInt(50) + 3;
    }

    //from: https://stackoverflow.com/questions/24006143/generating-a-random-prime-number-in-java
    public static long generateRandomPrimeNumber(){
        int num = 0;
        Random rand = new Random();
        do {
            num = rand.nextInt(1000) + 1;
        } while (!isPrime(num));
        return num;
    }

    private static boolean isPrime(int inputNum){
        if (inputNum <= 3 || inputNum % 2 == 0)
            return inputNum == 2 || inputNum == 3; //this returns false if number is <=1 & true if number = 2 or 3
        int divisor = 3;
        while ((divisor <= Math.sqrt(inputNum)) && (inputNum % divisor != 0))
            divisor += 2; //iterates through all possible divisors
        return inputNum % divisor != 0; //returns true/false
    }
}
