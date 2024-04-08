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
import java.util.Objects;

@ServerEndpoint("/chat")
public class ChatEndpoint {
    private final static Map<Session, Room> sessions = new HashMap<>();

    public static Map<Session, Room> getSessions() {
        return sessions;
    }

    ObjectMapper mapper = new ObjectMapper();

    private void sendMessage(Message message, Room room, Session session) {
        sessions.forEach((key, value) -> {
            if (value.getId() == room.getId() && !Objects.equals(session.getId(), key.getId())) {
                try {
                    key.getBasicRemote().sendText(mapper.writeValueAsString(message));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void sendMessage(JoinExitMessage message, Room room, Session session) {
        sessions.forEach((key, value) -> {
            if (value.getId() == room.getId() && !Objects.equals(session.getId(), key.getId())) {
                try {
                    key.getBasicRemote().sendText(mapper.writeValueAsString(message));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void sendActiveUsers(Room room) {
        int i = 0;
        for (Room room1 : sessions.values()) {
            if (room.getId() == room1.getId()) {
                i++;
            }
        }
        int finalI = i;
        sessions.forEach((key, value) -> {
            if (value.getId() == room.getId()) {
                try {
                    key.getBasicRemote().sendText(mapper.writeValueAsString(finalI));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void sendActiveUsers(Room room, Session session) {
        int i = 0;
        for (Room room1 : sessions.values()) {
            if (room.getId() == room1.getId()) {
                i++;
            }
        }
        int finalI = i-1;
        sessions.forEach((key, value) -> {
            if (value.getId() == room.getId() && !Objects.equals(session.getId(), key.getId())) {
                try {
                    key.getBasicRemote().sendText(mapper.writeValueAsString(finalI));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


    private void addUser(Room room, Session session) throws IOException {
        sessions.put(session, room);
        sendActiveUsers(room);
        RoomEndpoint endpoint = new RoomEndpoint();
        endpoint.resendData();
    }


    @OnClose
    public void close(Session session) throws IOException {
        sendActiveUsers(sessions.get(session),session);
        sessions.remove(session);
        RoomEndpoint endpoint = new RoomEndpoint();
        endpoint.resendData();
    }

    @OnMessage
    public void onMessage(String message, Session session) throws JSONException, IOException {
        JSONObject object = new JSONObject(message);
        if (object.has("chatName")) {
            System.out.println("a");
            Room room = new Room(object.getInt("id"), object.getInt("maxMembers"), object.getString("chatName"));
            addUser(room, session);
        } else if (object.has("sender")) {
            System.out.println("b");
            Message message1 = new Message(object.getString("sender"), object.getString("message"), object.getString("date"));
            sendMessage(message1, sessions.get(session), session);
        } else if (object.has("hasJoined")) {
            System.out.println("d");
            System.out.println(message);
            JoinExitMessage message1 = new JoinExitMessage(object.getString("username"), object.getBoolean("hasJoined"), object.getString("date"));
            sendMessage(message1, sessions.get(session), session);
        }
    }
}
