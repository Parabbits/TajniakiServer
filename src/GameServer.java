import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class GameServer {

    private List<ServerThread> clients = new ArrayList<>();
    private int currentId = 0;
    private ServerSocket server;

    public void start(String ip, int port) throws IOException {
        currentId = 0;
        server = ConnectionManager.createServer(ip, port);
        while(true){
            try{
                ServerThread client = createClient();
                clients.add(client);
            } catch(IOException e) {
                System.out.println("Error");
            }
        }
    }

    private ServerThread createClient() throws IOException {
        ServerThread client = ConnectionManager.waitAndConnectClient(server);
        client.setGameServer(this);
        client.getPlayer().setId(currentId);
        currentId++;
        return client;
    }

    public void handleMessage(String message, ServerThread client){
        if(message.startsWith(Command.CLOSE)){
            // TODO: można dodać getAdress()
            System.out.println("Usunięto klienta: " + client.getSocket().getInetAddress());
            clients.remove(client);
            System.out.println("Liczba klientów: " + clients.size());
        } else if (message.startsWith("SEND")){
            String messageToSend = message.split(":")[1];
            for(ServerThread c: clients){
                if(!c.equals(client)){
                    System.out.println("Wysłano do: " + c.getSocket().getInetAddress());
                    c.getCommunication().send(messageToSend);
                }
            }
        }
    }

    public void startGame() {
        List<String> words = null;
        try {
            words = getWords();
        } catch (IOException e) {
            // TODO: zrobić jakiś komunikat o błędzie
            return;
        }
        String wordsString = getWordsText(words);
        System.out.println(wordsString);
        for(ServerThread c: clients){
            c.getCommunication().send(Command.WORDS + ":" + wordsString);
        }
    }

    private String getWordsText(List<String> words){
        String DELIMITER = ",";
        StringBuilder builder = new StringBuilder();
        for(String word: words){
            builder.append(word);
            builder.append(DELIMITER);
        }
        return builder.toString();
    }

    private List<String> getWords() throws IOException {
        List<String> allWords = WordsHelper.readWords("resources/words.txt");
        return WordsHelper.randomWords(allWords, 25);
    }
}
