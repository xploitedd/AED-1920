package isel.grupo6.s3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

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

            System.out.println("Loaded " + vertices.size() + " nodes onto the network!");
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
            Stack<String> propagation = new Stack<>();
            // initialize bfs properties on each vertex
            for (Vertex w : vertices.values()) {
                w.dependencyCount = 0;
                w.distance = -1;
                w.shortestPathsCount = 0;
                w.predecessors = new ArrayList<>();
            }

            v.shortestPathsCount = 1;
            v.distance = 0;

            // BFS algorithm
            ArrayDeque<String> deque = new ArrayDeque<>();
            deque.offer(v.getId());
            while (!deque.isEmpty()) {
                Vertex s = vertices.get(deque.poll());
                propagation.push(s.getId());
                for (String n : s.getEdges()) {
                    Vertex k = vertices.get(n);
                    if (k.distance < 0) {
                        // if the vertex has not been visited
                        k.distance = s.distance + 1;
                        deque.offer(n);
                    }

                    if (k.distance == s.distance + 1) {
                        // check if this is a shortest path from k to s
                        k.shortestPathsCount += s.shortestPathsCount;
                        k.predecessors.add(s.getId());
                    }
                }
            }

            // back-propagation to get betweenness using Brandes (2001) formula
            while (!propagation.isEmpty()) {
                Vertex w = vertices.get(propagation.pop());
                for (String n : w.predecessors) {
                    Vertex s = vertices.get(n);
                    s.dependencyCount += ((double) s.shortestPathsCount / w.shortestPathsCount) * (1.0 + w.dependencyCount);
                }

                // divide by two because we are going through twice
                if (!w.getId().equals(v.getId()))
                    w.betweenness += w.dependencyCount / 2;
            }
        }

        for (Vertex v : vertices.values())
            System.out.println(v.getId() + " -> " + v.betweenness);
    }

}
