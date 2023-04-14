import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadInputFile {
    public static String[] readFile(String path) {
        try{
            int i = 0;//necessary initialization
            int length = Files.readAllLines(Paths.get(path)).size();
            String [] results = new String[length];//creating our input's array
            for (String line : Files.readAllLines(Paths.get(path))) {
                results[i++] = line;//adding string into array
            }
            return results;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
