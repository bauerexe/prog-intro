import java.util.Arrays;
import java.util.Scanner;

public class ReverseMinR {

    public static void minNums(int[][] arr, int count) {
        for (int i = 0; i <= count - 1; i++) {
            int min = Integer.MAX_VALUE;
            for (int j = 0; j <= arr[i].length - 1; j++) {
                min = Math.min(min, arr[i][j]);
                System.out.print(min + " ");
            }
            System.out.println();
        }
    }

    public static int[][] addLen2d(int[][] src) {
        int[][] arr = Arrays.copyOf(src, src.length * 2);
        return arr;
    }

    public static int[] addLen(int[] src) {
        int[] arr = Arrays.copyOf(src, src.length * 2);
        return arr;
    }

    public static void main(String[] args) {
        int[][] integer2d = new int[1][1];
        Scanner in = new Scanner(System.in);
        int i = 0;
        while (in.hasNextLine()) {
            int j = 0;
            int[] integer = new int[1];
            String str = in.nextLine();
            Scanner line = new Scanner(str);
            while (line.hasNextInt()) {
                if (j >= integer.length) {
                    integer = addLen(integer);
                }
                integer[j] = line.nextInt();
                j++;
            }
            if (i == integer2d.length) {
                integer2d = addLen2d(integer2d);
            }
            int[] integer_copy = new int[j];
            System.arraycopy(integer, 0, integer_copy, 0, j);
            integer2d[i] = integer_copy;
            i++;
        }
        minNums(integer2d, i);
        in.close();
    }
}