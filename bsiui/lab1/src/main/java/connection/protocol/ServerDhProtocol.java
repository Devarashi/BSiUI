package connection.protocol;

import connection.Connection;
import connection.CryptionStrategy;
import org.json.simple.JSONObject;
import utils.MathUtils;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static utils.MathUtils.isLong;

/**
 * Support for support Diffie-Hellman's Protocol on server side
 */
public class ServerDhProtocol {
    private static final long G = 5;

    /**
     *
     * @param welcomeSocket socket on which server waits for new connections
     * @return established connection with new client
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Connection setupConnection(ServerSocket welcomeSocket) throws IOException, ClassNotFoundException {
        long p = MathUtils.generateRandomPrimeNumber();
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
        String encryption = (String) encryptionFromClient.get("encryption");

        String secret = MathUtils.calculateSecret(secretB, p, a);
        return new Connection(in, out, socket, new CryptionStrategy(encryption, secret));
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

}