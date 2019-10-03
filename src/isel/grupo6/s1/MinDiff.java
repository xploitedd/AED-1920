package isel.grupo6.s1;

public class MinDiff {

    public static void main(String[] args) {
        int[] a1 = { 3, 27, 45, 68, 70, 81, 99 };
        int[] a2 = { 9, 16, 25, 35, 75, 84 };
        System.out.println("Min Difference: " + findMinDifference(a1, a2));
    }

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
            // check if it's the first iteration or if we found a new minimum
            if (min == -1 || cur < min)
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

}
