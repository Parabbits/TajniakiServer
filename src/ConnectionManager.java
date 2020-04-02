import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ConnectionManager {

    public static void connectToServer() throws IOException{
        System.out.println("Starting server");
        Socket socket = createSocket();

        InputStream inputToServer = socket.getInputStream();
        OutputStream outputFromServer = socket.getOutputStream();

        Scanner scanner = new Scanner(inputToServer, "UTF-8");
        PrintWriter serverPrintOut = new PrintWriter(new OutputStreamWriter(outputFromServer, "UTF-8"), true);
        serverPrintOut.println("Hej! :) ");

        boolean done = false;
        while (!done){
            if (scanner.hasNextLine()){
                String line = scanner.nextLine();
                System.out.println(line);
                serverPrintOut.println(line);
                if(line.toLowerCase().trim().equals("exit")){
                    done = true;
                }
            }

        }
    }

    public static Socket createSocket() throws IOException {
        Socket connectionSocket = null;
        ServerSocket serverSocket = new ServerSocket(7777);
//        ServerSocket serverSocket = new ServerSocket(25566, 10, InetAddress.getByName("192.168.0.30"));
//        ServerSocket serverSocket = new ServerSocket(0);
        System.out.println("ip address: " + serverSocket.getInetAddress());
        System.out.println("listening on port: " + serverSocket.getLocalPort());

        connectionSocket = serverSocket.accept();
        return connectionSocket;
    }
}
