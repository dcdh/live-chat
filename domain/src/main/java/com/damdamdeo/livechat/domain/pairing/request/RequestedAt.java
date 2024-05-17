package com.damdamdeo.livechat.domain.pairing.request;

import java.time.ZonedDateTime;
import java.util.Objects;

public record RequestedAt(ZonedDateTime at) {
    public RequestedAt {
        Objects.requireNonNull(at);
    }
}
