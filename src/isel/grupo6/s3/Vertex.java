package isel.grupo6.s3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Vertex {

    private String id;
    private Set<String> edges;

    // bfs variables
    int depth;
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

    Iterator<String> getEdgesIterator() { return edges.iterator(); }

    int getDegree() { return edges.size(); }

}
