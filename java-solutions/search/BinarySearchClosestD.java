package search;

public class BinarySearchClosestD {
    // Pre: index1 < 0 || index2 >= a.length || (index1 + 1 == index2)
    // Post: R: |a[index1] - x| <= |a[index2] - x| ? R = a[index1] : R = a[index2]
    private static int findClosest(int x, int[] a, int index1, int index2) {
        // index1 < 0 -> a[index1] = +INF -> a[index2] - x < a[index1] - x
        if (index1 < 0) {
            return a[index2];
        }
        // index2 > a.length -> a[index2] = -INF -> a[index1] - x < a[index2] - x
        if (index2 >= a.length) {
            return a[index1];
        }
        // |a[index1] - x| <= |a[index2] - x| -> a[index1] - x = min_diff
        // |a[index1] - x| > |a[index2] - x| -> a[index2] - x = min_diff
        return Math.abs(a[index1] - x) <= Math.abs(a[index2] - x) ? a[index1] : a[index2];
    }

    // Pre: a != null && forall i = 0...a.length - 2: a[i] >= a[i+1] &&
    //      && a[-1] = -INF && a[a.length] = +INF
    // Post: |R - x| = min(forall i = 0 ... a.length - 1: |a[i] - x|)
    public static int iterativeBinarySearch(int x, int[] a) {
        // a.length == 0 -> x not in a
        if (a.length == 0) {
            return -1;
        }
        int left = 0;
        int right = a.length - 1;
        // I: a[left - 1] < x <= a[right + 1] && 0 <= left <= right + 1 <= a.length
        while (left <= right) {
            // I && left <= right
            int mid = left + (right - left) / 2;
            // (right - left)/2 > 0 -> right > mid > left
            if (a[mid] == x) {
                // a[mid] == x -> min_diff = min(forall i = 0 ... a.length - 1: |a[i] - x|) = |a[mid] - x| = 0
                return a[mid];
            } else if (a[mid] < x) {
                // I && right > mid > left && a[mid] < x
                right = mid - 1;
                // right` = mid - 1
            } else {
                // I && right > mid > left && a[mid] > x
                left = mid + 1;
                // right` = mid - 1
            }
        }
        // I && left > right
        // a[left] <= x && right + 1 = left
        // a[left + 1] <= x && x >= a[left] >= a[left + 1] -> |a[left] - x| <= |a[left + 1] - x|
        // a[right - 1] > x && a[right - 1] >= a[right] > x -> |a[right] - x| <= |a[right - 1] - x|
        // -> |a[right] - x| <= |a[left] - x| || |a[right] - x| >= |a[left] - x|
        // min_diff = min(forall i = 0 ... a.length - 1: |a[i] - x|) &&
        //      (min_diff = a[left] || min_diff = a[right])
        return findClosest(x, a, right, left);
    }

    // Pre:  a != null && && forall i = 0...a.length - 2: a[i] >= a[i+1] && a[-1] = -INF && a[a.length] = +INF &&
    //        && 0 <= left <= right + 1 <= a.length
    // Post: |R - x| = min(forall i = left ... right: |a[i] - x|)
    public static int recursiveBinarySearch(int x, int[] a, int left, int right) {
        // 0 <= left <= right + 1 <= a.length && left > right
        if (left > right) {
            // a[left] <= x && right + 1 = left
            // a[left + 1] <= x && x >= a[left] >= a[left + 1] -> |a[left] - x| <= |a[left + 1] - x|
            // a[right - 1] > x && a[right - 1] >= a[right] > x -> |a[right] - x| <= |a[right - 1] - x|
            // -> |a[right] - x| <= |a[left] - x| || |a[right] - x| >= |a[left] - x|
            // min_diff = min(forall i = 0 ... a.length - 1: |a[i] - x|) &&
            //      (min_diff = a[left] || min_diff = a[right])
            return findClosest(x, a, right, left);
        }
        //  0 <= left <= right + 1 <= a.length && left <= right
        int mid = left + (right - left) / 2;
        // (right - left)/2 > 0 -> right > mid > left
        if (a[mid] <= x) {
            // a[left] > x >= a[right] && left <= right + 1 && a[mid] <= x
            // right > mid > left && a[mid] <= x
            // right` = mid - 1
            return recursiveBinarySearch(x, a, left, mid - 1);
        } else {
            // a[left] > x >= a[right] && left <= right + 1 && a[mid] > x
            // right > mid > left && a[mid] > x
            // left` = mid + 1
            return recursiveBinarySearch(x, a, mid + 1, right);
        }
    }

    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        int[] a = new int[args.length - 1];
        int sumA = 0;
        for (int i = 0; i < args.length - 1; i++) {
            int ai = Integer.parseInt(args[i + 1]);
            a[i] = ai;
            sumA += ai;
        }


        int closestValue;
        if ((x + sumA) % 2 == 0) {
            closestValue = recursiveBinarySearch(x, a, 0, a.length - 1);
        } else {
            closestValue = iterativeBinarySearch(x, a);
        }
        System.out.println(closestValue);
    }
}


