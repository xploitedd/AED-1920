/*
package series.serie2;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static series.serie2.Iterables.groupingEquals;

public class GroupingEqualsTest {

    static Iterable<String> sameWord=Arrays.asList("word","word","word","word");
    static Iterable<String> distinctWords=Arrays.asList("a","b","c","d","e","f");
    static List<String> wordsRepeatedExceptFirst =Arrays.asList("a","b","b","c","c","c",
            "d","d","d","d","e","e","e","e","e","f","f","f","f","f","f");
    static List<String> wordsRepeatedExceptLast =Arrays.asList("a","a","a","a","a","a",
            "b","b","b","b","b","c","c","c","c","d","d","d","e","e","f");



    @Test
    public void groupingEquals_empty_iterable(){
        assertIterableEquals(new ArrayList<>(),groupingEquals(new ArrayList<>()));
    }

    @Test
    public void groupingEquals_singleton_iterable(){
        Iterable<String> singleton=Collections.singletonList("word");
        Iterable<Pair<String, Integer>> it = groupingEquals(singleton);
        Iterable<Pair<String, Integer>> expected= Arrays.asList(new Pair<String,Integer>("word",1));
        assertIterableEquals(expected,it);
    }

    @Test
    public void groupingEquals_sameWord_iterable(){
        Iterable<Pair<String, Integer>> it = groupingEquals(sameWord);
        Iterable<Pair<String, Integer>> expected= Arrays.asList(new Pair<String,Integer>("word",4));
        assertIterableEquals(expected,it);
    }

    @Test
    public void groupingEquals_distinctWords_iterable(){
        Iterable<Pair<String, Integer>> it = groupingEquals(distinctWords);
        ArrayList<Pair<String, Integer>> expected=new ArrayList<Pair<String,Integer>>();
        for(String s:distinctWords) expected.add(new Pair<String,Integer>(s,1));
        assertIterableEquals(expected,it);
    }

    @Test
    public void groupingEquals_distinctWordAtBegin_iterable(){
        Iterable<Pair<String, Integer>> it = groupingEquals(wordsRepeatedExceptFirst);
        ArrayList<Pair<String, Integer>> expected=new ArrayList<Pair<String,Integer>>();
        int i=1;
        for(String s:distinctWords)expected.add(new Pair<String,Integer>(s,i++));
        assertIterableEquals(expected,it);
    }


    @Test
    public void groupingEquals_distinctWordAtEnd_iterable(){
        Iterable<Pair<String, Integer>> it = groupingEquals(wordsRepeatedExceptLast);
        ArrayList<Pair<String, Integer>> expected=new ArrayList<Pair<String,Integer>>();
        int i=6;
        for(String s:distinctWords)expected.add(new Pair<String,Integer>(s,i--));
        assertIterableEquals(expected,it);
    }



}
*/
