package com.sdizo;

import sun.misc.BASE64Decoder;

import java.io.IOException;

public class StringXORer {

    public String decode(String s, char key) {
        return new String(xorWithKey(base64Decode(s), (byte) key));
    }

    private byte[] xorWithKey(byte[] a, byte key) {
        byte[] out = new byte[a.length];
        for (int i = 0; i < a.length; i++) {
            out[i] = (byte) (a[i] ^ key);
        }
        return out;
    }

    private byte[] base64Decode(String s) {
        try {
            BASE64Decoder d = new BASE64Decoder();
            return d.decodeBuffer(s);
        } catch (IOException e) {throw new RuntimeException(e);}
    }

}