package isel.grupo6.s1.isbn2;

public class ISBNPriorityQueue {

    private int lastFileId = -1;
    private ISBNComparator comparator;
    private String[] isbnArray;
    private Integer[] fIDs;
    private int elements = 0;

    public ISBNPriorityQueue(int maxSize, ISBNComparator comparator) {
        isbnArray = new String[maxSize];
        fIDs = new Integer[maxSize];
        this.comparator = comparator;
    }

    public void insert(String isbn, int fid) {
        if (elements == isbnArray.length)
            return;

        isbnArray[elements] = isbn;
        fIDs[elements] = fid;

        if (elements != elements / 2) {
            for (int cur = elements; comparator.compare(isbnArray[cur], isbnArray[cur / 2]) < 0; cur /= 2) {
                ISBNUtil.swap(isbnArray, cur, cur / 2);
                ISBNUtil.swap(fIDs, cur, cur / 2);
            }
        }

        ++elements;
    }

    public String pop() {
        if (elements == 0)
            return null;

        // save elements that will be popped
        String isbn = isbnArray[0];
        lastFileId = fIDs[0];
        // change the first element to the last element
        isbnArray[0] = isbnArray[--elements];
        fIDs[0] = fIDs[elements];
        minHeapify(0);
        return isbn;
    }

    public int getFileId() {
        int fileId = lastFileId;
        lastFileId = -1;
        return fileId;
    }

    private void minHeapify(int pos) {
        int lc = 2 * pos + 1, rc = lc + 1;
        int smallest = pos;
        if (lc < elements && comparator.compare(isbnArray[lc], isbnArray[smallest]) < 0)
            smallest = lc;
        if (rc < elements && comparator.compare(isbnArray[rc], isbnArray[smallest]) < 0)
            smallest = rc;

        if (smallest != pos) {
            ISBNUtil.swap(isbnArray, pos, smallest);
            ISBNUtil.swap(fIDs, pos, smallest);
            minHeapify(smallest);
        }
    }

}
