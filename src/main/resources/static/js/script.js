
//document.querySelector('#registerButton') && document.querySelector('#registerButton').addEventListener('click', connect,true);
//document.querySelector('#messageForm').addEventListener('submit', sendMessage, true)
//document.querySelector('#addForm').addEventListener('submit', addContact, true)

let stompClient = null;
let user = undefined;

function connect(event) {

    userName = document.querySelector('#userName').value.trim();
    name = document.querySelector('#name').value.trim();
    surname = document.querySelector('#surname').value.trim();
    email = document.querySelector('#email').value.trim();
    password = document.querySelector('#password').value.trim();
    picUrl = document.querySelector('#picUrl').value.trim();

    user = {userName:userName,name:name,surname:surname,email:email,password:password,picUrl:picUrl};

    if (user) {
        document.querySelector('#welcome-page').classList.remove('register');
        document.querySelector('#welcome-page').classList.add('hidden');
        document.querySelector('#message-page').classList.remove('hidden');
        document.querySelector('#message-page').classList.add('wrap');
        console.log("sa");
        let socket = new SockJS('/do-chat');
        stompClient = Stomp.over(socket);

        //stompClient.connect({}, connectionSuccess);
    }
    event.preventDefault();
}

function prevent(event) {
    event.preventDefault();
    // Your code here
}

function connectionSuccess() {
    stompClient.subscribe('/topic/javainuse', onMessageReceived);

    stompClient.send("/app/chat.newUser", {}, JSON.stringify({
        sender : name,
        type : 'newUser'
    }))
}

function sendMessage(event) {
    var messageContent = document.querySelector('#chatMessage').value.trim();

    if (messageContent && stompClient) {
        var chatMessage = {
            sender : name,
            content : document.querySelector('#chatMessage').value,
            type : 'CHAT'
        };

        stompClient.send("/app/chat.sendMessage", {}, JSON
            .stringify(chatMessage));
        document.querySelector('#chatMessage').value = '';
    }
    event.preventDefault();
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    if (message.type === 'newUser') {
        messageElement.classList.add('event-data');
        message.content = message.sender + 'has joined the chat';
    } else if (message.type === 'Leave') {
        messageElement.classList.add('event-data');
        message.content = message.sender + 'has left the chat';
    } else {
        messageElement.classList.add('message-data');

        var element = document.createElement('i');
        var text = document.createTextNode(message.sender[0]);
        element.appendChild(text);

        messageElement.appendChild(element);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    document.querySelector('#messageList').appendChild(messageElement);
    document.querySelector('#messageList').scrollTop = document
        .querySelector('#messageList').scrollHeight;

}