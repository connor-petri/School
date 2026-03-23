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
- 