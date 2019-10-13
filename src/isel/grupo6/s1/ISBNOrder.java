package isel.grupo6.s1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ISBNOrder {

    /**
     * Since the ISBN are 13 chars + 4 then the total chars are 17
     * to obtain how many bytes are in a string we do 17 * 2 + 30 = 64 bytes
     * where 30 is the String class overhead
     * For older java versions the overhead is 38 bytes (java <= 7)
     * LINES_PER_CHUNK is the result of the maximum heap size divided by the
     * ISBN bytes
     */
    private static final long LINES_PER_CHUNK = Runtime.getRuntime().maxMemory() / 64;

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Arguments missing: <outputFile> <inputFiles...>");
            return;
        }

        String output = args[0];
        ArrayList<String> isbn = new ArrayList<>();
        for (int i = 1; i < args.length; ++i) {
            String file = args[i];
            try (Scanner sc = new Scanner(new FileInputStream(file))) {

            } catch (FileNotFoundException e) {
                System.out.println("The file " + file + " was not found!");
            }
        }
    }

}
