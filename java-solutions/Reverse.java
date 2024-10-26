import myscanner.MyScanner;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


public class Reverse {
    public static void reverse_dump(int[][] arr, int count) {
        for (int i = count - 1; i >= 0; i--) {
            for (int j = arr[i].length - 1; j >= 0; j--) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static int[][] addLen2d(int[][] src) {
        return Arrays.copyOf(src, src.length * 2);
    }

    public static int[] addLen(int[] src) {
        return Arrays.copyOf(src, src.length * 2);
    }

    public static void main(String[] args) throws IOException {
        int[][] integer2d = new int[1][1];
        MyScanner in = new MyScanner(System.in, StandardCharsets.UTF_8, null);
        int i = 0;
        while (in.hasNext()) {
            int j = 0;
            int[] integer = new int[1];
            String str = in.nextLine();
            try (MyScanner line = new MyScanner(str, null)) {
                while (line.hasNext()) {
                    if (j >= integer.length) {
                        integer = addLen(integer);
                    }
                    int b = line.nextInt();
                    integer[j] = b;
                    j++;
                }
            }
            if (i == integer2d.length) {
                integer2d = addLen2d(integer2d);
            }
            int[] integer_copy = new int[j];
            System.arraycopy(integer, 0, integer_copy, 0, j);
            integer2d[i] = integer_copy;
            i++;
        }
        reverse_dump(integer2d, i);
        in.close();
    }
}