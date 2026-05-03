# Graphs

---

- A set of vericies (nodes) connected by edges
- Verticies in sequence connected by edges form a **path**.
- A **cycle** is a path that starts and ends at the same vertex.
- **Acyclictic** graphs have no cycles.
- A **directed graph** has edges that go in one direction.
- A **subgraph** is a graph that consists of a subset of the vertices and edges of the original graph.
- A **DAG** is a directed acyclic graph.
- The **in-degree** and **out-degree** of a node are the number of edges coming into and out of the node, respectively.
- A **complete graph** is a graph where every pair of vertices is connected by an edge. 
- **Weighted graphs** have edges with associated weights.
- A **path minimization** algorithm finds the shortest path between two vertices in a weighted graph.
- **Breadth-first search (BFS)** and **depth-first search (DFS)** are traversal algorithms for graphs.
- **Topological sorting** is an ordering of vertices in a directed acyclic graph such that for every directed edge uv, vertex u comes before v in the ordering.

## Representation
### Adjacency Matrix
- A 2D array where the entry at row i and column j represents the edge from vertex i to vertex j.
- Can store booleans, weights, or Edge objects.
- $\Theta(|V|^2)$

### Adjacency List
- Maps each vertex to it's neighbors
- In format $V \mapsto E_1, E_2, \cdots, E_n$
- $\Theta(|V| + |E|)$

Flipping either of these is called finding the **transpose** of a graph. This named after the matrix operation.

## Breadth-First Search
- Starts at a source vertex and explores all vertices at the present distance level before moving on to vertices at the next distance level.

```java
public class Graph {

    private class Vertex {
        List<Vertex> neighbors;
        boolean discovered;
    }

    ArrayList<Vertex> graph = new ArrayList<>();

    private void ResetGraph() {
        for (Vertex v : graph) {
            v.discovered = false;
        }
    }

    public void BFS(Vertex start) {
        ResetGraph(); // Set all verticies to not discovered
        Queue<Vertex> q = new LinkedList<>();
        q.add(start);
        while (!q.isEmpty()) {
            Vertex v = q.poll();
            for (Vertex neighbor : v.neighbors) {
                if (!neighbor.discovered) {
                    neighbor.discovered = true;
                    q.add(neighbor);
                }
            }
        }
    }
```

### Cormen's BFS
- Each vertex has a color (white, gray, black) to indicate its state of discovery.
- Each vertex also has a distance from the source ($d$) and a parent pointer ($\pi$)

```java
public class Graph {

    private enum Color {
        WHITE, GRAY, BLACK
    }

    private class Vertex {
        List<Vertex> neighbors;
        Color color;
        int dist;
        Vertex parent;
    }

    private ArrayList<Vertex> graph = new ArrayList<>();

    private void ResetGraph() {
        for (Vertex v : graph) {
            v.color = Color.WHITE;
            v.dist = Integer.MAX_VALUE;
            v.parent = null;
        }
    }

    public void BFS(Vertex start) {
        ResetGraph(); // Set all verticies to not discovered
        Queue<Vertex> q = new LinkedList<>();
        start.color = Color.GRAY;
        start.dist = 0;
        q.add(start);
        while (!q.isEmpty()) {
            Vertex v = q.poll();
            for (Vertex neighbor : v.neighbors) {
                if (neighbor.color == Color.WHITE) {
                    neighbor.color = Color.GRAY;
                    neighbor.dist = v.dist + 1;
                    neighbor.parent = v;
                    q.add(neighbor);
                }
            }
            v.color = Color.BLACK;
        }
    }
```

### Analysis
- Initialization: $\Theta(|V|)$
- Let $V'$ and $E'$ be the set of vertices and edges reachable from the source vertex. Then, the loop runs $\Theta(|V'|)$ times and the inner loop runs $\Theta(|E'|)$ times. Thus, the total time complexity is $\Theta(|V| + |E|)$.
- Total runtime: $\Theta(|V| + |E'|)$ or $\Theta(|V| + |E|)$

## Depth-First Search
- Starts at a source vertex and explores as far as possible along each branch before backtracking.

```java
public class Graph {
    private class Vertex {
        List<Vertex> neighbors;
        boolean discovered;
    }

    ArrayList<Vertex> graph = new ArrayList<>();

    private void ResetGraph() {
        for (Vertex v : graph) {
            v.discovered = false;
        }
    }

    public void DFS(Vertex start) {
        ResetGraph();
        for (Vertex v : start.neighbors) {
            if (!v.discovered) {
                v.discovered = true;
                DFS(v);
            }
        }
    }
}
```

### "Timestamp" DFS
- Each vertex has a color, pi, discovery time, and finishing time.

```java
public class Graph {

    private enum Color {
        WHITE, GRAY, BLACK
    }

    private class Vertex {
        List<Vertex> neighbors;
        Color color;
        int discoveryTime;
        int finishingTime;
        Vertex parent;
    }

    private ArrayList<Vertex> graph = new ArrayList<>();
    private int time = 0;

    private void ResetGraph() {
        for (Vertex v : graph) {
            v.color = Color.WHITE;
            v.discoveryTime = 0;
            v.finishingTime = 0;
            v.parent = null;
            time = 1;
        }
    }

    private DFSVertex(Vertex v) {
        v.color = Color.GRAY;
        v.discoveryTime = time++;
        for (Vertex neighbor : v.neighbors) {
            if (neighbor.color == Color.WHITE) {
                neighbor.parent = v;
                DFSVertex(neighbor);
            }
        }
        v.color = Color.BLACK;
        v.finishingTime = time++;
    }

    public void DFS(Vertex start) {
        ResetGraph();
        DFSVertex(start);
    }
}
```

#### Edge Types
- **Tree edge**: An edge that is part of the DFS tree (a parent to a child).
- **Back edge**: An edge that points to an ancestor in the DFS tree. Implies a cycle in the graph.
- **Forward edge**: An edge that points to a descendant in the DFS tree (not a tree edge).
- **Cross edge**: An edge that points to a vertex that is neither an ancestor nor a descendant in the DFS tree.

### Analysis
- Initialization: $\Theta(|V|)$
- Let $V'$ and $E'$ be the set of vertices and edges reachable from the source vertex. Then, the loop runs $\Theta(|V'|)$ times and the inner loop runs $\Theta(|E'|)$ times. Thus, the total time complexity is $\Theta(|V| + |E|)$.
- Total runtime: $\Theta(|V| + |E'|)$ or $\Theta(|V| + |E|)$
- Note that deep trees can cause stack overflow with recursive DFS. In practice, an iterative implementation using a stack is often used to avoid this issue.

---

## Topological Sort
- An ordering of vertices in a directed acyclic graph such that for every directed edge uv, vertex u comes before v in the ordering.
- Used for ordering partially sorted data, such as tasks with dependencies or prerequisites.
- Can only be performed on DAGs, as cycles would create contradictions in the ordering.

### Kahn's Algorithm
- Repeatedly remove vertices with no incoming edges and add them to the topological order.
- If at any point there are no vertices with no incoming edges and the graph is not empty, then the graph has a cycle and a topological sort is not possible.

#### Observations
- A vertex with no incoming edges must go first.
- No need to start from scratch each time:
    - Compute incoming adjacency list just once to start.
    - Grab a set of all 0 in-degree vertices.
    - find new 0 in-degree verticies as vertices are removed.
- Clean up:
    - Remove vertices from incoming adjacency lists, not the graph itself.
    -No need to keep two differend ordered sets, with the same order.
- We don't care what the incoming edges are, only the in-degree. So we can just keep track of the in-degree count for each vertex, and update it as we remove vertices from the graph.
- The ordered list is very flexible (Queue, Stack, LinkedList, etc.) as long as it maintains the order of insertion.

```java
public class Graph {
    private class Vertex {
        int key;
        List<Vertex> incoming = new ArrayList<>();;
        List<Vertex> outgoing = new ArrayList<>();;
        int inDegree = 0;

        public Vertex(int key) {
            this.key = key;S
        }
    }

    private ArrayList<Vertex> graph = new ArrayList<>();

    public List<Vertex> topSort() {
        List<Vertex> sorted = new ArrayList<>();
        Queue<Vertex> q = new LinkedList<>();

        // Compute in-degree for each vertex
        for (Vertex v : graph) {
            v.inDegree = v.incoming.size();
            if (v.inDegree == 0) {
                q.add(v);
            }
        }

        while (!q.isEmpty()) {
            Vertex current = q.poll();
            sorted.add(current);
            for (Vertex v : current.outgoing) {
                v.inDegree--;
                if (v.inDegree == 0) {
                    q.add(v);
                }
            }
        }
        return sorted;
    }
}
```

### DFS-Based Topological Sort
- Perform a DFS on the graph, looking for verticies with no outgoing edges (sinks).
- When a sink is found, add it to the back of the topological order.

---

## Disjoint Sets
- A data structure that keeps track of a set of elements partitioned into a number of disjoint (non-overlapping) subsets.
- Supports two operations:
    - **Find**: Determine which subset a particular element is in. This can be used for determining if two elements are in the same subset.
    - **Union**: Join two subsets into a single subset.

---

## Single Source Shortest Paths
- Given a graph and a source vertex, find the shortest path from the source vertex to all other vertices in the graph.
- Can be solved using BFS for unweighted graphs, Bellman-Ford algorithm for graphs with negative edge weights, and Dijkstra's algorithm for weighted graphs with non-negative edge weights.

### "Relaxation" Technique
- A technique used in shortest path algorithms to iteratively improve the estimate of the shortest path from the source vertex to all other vertices.
- Involves updating the shortest path estimate for a vertex by comparing it to the shortest path estimate for a neighboring vertex plus the weight of the edge connecting them
- If the new estimate is shorter, it is updated. This process is repeated until no more updates can be made, indicating that the shortest paths have been found.

### Bellman-Ford Algorithm
- An algorithm for finding the shortest paths from a single source vertex to all other vertices in a graph, even if the graph contains edges with negative weights.
- The algorithm works by repeatedly relaxing all edges in the graph, and it can detect negative weight cycles. When one of these cycles is found, the distance is set to negative infinity for all vertices reachable from the cycle. Do not report these as valid paths.

### Dijkstra's Algorithm
- Grow a set of "finalized" vertices known to be the closest to the start vertex.
- Relax edges out of a vertex once it is added to finalized set.

```java
public class Graph {
    private class Vertex {
        int key;
        int dist;
        Vertex parent;
        List<Edge> neighbors;

        public Vertex(int key) {
            this.key = key;
            this.dist = Integer.MAX_VALUE;
            this.parent = null;
            this.neighbors = new ArrayList<>();
        }

        public void addNeighbor(Vertex neighbor, int weight) {
            this.neighbors.add(new Edge(this, neighbor, weight));
        }
    }

    private class Edge {
        Vertex from;
        Vertex to;
        int weight;

        public Edge(Vertex from, Vertex to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public boolean relax() {
            if (from.dist + weight < to.dist) {
                if (!pq.contains(to)) {
                    return false;
                }
                to.dist = from.dist + weight;
                to.parent = from;
            }
            return true;
        }
    }

    private ArrayList<Vertex> graph = new ArrayList<>();  
    // v.dist is the key for the pq
    private PriorityQueue<Vertex> pq = new PriorityQueue<>(Comparator.comparingInt(v -> v.dist)); 

    public void dijkstra(Vertex start) {
        start.dist = 0;
        pq.clear();
        
        pq.add(start);
        while (!pq.isEmpty()) {
            Vertex current = pq.poll();
            for (Edge e : current.neighbors) {
                e.relax();
            }
        }
    }
}
```

### Directed Acyclic Graphs
- Topological sort the vertices
- Relax edges of each vertex in topological order

---

## Minimum Spanning Trees
- A spanning tree of a graph is a subgraph that is a tree and connects all the vertices together. 
- A minimum spanning tree is a spanning tree with the smallest possible total edge weight.

### Cuts
- A cut of a graph partitions the vertices into two disjoint sets: $S \subseteq V$ and $V - S$.
- An edge crosses the cut if it connects a vertex in $S$ to a vertex in $V - S$.

#### Cross Cutting Theorem
- the edge with minimum weight crossing any cut is in the minimum spanning tree.
- Any cycle must cross any cut at least twice, so the minimum weight edge crossing the cut cannot be part of a cycle and must be in the minimum spanning tree.

#### Unique Spanning Tree Theorem
- If all edge weights are distinct, then the minimum spanning tree is unique.
- Removing the edge from the tree breaks it into two, defining a cut on $G$.
- If the edge has the minimum weight crossing that cut, it is in the tree.
- If another edge crossing the cut weighs less, that edge can reconnect the tree halves, for a lower weight spanning tree. This is a contradiction, so the edge must be in the tree.
- This argument applies for every edge in the MST, each is required to be in the tree, so the MST is unique.

#### Cycle theorem
- The maximum weight edge in any cycle is not in the minimum spanning tree.

## Kruskal's Algorithm
- Sort edges by weight consider them in order.
- If an edge's vertices are in different trees of the fortest, add it to the forest, combining two trees into one.
- If an edge falls between different trees in the forest, discard it.
- Used disjoint sets to keep track of which vertices are in which trees.
```java
public class Graph {
    private ArrayList<Edge> edges = new ArrayList<>();
    private ArrayList<Vertex> vertices = new ArrayList<>();

    public List<Edge> kruskal() {
        List<Edge> mst = new ArrayList<>();
        DisjointSet ds = new DisjointSet(vertices);
        edges.sort(Comparator.comparin  gInt(e -> e.weight));
        for (Edge e : edges) {
            if (ds.find(e.from) != ds.find(e.to)) {
                mst.add(e);
                ds.union(e.from, e.to);
            }
        }
        return mst;
    }
}
```

## Boruvka's Algorithm
- We start with a forest of single vertes "trees" spanning th graph.
- The minimum weight edge incident on any vertex $v$ is in the MST
- The minimum weight edge with one vertex in the forest tree $T_i$ is in the MST
- One phase:
    - Mark each $T_i$ tree of the forest
    - Find the lightyest edge leaving each $T_i$
    - Add all of those edges to the forest, combining trees as necessary
- Repeat until only one tree remains, which is the MST.

## Prim's Algorithm
- Start with a "special", but arbitrary, vertes $a$. Call it's forest tree $T_a$.
- Add minimun weight edge between $T_A$ and the rest of the graph.
- Keep doin that until $T_a$ spans the whole graph, at which point $T_a$ is the MST.

```java
public class Graph {
    private class Vertex {
        int key = Integer.MAX_VALUE;
        List<Edge> neighbors;
        public Vertex(int key) {
            this.key = key;
            this.neighbors = new ArrayList<>();
        }
    }
    
    private class Edge {
        Vertex to, from;
        int weight;

        public Edge(Vertex from, Vertex to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    private ArrayList<Vertex> graph = new ArrayList<>();
    private PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));

    public List<Edge> prim(Vertex start) {
        List<Edge> mst = new ArrayList<>();
        Set<Vertex> visited = new HashSet<>();
        pq.clear();
        start.key = 0;
        visited.add(start);
        pq.addAll(start.neighbors);
        while (!pq.isEmpty()) { 
            Edge e = pq.poll();
            if (visited.contains(e.to)) {
                continue;
            }
            visited.add(e.to);
            mst.add(e);
            for (Edge neighbor : e.to.neighbors) {
                if (!visited.contains(neighbor.to)) {
                    pq.add(neighbor);
                }
            }
        }
        return mst;
    }
}
```