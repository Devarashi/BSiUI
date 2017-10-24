import java.math.BigInteger;

public class ProtocolMathUtils {

    public static String calculateSecret(long s, long p, long g){
        BigInteger s1 = BigInteger.valueOf(s);
        BigInteger p1 = BigInteger.valueOf(p);
        BigInteger g1 = BigInteger.valueOf(g);
        return g1.modPow(s1, p1).toString();
    }
}
