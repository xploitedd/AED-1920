package isel.grupo6.s1.isbn2;

public class ISBNUtil {

    /**
     * Swap two elements of an array
     * @param a array where the elements are
     * @param idxA index of the element a
     * @param idxB index of the element b
     * @param <T> type of the array and elements
     */
    public static <T> void swap(T[] a, int idxA, int idxB) {
        T temp = a[idxA];
        a[idxA] = a[idxB];
        a[idxB] = temp;
    }

}
