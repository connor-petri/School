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