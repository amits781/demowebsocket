package com.aidyn.demowebsocket.service;

import com.aidyn.demowebsocket.config.MyWebSocketHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Service
public class MessageService {

    public boolean isClientConnected(String clientId) {
        WebSocketSession session = MyWebSocketHandler.sessions.get(clientId);
        return (session != null && session.isOpen());
    }

    public void sendMessageToClient(String clientId, String message) {
        try {
            WebSocketSession session = MyWebSocketHandler.sessions.get(clientId);
            if (session != null && session.isOpen()) {
                session.sendMessage(new TextMessage(message));
                System.out.println("Message sent to client " + clientId);
            } else {
                System.out.println("Client " + clientId + " is not connected");
            }
        } catch (Exception e) {
            System.out.println("Exception occured: " + e.getMessage());
        }
    }
}
