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

### Connectionless Transport: UDP - User Datagram Protocol
- "no frills" "bare bones"
- "best effort" service, UDP segments may be lost or delivered out-of-order
- *Connectionless:
    - No handshaking between UDP sender, reciever
    - each UDP segment handled independently of others
- UDP uses:
    - Streaming multimedia
    - DNS
    - SNMP
    - HTTP/3
- If reliable transfer needed over UDP:
    - add needed reliability at applicstion layer
    - add congestion control at application layer

#### Why UDP?
- no connection establishment
- simple: no connection state at sender, receiver
- small header size
- no congestion control
    - UDP can go as fast as desired
    - Can funciton in the face of congestion

### Checksums
- A checksum is a simple error-detection scheme used to detect errors in transmitted segments.
- The sender computes a checksum value based on the segment's contents and includes it in the segment header.
- The receiver recomputes the checksum on the received segment and compares it with the received checksum.
    - If the checksums match, the segment is considered error-free.
    - If the checksums do not match, the segment is considered corrupted and is typically discarded.
- Both TCP and UDP use checksums to provide basic error detection for their segments.
- Checksum is usually the one's complement of the sum of all 16-bit words in the segment.
- The checksum helps detect errors introduced during transmission, but it does not correct them.

---

## Reliable Data Transfer

### Principles
- Sender and receiver do not know the "state" of each other (e.g., whether a segment has been successfully received).
    - Unless communicated via a message

### Getting Started
- Incrementally develop sender, receiver sides of reliable data transfer protocols. (RDTs)
- Consider only unidirectional data transfer
    - but control information will flow in both directions
- Use finite state machines (FSM) to specify sender, receiver behavior.

#### rdt1.0: Reliable Data Transfer over a Perfect Channel
- Assumes a perfect channel: no loss, no corruption.
- Separate FSMs for sender, receiver
    - Sender sends data into underlying channel
    - Receiver receives data from underlying channel

#### rdt2.0: Channel with Bit Errors
- Underlying channel may flip bits in packet
    - chekcsum to detect bit errors
    - How do we recover from bit errors?

#### rdt2.0 has a fatal flaw
- What happens if an ACK/NAK is corrupted?
    - Sender doesn't know what happened at receiver!
    - can't just retransmit: possible duplicate

### rdt2.1 handles garbled ACK/NAKs
- Sender includes sequence numbers in packets and ACKs
- Receiver discards duplicate packets based on sequence numbers
- Ensures that each packet is delivered correctly and in order
- Handles corrupted ACKs/NAKs by retransmitting only when necessary
- Ensures reliable data transfer even in the presence of bit errors and corrupted control messages

### rdt2.2: Further Improvements, NAK-Free
- Instead of using NAKs, the receiver only sends ACKs
- If the sender does not receive an ACK within a certain time, it retransmits the packet
- Simplifies the protocol by eliminating the need for NAKs
- Still ensures reliable data transfer in the presence of bit errors and lost packets

### rdt3.0: Channel with Bit Errors and Loss
- New Channel Assumptions: underlyng channel may also lose bits
- Sender uses a timer to detect lost packets
- If the timer expires before an ACK is received, the sender retransmits the packet
- Ensures reliable data transfer even in the presence of bit errors and lost packets

### Summary of Reliable Data Transfer Protocols and the Problems They Address
- rdt1.0: Perfect channel, no loss, no corruption
- rdt2.0: Handles bit errors, uses ACKs/NAKs
- rdt2.1: Adds sequence numbers to handle corrupted ACKs/NAKs
- rdt2.2: NAK-free, retransmits on timeout
- rdt3.0: Handles both bit errors and packet loss, uses timers for retransmission
