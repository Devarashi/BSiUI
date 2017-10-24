import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientDhProtocol {
    static Connection setupConnection(String host, int port, String encryption) throws IOException, ClassNotFoundException {
        Socket socket = new Socket(host, port);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

        JSONObject initToServer = new JSONObject();
        initToServer.put("request", "keys");
        out.writeObject(initToServer);

        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        JSONObject pgFromServer = (JSONObject) in.readObject();
        if(!validPGRequest(pgFromServer)) throw new InvalidObjectException("Invalid P and G");

        JSONObject secretToServer = new JSONObject();
        secretToServer.put("a", "123");
        out.writeObject(secretToServer);

        JSONObject secretFromServer = (JSONObject) in.readObject();
        if(!validSecretRequest(secretFromServer)) throw new InvalidObjectException("Invalid secrect B");

        JSONObject encryptionToServer = new JSONObject();
        encryptionToServer.put("encryption", "none");
        out.writeObject(encryptionToServer);

        return new Connection(in, out, socket);
    }

    private static boolean validPGRequest(JSONObject request) {
        return request.containsKey("p") && isInteger((String) request.get("p"))
                && request.containsKey("g") &&  isInteger((String) request.get("g"));
    }

    private static boolean validSecretRequest(JSONObject request) {
        return request.containsKey("b") && isInteger((String)request.get("b"));
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
}