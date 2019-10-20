package isel.grupo6.s1.isbn2;

public class ISBNUtil {

    public static <T> void swap(T[] a, int idxA, int idxB) {
        T temp = a[idxA];
        a[idxA] = a[idxB];
        a[idxB] = temp;
    }

}
