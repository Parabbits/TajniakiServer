import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConnectionManager {

    public static void connectToServer() throws IOException{
        System.out.println("Starting server");
        ServerSocket serverSocket = new ServerSocket(7777, 10, InetAddress.getByName("192.168.0.31"));
//        Socket socket = createSocket();

//        InputStream inputToServer = socket.getInputStream();
//        OutputStream outputFromServer = socket.getOutputStream();

//        Scanner scanner = new Scanner(inputToServer, "UTF-8");
//        PrintWriter serverPrintOut = new PrintWriter(new OutputStreamWriter(outputFromServer, "UTF-8"), true);
//        serverPrintOut.println("Hej! :) ");
        int currentId = 0;
        List<Socket> sockets = new ArrayList<>();
        Socket socket = null;
        while (true){
            try{
                socket = serverSocket.accept();
                if (socket != null){
                    sockets.add(socket);
                }
            } catch(IOException e) {
                System.out.println("Error");
            }
            new ServerThread(socket, currentId).start();
            currentId++;
            // TODO: sprawdzenie, czy nastąpiło jakieś zdarzenie, które trzeba rozesłąć do clientóœ
//            if (scanner.hasNextLine()){
//                String line = scanner.nextLine();
//                System.out.println(line);
//                serverPrintOut.println(line);
//                if(line.toLowerCase().trim().equals("exit")){
//                    done = true;
//                }
//            }

        }
    }



    public static Socket createSocket() throws IOException {
        Socket connectionSocket = null;
        ServerSocket serverSocket = new ServerSocket(7777, 10, InetAddress.getByName("192.168.0.31"));
        System.out.println("ip address: " + serverSocket.getInetAddress());
        System.out.println("listening on port: " + serverSocket.getLocalPort());

        connectionSocket = serverSocket.accept();
        return connectionSocket;
    }
}

