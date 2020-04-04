import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionManager {

    public static ServerSocket createServer(String ip, int port) throws IOException {
        return new ServerSocket(port, 10, InetAddress.getByName(ip));
    }

    public static ServerThread waitAndConnectClient(ServerSocket server) throws IOException {
        Socket socket = server.accept();
        ServerThread client = new ServerThread(socket);
        client.start();
        return client;
    }
}

