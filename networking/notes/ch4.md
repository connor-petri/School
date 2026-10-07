# Chapter 4 - Network Data Plane

---

### Network Layer Services and Protocols
- transport segment from sending to receiving host
    - sender: encapsulates segments into packets
    - receiver: decapsulates packets to extract segments
- network layer protocols in every internet device: hosts, routers
- Routers:
    - examines header fields to determine packet's next hop and forwards it accordingly
    - does not typically inspect the packet's payload

### Two Key Network Layer Functions
- forwarding: moving packets from router's input to appropriate output
- routing: determining the path that packets take from source to destination

### Network Layer: Data Plane, Control Plane
- data plane: responsible for forwarding packets based on the routing table
    - local, per-router decision making based on the routing table
- control plane: responsible for determining the routing table and network topology
    - typically involves routing protocols to exchange information with other routers and build the routing table
- control plane: responsible for determining the routing table and network topology
    - typically involves routing protocols to exchange information with other routers and build the routing table

### Summary
- The network layer is responsible for delivering packets from the source to the destination across multiple networks.
- Forwarding is the local action of moving packets from input to output within a router.
- Routing is the global process of determining the path packets take through the network.
- The data plane handles packet forwarding based on the routing table.
- The control plane determines the routing table and network topology, often using routing protocols.

---

## What's Inside a Router?

### Router Architecture Overview
- consists of input ports, a switching fabric, and output ports
    - input ports: where packets enter the router, typically performing initial processing and buffering
    - switching fabric: the internal network that connects input ports to output ports, responsible for moving packets within the router
    - output ports: where packets exit the router, typically performing final processing and buffering before transmission

#### Input Port Functions
- packet reception: receives incoming packets from the physical layer
- packet buffering: temporarily stores packets in memory while waiting to be processed or forwarded
- packet forwarding: examines the packet header to determine the appropriate output port and forwards the packet accordingly

##### Decentralized Switching
- each input port contains its own switching logic and buffering
- reduces the need for a centralized switching fabric, potentially improving scalability and fault tolerance
- may lead to more complex input port design and increased cost per port

#### Destination-Based Forwarding
- packets are forwarded based on the destination address in the packet header
- the forwarding decision is made by consulting the forwarding table, which maps destination addresses to output ports
- this approach is common in IP routers, where the destination IP address determines the next hop for the packet
- the forwarding table is typically populated and maintained by the control plane, which runs routing protocols to learn about network topology and reachability
- this separation of concerns allows the data plane to focus on fast packet forwarding while the control plane handles the more complex task of network-wide routing decisions

##### Longest Prefix Match
- when multiple entries in the forwarding table match the destination address, the entry with the longest prefix (most specific match) is chosen
- this ensures that packets are forwarded along the most specific route available, which is particularly important in hierarchical IP addressing schemes
- longest prefix match is a key concept in IP routing, where more specific routes take precedence over less specific ones

#### Input Port Queueing
- input port queueing refers to the temporary storage of packets in a queue at the input port while waiting to be processed or forwarded
- helps manage congestion and ensures fair access to the switching fabric
- common queueing disciplines include first-in-first-out (FIFO), priority queueing, and weighted fair queueing (WFQ)
- Used if switch fabric slower than input ports, to prevent packet loss and manage congestion within the router

### Output Port Functions
- packet transmission: sends packets out to the physical layer for delivery to the next hop or destination
- packet buffering: temporarily stores packets in memory while waiting to be transmitted, helping to manage congestion and ensure smooth traffic flow
- packet scheduling: determines the order in which packets are transmitted, often based on priority or quality of service (QoS) requirements
- error checking: verifies the integrity of packets before transmission, typically using checksums or cyclic redundancy checks (CRC)
- congestion management: monitors and controls the flow of packets to prevent congestion and ensure efficient use of network resources

#### Output Port Queueing
- output port queueing refers to the temporary storage of packets in a queue at the output port while waiting to be transmitted
- helps manage congestion and ensures fair access to the transmission medium
- common queueing disciplines include first-in-first-out (FIFO), priority queueing, and weighted fair queueing (WFQ)
- used to prevent packet loss and manage congestion when the output link is slower than the incoming traffic rate
- can also be used to implement quality of service (QoS) policies by prioritizing certain types of traffic over others

### How Much Buffering is Needed?
- RFC 3439: Average buffering equal to typical RTT times link capacity $C$
- More recent recommendation: with $N$ flows, buffering equal to $\frac{RTT \cdot C}{\sqrt{N}}$

### Switching Fabric
- the switching fabric is the internal mechanism that connects input ports to output ports within a router or switch
- it is responsible for transferring packets from the input queues to the appropriate output queues based on the forwarding decisions made by the input port
- common types of switching fabrics include shared memory, crossbar, and bus-based architectures
- the performance of the switching fabric can significantly impact the overall throughput and latency of the router or switch
- efficient design and management of the switching fabric are crucial for handling high-speed network traffic and minimizing packet loss

