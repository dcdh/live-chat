package com.damdamdeo.livechat.domain.pairing.request.lifecycle;

import com.damdamdeo.livechat.domain.pairing.request.RequestedBy;

import java.util.Optional;

public interface MatchedOneToOneRepository {
    Optional<MatchedOneToOneLifecycle> findMatchedByRequester(RequestedBy requestedBy);

    void add(MatchedOneToOneLifecycle matchedOneToOneLifecycle);

    boolean has(RequestedBy requestedBy);
}
