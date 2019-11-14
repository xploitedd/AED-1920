package isel.grupo6.s2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class DocumentsSimilarity {

    private static final HashMap<String, Integer> file1 = new HashMap<>();
    private static final HashMap<String, Integer> file2 = new HashMap<>();

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java -jar documentsSimilarity.jar <file1> <file2>");
            return;
        }

        loadFile(args[0], file1);
        loadFile(args[1], file2);

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
     * Loads words in a file to the specified map
     * @param fileName name of the file to load
     * @param map map to load the words to
     */
    private static void loadFile(String fileName, HashMap<String, Integer> map) {
        try (Scanner sc = new Scanner(new FileInputStream(fileName))) {
            while (sc.hasNext()) {
                String w = sc.next();
                Integer count = map.get(w);
                count = (count != null ? count + 1 : 1);
                map.put(w, count);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Print all words from both files
     * (Repeated words are only printed once)
     */
    private static void allWords() {
        for (String k : file1.keySet()) {
            if (!file2.containsKey(k))
                System.out.println(k);
        }

        for (String k : file2.keySet()) {
            if (!file1.containsKey(k))
                System.out.println(k);
        }
    }

    /**
     * Print words that occur k times in total
     * @param k times that a word occur
     */
    private static void wordsWithTheSameOccurrence(int k) {
        // there are no words if k = 0
        if (k > 0) {
            // print each word that as k as the number of total
            // occurrences in both files
            for (String key : file1.keySet()) {
                int v2 = file2.getOrDefault(key, 0);
                int total = file1.get(key) + v2;
                if (v2 != 0 && total == k)
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
            Integer v2 = file2.getOrDefault(k, 0);
            if (v1 != v2) ++similarity;
        }

        for (String k : file2.keySet()) {
            if (!file1.containsKey(k)) ++similarity;
        }

        System.out.println("Similarity between files is " + similarity);
        System.out.println("Maximum words: " + (file1.size() + file2.size()));
    }

}
