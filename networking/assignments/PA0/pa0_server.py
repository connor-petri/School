"""
PA0 - Chat Application
server.py

Computer Networks - Top-Down Approach
------------------------------------------
This is the server side of a simple TCP chat application.
Run this file first, then run client.py in a second terminal.

The server:
  - Binds to a local address and port
  - Waits for one client to connect
  - Sends and receives messages simultaneously using threads
"""

import socket
import threading

# ---------------------------------------------------------------------------
# Configuration
# Change PORT if you get "Address already in use" errors
# ---------------------------------------------------------------------------
HOST = '127.0.0.1'   # Localhost - only accepts connections from this machine
PORT = 12345          # Port number the server listens on


def receive_messages(conn):
    """
    Runs in a background thread.
    Continuously listens for incoming messages from the client
    and prints them to the terminal.
    """
    while True:
        try:
            data = conn.recv(1024)          # Block until data arrives (up to 1024 bytes)
            if not data:                    # Empty data means the client closed the connection
                print("\n[Server] Client has disconnected.")
                break
            message = data.decode('utf-8')
            print(f"\n[Client]: {message}")
            print("You: ", end='', flush=True)   # Re-print the input prompt
        except OSError:
            break   # Socket was closed, exit the thread cleanly


def main():
    # Create a TCP socket (AF_INET = IPv4, SOCK_STREAM = TCP)
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    # Allow reuse of the port immediately after the server stops
    # (avoids "Address already in use" on quick restarts)
    server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

    # Bind the socket to the host and port, then start listening
    server_socket.bind((HOST, PORT))
    server_socket.listen(1)   # Queue up to 1 pending connection
    print(f"[Server] Listening on {HOST}:{PORT} ...")
    print("[Server] Waiting for a client to connect...\n")

    # Block here until a client connects
    conn, client_address = server_socket.accept()
    print(f"[Server] Client connected from {client_address}")
    print("=" * 50)
    print("Chat session started. Type a message and press Enter.")
    print("Type  'quit'  to end the session.")
    print("=" * 50 + "\n")

    # Start the receive thread so we can get messages while also typing
    recv_thread = threading.Thread(target=receive_messages, args=(conn,))
    recv_thread.daemon = True   # Thread will exit automatically when main program exits
    recv_thread.start()

    # Main thread handles outgoing messages
    while True:
        try:
            message = input("You: ")
            if message.lower() == 'quit':
                print("[Server] Closing connection.")
                break
            if message:   # Don't send empty messages
                conn.send(message.encode('utf-8'))
        except (KeyboardInterrupt, EOFError):
            print("\n[Server] Interrupted. Closing.")
            break

    conn.close()
    server_socket.close()


if __name__ == "__main__":
    main()
