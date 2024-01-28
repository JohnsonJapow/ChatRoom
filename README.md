**Chat Room Application**
- This simple chat room application consists of a server and client implemented in Java. 
- The server allows multiple clients to connect and communicate with each other in a shared chat space.

**#Client**
+ Usage
  - The client program connects to the chat room server using a socket and provides a basic command-line interface for users to interact with the chat. 
  - Users can send messages, change their nickname, and quit the chat using the provided commands.

+ How to Run
  - Compile and run the Client class. The client will attempt to connect to the server running at 127.0.0.1:9999. 
  - Ensure the server is running before starting the client.

+ type the commands in bash:
  - javac Client.java
  - java Client

+ Commands
  - Send a message: Type your message and press Enter.
  - Change nickname: Use the /nick command followed by the desired nickname (e.g., /nick NewNickname).
  - Quit the chat: Type /quit to leave the chat room.

**#Server**
+ Usage
  - The server manages client connections, creating a dedicated chat space for each client to communicate. 
  - It supports broadcasting messages to all connected clients, nickname changes, and handling clients leaving the chat.

+ How to Run
  - Compile and run the Server class. The server will listen for incoming connections on port 9999.

+ type the commands in bash:
  - javac Server.java
  - java Server

+ Features
  - Broadcast messages: Messages sent by one client are broadcasted to all connected clients.
  - Nickname changes: Clients can change their nickname using the /nick command.
  - Client disconnection: When a client leaves the chat using the /quit command, the server handles the disconnection and notifies other clients.

**#Note**
  - Ensure that the server is running before starting any clients.
  - The default server address is 127.0.0.1 with port 9999.
  - To test the chat room, open multiple client instances to simulate different users connecting to the server.

**#Reference**
  - https://www.youtube.com/watch?v=hIc_9Wbn704&list=LL&index=4
