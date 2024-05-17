package com.damdamdeo.livechat.domain.pairing.request.lifecycle;

import java.time.ZonedDateTime;
import java.util.Objects;

public record MatchedAt(ZonedDateTime at) {
    public MatchedAt {
        Objects.requireNonNull(at);
    }
}
