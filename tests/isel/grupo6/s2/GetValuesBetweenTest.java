package isel.grupo6.s2;

import org.junit.jupiter.api.Test;
import java.util.*;

import static isel.grupo6.s2.Iterables.getValuesBetween;
import static org.junit.jupiter.api.Assertions.*;

public class GetValuesBetweenTest{
	private static final Iterable<Integer> SE = Collections.emptyList();
	private static final List<Integer> S1= Collections.unmodifiableList( Arrays.asList( 3, 5, 7, 10, 12 ) );
	private static final int FIRST_VALUE_OF_S1 = S1.get(0);
	private static final int LAST_VALUE_OF_S1 = S1.get(S1.size()-1);




	private static void testValuesBetween( List<Integer> source,  int min, int max ) {
		List<Integer> expected = source.subList(source.indexOf(min), source.lastIndexOf(max)+1);
		assertIterableEquals(expected, getValuesBetween( source, min, max ));
		assertIterableEquals(expected, getValuesBetween( source, min-1, max ));
		assertIterableEquals(expected, getValuesBetween( source, min, max+1 ));
		assertIterableEquals(expected, getValuesBetween( source, min-1, max+1));

	}

	@Test
	public void getValuesBetween_withEmptySequences(){
		assertIterableEquals(new ArrayList<>(), getValuesBetween( SE,  0, 3 ) );
	}

	@Test
	public void getValuesBetween_withOneElementSequence(){
		List<Integer> s = Collections.singletonList( 3 );
		assertIterableEquals(new ArrayList<>(), getValuesBetween( s,  0, 2) );
		assertIterableEquals(new ArrayList<>(), getValuesBetween( s,  4, 10) );
		testValuesBetween(s,  s.get(0), s.get(0));
	}

	@Test
	public void getValuesBetween_allGreaterInSequences(){
		assertIterableEquals(new ArrayList<>(), getValuesBetween( S1,  FIRST_VALUE_OF_S1-2, FIRST_VALUE_OF_S1-1 ) );
	}

	@Test
	public void getValuesBetween_allLessInSequences(){
		assertIterableEquals(new ArrayList<>(), getValuesBetween( S1, LAST_VALUE_OF_S1+1, LAST_VALUE_OF_S1+2 ) );
	}


	@Test
	public void getValuesBetween__allSequence(){
		testValuesBetween(S1,  FIRST_VALUE_OF_S1, LAST_VALUE_OF_S1 );
	}

	@Test
	public void getValuesBetween__onlyFirstOfSequences(){
		testValuesBetween(S1,FIRST_VALUE_OF_S1, FIRST_VALUE_OF_S1 );
	}

	@Test
	public void getValuesBetween__inBeginOfSequences(){
		testValuesBetween( S1,  FIRST_VALUE_OF_S1, S1.get(S1.size()-3) );
	}

	@Test
	public void getValuesBetween_exceptLastElement(){
		testValuesBetween( S1,  FIRST_VALUE_OF_S1, S1.get(S1.size()-2) );
	}


	@Test
	public void getValuesBetween__onlyLastOfSequences(){
		testValuesBetween(S1,  LAST_VALUE_OF_S1, LAST_VALUE_OF_S1 );
	}


	@Test
	public void getValuesBetween__inEndOfSequences(){
		testValuesBetween( S1,S1.get(2), LAST_VALUE_OF_S1 );
	}

	@Test
	public void getValuesBetween_exceptFirstElement(){
		testValuesBetween( S1,  S1.get(1), LAST_VALUE_OF_S1 );
	}

	@Test
	public void getValuesBetween_exceptFirstAndLastElement(){
		testValuesBetween( S1, S1.get(1), S1.get(S1.size()-2) );
	}

	@Test
	public void getValuesBetween_nextInEmptySequence() {
		assertThrows(NoSuchElementException.class, () -> getValuesBetween(SE, 0, 0).iterator().next());
	}

	@Test
	public void getValuesBetween_NextWithNoElements(){
		Iterator<Integer> it=  getValuesBetween(S1,  LAST_VALUE_OF_S1, LAST_VALUE_OF_S1).iterator();
		assertEquals(LAST_VALUE_OF_S1, it.next().intValue());
		assertThrows(NoSuchElementException.class, it::next);
	}

	@Test
	public void getValuesBetween_RemoveElements(){
		Iterator<Integer> it=  getValuesBetween(S1, LAST_VALUE_OF_S1, LAST_VALUE_OF_S1).iterator();
		it.next();
		assertThrows(UnsupportedOperationException.class, it::remove);
	}
}
