import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ServerThread extends Thread {

    private Socket socket;
    private Player player;
    private Communication communication; // TODO: później zmienić na private

    // TODO: robiono eksperymentalnie
    private GameServer gameServer;

    public ServerThread(Socket clientSocket) throws IOException {
        this.socket = clientSocket;
        this.player = new Player();
        this.communication = new Communication(clientSocket);
    }

    public Player getPlayer(){
        return player;
    }

    public Communication getCommunication(){
        return communication;
    }

    public Socket getSocket(){
        return socket;
    }

    public void setGameServer(GameServer gameServer){
        // TODO: to w przyszłości może zostać zastąpione przez EventBus
        this.gameServer = gameServer;
    }

    public void run(){
        System.out.println("Połączono nowego klienta: " + socket.getInetAddress());
        String line;
        while(true){
            try {
                line = communication.receive();
                if ((line == null) || line.equalsIgnoreCase("QUIT")){
                   quit();
                   return;
                } else{
                    handleAction(line);
                }
            } catch (IOException e) {
                quit();
                return;
            }
        }
    }

    private void handleAction(String message){
        if (message.equalsIgnoreCase("NICK")){
            handleShowNickname();
        }
        else if(message.startsWith(Command.NICKNAME)){
           handleSetNickname(message);
        } else if (message.startsWith(Command.ECHO)){
            handlePrint(message);
        } else if (message.startsWith("SEND")){
//            String toSend = message.split(":")[1];
            gameServer.handleMessage(message, this);
        } else if(message.startsWith(Command.START_GAME)){ // TODO: to będzie trzeba zmienić na READY czy coś takiego
            gameServer.startGame();
        }
    }


    private void handleShowNickname(){
        if (player.getName() != null){
            communication.send(player.getName());
        } else {
            communication.send("Brak");
        }
    }

    private void handleSetNickname(String message){
        String nickname = message.split(":")[1];
        nickname = nickname.replaceAll("^\\s+|\\s+$", "");
        player.setName(nickname);
    }

    private void handlePrint(String message){
        String messageToPrint = message.split(":")[1];
        messageToPrint = messageToPrint.replace("a", "4");
        communication.send(messageToPrint);
        System.out.println(messageToPrint);
    }


    private void quit() {
        System.out.println("Disconnect: " + socket.getInetAddress());
        try {
            gameServer.handleMessage("CLOSE", this);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Communication{
    private BufferedReader input;
    private PrintWriter output;

    public Communication(Socket socket) throws IOException {
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
    }

    public void send(String message){
        output.println(message);
    }

    public String receive() throws IOException {
        return input.readLine();
    }
}
