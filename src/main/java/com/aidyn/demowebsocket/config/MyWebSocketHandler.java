package com.aidyn.demowebsocket.config;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyWebSocketHandler extends TextWebSocketHandler {

    public final static Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Client connected: " + session.getId()
                + " from " + session.getRemoteAddress() + " | Name: "+ session.getHandshakeHeaders().asSingleValueMap());
        Map<String,String> headers = session.getHandshakeHeaders().asSingleValueMap();
        if(headers.containsKey("name")){
            sessions.put(headers.get("name"), session);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("Client disconnected: " + session.getId()
                + " from " + session.getRemoteAddress()
                + ", Reason: " + status);
        // Remove session from any tracking list if required
        Map<String,String> headers = session.getHandshakeHeaders().asSingleValueMap();
        if(headers.containsKey("name")){
            sessions.remove(headers.get("name"));
        }
    }
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        // Handle the message: echo it back
        session.sendMessage(new TextMessage("Echo: " + payload));
    }
}
