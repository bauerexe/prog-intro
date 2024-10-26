package myscanner;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class MyScanner implements AutoCloseable {
    private Reader reader;
    private char[] buffer;
    private int index;
    private char c;
    private int bytesRead;
    private CheckIt checking;
    private CheckIt universal;

    public interface CheckIt {
        boolean isNeeded(char c);
    }

    public MyScanner(String fileName, Charset charsetName, CheckIt check) throws IOException {
        this(new InputStreamReader(new FileInputStream(fileName), charsetName), check);
    }

    public MyScanner(InputStream input, Charset charsetName, CheckIt check) throws IOException {
        this(new InputStreamReader(input, charsetName), check);
    }

    public MyScanner(String string, CheckIt check) {
        this(new StringReader(string), check);
    }
    private MyScanner(Reader read, CheckIt check){
        reader = read;
        universal = check;
    }

    private char nextChar() throws IOException {
        if (buffer == null || bytesRead == index) {
            readBlock();
        }
        if (buffer == null) {
            return '\0';
        }
        return buffer[index++];
    }

    private void readBlock() throws IOException {
        buffer = new char[64];
        bytesRead = reader.read(buffer);
        if (bytesRead == -1) {
            buffer = null;
        }
        index = 0;
    }

    public enum Type{
        INT,
        WORD,
        UNIVERSAl,
        ANY;
    }

    private void check(Type type) throws IOException {
        switch (type){
            case INT:
                checking = c -> (Character.isDigit(c) || c == '-');
                break;
            case WORD:
                checking = c -> (Character.isLetter(c) || Character.DASH_PUNCTUATION == Character.getType(c) || c == '\'');
                break;
            case UNIVERSAl:
                checking = universal;
                break;
            default:
                checking = c -> true;
                break;
        }
    }

    private char tryGetNext() throws IOException{
        if (c == '\0' || c == System.lineSeparator().charAt(System.lineSeparator().length()-1) ) {
            c = nextChar();
        }
        return c;
    }

    public void skipSpaces() throws IOException{
        c = tryGetNext();
        while ((Character.isWhitespace(c) || !checking.isNeeded(c)) && c != System.lineSeparator().charAt(System.lineSeparator().length()-1) && c != '\0') {
            c = nextChar();
        }
    }

    public boolean hasNext() throws IOException {
        check(Type.ANY);
        c = tryGetNext();
        return c != '\0';
    }

    public String nextLine() throws IOException {
        StringBuilder str = new StringBuilder();
        if (c == '\0') {
            c = tryGetNext();
        }
        while (c != System.lineSeparator().charAt(System.lineSeparator().length() - 1) && buffer != null ) {
            if (c != System.lineSeparator().charAt(0)){
                str.append(c);
            }
            c = nextChar();
        }

        return str.toString();
    }


    private String next() throws IOException {
        StringBuilder sb = new StringBuilder();
        if (universal != null){
            check(Type.UNIVERSAl);
        }
        skipSpaces();
        while (checking.isNeeded(c) && !Character.isWhitespace(c) && c != '\0') {
            sb.append(c);
            c = nextChar();
        }
        return sb.toString();
    }

    public int nextInt() throws IOException {
        if (checking == null){
            check(Type.INT);
        }
        String str = next();
        return Integer.parseInt(str);
    }

    public String nextWord() throws IOException {
        check(Type.WORD);
        String res = next();
        if (res.equals("")){
            return null;
        }
        return res;
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}