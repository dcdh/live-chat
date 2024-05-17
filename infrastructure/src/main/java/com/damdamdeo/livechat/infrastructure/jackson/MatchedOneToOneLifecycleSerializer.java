package com.damdamdeo.livechat.infrastructure.jackson;

import com.damdamdeo.livechat.domain.pairing.request.lifecycle.MatchedOneToOneLifecycle;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

@Deprecated
public final class MatchedOneToOneLifecycleSerializer extends JsonSerializer<MatchedOneToOneLifecycle> {
    @Override
    public void serialize(final MatchedOneToOneLifecycle matchedOneToOneLifecycle,
                          final JsonGenerator jsonGenerator,
                          final SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeString("matchedIdentifier");
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("initiatedBy", matchedOneToOneLifecycle.matchedIdentifier().initiatedBy().identifier().toString());
        jsonGenerator.writeStringField("joinedBy", matchedOneToOneLifecycle.matchedIdentifier().joinedBy().identifier().toString());
        jsonGenerator.writeEndObject();
        jsonGenerator.writeStringField("matchedAt", matchedOneToOneLifecycle.matchedAt().at().toString());
        if (matchedOneToOneLifecycle.leavedAt() != null) {
            jsonGenerator.writeStringField("leavedAt", matchedOneToOneLifecycle.leavedAt().at().toString());
        } else {
            jsonGenerator.writeNullField("leavedAt");
        }
        jsonGenerator.writeStringField("matchedStatus", matchedOneToOneLifecycle.matchedStatus().name());
        jsonGenerator.writeEndObject();
    }
}
