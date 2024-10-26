package md2html;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import myscanner.MyScanner;

public class Md2Html {
    private static final String[] HEADERS = {"p", null, "h1", "h2", "h3", "h4", "h5", "h6"}; // :NOTE: remove null
    private static boolean inBold = false;
    private static boolean inItalic = false;
    private static boolean inStrike = false;
    private static boolean inSymb = false;
    private static boolean inComm = false;
    private static int i = 0;

    public static void main(String[] args) { // :NOTE: to Main.java file
        String inputFileName = args[0];
        String outputFileName = args[1];
        try {
            String html = convertToHtml(inputFileName);
            writeHtml(html, outputFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String convertMarkdownToHTML(String markdownText) {
        StringBuilder htmlText = new StringBuilder();
        int count2 = 0;
        for (int k = 0; k < markdownText.length(); k++) {
            if (markdownText.charAt(k) == '_') {
                count2++;
            }
        }

        for (i = 0; i < markdownText.length(); i++) {
            char currentChar = markdownText.charAt(i);
            switch (currentChar) {
                case '-', '\'':
                    handleTag(htmlText, currentChar, markdownText.charAt(i + 1));
                    break;
                case '\\':
                    htmlText.append(markdownText.charAt(++i));
                    break;
                case '`':
                    addTag(htmlText, inSymb, "code");
                    inSymb = !inSymb;
                    break;
                case '<':
                    htmlText.append("&lt;");
                    break;
                case '>':
                    htmlText.append("&gt;");
                    break;
                case '&':
                    htmlText.append("&amp;");
                    break;
                case '*':
                case '_':
                    if (count2 % 2 == 0) {
                        handleTag(htmlText, currentChar, markdownText.charAt(i + 1));
                        break;
                    }
                default:
                    htmlText.append(currentChar);
            }
        }
        return htmlText.toString();
    }


    private static void handleTag(StringBuilder htmlText, char tag, char nextChar) {
        if (tag == '*' && nextChar == '*') {
            addTag(htmlText, inBold, "strong");
            inBold = !inBold;
            i++;
        } else if (tag == '_' && nextChar == '_') {
            addTag(htmlText, inBold, "strong");
            inBold = !inBold;
            i++;
        } else if ((tag == '*' || tag == '_')) {
            addTag(htmlText, inItalic, "em");
            inItalic = !inItalic;
        } else if ((tag == '-') && nextChar == '-') {
            addTag(htmlText, inStrike, "s");
            inStrike = !inStrike;
            i++;
        } else if ('\'' == tag && nextChar == '\'') {
            addTag(htmlText, inComm, "q");
            inComm = !inComm;
            i++;
        } else {
            htmlText.append(tag);
        }
    }

    private static void addTag(StringBuilder htmlText, boolean inTag, String tag) {
        if (!inTag) {
            htmlText.append("<").append(tag).append(">");
        } else {
            htmlText.append("</").append(tag).append(">");
        }
    }

    private static String convertToHtml(String inputFileName) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (MyScanner scanner = new MyScanner(inputFileName, StandardCharsets.UTF_8, null)) {
            List<String> lines = new ArrayList<>();
            StringBuilder sb1 = new StringBuilder();
            String currentLine;
            while (scanner.hasNext()) {
                currentLine = scanner.nextLine();

                if (!currentLine.isEmpty()) {
                    sb1.append(currentLine).append("\n");
                } else if (sb1.length() > 1) {
                    sb1.deleteCharAt(sb1.length() - 1);
                    String nice_line = convertMarkdownToHTML(sb1.toString());
                    lines.add(nice_line);
                    sb1.delete(0, sb1.length());
                }
            }

            if (sb1.length() > 0) {
                sb1.deleteCharAt(sb1.length() - 1);
                String nice_line = convertMarkdownToHTML(sb1.toString());
                lines.add(nice_line);
            }

            for (String line : lines) {
                int headerLevel = getHeaderLevel(line);
                sb.append("<").append(HEADERS[headerLevel]).append(">");
                sb.append(line.substring(headerLevel));
                sb.append("</").append(HEADERS[headerLevel]).append(">");
                if (!line.isEmpty()) {
                    sb.append("\n");
                }
            }
        }
        return sb.toString();
    }

    private static int getHeaderLevel(String line) {
        for (int i = 7; i >= 2; i--) { // :NOTE: 7 to constant
            if (line.startsWith("#".repeat(i - 1) + " ")) {
                return i;
            }
        }
        return 0;
    }

    private static void writeHtml(String html, String fileName) throws IOException {
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8)) {
            writer.write(html);
        }
    }
}
