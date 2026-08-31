# Chapter 2 - Application Layer

---

## Principles of Network Applications

### Client - Server Paradigm
#### Server:
- Always on host
- Permanent IP address
- Often in data centers, for scaling

#### Clients:
- Contact, communicate with server
- May be intermittently connected
- May have dynamic IP addresses
- Do *not* communicate directly with each other
- Examples:
    - HTTP
    - IMAP
    - FTP

### Peer to Peer Architecture
- *No* always-on server
- Arbitrary end systems directly communicate
- Peers request service from other peers, provide service in return to othher peers
- Peers are intermittently connected and change IP addresses
- Example: BitTorrent

### Inter-Process Communication
- **Process:** a program running within a host
- Within same host, two processes communicate using *inter-process communication* (defined by OS)
- Processes in different hosts communicate by exchanging *messages*
- **Client Process:** process that initiates communication
- **Server Process:** process that waits to be contacted
    - P2P applications have both a client and server process

### Sockets
- Process sends/recieves messages to/from its *socket*
- Socket analogous to door
    - Sending process shoves message out the door
    - Sending process relies on transport infrastructure on the other side of the door to deliver message to socket at receiving process
    - Two sockets involved: on on each side

### Addressing Processes
- To recieve messages, process must have an *identifier*
- Host device has unique 32-bit IP address
- The IP of the host alone does not suffice for identifying the process, we require a **port** to identify the process
    - HTTPS server: 80
    - Mail server: 25

### An Application-Layer Protocol Defines:
- Types of messages exchanged
- Message syntax
- Message semantics
- Rules for when and how processes send and respond to messages

#### Open Protocols
- Defnined in RFCs, everyone has access to protocol definition
- Allows for ineroperability

#### Proprietary/Closed Protocols
- Skype
- Zoom

### What Transport Service Does an App Need?
- **Data Integrity:**
    - Some apps require 100% reliable data transfer
    - other apps can tolerate *some* loss
- **Timing:**
    - Some apps require low delay to be effective, (i.e. Gaming)

### Internet Transport Protocols
#### TCP
- **Reliable Transport** between sending and receiving process
- **Flow control:** Sender wont overwhelm receiver
- **Congestion control:** throttle sender when network is overloaded
- **Connection-oriented:** setup required between client and server processes
- **Does not provide** timing, minimum throughput, or security
#### UDP
- **Unreliable Data Transfer** between sending and receiving process
- **Does not provide** reliability, flow control, congestion control, timing, throughput garuntee, security, or connection setup

### Secureing TCP
#### Vanilla TCP and UDP Sockets:
- No encryption
- Cleartext passwords sent into socket traverse Internet in cleartext (BAD!)
#### Transport Layer Security
- Provides encrypted TCP connections
- Data integrity
- End-point authentication
#### TLS Implemented in Application Layer
- Apps use TLS livraries that use TCP in turn
- Cleartext sent into "Socket" traverse internet *encrypted*

---

## Web and HTTP

### Quick Review
- Web page consists of *objects* each of which con be stored on different Wab servers
- Object can be HTML file, JPEG image, Java applet, audio file...etc.

### HTTP: Hyper Text Transfer Protocol
- Web's application-layer protocol
- Client/server model:
    - *Client:* browser
    - *Server:* Web server sends objects in response to requests

#### HTTP uses TCP:
- Client initiates TCP connection to server, port 80
- Server accepts TCP connection from client
- HTTP messages exchanged between browser and Web server
- TCP connection closed

#### HTTP is "Stateless"
- Server maintains *no* information about past client requests

### Two Types of HTTP Connections
**RTT:** Time for a small packet to travel from client to server and back
#### Non-Persistent HTTP
1. TCP connection opened
2. At most on object sent over TCP connection
3. TCP connection closed
- Downloading multiple objects requires multiple requests
- Requires 2 RTTs per object
- OS overhead for each each TCP connection
##### Cookies
- **Cookies** are used to preserve state on the client side
- Sent with each request
- Not defined by protocol - unique to each website/server/service

#### Persistent HTTP
- TCP connection opened to a server
- Multiple objects can be sent over *single* TCP connection
- Server leaves connection open after sending presponse
- subsequent HTTP messages between seme client/server sent over open connection
- client dsends requests as soon as it encounters a referenced object

### Two Types of HTTP messages
#### Request Message
- ASCII (human readable)
- `GET`, `POST`, and `HEAD`
TODO: Finish this with screenshots from lecture
```
GET /index.html HTTP/1.1\r\h
{Request Body}
```
- `\r` Carriage return character
- `\h` Line-feed character

##### Post Method
- Web page often includes form input
- user input sent from client ot server in entity body of HTTP POST request message

##### GET Method
- Include user data in URL field of HTTP GET request message

##### HEAD method
- Request headers only

##### PUT method 
- Uploads new file to sevrer
- Completely replaces file that exists at specified URL with content in entity body of POST HTTP request message

#### HTTP Response Message
TODO: Put screenshot from lecture

