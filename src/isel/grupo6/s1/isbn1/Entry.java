package isel.grupo6.s1.isbn1;

import java.util.Comparator;

public class Entry implements Comparable<Entry> {

    private static final Comparator<String> comparator = ISBNComparator.instance;
    public String isbn;
    public int fileID;

    Entry(String isbn, int fileID) {
        this.isbn = isbn;
        this.fileID = fileID;
    }

    @Override
    public int compareTo(Entry s) { return comparator.compare(isbn, s.isbn); }

}
