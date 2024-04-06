package com.mziuri.Response;

import com.mziuri.Classes.Room;
import com.mziuri.Request.AddRoomRequest;
import com.mziuri.Service.DatabaseReader;

public class AddRoomResponse {
    public AddRoomResponse(AddRoomRequest request) {
        Room room=request.getRoom();
        AddRoom(room);
    }
    private void AddRoom(Room room){
        DatabaseReader reader=DatabaseReader.getReader();
        reader.addRoom(room);
    }
}
