import java.io.*;
import java.util.*;

public class WordsHelper {

    public static List<String> readWords(String filepath) throws IOException {
        List<String> resultWords = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
        String line;
        while((line = bufferedReader.readLine()) != null){
            resultWords.add(line);
        }
        return resultWords;
    }

    public static List<String> randomWords(List<String> allWords, int numberOfWords){
        Collections.shuffle(allWords);
        return allWords.subList(0, numberOfWords);
    }
}
