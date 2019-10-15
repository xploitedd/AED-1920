package isel.grupo6.s1;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class ISBNOrder {

    /**
     * Since the ISBN are 13 chars + 4 then the total chars are 17
     * to obtain how many bytes are in a string we do 17 * 2 + 30 = 64 bytes
     * where 30 is the String class overhead
     * For older java versions the overhead is 38 bytes (java <= 7)
     * LINES_PER_CHUNK is the result of the maximum heap size divided by the
     * ISBN bytes
     */
    private static long LINES_PER_CHUNK = 300000;//Runtime.getRuntime().freeMemory() / 256;

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Arguments missing: <outputFile> <inputFiles...>");
            return;
        }

        if (LINES_PER_CHUNK > Integer.MAX_VALUE)
            LINES_PER_CHUNK = Integer.MAX_VALUE;

        long timeStart = System.currentTimeMillis();
        organizeISBN(args[0], Arrays.copyOfRange(args,1, args.length));
        long took = (System.currentTimeMillis() - timeStart) / 1000;
        System.out.println("Took " + took / 60 + "m" + took % 60 + "s to organize!");
    }

    public static void organizeISBN(String out, String... in) {
        int totalChunks = 0;
        String[] largeChunkFiles = new String[in.length];
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < in.length; ++i) {
            String file = in[i];
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line = reader.readLine(), nextLine;
                int chunks = 0;
                for (int j = 0; line != null; ++j, line = nextLine) {
                    nextLine = reader.readLine();
                    list.add(line);
                    if (nextLine == null || (j + 1) % LINES_PER_CHUNK == 0) {
                        list.sort(String::compareTo);
                        BufferedWriter fw = new BufferedWriter(new FileWriter(file + "." + chunks + ".chunk"));
                        for (int k = 0; k < list.size(); ++k)
                            fw.write(list.get(k) + "\n");

                        fw.flush();
                        fw.close();
                        ++chunks;
                        list = new ArrayList<>();
                    }
                }

                String[] chunkNames = new String[chunks];
                for (int j = 0; j < chunks; ++j)
                    chunkNames[j] = file + "." + j + ".chunk";

                String large = file + ".chunk";
                mergeChunks(LINES_PER_CHUNK, large, chunkNames);
                largeChunkFiles[i] = large;
                totalChunks += chunks;
            } catch (FileNotFoundException e) {
                System.out.println("The file " + file + " was not found!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            mergeChunks(totalChunks * LINES_PER_CHUNK, out, largeChunkFiles);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void mergeChunks(long totalLines, String out, String... chunks) throws IOException {
        BufferedWriter fw = new BufferedWriter(new FileWriter(out));
        BufferedReader[] br = new BufferedReader[chunks.length];
        for (int i = 0; i < chunks.length; ++i)
            br[i] = new BufferedReader(new FileReader(chunks[i]));

        PriorityQueue<String> pq = new PriorityQueue<>();
        for (long i = 0; i < totalLines; ++i) {
            for (int j = 0; j < chunks.length; ++j) {
                String line;
                if ((line = br[j].readLine()) != null)
                    pq.offer(line);
            }

            for (int j = 0; j < pq.size(); ++j)
                fw.write(pq.poll() + "\n");
        }

        // poll the remaining chunks.length of the priority queue
        for (int j = 0; j < chunks.length - 1; ++j) {
            String item = pq.poll();
            if (item != null && !item.equals(""))
                fw.write(item + "\n");
        }

        fw.flush();
        fw.close();

        // close inputs and delete temporary files
        for (int i = 0; i < chunks.length; ++i) {
            br[i].close();
            if (!new File(chunks[i]).delete())
                System.out.println("Could not delete temporary file " + chunks[i]);
        }
    }

}
