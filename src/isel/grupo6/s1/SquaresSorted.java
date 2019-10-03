package isel.grupo6.s1;

import java.util.Arrays;

public class SquaresSorted {

    public static void main(String[] args) {
        int[] array = { 2, 3, 5, 8, 13, 21 };
        for (int i : squaresSorted(array))
            System.out.println(i);
    }

    public static int[] squaresSorted(int[] v) {
        int[] newArray = new int[v.length];
        for (int i = 0; i < v.length; ++i)
            newArray[i] = v[i] * v[i];

        Arrays.sort(newArray);
        return newArray;
    }

}
