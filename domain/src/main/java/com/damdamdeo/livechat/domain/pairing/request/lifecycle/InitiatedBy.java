package com.damdamdeo.livechat.domain.pairing.request.lifecycle;

import com.damdamdeo.livechat.domain.pairing.request.ParticipantIdentifier;
import com.damdamdeo.livechat.domain.pairing.request.RequestedBy;

import java.util.Objects;
import java.util.UUID;

public record InitiatedBy(UUID identifier) implements ParticipantIdentifier {
    public InitiatedBy {
        Objects.requireNonNull(identifier);
    }

    public InitiatedBy(final RequestedBy requestedBy) {
        this(requestedBy.identifier());
    }

    public boolean is(final ParticipantIdentifier participantIdentifier) {
        return this.identifier.equals(participantIdentifier.identifier());
    }
}
