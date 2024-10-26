import myscanner.MyScanner;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ReverseMinRAbc {

    public static void minNums(String[][] arr, int count) {
        for (int i = 0; i <= count - 1; i++) {
            String min = "";
            for (int j = 0; j <= arr[i].length - 1; j++) {
                min = MinString(min, arr[i][j]);
                System.out.print(min + " ");
            }
            System.out.println();
        }
    }


    public static String check(String n1, String n2) {
        int check = n1.compareTo(n2);
        if (n1.length() < n2.length()) {
            String n0 = n1;
            n1 = n2;
            n2 = n0;
        }
        if (check <= 0 && n1.length() <= n2.length()) {
            return n1;
        } else {
            return n2;
        }
    }

    public static String MinString(String n1, String n2) {
        if (n1 == "") {
            return n2;
        }
        char nFirst = n1.charAt(0);
        char nSecond = n2.charAt(0);
        if (nFirst != '-' && nSecond != '-') {
            return check(n1, n2);
        } else if (nFirst == '-' && nSecond == '-') {
            n1 = n1.substring(1, n1.length());
            n2 = n2.substring(1, n2.length());
            String check = check(n1, n2);
            if (n1 == check) {
                return "-" + n2;
            } else {
                return "-" + n1;
            }
        } else {
            if (nFirst == '-') {
                return n1;
            } else {
                return n2;
            }
        }
    }


    public static void main(String[] args) throws IOException {
        String[][] integer2d = new String[1][1];
        MyScanner.CheckIt check = new MyScanner.CheckIt() {
            @Override
            public boolean isNeeded(char c) {
                return Character.isLetter(c) || c == '-';
            }
        };
        MyScanner in = new MyScanner(System.in, StandardCharsets.UTF_8, check);
        int i = 0;
        while (in.hasNext()) {
            int j = 0;
            String[] arr = new String[1];
            String str = in.nextLine();
            try (MyScanner line = new MyScanner(str, check)) {
                while (line.hasNext()) {
                    if (j >= arr.length) {
                        arr = Arrays.copyOf(arr, arr.length * 2);
                        ;
                    }
                    arr[j] = line.nextWord();
                    j++;
                }
            }
            if (i == integer2d.length) {
                integer2d = Arrays.copyOf(integer2d, integer2d.length * 2);
            }
            String[] arr_copy = new String[j];
            System.arraycopy(arr, 0, arr_copy, 0, j);
            integer2d[i] = arr_copy;
            i++;
        }
        minNums(integer2d, i);
        in.close();
    }
}