package isel.grupo6.s1;

import org.junit.jupiter.api.Test;

import static isel.grupo6.s1.Arrays.getTheKElementsNearestX;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class GetTheKElementsNearestXTest {
	
	private final static int[] singletonArray=new int[]{5};
	private final static int[] emptyArray={};
	private final static int[] array=new int[] {10,2,4,5,7,6,8,1,9,0,3};
	
	@Test
	public void getTheKElementsNearestX_onEmptyArray(){		
		assertArrayEquals(emptyArray,getTheKElementsNearestX(singletonArray,0,-1,3,10));
	}
	
	@Test
	public void getTheKElementsNearestX_onZeroK(){
		assertArrayEquals(emptyArray,getTheKElementsNearestX(array,0,10,3,0));
	}
	
	@Test
	public  void getTheKElementsNearestX_onSingletonArrayWithEquaTolK(){			
		assertArrayEquals(singletonArray,getTheKElementsNearestX(singletonArray,0,0,3,1));
	}
	

	@Test
	public void getTheKElementsNearestX_onSingletonArrayWithMoreThanK(){		
		assertArrayEquals(singletonArray,getTheKElementsNearestX(singletonArray,0,0,3,7));
	}
	@Test
	public void getTheKElementsNearestX_onArrayWithoutDuplicates(){
		int[] result=getTheKElementsNearestX(array,0,10,3,3);
		int[] expected=new int[]{2,3,4};
		java.util.Arrays.sort(result);
		assertArrayEquals(expected,result);	
		}

		
	}

