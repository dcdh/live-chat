package com.damdamdeo.livechat.infrastructure.jaxrs;

import com.damdamdeo.livechat.domain.pairing.request.lifecycle.MatchedOneToOneLifecycle;
import com.damdamdeo.livechat.domain.pairing.request.lifecycle.MatchedStatus;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.ZonedDateTime;

@Schema(name = "MatchedOneToOneLifecycle", required = true,
        requiredProperties = {"initiatedBy", "joinedBy", "matchedAt", "matchedStatus"})
public record MatchedOneToOneLifecycleDTO(@Schema(required = true) String initiatedBy,
                                          @Schema(required = true) String joinedBy,
                                          @Schema(required = true) ZonedDateTime matchedAt,
                                          @Schema ZonedDateTime leavedAt,
                                          @Schema(required = true) MatchedStatus matchedStatus) {
    public MatchedOneToOneLifecycleDTO(final MatchedOneToOneLifecycle matchedOneToOneLifecycle) {
        this(
                matchedOneToOneLifecycle.matchedIdentifier().initiatedBy().identifier().toString(),
                matchedOneToOneLifecycle.matchedIdentifier().joinedBy().identifier().toString(),
                matchedOneToOneLifecycle.matchedAt().at(),
                matchedOneToOneLifecycle.leavedAt() != null ? matchedOneToOneLifecycle.leavedAt().at() : null,
                matchedOneToOneLifecycle.matchedStatus()
        );
    }
}
