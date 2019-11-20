package isel.grupo6.s2;

import java.io.*;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;

public class OccurrencesHashMap {

    private static final float LOAD_FACTOR = 0.75f;
    private static final int[] PRIMES = { 5, 7, 13, 17, 19, 23 };

    private Node[] map;
    private int actualPrime = 0;
    private int size;

    public OccurrencesHashMap() { map = new Node[11]; }

    public void loadFile(String fileName, boolean inc) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            br.lines().forEach(line -> {
                String[] words = line.split(" ");
                for (String w : words) {
                    IntegerPair count = get(w);
                    if (count == null) {
                        count = new IntegerPair(inc ? 1 : 0, inc ? 0 : 1);
                    } else {
                        if (inc)
                            count.first = count.first + 1;
                        else
                            count.second = count.second + 1;
                    }

                    put(w, count);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void put(String key, IntegerPair value) {
        if (key == null) return;
        ++size;
        if ((float) size / map.length > LOAD_FACTOR)
            resize();

        int i = indexOf(key);

        if (map[i] == null) {
            Node node = new Node();
            node.key = key;
            node.value = value;
            map[i] = node;
        } else {
            Node kvNode = map[i];
            for (; kvNode != null; kvNode = kvNode.next) {
                if (kvNode.key.equals(key)) {
                    kvNode.value = value;
                    size--;
                    break;
                }
            }

            Node node = new Node();
            node.key = key;
            node.value = value;
            // if the loop finished then there are no equal nodes then we need
            // to insert one at the head of the list
            if (kvNode == null) {
                map[i].prev = node;
                node.next = map[i];
                map[i] = node;
            }
        }
    }

    public IntegerPair get(String key) {
        if (key == null) return null;
        Node node = map[indexOf(key)];
        for ( ; node != null; node = node.next) {
            if (node.key.equals(key))
                return node.value;
        }

        return null;
    }

    public boolean containsKey(String key) { return get(key) != null; }

    public int size() { return size; }

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
                return size;
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
        IntegerPair value;
        Node next;
        Node prev;

        @Override
        public boolean equals(Object o) {
            if (o == null) return false;
            if (this == o) return true;
            if (!(o instanceof Node)) return false;
            Node node = (Node) o;
            // Map.Entry equals as defined by java jdk11 specification
            return (key == null ? node.key == null : key.equals(node.key)) &&
                    (value == null ? node.value == null : value.equals(node.value));
        }

        @Override
        public int hashCode() {
            // Map.Entry hashcode as defined by java jdk11 specification
            return (key == null ? 0 : key.hashCode()) ^ (value == null ? 0 : value.hashCode());
        }
    }

}
