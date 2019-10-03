package isel.grupo6.s1;

public class CommonPrefix {

    public static void main(String[] args) {
        String[] array = { "agendar", "dia", "teste" };
        System.out.println(greaterCommonPrefix(array, 0, 2, "aaaaa"));
    }

    public static String greaterCommonPrefix(String[] v, int l, int r, String word) {
        if (l == r) {
            String cur = v[l];
            if (cur.startsWith(word))
                return cur;
            else
                return "";
        }

        // calculating the mid this way prevents the bug described
        // in https://ai.googleblog.com/2006/06/extra-extra-read-all-about-it-nearly.html
        int mid = (l + r) >>> 1;
        String prefix = greaterCommonPrefix(v, mid + 1, r, word);
        if (prefix.length() == 0)
            prefix = greaterCommonPrefix(v, l, mid, word);

        return prefix;
    }

}
