package com.damdamdeo.livechat.infrastructure.kafka;

import com.damdamdeo.livechat.domain.pairing.request.lifecycle.MatchedOneToOneLifecycle;
import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;

public final class MatchedOneToOneLifecycleSerializer implements Serializer<MatchedOneToOneLifecycle> {
    @Override
    public byte[] serialize(final String topic, final MatchedOneToOneLifecycle data) {
        final JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder()
                .add("matchedIdentifier", Json.createObjectBuilder()
                        .add("initiatedBy", data.matchedIdentifier().initiatedBy().identifier().toString())
                        .add("joinedBy", data.matchedIdentifier().joinedBy().identifier().toString())
                        .build()
                )
                .add("matchedAt", data.matchedAt().at().toString())
                .add("matchedStatus", data.matchedStatus().name());
        if (data.leavedAt() != null) {
            jsonObjectBuilder.add("leavedAt", data.leavedAt().at().toString());
        } else {
            jsonObjectBuilder.addNull("leavedAt");
        }
        return jsonObjectBuilder.build().toString().getBytes(StandardCharsets.UTF_8);
    }
}
