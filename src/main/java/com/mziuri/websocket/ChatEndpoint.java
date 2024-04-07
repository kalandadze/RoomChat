package com.mziuri.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.mziuri.Classes.JoinExitMessage;
import com.mziuri.Classes.Message;
import com.mziuri.Classes.Room;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint("/chat")
public class ChatEndpoint {
    private final static Map<Session, Room> sessions = new HashMap<>();

    public static Map<Session, Room> getSessions() {
        return sessions;
    }

    ObjectMapper mapper = new ObjectMapper();

    private void sendMessage(Message message, Room room) {
        sessions.forEach((key, value) -> {
            if (value == room) {
                try {
                    key.getBasicRemote().sendText(mapper.writeValueAsString(message));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    private void sendMessage(JoinExitMessage message, Room room) {
        sessions.forEach((key, value) -> {
            if (value == room) {
                try {
                    key.getBasicRemote().sendText(mapper.writeValueAsString(message));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    private void addUser(Room room, Session session) throws JsonProcessingException {
        sessions.put(session, room);
    }


    @OnClose
    public void close(Session session) throws JsonProcessingException {
        sessions.remove(session);
    }

    @OnOpen
    public void onOpen(Session session) {
    }

    @OnMessage
    public void onMessage(String message, Session session) throws JSONException, JsonProcessingException {
        JSONObject object = new JSONObject(message);
        if (object.has("chatName")) {
            System.out.println("a");
            Room room = new Room(object.getInt("id"), object.getInt("maxMembers"), object.getString("chatName"));
            addUser(room, session);
        } else if (object.has("sender")) {
            System.out.println("b");
            Message message1 = new Message(object.getString("sender"), object.getString("message"), (Date) object.get("date"));
            sendMessage(message1, sessions.get(session));
        } else if (object.has("hasJoined")) {
            System.out.println("d");
            JoinExitMessage message1=new JoinExitMessage(object.getString("username"), object.getBoolean("hasJoined"), (Date) object.get("date"));
            sendMessage(message1,sessions.get(session));
        }
    }
}
