package isel.grupo6.s2;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DocumentsSimilarity {

    private static final Pattern PATTERN = Pattern.compile("([A-Za-zÀ-ÖØ-öø-ÿ0-9-]+)");
    private static final HashMap<String, IntegerPair> file = new HashMap<>();

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java -jar documentsSimilarity.jar <file1> <file2>");
            return;
        }

        long loadStart = System.currentTimeMillis();
        loadFile(args[0], true);
        loadFile(args[1], false);
        long loadFinish = System.currentTimeMillis() - loadStart;
        System.out.println("Took: " + loadFinish / 1000.0 + " seconds to load!");

        Scanner sc = new Scanner(System.in);
        for ( ; ; ) {
            System.out.print("> ");
            if (sc.hasNext()) {
                long tstart = System.currentTimeMillis();
                String cmd = sc.next();

                switch (cmd) {
                    case "exit":
                        System.exit(0);
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
                        sc.nextLine();
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
     * @param inc true for the first file, false for the second
     */
    private static void loadFile(String fileName, boolean inc) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            br.lines().forEach(line -> {
                // match all words with regex pattern
                Matcher matcher = PATTERN.matcher(line);
                // iterate over each match in this line
                while (matcher.find()) {
                    String w = matcher.group(1);
                    IntegerPair count = file.get(w);
                    if (count == null) {
                        count = new IntegerPair(inc ? 1 : 0, inc ? 0 : 1);
                    } else {
                        if (inc) count.first = count.first + 1;
                        else count.second = count.second + 1;
                    }

                    file.put(w, count);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Print all words from both files
     * (Repeated words are only printed once)
     */
    private static void allWords() {
        for (String k : file.keySet())
            System.out.println(k);
    }

    /**
     * Print words that occur k times in total and are listed in both files
     * @param k times that a word occur
     */
    private static void wordsWithTheSameOccurrence(int k) {
        // there are no words if k = 0
        if (k > 1) {
            // print each word that as k as the number of total
            // occurrences in both files
            for (String key : file.keySet()) {
                IntegerPair pair = file.get(key);
                int f1c = pair.first, f2c = pair.second;
                int total = f1c + f2c;
                if (f1c > 0 && f2c > 0 && total == k)
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
        for (String k : file.keySet()) {
            IntegerPair pair = file.get(k);
            int f1c = pair.first;
            int f2c = pair.second;
            if (f1c != f2c) ++similarity;
        }

        System.out.println("Similarity between files is " + similarity);
        System.out.println("Maximum words: " + (file.size()));
    }

}
