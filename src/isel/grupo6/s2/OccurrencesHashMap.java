package isel.grupo6.s2;

import java.io.*;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OccurrencesHashMap {

    private static final Pattern PATTERN = Pattern.compile("([A-Za-zÀ-ÖØ-öø-ÿ0-9-]+)");
    private static final float LOAD_FACTOR = 0.75f;
    private static final int[] DIMENSIONS = { 32, 128, 8192, 131072, 524288, 8388608 };

    private Node[] map;
    private int actualDim = 0;
    private int size;

    public OccurrencesHashMap() { map = new Node[11]; }

    /**
     * Loads words in a file to this map
     * @param fileName name of the file to load
     * @param inc true for the first file, false for the second
     */
    public void loadFile(String fileName, boolean inc) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            br.lines().forEach(line -> {
                // match all words with regex pattern
                Matcher matcher = PATTERN.matcher(line);
                // iterate over each match in this line
                while (matcher.find()) {
                    String w = matcher.group(1);
                    IntegerPair count = get(w);
                    if (count == null) {
                        count = new IntegerPair(inc ? 1 : 0, inc ? 0 : 1);
                    } else {
                        if (inc) count.first = count.first + 1;
                        else count.second = count.second + 1;
                    }

                    put(w, count);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Insert a new Key-Value pair into this map
     * @param key key to be inserted
     * @param value corresponding value
     */
    public void put(String key, IntegerPair value) {
        if (key == null || value == null || size + 1 == map.length) return;
        // check if resize is needed
        if ((float) ++size / map.length > LOAD_FACTOR)
            resize();

        // insert the new element
        int i = indexOf(key);
        if (map[i] == null) {
            map[i] = new Node(key, value);
        } else {
            Node kvNode = map[i];
            for (; kvNode != null; kvNode = kvNode.next) {
                if (kvNode.key.equals(key)) {
                    kvNode.value = value;
                    size--;
                    break;
                }
            }

            // if the loop finished then there are no equal nodes then we need
            // to insert one at the head of the list
            if (kvNode == null) {
                Node node = new Node(key, value);
                node.next = map[i];
                map[i] = node;
            }
        }
    }

    /**
     * Get a value from the specified key
     * @param key key to search for
     * @return value if the key is found, null otherwise
     */
    public IntegerPair get(String key) {
        if (key == null) return null;
        // get the index of this key
        Node node = map[indexOf(key)];
        // iterate over the list at the index, and try to find a node with the same key
        for ( ; node != null; node = node.next) {
            if (node.key.equals(key))
                return node.value;
        }

        return null;
    }

    /**
     * Get the current number of elements present in the map
     * @return node count
     */
    public int size() { return size; }

    /**
     * Get a set over the keys of the map
     * @return a new Set that contains all of the available keys
     */
    public Set<String> keySet() {
        return new AbstractSet<>() {
            @Override
            public Iterator<String> iterator() {
                return new Iterator<>() {
                    int curIdx = 0;
                    Node cur = map[curIdx];

                    @Override
                    public boolean hasNext() {
                        // put the current node to a valid node, if possible
                        while (cur == null && curIdx + 1 < map.length)
                            cur = map[++curIdx];

                        // return false if there are no more nodes available to iterate through
                        return cur != null;
                    }

                    @Override
                    public String next() {
                        // check if a node is available, if not throw an exception
                        if (!hasNext())
                            throw new NoSuchElementException();

                        // get the next node on this list
                        Node aux = cur;
                        cur = cur.next;
                        // return the key of the matched node
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

    /**
     * Resizes the map to reduce the chance of having index collisions
     */
    private void resize() {
        if (actualDim > DIMENSIONS.length) {
            System.out.println("[Warning] Cannot grow the map anymore!");
            return;
        }

        Node[] oldMap = map;
        map = new Node[DIMENSIONS[actualDim++]];
        // copy the elements from the old map to the new one, calculating the indexes again
        for (Node kvNode : oldMap) {
            Node next;
            for (Node cur = kvNode; cur != null; cur = next) {
                next = cur.next;
                int newIdx = indexOf(cur.key);
                cur.next = map[newIdx];
                map[newIdx] = cur;
            }
        }
    }

    /**
     * Get the index of the specified key
     * @param key key to obtain the index for
     * @return index of the key
     */
    private int indexOf(String key) {
        int i = key.hashCode() % map.length;
        return i < 0 ? i + map.length : i;
    }

    private static class Node {
        String key;
        IntegerPair value;
        Node next;

        Node(String key, IntegerPair value) {
            this.key = key;
            this.value = value;
        }

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
