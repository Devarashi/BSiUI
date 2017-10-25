package connection;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;
import utils.CryptorUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection {
    public static final int CEZAR_SHIFT = 6;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket socket;
    private long secret;
    private String encryption;

    public Connection(ObjectInputStream in, ObjectOutputStream out, Socket socket, long secret, String encryption) {
        System.out.println(secret);
        this.in = in;
        this.out = out;
        this.socket = socket;
        this.secret = secret;
        this.encryption = encryption;
    }

    public void sendMessage(String message) throws IOException {
        JSONObject request4 = new JSONObject();
        request4.put("msg", encode(encrypt(message)));
        request4.put("from", "John");
        out.writeObject(request4);

    }

    private String encrypt(String message) {
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

    private byte[] encode(String message) {
        return Base64.encodeBase64(message.getBytes());
    }


    public void endConnection() throws IOException {
        socket.close();
    }

    public void readMessage() throws IOException, ClassNotFoundException {
        JSONObject msg = (JSONObject) in.readObject();
        System.out.println(decrypt(new String(decode(msg))));
    }

    private String decrypt(String message) {
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

    private byte[] decode(JSONObject msg) {
        return Base64.decodeBase64((byte[])msg.get("msg"));
    }

}
