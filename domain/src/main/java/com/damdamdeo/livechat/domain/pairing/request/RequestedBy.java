package com.damdamdeo.livechat.domain.pairing.request;

import java.util.Objects;
import java.util.UUID;

public record RequestedBy(UUID identifier) implements ParticipantIdentifier {
    public RequestedBy {
        Objects.requireNonNull(identifier);
    }

    public RequestedBy(final String identifier) {
        this(UUID.fromString(identifier));
    }

    public String identifierAsString() {
        return identifier.toString();
    }
}
