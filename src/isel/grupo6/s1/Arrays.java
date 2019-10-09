package isel.grupo6.s1;

public class Arrays {

    /**
     * Finds the minimum absolute difference between two elements of
     * the arrays with complexity O(elem1.length + elem2.length)
     * @param elem1 array of elements 1
     * @param elem2 array of elements 2
     * @return -1 if any of the arrays has zero elements, otherwise the min difference
     */
    public static int findMinDifference(int[] elem1, int[] elem2) {
        // we consider that the start min will be -1
        int aIdx = 0, bIdx = 0, min = -1;
        // we use a do-while because we've already checked that each array has
        // at least one element (so we don't need to do a redundant check)
        while (aIdx < elem1.length && bIdx < elem2.length) {
            int a = elem1[aIdx], b = elem2[bIdx];
            int cur = Math.abs(a - b);
            // check if we found a new minimum
            if (cur < min || min == -1)
                min = cur;

            // if a < b then we can increment a since any other values of elem2 array
            // will produce larger numbers when compared against a, otherwise we increment b
            if (a < b)
                ++aIdx;
            else
                ++bIdx;
        }

        return min;
    }

    /**
     * Find the greatest element that has the same prefix as word
     * in O(nlog2n) time complexity (using binary search)
     * @param v array to search
     * @param l left position to start from
     * @param r right position
     * @param word word that has the prefix to search for
     * @return null if there're invalid parameters or the word that matched
     */
    public static String greaterCommonPrefix(String[] v, int l, int r, String word) {
        if (v.length == 0 || l > r)
            return null;

        if (l < 0) l = 0;
        if (r > v.length - 1) r = v.length - 1;

        String ret = binarySearchPrefix(v, l, r, word);
        return ret == null ? v[r] : ret;
    }

    private static String binarySearchPrefix(String[] v, int l, int r, String word) {
        if (r > l) {
            int mid = l + (r - l) / 2;
            // we can check mid + 1 since mid < r
            if (v[mid + 1].charAt(0) <= word.charAt(0))
                return binarySearchPrefix(v, mid + 1, r, word);
            else
                return binarySearchPrefix(v, l, mid, word);
        } else if (l == r && v[l].charAt(0) == word.charAt(0)) {
            // greatest prefix found in subarray
            return v[l];
        }

        return null;
    }

    public static int[] getTheKElementsNearestX(int[] v, int l, int r, int x, int k){
        throw new UnsupportedOperationException();
    }

    public static int median(int[] v, int l, int r){
        throw new UnsupportedOperationException();
    }

    /**
     * Return the a new array with the squares of the elements
     * provided in the parameter array. Time complexity: O(n)
     * @param v array of the elements to be squared
     * @return sorted array with squared elements
     */
    public static int[] squaresSorted(int[] v) {
        int len = v.length;
        if (len == 0)
            return null;

        // find a divider between the positive and negative numbers, if needed
        int[] ret = new int[len];
        int divider = 0;
        for (int i = 0; i < len; i++) {
            int e = v[i];
            // if the first element is already greater or equal than
            // zero then the array won't need any sort
            if (e >= 0 && divider == 0)
                ret[i] = e * e;
            else if (e >= 0)
                break;
            else
                ++divider;
        }

        // because the divider is zero that means there were
        // no negative numbers in the provided array
        if (divider == 0)
            return ret;

        // use merge function to combine the positive and negative sub-arrays
        int i = 0, k = divider - 1, j = divider;
        while (k >= 0 && j < v.length) {
            int q1 = v[k] * v[k], q2 = v[j] * v[j];
            if (q1 < q2) {
                ret[i++] = q1;
                --k;
            } else {
                ret[i++] = q2;
                ++j;
            }
        }

        // fill the remaining
        for (; k >= 0; --k)
            ret[i++] = v[k] * v[k];

        for (; j < v.length; ++j)
            ret[i++] = v[j] * v[j];

        return ret;
    }

}
