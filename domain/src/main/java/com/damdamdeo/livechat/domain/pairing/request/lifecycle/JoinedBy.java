package com.damdamdeo.livechat.domain.pairing.request.lifecycle;

import com.damdamdeo.livechat.domain.pairing.request.ParticipantIdentifier;
import com.damdamdeo.livechat.domain.pairing.request.RequestedBy;

import java.util.Objects;
import java.util.UUID;

public record JoinedBy(UUID identifier) implements ParticipantIdentifier {
    public JoinedBy {
        Objects.requireNonNull(identifier);
    }

    public JoinedBy(final RequestedBy requestedBy) {
        this(requestedBy.identifier());
    }

    public boolean is(final ParticipantIdentifier participantIdentifier) {
        return this.identifier.equals(participantIdentifier.identifier());
    }
}
