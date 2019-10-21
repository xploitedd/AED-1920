package isel.grupo6.s1.isbn2;

public class ISBNArrayList {

    private static final int MAX_INSERTION_SORT = 32;
    private String[] elements;
    private int readPos, writePos;

    public ISBNArrayList(int maxSize) { elements = new String[maxSize]; }

    /**
     * Checks if the ArrayList is empty
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() { return writePos == readPos; }

    /**
     * Checks if the ArrayList is full
     * @return true if full, false otherwise
     */
    private boolean isFull() { return writePos == elements.length; }

    /**
     * Adds a new element to the writePos of the ArrayList
     * @param elem element to be added
     */
    public void add(String elem) {
        if (isFull()) return;
        elements[writePos++] = elem;
    }

    /**
     * Gets the element from readPos of the ArrayList and
     * puts that position to null in the array
     * @return element, or null if it is empty
     */
    public String next() {
        if (isEmpty()) return null;
        String elem = elements[readPos];
        elements[readPos++] = null;
        return elem;
    }

    /**
     * Sorts the ArrayList
     * @param comparator comparator to be used by the sorting algorithm
     */
    public void sort(ISBNComparator comparator) { timSort(elements, readPos, writePos - 1, comparator); }

    /**
     * Resets the ArrayList to its original state
     */
    public void reset() {
        readPos = 0;
        writePos = 0;
    }

    /**
     * Implementation of TimSort algorithm that, using InsertionSort and the merge function
     * sorts the array with worst-case complexity of O(nlgn)
     * @param a array to be sorted
     * @param l start index
     * @param r end index
     * @param comparator comparator to be used
     */
    private static void timSort(String[] a, int l, int r, ISBNComparator comparator) {
        int n = r - l + 1;
        // use insertion sort each MAX_INSERTION_SORT elements
        for (int i = 0; i < n; i += MAX_INSERTION_SORT)
            insertionSort(a, i, Math.min(i + (MAX_INSERTION_SORT - 1), n - 1), comparator);

        // merge the parts that are sorted using the merge function
        int shifti;
        for (int i = MAX_INSERTION_SORT; i < n; i = shifti) {
            shifti = i << 1;
            for (int j = 0; j < n; j += shifti) {
                int mid = Math.min(j + i - 1, n - 1);
                int right = Math.min((j + shifti - 1), (n - 1));
                merge(a, j, mid, right, comparator);
            }
        }
    }

    /**
     * Merges sub-array (a,l,m) with (a,m+1,r) into one array
     * in O(n) time complexity
     * @param a array where are the parts to be merged
     * @param l start index
     * @param m end of the first part index
     * @param r end of the second part index
     * @param comparator comparator to be used while merging
     */
    private static void merge(String[] a, int l, int m, int r, ISBNComparator comparator) {
        int len1 = m - l + 1, len2 = r - m;
        String[] a1 = new String[len1], a2 = new String[len2];
        for (int i = 0; i < len1; i++)
            a1[i] = a[i + l];

        for (int i = 0; i < len2; i++)
            a2[i] = a[i + m + 1];

        int i = 0, j = 0, k = l;
        while (i < len1 && j < len2) {
            if (comparator.compare(a1[i], a2[j]) <= 0)
                a[k] = a1[i++];
            else
                a[k] = a2[j++];

            ++k;
        }

        while (i < len1) a[k++] = a1[i++];
        while (j < len2) a[k++] = a2[j++];
    }

    /**
     * Sorts an array with insertion sort
     * @param a array to be sorted
     * @param l left index
     * @param r right index
     * @param comparator comparator to be used
     */
    private static void insertionSort(String[] a, int l, int r, ISBNComparator comparator) {
        String temp;
        for (int i = l + 1; i <= r; ++i) {
            temp = a[i];
            int j = i;
            for (; j > l && comparator.compare(a[j - 1], temp) > 0; --j)
                a[j] = a[j - 1];

            a[j] = temp;
        }
    }

}
