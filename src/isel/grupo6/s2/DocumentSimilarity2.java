package isel.grupo6.s2;

import java.util.Scanner;

public class DocumentSimilarity2 {

    private static final SimilarityHashMap file1 = new SimilarityHashMap();
    private static final SimilarityHashMap file2 = new SimilarityHashMap();

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java -jar documentsSimilarity.jar <file1> <file2>");
            return;
        }

        long loadStart = System.currentTimeMillis();
        file1.loadFile(args[0]);
        file2.loadFile(args[1]);
        long loadFinish = System.currentTimeMillis() - loadStart;
        System.out.println("Took: " + loadFinish / 1000.0 + " seconds to load!");

        Scanner sc = new Scanner(System.in);
        label:
        for ( ; ; ) {
            System.out.print("> ");
            if (sc.hasNext()) {
                long tstart = System.currentTimeMillis();
                String cmd = sc.next();

                switch (cmd) {
                    case "exit":
                        break label;
                    case "allWords":
                    case "aw":
                        allWords();
                        break;
                    case "wordsWithTheSameOccurrence":
                    case "wso":
                        wordsWithTheSameOccurrence(sc.nextInt());
                        break;
                    case "similarity":
                    case "s":
                        similarity();
                        break;
                    default:
                        if (sc.hasNext()) sc.next();
                        System.out.println("Command not available. Available Commands:");
                        System.out.println("allWords - list all words that are at least in one file.");
                        System.out.println("wordsWithTheSameOccurrence k - list words that are in both files with k occurrences.");
                        System.out.println("similarity - returns the similarity degree between the files.");
                        break;
                }

                long took = System.currentTimeMillis() - tstart;
                System.out.println("Took: " + took / 1000.0 + " seconds");
            }
        }
    }

    /**
     * Print all words from both files
     * (Repeated words are only printed once)
     */
    private static void allWords() {
        for (String k : file1.keySet())
            System.out.println(k);

        for (String k : file2.keySet()) {
            if (!file1.containsKey(k))
                System.out.println(k);
        }
    }

    /**
     * Print words that occur k times in total and are listed in both files
     * @param k times that a word occur
     */
    private static void wordsWithTheSameOccurrence(int k) {
        // there are no words if k = 0
        if (k > 0) {
            // print each word that as k as the number of total
            // occurrences in both files
            for (String key : file1.keySet()) {
                int v2 = file2.get(key);
                int total = file1.get(key) + v2;
                if (v2 != SimilarityHashMap.NON_EXISTANT && total == k)
                    System.out.println(key);
            }
        }
    }

    /**
     * Print the similarity between both files
     */
    private static void similarity() {
        int similarity = 0;
        // iterate each file and for each
        // difference increment the similarity in 1
        for (String k : file1.keySet()) {
            int v1 = file1.get(k);
            int v2 = file2.get(k);
            if (v1 != v2) ++similarity;
        }

        for (String k : file2.keySet()) {
            if (!file1.containsKey(k)) ++similarity;
        }

        System.out.println("Similarity between files is " + similarity);
        System.out.println("Maximum words: " + (file1.size() + file2.size()));
    }

}
