const webSocket = new WebSocket('ws://localhost:8989/RoomChat/chat');

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

function onOpen(event) {
    console.log("Welcome!");
}

function onError(event) {
    alert('An error occurred:' + event.data);
}

function send() {
    var input = document.getElementById("inpt");
    webSocket.send(input.value);
    const message={sender:"",message:"",Date:new Date}
    webSocket.send(JSON.stringify(message));
    input.value = "";
}

function addMessage(event){
    var div=document.createElement("div");
    var date=document.createElement("p");
    var sender=document.createElement("p");
    var message=document.createElement("p");
    const message=JSON.parse(event.data);
    console.log(message);
    div.appendChild(p);
}