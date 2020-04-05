import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args){
        String IP = "192.168.0.31";
        int PORT = 7777;
        try {
            List<String> words = WordsHelper.readWords("resources/words.txt");
            List<String> randomWords = WordsHelper.randomWords(words, 25);
            System.out.print(randomWords);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            GameServer gameServer = new GameServer();
            gameServer.start(IP, PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
