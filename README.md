# Java Online Chat Application

This project is a simple chat application built using Java socket programming. It allows multiple users to connect to a central server, send messages, and receive messages from other users in real-time. The server handles multiple clients and broadcasts messages to all connected clients.

## Features
- Multiple clients can connect to the server simultaneously.
- Clients can send messages to the server, which broadcasts them to all connected clients.
- A simple text-based user interface for clients to send and receive messages.

## Requirements
- Java Development Kit (JDK)
- IDE: Eclipse or IntelliJ IDEA
- Multi-console support to simulate multiple clients


## Implementation Details

### 1. **ChatServer.java (Server Implementation)**

- The server is implemented using **Java socket programming** with `ServerSocket` to accept client connections.
- Each time a new client connects, a separate thread is created to handle the communication for that client. This is done using the `ClientHandler` class, which extends `Thread`.
- The server assigns a unique user ID to each client and maintains a `Map` of connected clients, allowing it to manage client connections and send messages to all clients.
- The server broadcasts any message received from a client to all other connected clients.
- When a client disconnects, the server removes the client from the list of active connections.

Key methods:
- `broadcastMessage(String message, int senderID)`: This method sends a message from one client to all other clients. It iterates over the list of connected clients and sends the message.
- `removeClient(int userID)`: Removes the disconnected client from the list.

### 2. **ChatClient.java (Client Implementation)**

- The client connects to the server using a **Socket**. It uses input and output streams to send messages to the server and receive messages from other clients via the server.
- A separate thread is used to receive messages continuously while the main thread handles user input.
- The client reads messages from the terminal and sends them to the server, which then broadcasts them to other clients.
- The client listens for messages from the server in a background thread and prints them to the console.

Key methods:
- `startConnection(String ip, int port)`: Establishes a connection to the server.
- `MessageReceiver`: A thread that listens for incoming messages from the server and displays them.

### 3. **Multithreading**

- The server uses multithreading to handle multiple clients concurrently. Each client is assigned a new thread via the `ClientHandler` class, allowing simultaneous communication without blocking the main server thread.
- The client also uses multithreading to receive messages asynchronously in a background thread while allowing the user to continue typing and sending messages.

### 4. **Message Broadcasting**

- The server handles all message broadcasting. When a client sends a message to the server, the message is forwarded to all other connected clients.
- This is achieved using the `broadcastMessage` method, which iterates through the list of active clients and sends the message to each one.

## Sample Output

- When the server and clients are running, messages typed by one client will appear on all other clients' terminals.
- Each client will be identified by their unique user ID when sending or receiving messages.


## How to Run the Application

### Steps to Run in **Eclipse**

1. **Create a New Java Project**:
   - Open Eclipse and select **File** → **New** → **Java Project**.
   - Name the project (e.g., `ChatApplication`) and click **Finish**.

2. **Create Java Classes**:
   - In the **src** folder of your project, create two classes:
      - **ChatServer.java**
      - **ChatClient.java**
   - Copy the server and client code into the respective classes.

3. **Compile and Run the Server**:
   - Right-click on **ChatServer.java** in the Project Explorer.
   - Select **Run As** → **Java Application**.
   - This will start the server, which listens for client connections.

4. **Run Multiple Clients**:
   - Open another Eclipse instance by going to **File** → **New** → **Java Project** and repeat the steps to create a new client project.
   - Alternatively, you can open multiple **Console Views** in Eclipse:
      - Go to **Window** → **Show View** → **Console**.
      - Use the **Display Selected Console** button to switch between the server and client consoles.

   - For each instance, right-click on **ChatClient.java** and select **Run As** → **Java Application**.
   - Each client will connect to the server.

5. **Test the Broadcast Message**:
   - Type a message in one client’s console and hit Enter.
   - The message will be broadcast to all connected clients, and each will receive and display the message.

### Steps to Run in **IntelliJ IDEA**

1. **Create a New Java Project**:
   - Open IntelliJ IDEA and click on **New Project**.
   - Select **Java**, then click **Next** and **Finish**.

2. **Create Java Classes**:
   - In the **src** folder of your project, right-click and select **New** → **Java Class**.
   - Create two classes:
      - **ChatServer.java**
      - **ChatClient.java**
   - Copy the server and client code into the respective classes.

3. **Run the Server**:
   - Right-click on **ChatServer.java** in the Project view.
   - Select **Run 'ChatServer'** to start the server.

4. **Run Multiple Clients**:
   - Open a new **Run/Debug Configuration** by clicking **Edit Configurations** from the top-right corner.
   - Create a **new Application** configuration for **ChatClient.java**.
   - To simulate multiple clients, you can run multiple configurations or open new terminal windows:
      - **Run** → **Run 'ChatClient'** for each client instance.
   - Alternatively, open multiple **Console Tabs** in IntelliJ by going to **Run** → **New Console Tab**.

5. **Test the Broadcast**:
   - Enter a message in one client's console.
   - The server will broadcast the message to all clients, and they will display the message in their consoles.



## Authors
Ram Bajagain
"# chat_application_online" 
"# chat_application_online" 
