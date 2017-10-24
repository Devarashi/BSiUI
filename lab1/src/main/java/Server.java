import java.io.*;
import java.net.ServerSocket;


public class Server {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket welcomeSocket = new ServerSocket(6789);

        while (true) {
            Connection connection = ServerDhProtocol.setupConnection(welcomeSocket);
            connection.readMessage();
        }
    }

}
