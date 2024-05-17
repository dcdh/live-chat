package com.damdamdeo.livechat.infrastructure.inmemory;

import com.damdamdeo.livechat.domain.pairing.request.RequestedBy;
import com.damdamdeo.livechat.domain.pairing.request.lifecycle.MatchedIdentifier;
import com.damdamdeo.livechat.domain.pairing.request.lifecycle.MatchedOneToOneLifecycle;
import com.damdamdeo.livechat.domain.pairing.request.lifecycle.MatchedOneToOneRepository;
import com.damdamdeo.livechat.domain.pairing.request.lifecycle.MatchedStatus;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class InMemoryMatchedOneToOneRepository implements MatchedOneToOneRepository {
    private static final Map<MatchedIdentifier, MatchedOneToOneLifecycle> MATCHED_ONE_TO_ONE_BY_IDENTIFIER = new ConcurrentHashMap<>();

    @Override
    public Optional<MatchedOneToOneLifecycle> findMatchedByRequester(final RequestedBy requestedBy) {
        return MATCHED_ONE_TO_ONE_BY_IDENTIFIER.entrySet().stream()
                .filter(entry -> entry.getKey().belongsTo(requestedBy))
                .filter(entry -> MatchedStatus.MATCHED.equals(entry.getValue().matchedStatus()))
                .map(Map.Entry::getValue)
                .findFirst();
    }

    @Override
    public void add(final MatchedOneToOneLifecycle matchedOneToOneLifecycle) {
        MATCHED_ONE_TO_ONE_BY_IDENTIFIER.put(matchedOneToOneLifecycle.matchedIdentifier(), matchedOneToOneLifecycle);
    }

    @Override
    public boolean has(final RequestedBy requestedBy) {
        return MATCHED_ONE_TO_ONE_BY_IDENTIFIER.keySet()
                .stream()
                .anyMatch(matchedIdentifier -> matchedIdentifier.belongsTo(requestedBy));
    }
}
