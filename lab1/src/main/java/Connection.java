import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection {
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket socket;
    private long secret;

    public Connection(ObjectInputStream in, ObjectOutputStream out, Socket socket, long secret) {
        System.out.println(secret);
        this.in = in;
        this.out = out;
        this.socket = socket;
        this.secret = secret;
    }

    public void sendMessage(String message) throws IOException {
        JSONObject request4 = new JSONObject();
        request4.put("msg",  Base64.encodeBase64(message.getBytes()));
        request4.put("from", "John");
        out.writeObject(request4);

    }


    public void endConnection() throws IOException {
        socket.close();
    }

    public void readMessage() throws IOException, ClassNotFoundException {
        JSONObject msg = (JSONObject) in.readObject();
        System.out.println(new String(Base64.decodeBase64((byte[])msg.get("msg"))));
    }
}
