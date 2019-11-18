package isel.grupo6.s2;

import java.io.*;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;

public class SimilarityHashMap {

    public static final int NON_EXISTANT = -1;
    private static final float LOAD_FACTOR = 0.75f;
    private static final int[] PRIMES = { 5, 7, 13, 17, 19, 23 };

    private Node[] map;
    private int actualPrime = 0;
    private int n;

    public SimilarityHashMap() { map = new Node[11]; }

    public void loadFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            br.lines().forEach(line -> {
                String[] words = line.split(" ");
                for (String w : words) {
                    int count = get(w);
                    count = (count != NON_EXISTANT ? count + 1 : 1);
                    put(w, count);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void put(String key, int value) {
        if (key == null) return;
        ++n;
        if ((float) n / map.length > LOAD_FACTOR)
            resize();

        int i = indexOf(key);
        Node node = new Node();
        node.key = key;
        node.value = value;

        if (map[i] == null) {
            map[i] = node;
        } else {
            Node kvNode = map[i];
            for (; kvNode != null; kvNode = kvNode.next) {
                if (kvNode.key.equals(key)) {
                    kvNode.value = node.value;
                    n--;
                    break;
                }
            }

            // if the loop finished then there are no equal nodes then we need
            // to insert one at the head of the list
            if (kvNode == null) {
                map[i].prev = node;
                node.next = map[i];
                map[i] = node;
            }
        }
    }

    public int get(String key) {
        if (key == null) return NON_EXISTANT;
        Node node = map[indexOf(key)];
        for ( ; node != null; node = node.next) {
            if (node.key.equals(key))
                return node.value;
        }

        return NON_EXISTANT;
    }

    public boolean containsKey(String key) { return get(key) != NON_EXISTANT; }

    public int size() { return n; }

    public Set<String> keySet() {
        return new AbstractSet<>() {
            @Override
            public Iterator<String> iterator() {
                return new Iterator<>() {
                    int curIdx = 0;
                    Node cur = map[curIdx];

                    @Override
                    public boolean hasNext() {
                        while (cur == null && curIdx + 1 < map.length)
                            cur = map[++curIdx];

                        return cur != null;
                    }

                    @Override
                    public String next() {
                        if (!hasNext()) return null;
                        Node aux = cur;
                        cur = cur.next;
                        return aux.key;
                    }
                };
            }

            @Override
            public int size() {
                return n;
            }
        };
    }

    private void resize() {
        if (actualPrime > PRIMES.length)
            throw new RuntimeException("File size too large");

        Node[] oldMap = map;
        map = new Node[((int) Math.pow(2, PRIMES[actualPrime++])) - 1];
        // copy the elements from the old map to the new one, calculating the indexes again
        for (Node kvNode : oldMap) {
            Node next;
            for (Node cur = kvNode; cur != null; cur = next) {
                next = cur.next;
                int newIdx = indexOf(cur.key);
                cur.next = map[newIdx];
                if (map[newIdx] != null) map[newIdx].prev = cur;
                map[newIdx] = cur;
            }
        }
    }

    private int indexOf(String key) {
        int i = key.hashCode() % map.length;
        return i < 0 ? i + map.length : i;
    }

    private static class Node {
        String key;
        int value;
        Node next;
        Node prev;

        @Override
        public boolean equals(Object o) {
            if (o == null) return false;
            if (this == o) return true;
            if (!(o instanceof Node)) return false;
            Node node = (Node) o;
            // Map.Entry equals as defined by java jdk11 specification
            return (key == null ? node.key == null : key.equals(node.key)) && value == node.value;
        }

        @Override
        public int hashCode() {
            // Map.Entry hashcode as defined by java jdk11 specification
            return (key == null ? 0 : key.hashCode()) ^ (value * 31);
        }
    }

}
