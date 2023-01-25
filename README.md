# do-chat using Chat-GPT


- Purpose: Making a chat application.
- Method: From scratch by asking questions to Chat-Gpt.

## 1. Introduction [1]
## 1.1 Requirements
  First thing first, we must figure out the exact requirements of the target system. For Slack (or other apps you use), the following features can be expected:

- Direct messaging: two users can chat with each other
- Group chat: users can participate in group conversations
- Join/leave groups, (add/delete friends not important for Slack)
- Typing indicator: when typing, the recipient gets notified
- User status: whether you are online or offline

## 1.2 Traffic Estimation

- We expect millions of daily active users(DAU) from all over the world.
- On average each message is around a few hundred characters. Each person sends out a few hundred messages per day.
- Some groups have a handful of people, some others have hundreds of members

- The system is write-heavy, as messages are written into DB but rarely read (most of them are delivered via the notification service and saved locally on the client. We shall see why later )

## 2. High-level Design[1]

### 2.1. Database Design
  When it comes to database design, it is always a good idea to consider the access pattern of your application.

**Read Operations**
- Given user A and user B, retrieve messages after a certain timestamp
- Given group G, retrieve all messages after a certain timestamp
- Given group G, find all member ID
- Given user A, find all groups he/she joined

**Write Operations**
- Save a message between user A and user B
- Save a new message by user A in group G
- Add/delete user A to/from group G

**Schema**

![image](https://user-images.githubusercontent.com/97912815/208916193-62de4ea8-c714-4255-8c2f-3541189ad7a0.png)

![image](https://user-images.githubusercontent.com/97912815/208916369-070a59dd-bd5a-4a6f-8ea4-e026dd858ff8.png)

![image](https://user-images.githubusercontent.com/97912815/208916624-077980ba-8160-4cc7-b804-781d352c9307.png)

  We need two tables to capture the relationship between users and groups. The Group Membership table is used for message broadcasting — we need to figure out who gets the message. The User Membership table is for listing all the groups a user has joined. We could use a single table with a secondary index, but the cardinality of group ID/user ID is too large for a secondary index. The two-table approach isn’t without problems either— if we mutate one, the other should be modified to maintain consistency, which requires distributed transactions.
  
![image](https://user-images.githubusercontent.com/97912815/208917097-39717921-ebc5-415f-b7ca-50d6e2eaebef.png)

## 3. Get Preliminary Information with Chat-Gpt

### 3.1 
- Direct messaging: two users can chat with each other
- Group chat: users can participate in group conversations
- Join/leave groups, (add/delete friends not important for Slack)
- Typing indicator: when typing, the recipient gets notified
- User status: whether you are online or offline

What are the steps to make a chat application with its features?

### 3.1.1 Chat-Gpt's answer

Here are the steps to create a chat application with the features you described:

- Design the database schema to store the necessary data. This could involve creating tables or collections for users, chats, messages, and any other relevant data. You may want to consider using a NoSQL database like MongoDB, which is well-suited to storing the type of flexible, unstructured data that is commonly found in chat applications.

- Implement the server-side logic to handle incoming requests and manage the data in the database. This could involve creating routes and controllers to handle user authentication, creating and updating chats and messages, and any other relevant functionality. You may want to use a server-side framework such as Express to simplify the development process.

- Implement the client-side logic to handle user interactions and display the data to the user. This could involve creating views and controllers in a front-end framework such as React or Angular to handle user input, update the chat interface in real-time, and display notifications and other information to the user.

- Implement the real-time communication functionality using WebSockets or a similar technology. This will allow the server to push updates to the client in real-time, enabling features like typing indicators and user status updates.

- Test and debug the application to ensure that it is working correctly and all of the features are functioning as expected. This may involve writing unit tests, integration tests, and functional tests to validate the different components of the application.

- Deploy the application to a hosting platform, such as Heroku or AWS, and make it available to users. This will involve configuring the hosting environment, setting up any necessary infrastructure, and ensuring that the application is performant and scalable.

- Monitor and maintain the application to fix any bugs that are discovered and add new features as needed. This may involve tracking user feedback and implementing improvements based on user needs and requests.

### 3.2 How to design a chat application that works the fastest?
### 3.2.1 Chat-Gpt's answer

  There are several ways you can design a chat application to make it work as fast as possible. Some strategies you can consider include:

1. Use a fast database: Choose a database that is optimized for fast reads and writes, such as a NoSQL database like MongoDB or Cassandra.

2. Use cache: Implement caching techniques to store frequently accessed data in memory, so that you can retrieve it faster without having to go to the database.

3. Use indexing: Index important fields in your database to speed up searches and queries.

4. Use sharding: Divide your database into smaller chunks (shards) and distribute them across multiple servers, so that you can process queries in parallel and speed up performance.

5. Use a load balancer: Use a load balancer to distribute traffic across multiple servers and reduce the load on any one server, which can help improve performance.

6. Use a message queue: Implement a message queue to buffer requests and process them asynchronously, which can help improve performance and scalability.

7. Use a distributed database: Consider using a distributed database, which allows you to scale horizontally by adding more servers as needed. This can help improve performance and scalability.

  It's important to note that the specific design approaches you choose will depend on the specific needs and requirements of your chat application. It may be helpful to perform load testing and performance testing to determine the optimal design for your specific use case.

### 3.1.2 My Decisions Based on Answer

1. Use NoSQL Database
2. The system is write-heavy so i dont care caching and indexing so much.
3. MongoDB supports horizontal scaling through sharding. [2]
4. Use RabbitMQ

## 3.3 Can you give me a database logical design for a chat application?

### 3.3.1 Chat-Gpt's answer

Sure! Here is a conceptual design for a chat application using a NoSQL database such as MongoDB:

1. First, we will need a users collection to store information about each user, such as their username, password, and any other relevant details. The primary key for this collection could be an auto-generated ObjectId or a UUID.

2. Next, we will need a chats collection to store information about each chat, including both group chats and direct messages. Each chat document could have an array of user IDs to store the participants, as well as a chat name or subject if applicable. The chat document should also have a field to indicate whether the chat is a group chat or a direct message between two users.

3. Finally, we will need a messages collection to store the actual messages that are sent in the chat. Each message document should have a reference to the chat document that it belongs to, as well as a reference to the user document of the user who sent the message. The messages collection should also have a timestamp for when the message was sent, as well as the message content itself.

In addition to these core collections, you may also want to consider adding additional collections or documents to support additional features of the chat application, such as:

A notifications collection to store notification records for users, such as when they receive a new message or are added to a chat.
A reactions collection to store reactions to messages, such as likes or emojis.
A files collection to store file attachments that are sent in the chat.
Here is an example of what the schema for these core collections might look like in MongoDB:

users
----------
_id (PK)  |  username  |  password  |  other_details

chats
----------
_id (PK)  |  participants (Array of user _id references)  |  chat_name  |  created_at  |  type (Group or Direct)

messages
----------
_id (PK)  |  chat (Reference to chat _id)  |  sender (Reference to user _id)  |  created_at  |  content

## 3.4 Can you design an api for the chat application that includes your operations?
### 3.4.1 Chat-Gpt's answer

Sure! Here is an example API design for a chat application that includes the following operations:

Authentication:
- POST /api/login: Log in to the application using a username and password.
- POST /api/logout: Log out of the application.
- POST /api/register: Register a new user with the application.

Chats:
- GET /api/chats: Retrieve a list of all chats that the authenticated user is a part of.
- POST /api/chats: Create a new chat with the specified participants.
- GET /api/chats/:id: Retrieve the details of the chat with the specified ID.
- DELETE /api/chats/:id: Leave the chat with the specified ID.

Messages:
- GET /api/chats/:id/messages: Retrieve a list of all messages in the chat with the specified ID.
- POST /api/chats/:id/messages: Send a new message to the chat with the specified ID.
- DELETE /api/chats/:id/messages/:message_id: Delete the message with the specified ID from the chat with the specified ID.

Users:
- GET /api/users: Retrieve a list of all users registered with the application.
- GET /api/users/:id: Retrieve the details of the user with the specified ID.
- PUT /api/users/:id: Update the details of the authenticated user.

Other:
- GET /api/status: Retrieve the online status of the authenticated user.
- PUT /api/status: Update the online status of the authenticated user.
- GET /api/typing/:id: Retrieve the typing status of the user with the specified ID in the specified chat.
- PUT /api/typing/:id: Update the typing status of the authenticated user in the specified chat.

**Technologies I need to learn as a result of Chat-Gpt's answers**
- Web Socket
- RabbitMQ
- NoSQL Database

## 4. Questions I asked Chat-gpt during the learning phase

### 4.1 [3]

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker("/topic");
    config.setApplicationDestinationPrefixes("/app");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/gs-guide-websocket").withSockJS();
  }

}

### 4.1.1 Chat-Gpt's answer

This is a Java configuration class that enables WebSocket message broker support in a Spring Boot application.

The @Configuration and @EnableWebSocketMessageBroker annotations indicate that this class contains WebSocket configuration information.

The WebSocketMessageBrokerConfigurer interface provides methods that can be overridden to customize the configuration of the WebSocket message broker.

In the configureMessageBroker method, the enableSimpleBroker method sets up a simple message broker to carry messages back and forth from the client to the server over a WebSocket connection. This message broker is used to send messages to subscribed clients through a message queue. The /topic destination prefix is used for messages that are broadcast to all subscribed clients.

The setApplicationDestinationPrefixes method sets the prefix for destination names that specify the target for a message. This prefix is used to differentiate between messages intended for the application and messages intended for the message broker.

The registerStompEndpoints method registers STOMP (Simple Text Oriented Messaging Protocol) endpoints that the clients can use to connect to the WebSocket server. STOMP is a simple text-based protocol that allows for the communication of messages between client and server. The addEndpoint method adds a WebSocket endpoint at the specified URL (/gs-guide-websocket) that the clients can use to connect to the WebSocket server. The withSockJS method enables SockJS fallback options so that the WebSocket connection can work even if the client's WebSocket implementation is not fully compliant with the WebSocket specification.



[1]: https://towardsdatascience.com/ace-the-system-interview-design-a-chat-application-3f34fd5b85d0
[2]: https://www.mongodb.com/docs/manual/sharding/
[3]: https://spring.io/guides/gs/messaging-stomp-websocket/
