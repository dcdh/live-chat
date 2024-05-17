package com.damdamdeo.livechat.domain.pairing.request.lifecycle;

import java.time.ZonedDateTime;
import java.util.Objects;

public record LeavedAt(ZonedDateTime at) {
    public LeavedAt {
        Objects.requireNonNull(at);
    }
}
