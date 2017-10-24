import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerDhProtocol {
    private static final String P = "23";
    private static final String G = "5";
    static Connection setupConnection(ServerSocket welcomeSocket) throws IOException, ClassNotFoundException {
        Socket socket = welcomeSocket.accept();
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        JSONObject initFromClient = (JSONObject) in.readObject();
        if(!validInitRequest(initFromClient)) throw new InvalidObjectException("Invalid init");

        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        JSONObject pgToClient = new JSONObject();
        pgToClient.put("p", P);
        pgToClient.put("g", G);
        out.writeObject(pgToClient);

        JSONObject secretFromClient = (JSONObject) in.readObject();
        if(!validSecretRequest(secretFromClient)) throw new InvalidObjectException("Invalid secrect A");

        JSONObject secretToClient = new JSONObject();
        secretToClient.put("b", "123");
        out.writeObject(secretToClient);

        JSONObject encryptionFromClient = (JSONObject) in.readObject();
        if(!validEncryptionRequest(encryptionFromClient)) throw new InvalidObjectException("Invalid encryption");

        return new Connection(in, out, socket);
    }

    private static boolean validEncryptionRequest(JSONObject request) {
        return isEncryptionOmitted(request) || request.containsKey("encryption") && isSupportedEncryption(request);
    }

    private static boolean isEncryptionOmitted(JSONObject request) {
        return request.containsKey("msg");
    }

    private static boolean isSupportedEncryption(JSONObject request) {
        return request.get("encryption").equals("none")
                || request.get("encryption").equals("xor")
                || request.get("encryption").equals("cezar");
    }

    private static boolean validSecretRequest(JSONObject request) {
        return request.containsKey("a") && isInteger((String)request.get("a"));
    }

    private static boolean validInitRequest(JSONObject request) {
        return request.containsKey("request") && request.get("request").equals("keys");
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