"""
PA0 - Chat Application
client.py

Computer Networks - Top-Down Approach
------------------------------------------
This is the client side of a simple TCP chat application.
Make sure server.py is already running before you start this file.

The client:
  - Connects to the server using a TCP socket
  - Sends and receives messages simultaneously using threads
"""

import socket
import threading

# ---------------------------------------------------------------------------
# Configuration
# HOST and PORT must match exactly what is set in server.py
# ---------------------------------------------------------------------------
HOST = '127.0.0.1'   # Address of the server (localhost = same machine)
PORT = 12345          # Must match the PORT in server.py


def receive_messages(sock):
    """
    Runs in a background thread.
    Continuously listens for incoming messages from the server
    and prints them to the terminal.
    """
    while True:
        try:
            data = sock.recv(1024)          # Block until data arrives (up to 1024 bytes)
            if not data:                    # Empty data means the server closed the connection
                print("\n[Client] Server has disconnected.")
                break
            message = data.decode('utf-8')
            print(f"\n[Server]: {message}")
            print("You: ", end='', flush=True)   # Re-print the input prompt
        except OSError:
            break   # Socket was closed, exit the thread cleanly


def main():
    # Create a TCP socket (AF_INET = IPv4, SOCK_STREAM = TCP)
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    # Attempt to connect to the server
    try:
        client_socket.connect((HOST, PORT))
    except ConnectionRefusedError:
        print(f"[Error] Could not connect to {HOST}:{PORT}")
        print("        Make sure server.py is running in another terminal first.")
        return

    print(f"[Client] Connected to server at {HOST}:{PORT}")
    print("=" * 50)
    print("Chat session started. Type a message and press Enter.")
    print("Type  'quit'  to end the session.")
    print("=" * 50 + "\n")

    # Start the receive thread so we can get messages while also typing
    recv_thread = threading.Thread(target=receive_messages, args=(client_socket,))
    recv_thread.daemon = True   # Thread will exit automatically when main program exits
    recv_thread.start()

    # Main thread handles outgoing messages
    while True:
        try:
            message = input("You: ")
            if message.lower() == 'quit':
                print("[Client] Closing connection.")
                break
            if message:   # Don't send empty messages
                client_socket.send(message.encode('utf-8'))
        except (KeyboardInterrupt, EOFError):
            print("\n[Client] Interrupted. Closing.")
            break

    client_socket.close()


if __name__ == "__main__":
    main()
