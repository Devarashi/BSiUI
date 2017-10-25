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

    private static void readAndSendMessage(Connection connection) throws IOException {
        Scanner reader = new Scanner(System.in);
        System.out.print("Enter message: " );
        String message = reader.next();
        connection.sendMessage(message);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        new Client("localhost", 6789, "cezar").connectToServer();
    }
}
