package graphs.PathFinding;

import java.util.*;

public class Graph {
    private class Edge {
        private Vertex to, from;
        private int weight;

        public Edge(Vertex to, Vertex from, int weight) {
            this.to = to;
            this.from = from;
            this.weight = weight;
            edges.add(this);
        }

        public boolean relax() {
            if (from.dist + weight < to.dist) {
                to.dist = from.dist + weight;
                to.parent = from;
                return true;
            }
            return false;
        }
    }

    private class Vertex {
        private int key;
        private List<Edge> vEdges;
        private int dist;
        private Vertex parent;

        private Vertex(int key) {
            this.key = key;
            vEdges = new ArrayList<>();
            dist = Integer.MAX_VALUE;
            vertecies.add(this);
        }

        public void add(Vertex to, int weight) {
            Edge e = new Edge(to, this, weight);
            vEdges.add(e);
        }

        public Edge getEdgeTo(Vertex to) {
            for (Edge e : vEdges) {
                if (e.to.equals(to)) {
                    return e;
                }
            }
            return null;
        }
    }

    public List<Vertex> vertecies = new ArrayList<>();
    public List<Edge> edges = new ArrayList<>();

    public void printShortestPath(Vertex end) {
        if (end.dist == Integer.MIN_VALUE) {
            System.out.println("No shortest path (negative cycle)");
            return;
        }

        Vertex current = end;

        while (current != null) {
            System.out.print("(" + current.key + ")");
            if (current.parent != null) {
                System.out.print(" <-- " + current.parent.getEdgeTo(current).weight + " -- ");
            }
            current = current.parent;
        }
        System.out.println();
    }

    public void bellmanFord(Vertex start) {
        // Initialize
        for (Vertex v : vertecies) {
            v.dist = Integer.MAX_VALUE;
            v.parent = null;
        }
        start.dist = 0;

        boolean changed = true;
        int i = 1;

        // First pass
        while (changed && i <= vertecies.size() - 1) {
            changed = false;
            i++;
            for (Edge e : edges) {
                changed = e.relax() || changed;
            }
        }

        // Check for negative edge cycles
        if (changed) {
            for (Edge e : edges) {
                if (e.from.dist + e.weight < e.to.dist) {
                    e.to.dist = Integer.MIN_VALUE;
                    e.to.parent = e.from;
                }
            }
        }

        // Second pass
        i = 2;
        while (changed && i <= vertecies.size() - 1) {
            changed = false;
            i++;
            for (Edge e : edges) {
                changed = e.relax() || changed;
            }
        }
    }

    public static void TestBellmanFordNoCycle() {
        Graph g = new Graph();
        Vertex[] vs = new Vertex[5];

        for (int i = 0; i < 5; i++) {
            vs[i] = g.new Vertex(i);
        }

        vs[0].add(vs[1], 6);
        vs[0].add(vs[3], 18);
        vs[0].add(vs[4], 3);
        
        vs[1].add(vs[2], 4);
        
        vs[3].add(vs[2], -4);

        vs[4].add(vs[2], -2);

        g.bellmanFord(vs[0]);
        g.printShortestPath(vs[2]);
    }

    public static void TestBellmanFordCycle() {
        Graph g = new Graph();
        Vertex[] vs = new Vertex[5];

        for (int i = 0; i < 5; i++) {
            vs[i] = g.new Vertex(i);
        }

        vs[0].add(vs[1], 6);
        vs[0].add(vs[3], 18);
        vs[0].add(vs[4], 3);
        
        vs[1].add(vs[2], 4);
        
        vs[3].add(vs[2], -4);

        vs[4].add(vs[0], -5); // Negative cycle with vs[0]
        vs[4].add(vs[2], -2);

        g.bellmanFord(vs[0]);
        g.printShortestPath(vs[2]);
    }

    public void dijkstra(Vertex start) {
        ArrayList<Vertex> unfinished = new ArrayList<>(vertecies);
        // Initialize
        for (Vertex v : vertecies) {
            v.dist = Integer.MAX_VALUE;
            v.parent = null;
        }
        start.dist = 0;
        unfinished.sort((v1, v2) -> Integer.compare(v1.dist, v2.dist));

        while (!unfinished.isEmpty()) {
            Vertex current = unfinished.getFirst();
            for (Edge e : current.vEdges) {
                e.relax();
            }
            unfinished.remove(current);
            unfinished.sort((v1, v2) -> Integer.compare(v1.dist, v2.dist));
        }
    }

    public static void TestDijkstra() {
        Graph g = new Graph();
        Vertex[] v = new Vertex[7];
        for (int i = 0; i < 7; i++) {
            v[i] = g.new Vertex(i);
        }

        v[0].add(v[1], 2);
        v[0].add(v[2], 8);
        v[0].add(v[5],10);
        v[1].add(v[2], 1);
        v[1].add(v[4], 12);
        v[1].add(v[5], 2);
        v[2].add(v[1], 7);
        v[2].add(v[3], 1);
        v[3].add(v[4], 2);
        v[5].add(v[6], 9);
        v[6].add(v[4], 1);

        g.dijkstra(v[0]);
        g.printShortestPath(v[4]);
    }
}
