package connection;

import utils.CryptorUtil;

/**
 * Supports encryption and decryption operations, applies specific algorithm depending on set strategy
 */
public class CryptionStrategy {
    public static final int CEZAR_SHIFT = 6;
    private String encryption;
    private String secret;

    /**
     *
     * @param strategy strategy to use while crypting
     * @param secret secret key used to crypt
     */
    public CryptionStrategy(String strategy, String secret) {
        this.encryption = strategy;
        this.secret = secret;
    }


    /**
     * Encrypts message using algorithm specified in constructor
     * @param message
     */
    public String encrypt(String message) {
        if (encryption == null || encryption.equals("none")) {
            return message;
        }
        if (encryption.equals("xor")) {
            return CryptorUtil.xorCrypt(message, String.valueOf(secret));
        }
        if (encryption.equals("cezar")) {
            return CryptorUtil.cezarCrypt(message, CEZAR_SHIFT);
        }
        return null;
    }

    /**
     * Encrypts message using algorithm specified in constructor
     */
    public String decrypt(String message) {
        if (encryption == null || encryption.equals("none")) {
            return message;
        }
        if (encryption.equals("xor")) {
            return CryptorUtil.xorCrypt(message, String.valueOf(secret));
        }
        if (encryption.equals("cezar")) {
            return CryptorUtil.cezarCrypt(message, -CEZAR_SHIFT);
        }
        return null;
    }
}
