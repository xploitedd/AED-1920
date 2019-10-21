package isel.grupo6.s1.isbn2;

public class ISBNPriorityQueue {

    private Entry[] entries;
    private int elements = 0;

    public ISBNPriorityQueue(int maxSize) {
        entries = new Entry[maxSize];
    }

    /**
     * Inserts an element into the priority queue
     * @param entry element to be inserted
     */
    public void insert(Entry entry) {
        if (elements == entries.length)
            return;

        entries[elements] = entry;
        int parent;
        // increase key for the new element inserted
        if (elements > 0) {
            for (int cur = elements; ; cur = parent) {
                parent = (cur - 1) / 2;
                if (entries[cur].compareTo(entries[parent]) >= 0) break;
                ISBNUtil.swap(entries, cur, parent);
            }
        }

        ++elements;
    }

    /**
     * Pops the lower element of the priority queue (root element)
     * @return root element
     */
    public Entry pop() {
        if (elements == 0)
            return null;

        // save elements that will be popped
        Entry entry = entries[0];
        // change the first element to the last element
        entries[0] = entries[--elements];
        minHeapify(0);
        return entry;
    }

    /**
     * Finds if the heap-property is correct and if it is not then
     * fix it
     * @param pos position where the heap property is violated
     */
    private void minHeapify(int pos) {
        int lc = 2 * pos + 1, rc = lc + 1;
        int smallest = pos;
        if (lc < elements && entries[lc].compareTo(entries[smallest]) < 0)
            smallest = lc;
        if (rc < elements && entries[rc].compareTo(entries[smallest]) < 0)
            smallest = rc;

        if (smallest != pos) {
            ISBNUtil.swap(entries, pos, smallest);
            minHeapify(smallest);
        }
    }

}
