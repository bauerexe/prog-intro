import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WordStatCountPrefixL {
    public static List<Map.Entry<String, Integer>> sort(LinkedHashMap<String, Integer> map) {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(map.entrySet());
        entries.sort(Map.Entry.comparingByValue());
        return entries;
    }

    public static void main(String[] args) {
        LinkedHashMap<String, Integer> dict = new LinkedHashMap<>();
        try {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(
                    new FileInputStream(args[0]), "utf8"))) {
                int read = in.read();
                StringBuilder str = new StringBuilder();
                while (read >= 0) {
                    char ch = (char) read;
                    if ((Character.isLetter(ch) || Character.DASH_PUNCTUATION == Character.getType(ch) || ch == '\'')) {
                        str.append(ch);
                    } else if (str.length() > 2) {
                        String sub = str.substring(0, 3).toLowerCase();
                        if (dict.containsKey(sub)) {
                            dict.put(sub, dict.get(sub) + 1);
                        } else {
                            dict.put(sub, 1);
                        }
                        str.setLength(0);
                    } else {
                        str.setLength(0);
                    }
                    read = in.read();
                }
            } catch (IOException e) {
                System.err.println("Reader error" + e.getMessage());
            }
            try (Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(args[1]), "utf8"))) {
                List<Map.Entry<String, Integer>> dict1 = sort(dict);
                int i = 1;
                for (Map.Entry<String, Integer> entry : dict1) {
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