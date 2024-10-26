import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import myscanner.MyScanner;
public class WsppPosition {
    public static void main(String[] args) {
        Map<String, List<String>> dict = new LinkedHashMap<>();
        try {
            try (MyScanner in = new MyScanner(args[0], StandardCharsets.UTF_8, null)) {
                String word;
                int count_for_line = 1;
                while (in.hasNext()) {
                    int count = 0;
                    String line = in.nextLine();
                    int c1 = 0;
                    MyScanner in_2 = new MyScanner(line,null);
                    while (in_2.hasNext()){
                        if (in_2.nextWord() != null){
							c1 ++;
						}
                        
                    }
                    MyScanner in_1 = new MyScanner(line,null);
                    while (in_1.hasNext()) {
                        word = in_1.nextWord();
                        if (word != null) {
                            word = word.toLowerCase();
                            if (!dict.containsKey(word)) {
                                ArrayList<String> arr = new ArrayList<String>();
                                dict.put(word, arr);
                            }
                            dict.get(word).add(count_for_line + ":" + (c1 - count));
                            count += 1;
                        }
                    }
                    count_for_line ++;

                }
            } catch (IOException e) {
                System.err.println("Reader error" + e.getMessage());
            }
            try (Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(args[1]), "utf8"))) {

                for (Map.Entry<String, List<String>> entry : dict.entrySet()) {
                    out.write(entry.getKey() + " " + entry.getValue().size());
                    for (String x: entry.getValue()) {
                        out.write(" " + x);
                    }
                    out.write("\n");
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File to Reading Not Found error" + e.getMessage());
        } catch (IOException e) {
            System.err.println("IOException error" + e.getMessage());
        }
    }
}