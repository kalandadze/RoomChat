const webSocket = new WebSocket('ws://localhost:8989/RoomChat/chat');
var room=JSON.parse(sessionStorage.getItem('roomData'));
var active=parseInt(sessionStorage.getItem('active'))+1;
var active1=parseInt(sessionStorage.getItem('active'))+1;
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

function onMessage(event) {
    console.log(event);
    addMessage(event);
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

function addMessage(event) {
    // var div = document.createElement("div");
    // var date = document.createElement("p");
    // var sender = document.createElement("p");
    // var message = document.createElement("p");
    // const message = JSON.parse(event.data);
    // console.log(message);
    // div.appendChild(p);
}
function enter(){
    name=document.getElementById("userName").value;
    console.log(name);
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