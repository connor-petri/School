package graphs.BFS;

import java.util.*;

public class Graph {
    private class Vertex {
        private enum Color {
            WHITE, GRAY, BLACK
        }

        private int key;
        private ArrayList<Vertex> neighbors = new ArrayList<>();
        private Color color;
        private int dist;
        private Vertex parent;

        public Vertex(int key) {
            this.key = key;
            reset();
        }

        private void reset() {
            color = Color.WHITE;
            dist = Integer.MAX_VALUE;
            parent = null;
        }
    }

    private ArrayList<Vertex> vertices = new ArrayList<>();
    int time;

    private void BFS() {
        Queue<Vertex> q = new LinkedList<>();
        time = 0;

        for (Vertex v : vertices) {
            if (v.color != Vertex.Color.WHITE) { continue; }
            v.color = Vertex.Color.GRAY;
            v.dist = 0;
            q.add(v);
            while (!q.isEmpty()) {
                Vertex current = q.poll();
                for (Vertex n : current.neighbors) {
                    if (n.color != Vertex.Color.WHITE) {continue;}
                    n.color = Vertex.Color.GRAY;
                    n.dist = current.dist + 1;
                    n.parent = current;
                    q.add(n);
                }
                current.color = Vertex.Color.BLACK;
            }
        }
    }

    public static void Test1() {
        Graph g = new Graph();
        int n = 20;
        Vertex[] v = new Vertex[n];
        for (int i = 0; i < n; i++) {
            v[i] = g.new Vertex(i);
            g.vertices.add(v[i]);
        }

        v[0].neighbors.add(v[1]);
        v[0].neighbors.add(v[2]);
        v[0].neighbors.add(v[3]);

        v[1].neighbors.add(v[4]);
        
        v[2].neighbors.add(v[5]);
        v[2].neighbors.add(v[6]);
        
        v[3].neighbors.add(v[7]);

        v[4].neighbors.add(v[8]);

        v[8].neighbors.add(v[9]);
        v[8].neighbors.add(v[10]);

        v[9].neighbors.add(v[11]);

        v[12].neighbors.add(v[3]);
        v[12].neighbors.add(v[13]);

        v[13].neighbors.add(v[11]);
        v[13].neighbors.add(v[14]);
        v[13].neighbors.add(v[15]);

        v[14].neighbors.add(v[16]);
        v[14].neighbors.add(v[17]);
        v[14].neighbors.add(v[18]);

        v[16].neighbors.add(v[19]);

        g.BFS();

        for (Vertex vert : v) {
			System.out.println(vert.key + " " + vert.color.toString() + " " + (vert.parent != null ? vert.parent.key : "") + " " + vert.dist);
		}
    }
}
