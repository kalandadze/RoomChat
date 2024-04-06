package com.mziuri.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mziuri.Classes.Room;
import com.mziuri.Service.DatabaseReader;

import java.util.List;

public class GetRoomsResponse {
    private final DatabaseReader reader=DatabaseReader.getReader();
    public List<Room> getResultList(){
        return reader.findRooms();
    }
    public String getResultString() throws JsonProcessingException {
        ObjectMapper mapper=new ObjectMapper();
        return mapper.writeValueAsString(getResultList());
    }
}
