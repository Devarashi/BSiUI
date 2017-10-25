import connection.Connection;
import connection.protocol.ServerDhProtocol;

import java.io.*;
import java.net.ServerSocket;


public class Server {
    private int port;

    public Server(int port) {
        this.port = port;
    }

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
