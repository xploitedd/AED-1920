package isel.grupo6.s1.isbn2;

import java.io.*;

public class JuntarFicheiros {

    private static final ISBNComparator comparator = ISBNComparator.instance;
    private static final int LINES_PER_CHUNK = 50000;

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Arguments missing: <outputFile> <inputFiles...>");
            return;
        }

        String[] inputs = new String[args.length - 1];
        System.arraycopy(args, 1, inputs, 0, args.length - 1);
        long timeStart = System.currentTimeMillis();
        organizeISBN(args[0], inputs);
        long took = (System.currentTimeMillis() - timeStart) / 1000;
        System.out.println("Took " + took / 60 + "m" + took % 60 + "s to sort!");
    }

    public static void organizeISBN(String out, String... in) {
        long maxLinesPerBigChunk = LINES_PER_CHUNK;
        String[] largeChunkFiles = new String[in.length];
        ISBNArrayList list = new ISBNArrayList(LINES_PER_CHUNK);
        // iterate through each file
        for (int i = 0; i < in.length; ++i) {
            String file = in[i];
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line = reader.readLine(), nextLine;
                int chunks = 0, linec = 0;
                for (; line != null; ++linec, line = nextLine) {
                    // read the next line for checking purposes
                    nextLine = reader.readLine();
                    list.add(line);
                    // if the next line is null then this will be the last line
                    // also, if this is the end of a chunk we need to process it too
                    if (nextLine == null || (linec + 1) % LINES_PER_CHUNK == 0) {
                        // first sort the chunk using the default sorting algorithm
                        list.sort(comparator);
                        // write the sorted list to the chunk file
                        BufferedWriter fw = new BufferedWriter(new FileWriter(file + "." + chunks + ".chunk"));
                        String sorted;
                        while((sorted = list.next()) != null)
                            fw.write(sorted + "\n");

                        fw.flush();
                        fw.close();
                        ++chunks;
                        list.reset();
                    }
                }

                // merge the small chunks into one big chunk file
                String[] chunkNames = new String[chunks];
                for (int j = 0; j < chunks; ++j)
                    chunkNames[j] = file + "." + j + ".chunk";

                String large = in.length == 1 ? out : file + ".chunk";
                mergeChunks(LINES_PER_CHUNK, large, chunkNames);
                // add the big chunk file to an array to process when all files are processed
                largeChunkFiles[i] = large;
                if (maxLinesPerBigChunk < linec) maxLinesPerBigChunk = linec;
            } catch (FileNotFoundException e) {
                System.out.println("The file " + file + " was not found!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (in.length > 1) {
            try {
                // merge the big chunk files into the output file
                // by processing the large chunk files array
                mergeChunks(maxLinesPerBigChunk, out, largeChunkFiles);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void mergeChunks(long totalLines, String out, String... chunks) throws IOException {
        // create a reader for each chuck file
        BufferedReader[] br = new BufferedReader[chunks.length];
        for (int i = 0; i < chunks.length; ++i)
            br[i] = new BufferedReader(new FileReader(chunks[i]));

        ISBNPriorityQueue pq = new ISBNPriorityQueue(chunks.length);
        // initialize the priority queue with the first element from each chunk
        for (int i = 0; i < chunks.length; ++i) {
            String line;
            if ((line = br[i].readLine()) != null)
                pq.insert(new Entry(line, i));
        }

        BufferedWriter fw = new BufferedWriter(new FileWriter(out));
        // sort the chunk files
        for (long i = 0; i < totalLines * chunks.length; ++i) {
            // pick the smaller element from the queue
            Entry entry = pq.pop();
            if (entry == null)
                break;

            // because we removed the smaller element from file #fileId
            // we need to offer another element from the same file
            String next;
            if ((next = br[entry.fileID].readLine()) != null)
                pq.insert(new Entry(next, entry.fileID));

            // write the smaller element to the output
            fw.write(entry.isbn + "\n");
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
