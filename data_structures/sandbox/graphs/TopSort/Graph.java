package graphs.TopSort;

import java.util.*;

public class Graph {
    private class Vertex {
        int key;
        List<Vertex> incoming;
        List<Vertex> outgoing;
        int inDegree = 0;

        public Vertex(int key) {
            this.key = key;
            incoming = new ArrayList<>();
            outgoing = new ArrayList<>();
        }

        public void add(Vertex out) {
            outgoing.add(out);
            out.incoming.add(this);
        }
    }

    private List<Vertex> graph;

    public Graph() {
        graph = new ArrayList<>();
    }

    public List<Vertex> topSort() {
        ArrayList<Vertex> sorted = new ArrayList<>();
        Queue<Vertex> q = new LinkedList<>();

        for (Vertex v : graph) {
            v.inDegree = v.incoming.size();
            if (v.inDegree == 0) {
                q.add(v);
            }
        }

        while (!q.isEmpty()) {
            Vertex u = q.poll();
            sorted.add(u);
            for (Vertex v : u.outgoing) {
                v.inDegree--;
                if (v.inDegree == 0) {
                    q.add(v);
                }
            }
        }

        return sorted;
    }

    public static void Test1() {
        Graph g = new Graph();
        Vertex[] v = new Vertex[8];

        for (int i = 0; i < 8; i++) {
            v[i] = g.new Vertex(i);
            g.graph.add(v[i]);
        }
        
        v[0].add(v[1]);
        v[0].add(v[2]);

        v[1].add(v[3]);
        v[1].add(v[4]);
        v[1].add(v[5]);
        
        v[2].add(v[4]);
        v[2].add(v[7]);
        
        v[3].add(v[5]);
        v[4].add(v[6]);
        v[5].add(v[6]);
        v[6].add(v[7]);

        List<Vertex> sorted = g.topSort();
        for (Vertex vx : sorted) {
            System.out.println(vx.key);
        }
    }
}
