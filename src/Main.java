import java.io.IOException;

public class Main {

    public static void main(String[] args){
        String IP = "192.168.0.31";
        int PORT = 7777;
        try {
            GameServer gameServer = new GameServer();
            gameServer.start(IP, PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
