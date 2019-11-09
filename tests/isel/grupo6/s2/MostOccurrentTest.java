package isel.grupo6.s2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static isel.grupo6.s2.ListUtils.*;

public class MostOccurrentTest {

	static final Comparator<Integer> CMP_NATURAL_ORDER = Comparator.naturalOrder();


	static final ArrayList<Integer> DIFFERENT_ELEMENTS = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
	static final ArrayList<Integer> EQUAL_ELEMENTS = new ArrayList<Integer>(Arrays.asList(2, 2, 2, 2, 2, 2, 2, 2, 2, 2));
	static final ArrayList<ArrayList<Integer>> SETS_OF_ELEMENTS = new ArrayList<ArrayList<Integer>>(
			Arrays.asList(new ArrayList<Integer>(Arrays.asList(1, 2, 2, 5, 6, 6)),
					new ArrayList<Integer>(Arrays.asList(0, 0, 2, 2, 6)),
					new ArrayList<Integer>(Arrays.asList(6, 6, 7, 7, 7, 7, 8))));
	static final ArrayList<Integer> TWO_SETS_OF_ELEMENTS =new ArrayList<Integer>(Arrays.asList(3,3,2,3,2,2,2,3,2,2));

		
	@Test
	public void  mostOccurrent_empty_array(){
		Node<Integer>[] array=(Node<Integer>[]) new Node[10];
		assertEquals(null, mostOccurrent(array, CMP_NATURAL_ORDER));
	}
	
	@Test
	public void mostOccurrent_singleton_listsWithDifferentElements(){
		Node<Integer>[] array=(Node<Integer>[])new Node[10];
		int i=0;
		for(Integer in: DIFFERENT_ELEMENTS) {
			array[i++] = new Node<Integer>(in);
		}
		assertEquals(0,mostOccurrent(array, CMP_NATURAL_ORDER));
	}

	@Test
	public void mostOccurrent_singleton_listsWithEqualElements() {
		Node<Integer>[] array = (Node<Integer>[]) new Node[10];
		int i = 0;
		for (Integer in : EQUAL_ELEMENTS) {
			array[i++] = new Node<Integer>(in);
		}
			assertEquals(2, mostOccurrent(array, CMP_NATURAL_ORDER));
	}

	@Test
	public void mostOccurrent_singleton_WithMoreThanOneOcurrenceInDifferentLists(){
		Node<Integer>[] array=(Node<Integer>[])new Node[TWO_SETS_OF_ELEMENTS.size()];
		int i=0;
		for(Integer in: TWO_SETS_OF_ELEMENTS){
			array[i++]=new Node<Integer>(in);
		}
		assertEquals(2,mostOccurrent(array, CMP_NATURAL_ORDER));
	}

	@Test
	public void mostOccurrent_listsWithMoreThanOneOcurrence(){
		Node<Integer>[] array=(Node<Integer>[])new Node[3];
		initData(array,SETS_OF_ELEMENTS);
		assertEquals(6,mostOccurrent(array, CMP_NATURAL_ORDER));
	}

	private static void initData(Node<Integer>[] array, ArrayList<ArrayList<Integer>> arraylist){
		if(array.length!=arraylist.size()) return;
		for(int i=0;i<arraylist.size();i++){
			for(int j=arraylist.get(i).size()-1; j>=0;j--){
				Node<Integer> novo=new Node<Integer>(arraylist.get(i).get(j));
				novo.next=array[i];
				if(array[i]!=null)array[i].previous=novo;
				array[i]=novo;
			}
		}
	}

}

