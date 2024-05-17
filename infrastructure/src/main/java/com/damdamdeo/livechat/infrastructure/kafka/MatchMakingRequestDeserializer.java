package com.damdamdeo.livechat.infrastructure.kafka;

import com.damdamdeo.livechat.domain.pairing.request.MatchMakingRequest;
import com.damdamdeo.livechat.domain.pairing.request.MatchMakingRequestType;
import com.damdamdeo.livechat.domain.pairing.request.RequestedAt;
import com.damdamdeo.livechat.domain.pairing.request.RequestedBy;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import org.apache.kafka.common.serialization.Deserializer;

import java.time.ZonedDateTime;

public final class MatchMakingRequestDeserializer implements Deserializer<MatchMakingRequest> {
    @Override
    public MatchMakingRequest deserialize(final String topic, byte[] data) {
        final JsonObject jsonObject = Buffer.buffer(data).toJsonObject();
        return new MatchMakingRequest(
                new RequestedBy(jsonObject.getString("requestedBy")),
                new RequestedAt(
                        ZonedDateTime.parse(jsonObject.getString("requestedAt"))),
                MatchMakingRequestType.valueOf(jsonObject.getString("matchMakingRequestType"))
        );
    }
}
