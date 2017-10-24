import org.json.simple.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket welcomeSocket = new ServerSocket(6789);

        while (true) {
            Socket socket = welcomeSocket.accept();
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            JSONObject request1 = (JSONObject) in.readObject();
            System.out.println("Received: " + request1.toJSONString());

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            JSONObject response1 = new JSONObject();
            response1.put("p", "123");
            response1.put("g", "123");
            out.writeObject(response1);

            JSONObject request2 = (JSONObject) in.readObject();
            System.out.println("Received: " + request2.toJSONString());

            JSONObject response2 = new JSONObject();
            response2.put("b", "123");
            out.writeObject(response2);

           JSONObject request3 = (JSONObject) in.readObject();
            System.out.println("Received: " + request3.toJSONString());
            //done


            JSONObject msg = (JSONObject) in.readObject();
            System.out.println("Received: " + msg.toJSONString());




        }
    }
}
