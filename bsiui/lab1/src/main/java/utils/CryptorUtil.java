package utils;

/**
 * Utility class for crypting
 */
public class CryptorUtil {


    /**
     * from:  https://stackoverflow.com/questions/13187652/one-time-pad-encryption-and-decryption
     */
    public static String xorCrypt(String message, String secret) {
        final byte[] msg = message.getBytes();
        final byte key = secret.getBytes()[secret.getBytes().length-1];
        final byte[] encoded = xorWithByte(msg, key);
        return new String(encoded);
    }


    private static byte[] xorWithByte(byte[] message, byte key) {
        final byte[] decoded = new byte[message.length];
        // Decrypt
        for (int i = 0; i < message.length; i++) {
            decoded[i] = (byte) (message[i] ^ key);
        }
        return decoded;
    }

    /**
     * from: https://stackoverflow.com/questions/19108737/java-how-to-implement-a-shift-cipher-caesar-cipher
     */
    public static String cezarCrypt(String message, int shift) {
        if (shift < 0) {
            shift = 26 + shift;
        }
        String s = "";
        int len = message.length();
        for(int x = 0; x < len; x++){
            char y = (char)(message.charAt(x));
            if(!((y < 'z' && y > 'z' )|| (y <= 'Z' && y >= 'A'))){
                s += y;
                continue;
            }

            char c = (char)(message.charAt(x) + shift);
            if(c < 'z' && c > 'z' || c <= 'Z' && c >= 'A')
            if (c > 'z')
                s += (char)(message.charAt(x) - (26-shift));
            else
                s += (char)(message.charAt(x) + shift);
        }
        return s;
    }
}
