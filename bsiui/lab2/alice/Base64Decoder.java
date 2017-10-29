import sun.misc.BASE64Decoder;

import java.io.IOException;

public class Base64Decoder {

    public static byte[] decode(String text){
        try {
            BASE64Decoder d = new BASE64Decoder();
            return d.decodeBuffer(text);
        } catch (IOException e) {throw new RuntimeException(e);}
    }
}
