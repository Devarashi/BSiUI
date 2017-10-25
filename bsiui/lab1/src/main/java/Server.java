import connection.Connection;
import connection.protocol.ServerDhProtocol;

import java.io.*;
import java.net.ServerSocket;

/**
 * Client of TCP based simple server
 */
public class Server {
    private int port;

    /**
     * @param port on which server will listen for incoming connections
     */
    public Server(int port) {
        this.port = port;
    }

    /**
     * Starts listening for incoming connections on port specified in constructor.
     * Uses DH Protocol to establish connection.
     */
    public void startListening() throws IOException, ClassNotFoundException {
        ServerSocket socket = new ServerSocket(port);
        Connection connection = ServerDhProtocol.setupConnection(socket);
        while (true) {
            connection.readMessage();
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int port = 6789;
        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }
        new Server(port).startListening();
    }

}
