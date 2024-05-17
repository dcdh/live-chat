package com.damdamdeo.livechat.infrastructure.jaxrs;

import com.damdamdeo.livechat.domain.pairing.request.RequestedBy;
import com.damdamdeo.livechat.domain.pairing.request.lifecycle.MatchedOneToOneLifecycle;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Multi;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.sse.OutboundSseEvent;
import jakarta.ws.rs.sse.Sse;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.jboss.resteasy.reactive.RestStreamElementType;

import java.util.Objects;

@Path("/api/oneToOne/matchmaking/lifecycle")
@ApplicationScoped
public class MatchMakingLifecycleEndpoint {
    private final Multi<MatchedOneToOneLifecycle> matchedOneToOneConsumer;
    private final Sse sse;

    public MatchMakingLifecycleEndpoint(@Channel("one-to-one-matchmaking-match") final Multi<MatchedOneToOneLifecycle> matchedOneToOneConsumer,
                                        final Sse sse) {
        this.matchedOneToOneConsumer = Objects.requireNonNull(matchedOneToOneConsumer);
        this.sse = Objects.requireNonNull(sse);
    }

    @GET
    @Path("/{requestedBy}/stream")
    @RestStreamElementType(MediaType.APPLICATION_JSON)
    public Multi<OutboundSseEvent> subscribe(
            @Parameter(required = true, schema = @Schema(type = SchemaType.STRING), name = "requestedBy", examples = {
                    @ExampleObject(
                            name = "00000000-0000-0000-0000-000000000001",
                            value = "00000000-0000-0000-0000-000000000001"
                    ),
                    @ExampleObject(
                            name = "00000000-0000-0000-0000-000000000002",
                            value = "00000000-0000-0000-0000-000000000002"
                    )
            })
            @PathParam("requestedBy") final RequestedBy requestedBy) {
        return matchedOneToOneConsumer.onItem()
                .transform(matchedOneToOneLifecycle -> {
                    Log.infov("Broadcasting: {0}", matchedOneToOneLifecycle.toString());// TODO debugv
                    return matchedOneToOneLifecycle;
                })
                .filter(matchedOneToOneLifecycle -> matchedOneToOneLifecycle.belongsTo(requestedBy))
                .map(matchedOneToOneLifecycle -> sse.newEventBuilder()
                        .name("MatchedOneToOneLifecycle")
                        .data(MatchedOneToOneLifecycleDTO.class, new MatchedOneToOneLifecycleDTO(matchedOneToOneLifecycle))
                        .build());
    }
}
