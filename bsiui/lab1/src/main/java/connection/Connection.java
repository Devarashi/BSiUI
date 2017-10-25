package connection;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 */
public class Connection {
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket socket;
    private CryptionStrategy cryptionStrategy;

    public Connection(ObjectInputStream in, ObjectOutputStream out, Socket socket, CryptionStrategy cryptionStrategy) {
        this.in = in;
        this.out = out;
        this.socket = socket;
        this.cryptionStrategy = cryptionStrategy;
    }

    public void sendMessage(String message) throws IOException {
        JSONObject request4 = new JSONObject();
        request4.put("msg", encode(cryptionStrategy.encrypt(message)));
        request4.put("from", "John");
        out.writeObject(request4);
    }


    public void readMessage() throws IOException, ClassNotFoundException {
        JSONObject msg = (JSONObject) in.readObject();
        System.out.println(cryptionStrategy.decrypt(new String(decode(msg))));
    }

    public void endConnection() throws IOException {
        socket.close();
    }

    private byte[] encode(String message) {
        return Base64.encodeBase64(message.getBytes());
    }

    private byte[] decode(JSONObject msg) {
        return Base64.decodeBase64((byte[])msg.get("msg"));
    }

}
