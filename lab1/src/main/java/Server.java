import java.io.*;
import java.net.ServerSocket;


public class Server {
    private String host;
    private int port;

    public Server(String host, int port) {
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
        new Server("localhost",6789).startListening();
    }

}
