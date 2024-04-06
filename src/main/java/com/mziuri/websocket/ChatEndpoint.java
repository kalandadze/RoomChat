package com.mziuri.websocket;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint("/chat")
public class ChatEndpoint {
    private final Map<String, Session> sessions= new HashMap<>();

    private void sendMessage(String message) {
        sessions.forEach((key, value) -> {
            try {
                value.getBasicRemote().sendText(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @OnOpen
    public void onOpen(Session session) {
        sessions.put(session.getId(), session);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        sessions.remove(session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        sendMessage("[" + session.getId() + "]: " + message);
    }
}
