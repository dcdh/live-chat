package com.damdamdeo.livechat.infrastructure;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;

import java.io.StringReader;

public class MessageDecoder implements Decoder.Text<Message> {
    @Override
    public Message decode(final String jsonMessage) throws DecodeException {
        final JsonObject jsonObject = Json
                .createReader(new StringReader(jsonMessage)).readObject();
        return new Message(
                jsonObject.getString("name"),
                jsonObject.getString("data", null));
    }

    @Override
    public boolean willDecode(final String jsonMessage) {
        try {
            // Check if incoming message is valid JSON
            Json.createReader(new StringReader(jsonMessage)).readObject();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
