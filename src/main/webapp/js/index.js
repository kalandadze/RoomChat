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
}
function setup() {
    const overlay = document.getElementById('overlay');
    overlay.addEventListener('click', () => {
        closeModel();
    })

    const btn = document.getElementById('btn');
    btn.addEventListener('click', () => {
        close();
    })
}

async function onOpen(event) {
    setup();
    var url = "http://localhost:8989/RoomChat/room";
    var response = await fetch(url, { method: "GET" });
    const rooms = await response.json();
    console.log(rooms);
    for (var i = 0; i < rooms.length; i++) {
        addRoom(rooms[i]);
    }
}
function addRoom(room) {
    console.log(room);

    // main div
    var div = document.createElement("div");
    div.className = "room";
    div.addEventListener('click', function () {
        open(room);
    });

    // name
    var name = document.createElement("p");
    name.textContent = room.chatName;
    div.appendChild(name);

    //        child div
    // capacityInfo
    var div2 = document.createElement("div");
    div2.className = "capacityInfo";
    // current users
    var current = document.createElement("p");
    current.textContent = 0;
    div2.appendChild(current);
    // max users
    var max = document.createElement("p");
    max.textContent = " / " + room.maxMembers;
    div2.appendChild(max);
    div.appendChild(div2);

    var div1 = document.getElementById("rooms");
    div1.appendChild(div);
}
function onError(event) {
    alert('An error occurred:' + event.data);
}
function open(room) {
    console.log(room);
}
function add() {
    const modal = document.getElementById("modal");
    modal.classList.add('active');
    const overlay = document.getElementById('overlay');
    overlay.classList.add('active');
}
async function close() {
    var name=document.getElementById("chatName").value;
    var max=document.getElementById("amount").value;
    var url="http://localhost:8989/RoomChat/room?name="+name+"&limit="+max;
    var response= await fetch(url,{method:"POST"});
    if(response.ok){
        location.reload();
    }else{
        alert("name and mebmer limit is nesseccary");
    }
}
function closeModel() {
    const modal = document.getElementById("modal");
    modal.classList.remove('active');
    const overlay = document.getElementById('overlay');
    overlay.classList.remove('active');
}


