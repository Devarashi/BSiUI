import java.io.*;

public class Client {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Connection connection = ClientDhProtocol.setupConnection();
        connection.sendMessage("siemanko");
        connection.endConnection();
    }

}
