public class StringXORer {


    // loop - find R
        // if R, if next is 0, next Z, next W, A, L, _, {,
            // print position and key
    private static void xorWithKey(byte[] a, byte rKey) {
        for (int i = 0; i < a.length-10; i++) {
            if(isSpecificLetter(a[i], rKey, 'R')){
                char oKey = (char) findKeyForLetter(a[i + 1], 'O');
                if (oKey != -1) {
                    char zKey = (char) findKeyForLetter(a[i + 2], 'Z');
                    if (zKey != -1) {
                        char wKey = (char) findKeyForLetter(a[i + 3], 'W');
                        if (wKey != -1) {
                            char aKey = (char) findKeyForLetter(a[i + 4], 'A');
                            if (aKey != -1) {
                                char lKey = (char) findKeyForLetter(a[i + 5], 'L');
                                if (lKey != -1) {
                                    char underscoreKey = (char) findKeyForLetter(a[i + 6], '_');
                                    if (underscoreKey != -1) {
                                        char openbraceKey = (char) findKeyForLetter(a[i + 7], '{');
                                        if (openbraceKey != -1) {
                                            System.out.print("Rozwal starts at position: " + i);
                                            System.out.println(", Keys: " + (char) rKey + oKey + zKey + wKey +aKey + lKey + underscoreKey + openbraceKey);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static int findKeyForLetter(byte crypted, char toFind) {
        for (int k=32; k<128; k++) {
            if(isKey(crypted, (byte) toFind, (byte) (char) k)){
                if (isReasonableAsciiCharacter(k)) {
                    return k;
                }
            }
        }
        return -1;
    }

    private static boolean isReasonableAsciiCharacter(int k) {
        return k >= 32 && k <128;
    }

    private static boolean isKey(byte crypted, byte toFind, byte c) {
        return (crypted ^ c) == toFind;
    }


    private static boolean isSpecificLetter(byte b, byte key, char s) {
        return (b ^ key) == (byte) s;
    }


    public static void findAndPrintRozwalKeys(byte[] text) {
        for (int c=32; c<128; c++) {
            xorWithKey(text, (byte) (char) c);
        }
    }
}