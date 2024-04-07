const webSocket = new WebSocket('ws://localhost:8989/RoomChat/chat');
var room=JSON.parse(sessionStorage.getItem('roomData'));
var active=parseInt(sessionStorage.getItem('active'))+1;
let name;
webSocket.onerror = function (event) {
    onError(event)
};
webSocket.onopen = function (event) {
    onOpen(event)
};
webSocket.onmessage = function (event) {
    onMessage(event)
};
webSocket.onclose=function(event){
    onClose(event);
}
function onClose(){
    var date= new Date();
    var formatDate="["+date.getHours()+":"+date.getMinutes()+"]"
    if(inp.value!=""){
        const message={
            username:name,
            hasJoined:false,
            date:formatDate
        }
        webSocket.send(JSON.stringify(message));s
    }
}
function onMessage(event) {
    console.log(event.data);
    addMessage(JSON.parse(event.data),"sender");
}
function setup(){
    console.log(room);
    var h2=document.getElementById("name");
    h2.textContent=room.chatName;
    var p=document.getElementById("max");
    max.textContent="/ "+room.maxMembers;
    var p1=document.getElementById("current");
    p1.textContent=active;
}

function onOpen(event) {
    webSocket.send(JSON.stringify(room));
    setup();
}

function onError(event) {
    alert('An error occurred:' + event.data);
}

function send() {
    var input = document.getElementById("inpt");
    webSocket.send(input.value);
    const message = { sender: "", message: "", Date: new Date }
    webSocket.send(JSON.stringify(message));
    input.value = "";
}

function addMessage(message, classname) {
    console.log(message);
    var chat=document.getElementById("chat");

    var div = document.createElement("div");
    div.className="message";

    var date = document.createElement("p");
    date.className="date";
    date.textContent=message.date;
    div.appendChild(date);

    var sender = document.createElement("p");
    sender.className=classname;
    sender.textContent=message.sender;
    div.appendChild(sender);

    var message1 = document.createElement("p");
    message1.textContent=":   "+message.message;
    div.appendChild(message1);

    chat.appendChild(div);
}
function enter(){
    name=document.getElementById("userName").value;
    console.log(name);
    var p=document.getElementById("user");
    p.textContent=name;
    const modal = document.getElementById("modal");
    modal.classList.remove('active');
    const overlay = document.getElementById('overlay');
    overlay.classList.remove('active');
}
function popUp(){
    const modal = document.getElementById("modal");
    modal.classList.add('active');
    const overlay = document.getElementById('overlay');
    overlay.classList.add('active');
}
function sendMessage(){
    var inp=document.getElementById("TextInput");
    var date= new Date();
    var formatDate="["+date.getHours()+":"+date.getMinutes()+"]"
    if(inp.value!=""){
        const message={
            sender:name,
            message:inp.value,
            date:formatDate
        }
        inp.value="";
        webSocket.send(JSON.stringify(message));
        addMessage(message,"mine")
    }
}
function exit(){
    window.location.href = "http://localhost:8989/RoomChat";
}