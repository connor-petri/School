# Chapter 3: Transport Layer

---

## Transport Services and Protocols
- Provide *logical communication* between application processes running on different hosts

### Transport vs Network Layer Services and Protocols
- Household Analogy
    - host = house
    - processes = resident of house
    - app messages = letters to the resident of the house
- Hosts are the machine or VM the target of the packet is running on
- Ports are which process within the machine the packet goes to
- The app message is the data within the packet

### Two principal Internet transport protocols
- TCP: Transmission Control Protocol
    - Reliable, in order delivery
    - Congestion control
    - Flow control
    - Connection setup
- UDP: User Datagram Protocol
    - unreliable, unordered delivery
    - no-frills extension of "best-effort" IP
- Not provided by transport layer
    - Delay guarantees
    - Bandwidth guarantees

### Multiplexing/Demultiplexing
- **Multiplexing**: The process of gathering data from multiple application processes, encapsulating it into transport layer segments, and passing it to the network layer.
- **Demultiplexing**: The process of receiving transport layer segments from the network layer, extracting the data, and delivering it to the appropriate application process based on port numbers.

### Connection-Oriented vs Connectionless Transport
- **Connection-Oriented Transport**: Establishes a connection before transmitting data, ensures reliable and ordered delivery, and typically includes flow and congestion control. Example: TCP.
- **Connectionless Transport**: Does not establish a connection before transmitting data, provides best-effort delivery without guarantees of reliability or order, and typically has lower overhead. Example: UDP.

###