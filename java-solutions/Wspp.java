import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import myscanner.MyScanner;

public class Wspp {
    public static void main(String[] args) {
        LinkedHashMap<String, ArrayList<Integer>> dict = new LinkedHashMap<>();
        try {
            try (MyScanner in = new MyScanner(args[0], StandardCharsets.UTF_8, null)) {
                String word;
                int count = 1;
                while (in.hasNext()) {
                    word = in.nextWord();
					if (word != null) {
                        word = word.toLowerCase();								
                        if (!dict.containsKey(word)) {
                            ArrayList<Integer> arr = new ArrayList<Integer>();
							dict.put(word, arr);
                        }
						dict.get(word).add(count);
						count += 1;
                    }

                }
            } catch (IOException e) {
                System.err.println("Reader error" + e.getMessage());
            }
            try (Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(args[1]), "utf8"))) {
                int i = 1;

                for (Map.Entry<String, ArrayList<Integer>> entry : dict.entrySet()) {
                    out.write(entry.getKey() + " " + entry.getValue().size());
                    for (Integer x: entry.getValue()) {
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