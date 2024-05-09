package com.damdamdeo.livechat.infrastructure;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;

public class MessageEncoder implements Encoder.Text<Message> {
    public static final String NAME = "name";
    public static final String DATA = "data";

    @Override
    public String encode(final Message message) throws EncodeException {
        final JsonObjectBuilder builder = Json.createObjectBuilder()
                .add(NAME, message.name());
        if (message.data() != null) {
            builder.add(DATA, message.data());
        } else {
            builder.addNull(DATA);
        }
        return builder.build().toString();
    }
}
