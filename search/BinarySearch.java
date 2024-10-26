package search;


public class BinarySearch {
    // Pre: a[0] = min(a) && a[a.length] == inf(a) && a[-1] = sup(a) && a != null && forall i = 0...a.length - 2: a[i] >= a[i+1]
    // Post : a[R] <= x && a[R - 1] > x
    public static int iterativeBinarySearch(int x, int[] a) {
        // Pre
        int left = 0;
        int right = a.length;
        int mid;
        // I: 0 <= left <= right <= a.length && a[right] <= x < a[left]
        while (right > left) {
            // I && right> left
            mid = left + (right - left) / 2;
            // (right - left)/2 > 0 -> right > mid > left
            if (a[mid] > x) {
                // a[mid] > x
                left = mid + 1;
                // left < mid + 1 <= right -> left` < right
                // a[right] <= x < a[left`] && left` < right
            } else {
                // a[mid] <= x
                right = mid;
                // left < mid < right -> left < right`
                // a[right`] <= x < a[left] && left < right`
            }
        }
        // I && (left < right <= left + 1) -> right = left + 1
        // a[left] > x -> a[right - 1] > x
        // I -> a[left + 1] <= x < a[left] -> a[right] <= x && a[right - 1] > x
        return right;
    }

    // Pre: a[0] = min(a) && a[a.length - 1] == max(a)  && a[-1] = sup(a) && a[a.length] = inf(a) &&
    //       && a != null && forall i = 0...a.length - 2: a[i] >= a[i+1] && left <= right + 1
    // Post : a[R] <= x && a[R - 1] > x
    // I : a[left] > x >= a[right] && left < right
    public static int recursiveBinarySearch(int x, int[] a, int left, int right) {
        // Pre && I
        if (left > right) {
            // a[left] > x >= a[right] && left <= right + 1 -> a[left`] <= x && a[left` - 1] > x
            return left;
        }
        // a[left] > x >= a[right] && left + 1 < right
        int mid = left + (right - left) / 2;
        // (right - l)/2 > 0 -> right > mid > left && a[left] > x >= a[right] && left + 1 < right
        if (a[mid] <= x) {
            // a[left] > x >= a[right] && left + 1 < right && a[mid] <= x
            return recursiveBinarySearch(x, a, left, mid - 1);
        } else {
            // a[left] > x >= a[right] && left + 1 < right, a[mid] > x
            return recursiveBinarySearch(x, a, mid + 1, right);
        }
    }


    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        int[] a = new int[args.length - 1];
        for (int i = 0; i < args.length - 1; i++) {
            a[i] = Integer.parseInt(args[i + 1]);
        }
        int recursiveResult = recursiveBinarySearch(x, a , 0, a.length - 1);
        System.out.println(recursiveResult);
        //int iterativeResult = iterativeBinarySearch(x, a);
        //System.out.println(iterativeResult);
    }
}
