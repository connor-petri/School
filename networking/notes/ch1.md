# Chapter 1: Introduction

---

## Goal: 
Get a feel for the course material

## Overview
- What is the Internet?
- What is a protocol?
- **Network Edge:** hosts, access network, physical media
- **Network Core:** packet/circuit switching, internet structure
- **Performance:** loss, delay, throughput, etc.
- Protocol layers and their service models
- Security
- History

---

## The Internet: A "Nuts and Bolts" View
- Billions of connected **computing devices**
    - **Hosts** are end systems
    - Running network **apps** at the internet's *edge*
- **Packet switches:** forwards packets
    - *Routers, switches*
- **Communication links**
    - Fiber, copper, radio, satellite
    - Transmission rate: *bandwidth*
- **Networks**
    - Collection of devices, routers, links
    - Managed by an organization

### The Internet - A Network of Networks
- Interconnected ISPs (AT&T, Xfinity, etc.)
- **Protocols** are *everywhere*
    - Control sending, recieving of messages
    - CAN, HTTP, TCP, IP, ethernet, etc.

### Internet Standards
- Governed by a *board of standards*
    - **RFC:** Request for Comments
    - **IETF:** Internet Engineering Task Force

## The Internet: A "Services" View
- **Infrastructure** that provides services to applications:
    - Web, streaming video, multimedia teleconferencing, email, games, e-commerce, social media, inter-connected appliances, etc.
- Provides **programming interface** to distributed applications:
    - **Hooks** allowing sending/recieving apps to "connect" to, use Internet transport service
    - Provides service options, analogous to postal service

---

## What's a Protocol?

### Human Protocols
- "What's the time?"
- "I have a question"
- Introductions
- Rules for:
    - specific messages sent
    - specific actions taken when message recieved

## Structure
### Network Edge
- *Hosts:* clients and servers
- Servers often in data centers

### Access networks, physical media
- wired connection
- wireless connection
#### How to connect end systems to edge router?
- Residential access nets
- Institutional access networks (school, company, etc.)
- Mobile access networks (Wifi, 4G/5G)

#### Network Core
- interconnected routers
- network of networks
- *packet-switching:* Hosts break application-layer messages into *packets*


### Host: sends *packets* of data
- takes application message
- breaks into cmaller chunks, known as *packets*, of length $L$ bits
- transmits packet into access network at *transmission rate* $R$
    - link tranmission rate, aka link *capacity, aka link bandwidth*

### Links: Physical Media
- *Bit:* propagates between Tx/Rx pairs
- *Physical Link:* what lies between Tx/Rx
- *Guided Media:*
    - Signals propagate in solid media: copper, fiber, coax
- *Unguided Media:*
    - Signals propagate freely: radio

---

## Functions of the Network Core
### Forwarding
- aka switching
- *local* action: move arriving packets from input link to the appropriate output link
### Routing
- *Global* actions: determine source-destination paths taken by packets
- Routing algorithms

### Packet Switching: Store-and-Forward:
- **Packet Transmission Delay:** Takes $\frac{L}{R}$ seconds to transmite $L$-hit packet into link at $R$ bps.
- **Store and Forward:"** *Entire* packet must arrive at router before it can be transmitted on next link.
- **Packet Queueing and Loss:** if arribal rate to link exceeds transmission rate for some period of time
    - Packets are loss if memory buffer fills up.

### Circuit switching
End-end resources allocated to , reserved for "call" between source and destination
#### Frequency Division Multiplexing (FDM)
- Optical, EM frequencies divided into narrow frequency bands
- Each call allocated its own band, can transmit at max rate of that narrow band

#### Time Division Multiplexing (TDM)
- Time divided into slots

#### Pros and Cons
Example:
- 1 Gb/s link
- each user:
    -100 Mb/s when "active"
    - Active 10% of the time

##### Number of users
- Circuit-switching: 10 users
- Packet Switching: with 35 users, probability > 10 active at the same time is less than .0004%

##### Is packet switching a "slam dunk winner?"
- great for bursty data - sometimes has data to send, but at other times not
    - resource sharing
    - simpler, no call setup
- **Excessive congestion possible:** packet delay and loss due to buffer overflow
    - protocols needed for reliable data trasfer, congestion control

### How do ISPs talk to each other
- **Internet Exchange Points** (IXP)

---

## Security

### Internet not originally designed with much security in mind
- *Original vision:* "a group of mutually trusting users attached to a transparent network :)
- Internet protocol designers playing catch-up"
- Security considerations in all layers

### We now need to think about
- How bad actors can attack computer networks
- How we can defend networks against attacks
- How to design architectures that are resistant to attacks

#### Packet Sniffing
- Broadcast media (shared ethernet, wireless)
- Promiscuous network interface reads/records all packets passing by

### Lines of Defense
- Authentication
- Encryption
- Integrety Checks
- Access Restrictions
- Firewalls

### Packet Delay: Four Sources
- **Nodal Processing:** Check bit errors, determine output link, typically < msec
- $d_{nodal} = d_{proc} + d_{queue} + d_{trans} + d_{prop}$
- **Queueing Delay:** time waiting at output link for transmission, depends on congestion level of router
- **Transmission Delay:** $d_{trans} = \frac{L}{R}$, $L$ = packet length (bits), $R$ = link bandwidth (bps)
- **Proccessing Delay:** - Internal processing of the packet at the router, typically < msec
- **Propogation Delay:** $d_{prop} = \frac{d}{s}$, $d$ = length of physical link, $s$ = propagation speed in medium (~2x10^8 m/s)

### Traffic Intensity
- $a = La/R$, where $a$ = average packet arrival rate, $L$ = average packet length, $R$ = link bandwidth
- If $a > 1$, more bits arrive than can be sent, queue grows without bound, packets will be dropped
- If $a < 1$, average rate at which bits arrive is less than the link bandwidth,

### Packet Loss
- Packets dropped if buffer fills up

#### Throughput
- Rate (bits/sec) at which bits transferred between sender/receiver
    - **Instantaneous throughput:** rate at given point in time
    - **Average throughput:** rate over longer period of time

---

## Protocol Layers and Their Service Models
### Layered Internet Protocol Stack
- **Application** supporting network applications
    - HTTP, IMAP, SMTP, DNS
- **Transport** process-process data transfer
    - TCP, UDP
- **Network** routing of datagrams from source to destination
    - IP, routing protocols
- **Link** data transfer between neighboring network elements
    - Ethernet, WiFi
- **Physical** the medium through which the datagram propogates through
    - Wire
    - EMR