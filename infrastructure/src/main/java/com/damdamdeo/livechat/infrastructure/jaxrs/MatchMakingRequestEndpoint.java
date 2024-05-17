package com.damdamdeo.livechat.infrastructure.jaxrs;

import com.damdamdeo.livechat.domain.pairing.request.MatchMakingRequest;
import com.damdamdeo.livechat.domain.pairing.request.MatchMakingRequestType;
import com.damdamdeo.livechat.domain.pairing.request.RequestedAtProvider;
import com.damdamdeo.livechat.domain.pairing.request.RequestedBy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import java.util.Objects;
import java.util.concurrent.CompletionStage;

@Path("/api/oneToOne/matchmaking/request")
@ApplicationScoped
public class MatchMakingRequestEndpoint {
    private final Emitter<MatchMakingRequest> matchMakingRequestEmitter;
    private final RequestedAtProvider requestedAtProvider;

    public MatchMakingRequestEndpoint(@Channel("one-to-one-matchmaking-request-out") final Emitter<MatchMakingRequest> matchMakingRequestEmitter,
                                      final RequestedAtProvider requestedAtProvider) {
        this.matchMakingRequestEmitter = Objects.requireNonNull(matchMakingRequestEmitter);
        this.requestedAtProvider = Objects.requireNonNull(requestedAtProvider);
    }

    // When two people call this request there is a match
    // So I need to store them inside a FIFO
    // Each second I need to check the stack to notify the match ...
    @POST
    @Path("/{requestedBy}/findAStranger")
    public CompletionStage<Void> findAStranger(
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
        return matchMakingRequestEmitter.send(
                new MatchMakingRequest(requestedBy, requestedAtProvider.now(), MatchMakingRequestType.FIND));
    }

    @POST
    @Path("/{requestedBy}/cancel")
    public CompletionStage<Void> cancelMatchMaking(
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
        return matchMakingRequestEmitter.send(
                new MatchMakingRequest(requestedBy, requestedAtProvider.now(), MatchMakingRequestType.CANCEL));
    }

    @POST
    @Path("/{requestedBy}/leave")
    public CompletionStage<Void> leaveMatchMaking(
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
        return matchMakingRequestEmitter.send(
                new MatchMakingRequest(requestedBy, requestedAtProvider.now(), MatchMakingRequestType.LEAVE));
    }
}
