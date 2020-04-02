import java.io.IOException;

public class Main {

    public static void main(String[] args){
        try {
            ConnectionManager.connectToServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
