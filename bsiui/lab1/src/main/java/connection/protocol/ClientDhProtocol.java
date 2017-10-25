package connection.protocol;

import connection.Connection;
import connection.CryptionStrategy;
import org.json.simple.JSONObject;
import utils.MathUtils;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static utils.MathUtils.isLong;

/**
 * Support for Diffie-Hellman's Protocol on client side
 */
public class ClientDhProtocol {

    /**
     *
     * @param host server's IP address
     * @param port server's port
     * @param encryption type of encryption
     * @return established connection object
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Connection setupConnection(String host, int port, String encryption) throws IOException, ClassNotFoundException {
        Socket socket = new Socket(host, port);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

        JSONObject initToServer = new JSONObject();
        initToServer.put("request", "keys");
        out.writeObject(initToServer);

        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        JSONObject pgFromServer = (JSONObject) in.readObject();
        if(!validPGRequest(pgFromServer)) throw new InvalidObjectException("Invalid P and G");
        long p = extractLong(pgFromServer, "p");
        long g = extractLong(pgFromServer, "g");

        JSONObject secretToServer = new JSONObject();
        long secretA = MathUtils.generateRandomNumber();
        secretToServer.put("a", MathUtils.calculateSecret(secretA, p, g));
        out.writeObject(secretToServer);

        JSONObject secretFromServer = (JSONObject) in.readObject();
        if(!validSecretRequest(secretFromServer)) throw new InvalidObjectException("Invalid secrect B");
        long b = extractLong(secretFromServer, "b");

        JSONObject encryptionToServer = new JSONObject();
        encryptionToServer.put("encryption", encryption);
        out.writeObject(encryptionToServer);

        String secret = MathUtils.calculateSecret(secretA, p, b);
        return new Connection(in, out, socket, new CryptionStrategy(encryption, secret));
    }

    private static long extractLong(JSONObject pgFromServer, String key) {
        return Long.valueOf((String) pgFromServer.get(key));
    }

    private static boolean validPGRequest(JSONObject request) {
        return request.containsKey("p") && isLong((String) request.get("p"))
                && request.containsKey("g") &&  isLong((String) request.get("g"));
    }

    private static boolean validSecretRequest(JSONObject request) {
        return request.containsKey("b") && isLong((String)request.get("b"));
    }
}