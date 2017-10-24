import org.json.simple.JSONObject;

import java.io.*;
import java.net.Socket;
import org.apache.commons.codec.binary.Base64;

public class Client {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket = new Socket("localhost", 6789);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

        JSONObject req1 = new JSONObject();
        req1.put("request", "keys");
        out.writeObject(req1);

        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        JSONObject response1 = (JSONObject) in.readObject();
        System.out.println("Received: " + response1.toJSONString());

        JSONObject request2 = new JSONObject();
        request2.put("a", "123");
        out.writeObject(request2);

        JSONObject response2 = (JSONObject) in.readObject();
        System.out.println("Received: " + response2.toJSONString());

        JSONObject request3 = new JSONObject();
        request3.put("encryption", "none");
        out.writeObject(request3);

        JSONObject request4 = new JSONObject();
        request4.put("msg",  Base64.encodeBase64("witam".getBytes()));
        request4.put("from", "John");
        out.writeObject(request4);


        socket.close();
    }
}
