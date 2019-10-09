package isel.grupo6.s1;
import org.junit.jupiter.api.Test;

import static isel.grupo6.s1.Arrays.squaresSorted;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class SquaresSortedTest {

    @Test
    public void squaresSorted_onArrayWithNoElements(){
        int[] array={};
        assertArrayEquals(null,squaresSorted(array));
    }

    @Test
    public void squaresSorted_onArrayWithOnePositiveElement(){
        int[] array={3};
        assertArrayEquals(new int[]{9},squaresSorted(array));
    }

    @Test
    public void squaresSorted_onArrayWithOneNegativeElement(){
        int[] array={-3};
        assertArrayEquals(new int[]{9},squaresSorted(array));
    }

    @Test
    public void squaresSorted_onArrayWithTwoElements1(){
        int[] array={-3,4};
        assertArrayEquals(new int[]{9,16},squaresSorted(array));
    }


    @Test
    public void squaresSorted_onArrayWithTwoElements2(){
        int[] array={-4,3};
        assertArrayEquals(new int[]{9,16},squaresSorted(array));
    }

    @Test
    public void squaresSorted_onArrayWithTwoElements3(){
        int[] array={-3,3};
        assertArrayEquals(new int[]{9,9},squaresSorted(array));
    }

    @Test
    public void squaresSorted_onSomeArrayWithAGreaterPositiveElement(){
       int[] array={-9,-9,-7,-3,-2,0,0,1,2,2,3,4,5,6,7,7,8,10};
        assertArrayEquals(new int[]{0,0,1,4,4,4,9,9,16,25,36,49,49,49,64,81,81,100},squaresSorted(array));
    }

    @Test
    public void squaresSorted_onSomeArrayWithAGreaterNegativeElement(){
        int[] array={-9,-9,-7,-3,-2,0,0,1,2,2,3,4,5,6,7,7,8};
        assertArrayEquals(new int[]{0,0,1,4,4,4,9,9,16,25,36,49,49,49,64,81,81},squaresSorted(array));
    }
}
