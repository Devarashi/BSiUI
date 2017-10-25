import connection.protocol.ClientDhProtocol;
import connection.Connection;

import java.io.*;
import java.util.Scanner;

public class Client {
    private String host;
    private int port;
    private String encryption;

    public Client(String host, int port, String encryption) {
        this.host = host;
        this.port = port;
        this.encryption = encryption;
    }

    public void connectToServer() throws IOException, ClassNotFoundException {
        Connection connection = ClientDhProtocol.setupConnection(host, port, encryption);

        while (true) {
            readAndSendMessage(connection);
        }
    }

    private void readAndSendMessage(Connection connection) throws IOException {
        Scanner reader = new Scanner(System.in);
        System.out.print("Enter message: " );
        String message = reader.next();
        connection.sendMessage(message);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String host = "localhost";
        int port = 6789;
        String encryption = "none";
        if (args.length >= 2) {
            host = args[0];
            port = Integer.parseInt(args[1]);
        }
        if (args.length == 3) {
            encryption = args[2];
        }

        new Client(host, port, encryption).connectToServer();
    }
}
