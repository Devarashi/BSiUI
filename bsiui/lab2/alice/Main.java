public class Main {

    static String textToDecode = "PAADREshCgcRDgZPBAoPSxgGBBEOAR0ABksVEUUeDw4YCksoBlQWAg5PBgoRGBUNAxkEGBUGSx8KGkUfDgQHEUVLIgEWAgoDVAoFSw0NBksFBhEGBEsLGBARGBUNSUsKDQ1FBgQVGAwcDk8WHAcETwQXEQ4fBgocCgsOAAUCClQADQ4EABwcBQoTCksKGxUOHkVPOQoRDk8eABgRDA4ASx8dGwYDDk8EBA8PBhoCHksVEQcSSwEdAEsJFhgKSxEOVBEZHgsaCkVLIB9JSw0DFQIKSxsbRTkkNSMkJzQUNQkCCAo9FiIGHwYAGBgKEBg=";

    public static void main(String[] args) {
        byte[] asciiText = Base64Decoder.decode(textToDecode);
        byte[] out = new byte[asciiText.length];

    //    StringXORer.findAndPrintRozwalKeys(asciiText);
        byte[] kotek = "kotek".getBytes();
        for(int i=0; i<asciiText.length; i++) {
            out[i] = (byte) (asciiText[i] ^ kotek[i% kotek.length]);
        }

        System.out.println(new String(out));
    }
}
