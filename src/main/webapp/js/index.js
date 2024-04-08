const webSocket = new WebSocket('ws://localhost:8989/RoomChat/room');
let active;
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
    active=JSON.parse(event.data);
    console.log(active);
    for(var i=0;i<active.length;i++){
        changeData(active[i]);
    }
}
function changeData(activeUsers){
    console.log(activeUsers.room);
    var p=document.getElementById(activeUsers.room.id);
    p.textContent=activeUsers.active;
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
    for (var i = 0; i < rooms.length; i++) {
        addRoom(rooms[i]);
    }
    for(var i=0;i<active.length;i++){
        changeData(active[i]);
    }
}
function addRoom(room) {

    // main div
    var div = document.createElement("div");
    div.className = "room";
    div.onclick = function () {
        openRoom(room);
    }

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
    current.id=room.id;
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
async function openRoom(room) {
    var active=document.getElementById(room.id).textContent;
    if(parseInt(active)==room.maxMembers){
        alert("the Room is already full");
    }else{
        sessionStorage.setItem('roomData', JSON.stringify(room));
        sessionStorage.setItem('active',active);
        window.location.href = "http://localhost:8989/RoomChat/chatroom.html";
    }
}
function add() {
    const modal = document.getElementById("modal");
    modal.classList.add('active');
    const overlay = document.getElementById('overlay');
    overlay.classList.add('active');
}
async function close() {
    var name = document.getElementById("chatName").value;
    var max = document.getElementById("amount").value;
    var url = "http://localhost:8989/RoomChat/room?name=" + name + "&limit=" + max;
    var response = await fetch(url, { method: "POST" });
    if (response.ok) {
        location.reload();
    } else {
        alert("name and mebmer limit is nesseccary");
    }
}
function closeModel() {
    const modal = document.getElementById("modal");
    modal.classList.remove('active');
    const overlay = document.getElementById('overlay');
    overlay.classList.remove('active');
}


