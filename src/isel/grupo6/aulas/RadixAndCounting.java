package isel.grupo6.aulas;

public class RadixAndCounting {

    public static int[] countingSort(int[] a, int min) {
        int len = a.length;
        int k = a[0];
        for (int i = 1; i < len; ++i) if (i > k) k = a[i];

        int[] c = new int[k + 1];
        for (int i = 0; i < len; ++c[a[i] - min], ++i);
        for (int i = 1; i < k + 1; c[i] += c[i - 1], ++i);

        int[] b = new int[len];
        for (int i = len - 1; i >= 0; b[--c[a[i] - min]] = a[i], --i);
        return b;
    }
}
