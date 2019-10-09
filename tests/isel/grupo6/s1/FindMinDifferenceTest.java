package isel.grupo6.s1;

import org.junit.jupiter.api.Test;
import static isel.grupo6.s1.Arrays.findMinDifference;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindMinDifferenceTest {
	
	@Test
	public void findMinDifference_OnBothEmptyArrays(){
		int[] v1 = new int[0];
		int[] v2 = new int[0];
		int diff = findMinDifference(v1, v2);
		assertEquals(-1, diff);

	}
	
	@Test
	public void findMinDifference_OnAnArraysWithOneElementAndEmpty(){
		int[] v1 = new int[]{1};
		int[] v2 = new int[0];
		int diff = findMinDifference(v1, v2);
		assertEquals(-1, diff);	
		diff = findMinDifference(v2, v1);
		assertEquals(-1, diff);	
	}
	
	@Test
	public void findMinDifference_OnAnArraysWithOneElement(){
		int[] v1 = new int[]{-1};
		int[] v2 = new int[]{-1};
		int diff = findMinDifference(v1, v2);
		assertEquals(0, diff);	
		diff = findMinDifference(v2, v1);
		assertEquals(0, diff);	
	}
	
	@Test
	public void findMinDifference_OnAnArraysWithRandomElements(){
		int[] v1 = new int[]{-23, -10, 34, 68};
		int[] v2 = new int[]{-15, -12, 32, 33};
		int diff = findMinDifference(v1, v2);
		assertEquals(1, diff);	
		diff = findMinDifference(v2, v1);
		assertEquals(1, diff);	
	}
	
	@Test
	public void findMinDifference_OnAnArraysWithRandomNegativeElements(){
		int[] v1 = new int[]{-99,-81,-70,-68,-45,-27,-3};
		int[] v2 = new int[]{-84, -35,-25,-16,-9};
		int diff = findMinDifference(v1, v2);
		assertEquals(2, diff);	
		diff = findMinDifference(v2, v1);
		assertEquals(2, diff);	
	}
	
	@Test
	public void findMinDifference_OnAnArraysWithRandomPositiveElements(){
		int[] v1 = new int[]{3,27,45,68,70,81,99};
		int[] v2 = new int[]{9,16,25,35,75,84};
		int diff = findMinDifference(v1, v2);
		assertEquals(2, diff);	
		diff = findMinDifference(v2, v1);
		assertEquals(2, diff);	
	}
	
}
