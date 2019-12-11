package isel.grupo6.s3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Friends {

    private static final HashMap<String, Vertex> vertices = new HashMap<>();

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please insert the edges filename.");
            return;
        }

        loadFile(args[0]);
        Scanner sc = new Scanner(System.in);
        for ( ; ; ) {
            System.out.print("> ");
            if (sc.hasNext()) {
                long tstart = System.currentTimeMillis();
                String cmd = sc.next();

                switch (cmd) {
                    case "e":
                        System.exit(0);
                    case "d":
                        obtainDegree();
                        break;
                    case "b":
                        obtainBetweenness();
                        break;
                }

                long took = System.currentTimeMillis() - tstart;
                System.out.println("Took: " + took / 1000.0 + " seconds");
            }
        }
    }

    private static void loadFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            br.lines().forEach(line -> {
                String[] relationships = line.split("(\\t|\\s)+");
                if (relationships.length == 2) {
                    String friend1ID = relationships[0];
                    String friend2ID = relationships[1];

                    Vertex friend1 = vertices.containsKey(friend1ID) ? vertices.get(friend1ID) : new Vertex(friend1ID);
                    Vertex friend2 = vertices.containsKey(friend2ID) ? vertices.get(friend2ID) : new Vertex(friend2ID);

                    friend1.addEdge(friend2);
                    friend2.addEdge(friend1);

                    vertices.put(friend1ID, friend1);
                    vertices.put(friend2ID, friend2);
                }
            });
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private static void obtainDegree() {
        for (Vertex v : vertices.values())
            System.out.println(v.getId() + " -> " + v.getDegree());
    }

    private static void obtainBetweenness() {
        for (Vertex v : vertices.values()) {
            double sum = 0.0;
            for (Vertex s : vertices.values()) {
                // initialize all vertices
                for (Vertex i : vertices.values()) {
                    i.depth = 0;
                    i.predecessors = new ArrayList<>();
                }

                int shortestPathsV = 0;
                int shortestPaths = 0;
                if (!s.getId().equals(v.getId())) {
                    for (Vertex t : vertices.values()) {
                        if (!t.getId().equals(v.getId()) && !t.getId().equals(s.getId())) {
                            // calculate betweenness of v between s and t

                        }
                    }
                }

                // divide by two because we counted the shortest paths twice
                sum += (shortestPathsV / 2.0) / (shortestPaths / 2.0);
            }

            System.out.println(v.getId() + " -> " + sum);
        }
    }

}
