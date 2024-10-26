import myscanner.MyScanner;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WordStatInput {
    public static List<Map.Entry<String, Integer>> sort(LinkedHashMap<String, Integer> map) {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(map.entrySet());
        entries.sort(Map.Entry.comparingByValue());
        return entries;
    }

    public static void main(String[] args) {
        LinkedHashMap<String, Integer> dict = new LinkedHashMap<>();
        try {
            try (MyScanner in = new MyScanner(args[0], StandardCharsets.UTF_8, null)) {
                String word;
                while (in.hasNext()) {
                    word = in.nextWord();
                    if (word != null) {
                        word = word.toLowerCase();
                        if (dict.containsKey(word)) {
                            dict.put(word, dict.get(word) + 1);
                        } else {
                            dict.put(word, 1);
                        }
                    }

                }
            } catch (IOException e) {
                System.err.println("Reader error" + e.getMessage());
            }
            try (Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(args[1]), "utf8"))) {
                int i = 1;
                for (Map.Entry<String, Integer> entry : dict.entrySet()) {
                    out.write(entry.getKey() + " " + entry.getValue());
                    if (dict.size() != i) {
                        out.write("\n");
                        i++;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File to Reading Not Found error" + e.getMessage());
        } catch (IOException e) {
            System.err.println("IOException error" + e.getMessage());
        }
    }
}