package isel.grupo6.s1;

public class Arrays {

    /**
     * Finds the minimum absolute difference between two elements of
     * the arrays with complexity O(n + m)
     * where n = elem1.length and m = elem2.length
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
     * in O(m*log2 n) time complexity (using binary search)
     * where m is the time used for the comparision between two elements
     * and n are the elements of the array
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

        // by default the last element is a match
        String match = v[r];
        // use a modified binary search
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (l == r) {
                // make sure the first chars are equal
                // because if we don't find any match we want to return
                // only the last element
                if (v[r].charAt(0) == word.charAt(0))
                    match = v[r];

                break;
            }

            // select the sides
            // if there's a char with the same initial letter and greater characters in common
            // then it's going to select second side, otherwise it selects the first
            if (v[mid + 1].charAt(0) <= word.charAt(0) && comparateTwoStrings(v[mid + 1], word) >= comparateTwoStrings(v[mid], word))
                l = mid + 1;
            else
                r = mid;
        }

        return match;
    }

    private static int comparateTwoStrings(String a, String b) {
        int c = 0;
        for (; c < a.length() && c < b.length() && a.charAt(c) == b.charAt(c); ++c);
        return c;
    }

    /**
     * Get the k elements near x in O(n*log2 k) time complexity
     * where n = r - l + 1
     * @param v the array containing the elements
     * @param l left position of the array inclusive
     * @param r right position of the array inclusive
     * @param x the element to search for
     * @param k how many results we want
     * @return returns empty array if parameters are invalid otherwise the k elements nearest x
     */
    public static int[] getTheKElementsNearestX(int[] v, int l, int r, int x, int k) {
        if (r < l || k == 0) return new int[0];
        if (v.length <= k) return v;

        int[] heap = new int[k];
        // insert the first k elements in the heap
        for (int i = l; i < l + k; ++i)
            heap[i - l] = v[i];

        // guarantee that the array is now a heap
        maxHeapifyForKElementsNearX(heap, 0, x);
        // for the next elements see if the difference with x is less than the
        // root difference with x and if it is change the root to the element
        // and call max-heapify to reorganize the heap
        for (int i = l + k; i <= r; ++i) {
            int dif = Math.abs(v[i] - x);
            if (dif < Math.abs(heap[0] - x)) {
                heap[0] = v[i];
                maxHeapifyForKElementsNearX(heap, 0, x);
            }
        }

        return heap;
    }

    public static void maxHeapifyForKElementsNearX(int[] heap, int elem, int x) {
        // get left child and right child
        int lc = 2 * elem + 1, rc = lc + 1;
        // find if the largest between the elem, the lc and rc
        int largest = elem;
        if (lc < heap.length && Math.abs(heap[lc] - x) > Math.abs(heap[elem] - x))
            largest = lc;
        if (rc < heap.length && Math.abs(heap[rc] - x) > Math.abs(heap[largest] - x))
            largest = rc;

        // if the largest is one of the childs then swap and max-heapify again
        if (largest != elem) {
            swap(heap, elem, largest);
            maxHeapifyForKElementsNearX(heap, largest, x);
        }
    }

    /**
     * Calculate the median in O(n) complexity
     * where n is the number of elements in the array
     * @param v the array
     * @param l the array lowest bound
     * @param r the array highest bound
     * @return the median of the array or -1 if the input is invalid
     */
    public static int median(int[] v, int l, int r) {
        if (r < l || v.length == 0)
            return -1;

        int mid = l + (r - l) / 2, len = r - l + 1;
        // first we need to find the element at the mid position
        int first = getElementAtKPosition(v, l, r, mid);
        // if the length of the sub-array is even we to find the mid + 1 position
        // and calculate a simple arithmetic mean of the two elements
        if (len % 2 == 0)
            return (first + getElementAtKPosition(v, mid + 1, r, mid + 1)) / 2;

        return first;
    }

    private static int getElementAtKPosition(int[] v, int l, int r, int k) {
        // use the quicksort partition algorithm to find the element
        // that stays at the position k, this is possible because the element
        // returned by the partition method will always be ordered
        int pivot = partition(v, l, r);
        while (pivot != k) {
            // if the current pivot is greater than k then the k element will
            // be on the left side, otherwise we search for the element on the
            // right side of the array
            if (pivot > k)
                pivot = partition(v, l, pivot - 1);
            else
                pivot = partition(v, pivot + 1, r);
        }

        return v[pivot];
    }

    private static int partition(int[] v, int l, int r) {
        // TODO: Use Hoare partition scheme instead of Lomuto's since it's more efficient and we don't care about stability
        int i = l - 1;
        for (int j = l; j <= r - 1; ++j) {
            if (v[j] < v[r]) {
                ++i;
                swap(v, i, j);
            }
        }

        swap(v, i + 1, r);
        return i + 1;
    }

    private static void swap(int[] v, int idxA, int idxB) {
        int aux = v[idxA];
        v[idxA] = v[idxB];
        v[idxB] = aux;
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
