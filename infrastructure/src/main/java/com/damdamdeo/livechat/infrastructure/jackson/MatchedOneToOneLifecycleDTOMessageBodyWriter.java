package com.damdamdeo.livechat.infrastructure.jackson;

import com.damdamdeo.livechat.infrastructure.jaxrs.MatchedOneToOneLifecycleDTO;
import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyWriter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public final class MatchedOneToOneLifecycleDTOMessageBodyWriter implements MessageBodyWriter<MatchedOneToOneLifecycleDTO> {
    @Override
    public boolean isWriteable(final Class<?> type,
                               final Type genericType,
                               final Annotation[] annotations,
                               final MediaType mediaType) {
        return MatchedOneToOneLifecycleDTO.class.isAssignableFrom(type);
    }

    @Override
    public void writeTo(final MatchedOneToOneLifecycleDTO matchedOneToOneLifecycleDTO,
                        final Class<?> type,
                        final Type genericType,
                        final Annotation[] annotations,
                        final MediaType mediaType,
                        final MultivaluedMap<String, Object> httpHeaders,
                        final OutputStream entityStream) throws IOException, WebApplicationException {
        final JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder()
                .add("initiatedBy", matchedOneToOneLifecycleDTO.initiatedBy())
                .add("joinedBy", matchedOneToOneLifecycleDTO.joinedBy())
                .add("matchedAt", matchedOneToOneLifecycleDTO.matchedAt().toString())
                .add("matchedStatus", matchedOneToOneLifecycleDTO.matchedStatus().name());
        if (matchedOneToOneLifecycleDTO.leavedAt() != null) {
            jsonObjectBuilder.add("leavedAt", matchedOneToOneLifecycleDTO.leavedAt().toString());
        } else {
            jsonObjectBuilder.addNull("leavedAt");
        }
        entityStream.write(
                jsonObjectBuilder.build().toString().getBytes(StandardCharsets.UTF_8)
        );
    }
}
