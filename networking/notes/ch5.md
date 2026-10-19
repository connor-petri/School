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