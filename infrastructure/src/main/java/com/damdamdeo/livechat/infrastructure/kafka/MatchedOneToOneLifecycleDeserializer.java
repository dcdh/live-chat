package com.damdamdeo.livechat.infrastructure.kafka;

import com.damdamdeo.livechat.domain.pairing.request.lifecycle.*;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import org.apache.kafka.common.serialization.Deserializer;

import java.time.ZonedDateTime;
import java.util.UUID;

public final class MatchedOneToOneLifecycleDeserializer implements Deserializer<MatchedOneToOneLifecycle> {
    @Override
    public MatchedOneToOneLifecycle deserialize(final String topic, final byte[] data) {
        final JsonObject jsonObject = Buffer.buffer(data).toJsonObject();
        return new MatchedOneToOneLifecycle(
                new MatchedIdentifier(
                        new InitiatedBy(
                                UUID.fromString(jsonObject.getJsonObject("matchedIdentifier").getString("initiatedBy"))),
                        new JoinedBy(
                                UUID.fromString(jsonObject.getJsonObject("matchedIdentifier").getString("joinedBy")))
                ),
                new MatchedAt(
                        ZonedDateTime.parse(jsonObject.getString("matchedAt"))),
                jsonObject.getString("leavedAt") != null ? new LeavedAt(
                        ZonedDateTime.parse(jsonObject.getString("leavedAt"))) : null,
                MatchedStatus.valueOf(jsonObject.getString("matchedStatus"))
        );
    }
}
