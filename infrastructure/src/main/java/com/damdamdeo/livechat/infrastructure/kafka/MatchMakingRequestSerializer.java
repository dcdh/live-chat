package com.damdamdeo.livechat.infrastructure.kafka;

import com.damdamdeo.livechat.domain.pairing.request.MatchMakingRequest;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;

public final class MatchMakingRequestSerializer implements Serializer<MatchMakingRequest> {
    @Override
    public byte[] serialize(final String topic, final MatchMakingRequest data) {
        final JsonObject jsonObject = Json.createObjectBuilder()
                .add("requestedBy", data.requestedBy().identifier().toString())
                .add("requestedAt", data.requestedAt().at().toString())
                .add("matchMakingRequestType", data.matchMakingRequestType().name())
                .build();
        return jsonObject.toString().getBytes(StandardCharsets.UTF_8);
    }
}
