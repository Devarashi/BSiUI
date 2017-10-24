import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerDhProtocol {
    private static final long G = 5;

    static Connection setupConnection(ServerSocket welcomeSocket) throws IOException, ClassNotFoundException {
        long p = MathUtils.generateRandomNumber();
        long secretB = MathUtils.generateRandomNumber();

        Socket socket = welcomeSocket.accept();
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        JSONObject initFromClient = (JSONObject) in.readObject();
        if(!validInitRequest(initFromClient)) throw new InvalidObjectException("Invalid init");

        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        JSONObject pgToClient = new JSONObject();
        pgToClient.put("p", String.valueOf(p));
        pgToClient.put("g", String.valueOf(G));
        out.writeObject(pgToClient);

        JSONObject secretFromClient = (JSONObject) in.readObject();
        if(!validSecretRequest(secretFromClient)) throw new InvalidObjectException("Invalid secrect A");
        long a = extractLong(secretFromClient, "a");

        JSONObject secretToClient = new JSONObject();
        secretToClient.put("b", MathUtils.calculateSecret(secretB, p, G));
        out.writeObject(secretToClient);

        JSONObject encryptionFromClient = (JSONObject) in.readObject();
        if(!validEncryptionRequest(encryptionFromClient)) throw new InvalidObjectException("Invalid encryption");

        String secret = MathUtils.calculateSecret(secretB, p, a);
        return new Connection(in, out, socket, Long.valueOf(secret));
    }

    private static long extractLong(JSONObject pgFromServer, String key) {
        return Long.valueOf((String) pgFromServer.get(key));
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
        return request.containsKey("a") && isLong((String)request.get("a"));
    }

    private static boolean validInitRequest(JSONObject request) {
        return request.containsKey("request") && request.get("request").equals("keys");
    }

    public static boolean isLong(String s) {
        try {
            Long.parseLong(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
}