package com.mziuri.Request;

import com.mziuri.Classes.Room;
import lombok.Getter;

@Getter
public class AddRoomRequest {
    Room room;

    public AddRoomRequest(String chatName,String maxMembers) {
        this.room = new Room();
        room.setChatName(chatName);
        room.setMaxMembers(Integer.parseInt(maxMembers));
    }
}
