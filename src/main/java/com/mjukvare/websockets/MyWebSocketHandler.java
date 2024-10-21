package com.mjukvare.websockets;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyWebSocketHandler extends TextWebSocketHandler {

    private final Logger log = LoggerFactory.getLogger(MyWebSocketHandler.class);
    private final ObjectMapper mapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("New connection established!");
        session.sendMessage(new TextMessage("hello friend"));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        String payload = message.getPayload();
        CustomMessage converted = mapper.readValue(payload, CustomMessage.class);

        log.info("Raw message: {}", payload);
        log.info("Here's the message property: {}", converted.getMessage());
    }

    public static class CustomMessage {
        private CustomMessage() { }
        private String message;

        public String getMessage() {
            return message;
        }
    }
}
