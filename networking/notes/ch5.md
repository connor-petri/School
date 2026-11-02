# Chapter 5: Network layer control plane

---

### Router Algorithm Classification
- Global algorithms: make routing decisions based on the entire network topology
- Distributed algorithms: make routing decisions based on local information and exchange of routing messages with neighboring routers
    - Examples include distance-vector routing and link-state routing
        - Distance-vector routing: routers share their routing tables with neighbors periodically
        - Link-state routing: routers share information about the state of their links with all other routers in the network
- Static: route changes slowly over time
- Dynamic: route changes frequently based on network conditions

---

### Dijkstra's Link-State routing Algorithm
- Centralized: netork topology, link costs known to all nodes
    - Accomplished via link state broadcast
    - All nodes have the same info
- Computes least cost paths from source note to all other nodes
    - Gives forwaring table for that node
- Iterative: After $k$ iterations, know least cost path to $k$ destinations.

#### Notation
- $c_{x,y}$ - direct link cost from node x to node y
- $D(v)$ - current estimate of cost of least-cost-path from source to destination $v$
- $p(v)$ - predecessor
- $N'$ - current path estimate

#### Oscillations Possible
- When link costs depend on traffic volume, route oscillations are possible

## Distance Vector Algorithm
- Based on Bellman-Ford, a dynamic algorithm that calculates the shortest distance between a source node to every possible destination node.
- Let $D_x(y)$ be the cost of least-cost path from $x$ to $y$
    - $D_x(y) = \min_v(c_{x,v} + D_v(y))$
- From time-to-time, each node sends its own distance vector estimate to its neighbors

### Each Node
- Wait for change in local link cost or message from neighbor
- Recompute DV estimates using DV received from neighbor
- If DV to any destination has changed, notify neighbors
- (Loop)

#### "Relaxation" Technique
- A technique used in shortest path algorithms to iteratively improve the estimate of the shortest path from the source vertex to all other vertices.
- Involves updating the shortest path estimate for a vertex by comparing it to the shortest path estimate for a neighboring vertex plus the weight of the edge connecting them
- If the new estimate is shorter, it is updated. This process is repeated until no more updates can be made, indicating that the shortest paths have been found.

#### Bellman-Ford Algorithm
- An algorithm for finding the shortest paths from a single source vertex to all other vertices in a graph, even if the graph contains edges with negative weights.
- The algorithm works by repeatedly relaxing all edges in the graph, and it can detect negative weight cycles. When one of these cycles is found, the distance is set to negative infinity for all vertices reachable from the cycle. Do not report these as valid paths.

### Poison Reverse
- A technique used in distance vector routing to prevent routing loops.
- When a router learns that a route to a destination is through a particular neighbor, it advertises that route to that neighbor with an infinite metric (poisoned) to indicate that the route is not valid through that neighbor.
- Helps to quickly propagate information about unreachable destinations and avoid the count-to-infinity problem.

### Link State vs Distance Vector
- Link State:
    - $n$ nodes, $O(n^2)$ messages sent
    - Each node independently computes shortest paths using Dijkstra's algorithm
- Distance Vector:
    - $n$ nodes, $O(n)$ messages sent per link cost change
    - Nodes iteratively exchange distance vectors with neighbors until convergence
    - Susceptible to routing loops and the count-to-infinity problem
    - Requires mechanisms like split horizon or poison reverse to mitigate routing loops

---

## Making Routing Scalable
- So far, our routing has been idealized
    - All routers equal
    - Network is flat, no hierarchy
- In reality, networks are hierarchical with different levels of routers and areas to improve scalability and manageability
    - Core routers handle high-level routing between areas
    - Edge routers handle routing within an area
    - Hierarchical routing reduces the size of routing tables and limits the scope of routing updates
- Different network admins may want to manage their own areas independently without affecting the entire network
    - This leads to the concept of autonomous systems (AS) in large-scale networks, where each AS can implement its own routing policies and protocols.
- Inter-AS routing is typically handled by protocols like BGP (Border Gateway Protocol), which allows different ASes to exchange routing information while respecting their individual policies.
- Gateway routers connect different autonomous systems and are responsible for exchanging routing information between them, typically using inter-AS routing protocols like BGP.

### Inter-AS Routing Algorithms
- RIP (Routing Information Protocol)
    - Distance vector protocol used for inter-AS routing
    - Exchanges routing information between gateway routers
    - Limited to smaller networks due to scalability issues and slow convergence
- EIGRP (Enhanced Interior Gateway Routing Protocol)
    - Advanced distance vector protocol used for inter-AS routing
    - Combines features of distance vector and link state protocols
    - Supports rapid convergence and scalability
    - Uses the Diffusing Update Algorithm (DUAL) to compute loop-free paths
- OSPF (Open Shortest Path First) (most common link-state protocol)
    - Link-state protocol used for inter-AS routing
    - Maintains a complete map of the network topology
    - Computes shortest paths using Dijkstra's algorithm
    - Supports hierarchical routing with areas to improve scalability
    - Provides fast convergence and loop-free routing

### OSPF (Open Shortest Path First) Routing
- "Open" standard
    - Developed as an open standard for link-state routing within an autonomous system
    - Widely used in enterprise and service provider networks
- Classic link-state protocol
    - Each router maintains a complete map of the network topology
    - Routers exchange link-state advertisements (LSAs) to update the network map
    - Computes shortest paths using Dijkstra's algorithm
    - Supports hierarchical routing with areas to improve scalability
    - Provides fast convergence and loop-free routing
- Security:
    - All OSPF messages authenticated to prevent malicious intrusion

#### Hierarchical OSPF
- Two-Level Hierarchy
    - OSPF supports a two-level hierarchical structure with a backbone area (Area 0) and multiple non-backbone areas
    - Backbone area connects all other areas and facilitates inter-area routing
    - Non-backbone areas connect to the backbone area through area border routers (ABRs)
    - Hierarchical design reduces routing table size and limits the scope of routing updates within each area

---

## Routing Among ISPs: BGP
### BGP (Boarder Gateway Protocol)
- The de-facto inter-domain routing protocol
- The "glue" that holds the Internet together by enabling different autonomous systems to exchange routing information
- Allows subnet to advertise its existence, and the destinations it can reach, to the rest of the Internet

#### Path Vector Protocol
- What BGP keeps from distance vector ogic:
    - Routers earn from their neighbors the paths to various destinations, similar to distance vector protocols
    - Routes spread by advertisement, one hop at a time
- What Changes: The Path Vector
    - Distance vector advertises a destination and the cost to reach it
    - BGP advertises the entire path (sequence of ASes) to reach a destination, rather than just the cost
    - A router that sees its own AS in the path drops the route, so AS-level loops are prevented

### Cost-Driven vs Policy-Driven Routing
- Cost-Driven Routing:
    - Traditional routing protocols like OSPF and RIP focus on finding the shortest path based on metrics such as hop count or link cost
    - The primary goal is to optimize network performance and efficiency
- Policy-Driven Routing:
    - BGP allows network administrators to define routing policies based on business agreements, security considerations, and other non-technical factors
    - Routing decisions are influenced by policies rather than purely by path cost'

### BGP Goals
- Obtain Destination network reachability info from  neighboring ASes
- Propagate this reachability information to all AS-internal routers
- Determine routes to other networks based on reachability information and routing policies

### BGP Basics
- BGP Session: two BGP routers ("peers") exchanged BGP messages over semi-permanent TCP connection
- BGP Message Types: BGP uses several message types to establish and maintain sessions, including OPEN, UPDATE, NOTIFICATION, and KEEPALIVE messages


### Why Different Intra-, Inter-AS routing?
- Policy:
    - Inter-AS: admin wants control oer how its traffic routed, who routes through its network
    - Intra-AS: Single admin, so policy is less of an issue
- Scale:
    - Hierarchical routing within an AS helps manage scale by limiting the scope of routing updates and reducing the size of routing tables
- Performance:
    - Intra-AS: can focus on performance
    - Inter-AS: policy dominates over performance

---

## ICMP (Internet Control Message Protocol)
- Used by hosts and routers to communicate network-level information
    - Error reporting: Unreachable host, network, or port
    - Echo request/reply (used by the ping utility)
- Netowrk-layer "above" IP:
    - ICMP messages carried in IP datagrams
- ICMP message: type, code plus first 8 bytes of IP datagram causing error
    - Type: indicates the kind of message (e.g., Destination Unreachable, Echo Request)
    - Code: provides additional context for the type (e.g., network unreachable, host unreachable)

### Traceroute and ICMP
- Source sends sets of UDP segments to destination
- Datagram in *n*th set arrives to *n*th router along the path
- That router returns ICMP Time Exceeded message to source
- Source uses ICMP messages to infer the route to destination-hop by hop
- When ICMP message arrives at source: record RTTs
