package isel.grupo6.s3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Vertex {

    private String id;
    private Set<String> edges;

    // bfs variables
    int distance;
    int shortestPathsCount;
    double dependencyCount;
    double betweenness;
    ArrayList<String> predecessors;

    Vertex(String id) {
        this.id = id;
        edges = new HashSet<>();
    }

    String getId() { return id; }

    void addEdge(Vertex other) {
        if (other.id.equals(id)) return;
        edges.add(other.id);
    }

    Iterable<String> getEdges() { return edges; }

    int getDegree() { return edges.size(); }

}