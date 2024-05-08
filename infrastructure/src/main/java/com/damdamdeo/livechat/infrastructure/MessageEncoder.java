package com.damdamdeo.livechat.infrastructure;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;

public class MessageEncoder implements Encoder.Text<Message> {
    @Override
    public String encode(final Message message) throws EncodeException {
        final JsonObject jsonObject = Json.createObjectBuilder()
                .add("name", message.name())
                .add("data", message.data()).build();
        return jsonObject.toString();
    }
}
