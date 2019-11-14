package isel.grupo6.s2;

public class FileHashMap<K, V> {

    private static final float LOAD_FACTOR = 0.75f;

    private Node<K, V>[] map;
    private int m;

    @SuppressWarnings("unchecked")
    public FileHashMap() { map = (Node<K, V>[]) new Node[11]; }

    public void put(K key, V value) {
        if (key == null) return;
        ++m;
        if ((float) m / map.length > LOAD_FACTOR)
            resize();

        int i = indexOf(key);
        Node<K, V> node = new Node<>();
        node.key = key;
        node.value = value;
        if (map[i] == null) {
            map[i] = node;
        } else {
            Node<K, V> kvNode = map[i];
            for (; kvNode != null; kvNode = kvNode.next) {
                // for equal objects there are equal hashcodes
                if (kvNode.equals(node) && kvNode.hashCode() == node.hashCode()) {
                    if (kvNode.prev != null) kvNode.prev.next = node;
                    if (kvNode.next != null) kvNode.next.prev = node;
                    break;
                }
            }

            // if the loop finished then there are no equal nodes and we need
            // to insert one at the head of the list
            if (kvNode == null) {
                map[i].prev = node;
                node.next = map[i];
                map[i] = node;
            }
        }
    }

    public V get(K key) {
        if (key == null) return null;
        Node<K, V> node = map[indexOf(key)];
        for ( ; node != null; node = node.next) {
            if (node.key.equals(key))
                return node.value;
        }

        return null;
    }

    public boolean contains(K key) { return get(key) != null; }

    @SuppressWarnings("unchecked")
    private void resize() {
        // resize and save the old map for copy
        Node<K, V>[] oldMap = map;
        map = (Node<K, V>[]) new Node[map.length << 1];
        // copy the elements from the old map to the new one, calculating the indexes again
        for (Node<K, V> kvNode : oldMap) {
            for (Node<K, V> cur = kvNode; cur != null; cur = cur.next) {
                int newIdx = indexOf(cur.key);
                cur.next = map[newIdx];
                if (map[newIdx] != null) map[newIdx].prev = cur;
                map[newIdx] = cur;
            }
        }
    }

    private int indexOf(K key) {
        int i = key.hashCode() % map.length;
        return i < 0 ? i + map.length : i;
    }

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;
        Node<K, V> prev;

        public K getKey() { return key; }
        public V getValue() { return value; }

        public V setValue(V v) {
            V old = value;
            value = v;
            return old;
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
