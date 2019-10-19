package isel.grupo6.s1.isbn1;

import java.util.Comparator;

public class ISBNComparator implements Comparator<String> {

    public static final Comparator<String> instance = new ISBNComparator();

    private ISBNComparator() {}

    @Override
    public int compare(String s1, String s2) {
        // we can implement the comparator we want here
        int len1 = s1.length(), len2 = s2.length();
        for (int i = 0, j = 0; i < len1 && j < len2; ++i, ++j) {
            char c1 = s1.charAt(i), c2 = s2.charAt(j);
            if (c1 == '-') {
                if (++i == len1) break;
                c1 = s1.charAt(i);
            }

            if (c2 == '-') {
                if (++j == len2) break;
                c2 = s2.charAt(j);
            }

            if (c1 != c2) return c1 - c2;
        }

        return len1 - len2;
    }

}
