# Chapter 6

## Link Layer and LANs

### Services
- Framing, link access
    - Encapsulate datagrams into one frame for transmission over the link
    - Provide error detection and possibly correction for frames transmitted over the link
    - MAX addresses in fram headers identify the source and destination of the frame
- Reliable delivery between adjacent nodes
    - Some link-layer protocols provide reliable delivery by retransmitting lost or corrupted frames between directly connected nodes
    - This is typically used in point-to-point links or wireless links where errors are more common
- Flow control
    - pacing between adjacent nodes to prevent overwhelming the receiver with too many frames at once
- Error detection and correction
    - Link-layer protocols often include mechanisms to detect errors in transmitted frames, such as checksums or cyclic redundancy checks (CRC)
    - Some protocols also provide error correction by retransmitting corrupted frames or using forward error correction techniques
- Half-duplex and full-duplex communication
    - Half-duplex: communication can occur in both directions, but only one direction at a time
    - Full-duplex: communication can occur simultaneously in both directions

### Host link-layer implementation
- In each-and-every host
- link layer implemented on-chip or in network interface card
    - Implements link, physical layer
- Attaches into host's system buses
- Combination of hardware, software, firmware
    - Hardware: network interface card (NIC) handles link-layer functions
    - Software: device drivers and operating system manage communication with NIC
    - Firmware: embedded code in NIC for low-level link-layer operations

---

## Error Detection and Correction
- Error detection involves identifying errors in transmitted frames using techniques such as checksums or cyclic redundancy checks (CRC)

### Parity Bits and Parity Checks
- A parity bit is an extra bit added to a frame to make the number of 1s either even (even parity) or odd (odd parity)
- The receiver checks the parity bit to detect single-bit errors in the transmitted frame
- Parity checks can only detect an odd number of bit errors and cannot correct errors
- Common types of parity checks include:
    - Even parity: the total number of 1s in the frame, including the parity bit, is even
    - Odd parity: the total number of 1s in the frame, including the parity bit, is odd

### Checksums
- A checksum is a value calculated from the data in a frame and sent along with the frame
- The receiver recalculates the checksum from the received data and compares it with the transmitted checksum to detect errors
- Checksums can detect multiple-bit errors, but they are not foolproof

### Cyclic Redundancy Check (CRC)
- CRC is a more robust error detection technique that treats the data as a polynomial and divides it by a generator polynomial
- The remainder of this division is appended to the frame as the CRC value
- The receiver performs the same division and checks if the remainder matches the received CRC value
- CRC can detect burst errors and is widely used in link-layer protocols
- $R$ is the remainder obtained from dividing the data polynomial by the generator polynomial
    - $R = (D \cdot 2^r) \mod G$
        - Where $D$ is the data polynomial, $r$ is the degree of the generator polynomial $G$, and $R$ is the remainder (CRC value)