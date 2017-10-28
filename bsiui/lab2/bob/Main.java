package com.sdizo;

public class Main {

    static String textToDecode = "GCg7Ozs7Oy01e3oNMz4gP3ogP3ovPjs2NXoZM3opMz96KDUgKSAjPCg1LTs5ei4/MSkueiA7KSAjPCg1LTs0I3oqNTA/PiM0OSAjN3o4OzAuPzd0ehQ1ej41OCg7dno8Njs9O3ouNWB6CBUADRsWBSEJMzQ9Nj8CNSgYIy4/GTMqMj8oJw==";
    public static void main(String[] args) {
        for (int c=32; c<128; c++) {
            String res = new StringXORer().decode(textToDecode, (char) c);
            if (res.contains("ROZWAL")) {
                System.out.println("Rozwiązanie:  " + c);
                System.out.println(res);
                break;
            }
        }
    }
}
