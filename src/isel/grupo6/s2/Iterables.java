package isel.grupo6.s2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Iterables {

    public static Iterable<Pair<String, Integer>> groupingEquals(Iterable<String> words) {
        return () -> new Iterator<>() {
            Iterator<String> cur = words.iterator();
            String nextWord = null;

            @Override
            public boolean hasNext() { return nextWord != null || cur.hasNext(); }

            @Override
            public Pair<String, Integer> next() {
                int count = 1;
                String word;
                if (nextWord == null) {
                    word = cur.next();
                } else {
                    word = nextWord;
                    nextWord = null;
                }

                while (cur.hasNext()) {
                    String n = cur.next();
                    if (word.equals(n)) {
                        ++count;
                    } else {
                        nextWord = n;
                        break;
                    }
                }

                return new Pair<>(word, count);
            }
        };
    }

    public static Iterable<Integer> getValuesBetween(Iterable<Integer> src, int l, int r) {
        return () -> new Iterator<>() {
            Iterator<Integer> cur = src.iterator();
            Integer next;

            @Override
            public boolean hasNext() {
                if (next != null)
                    return next >= l && next <= r;

                while (cur.hasNext()) {
                    int a = cur.next();
                    if (a >= l && a <= r) {
                        next = a;
                        return true;
                    }
                }

                return false;
            }

            @Override
            public Integer next() {
                if (!hasNext())
                    throw new NoSuchElementException();

                int ret = next;
                next = cur.hasNext() ? cur.next() : null;
                return ret;
            }
        };
    }

}
