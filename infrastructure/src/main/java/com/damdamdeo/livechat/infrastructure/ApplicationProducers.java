package com.damdamdeo.livechat.infrastructure;

import com.damdamdeo.livechat.domain.pairing.request.RequestedAt;
import com.damdamdeo.livechat.domain.pairing.request.RequestedAtProvider;
import com.damdamdeo.livechat.domain.pairing.request.lifecycle.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

import java.time.ZonedDateTime;

@ApplicationScoped
public class ApplicationProducers {
    @ApplicationScoped
    @Produces
    public OneToOneMatchingUseCase produceMatchUseCase(final RequesterRepository requesterRepository,
                                                       final MatchedOneToOneRepository matchedOneToOneRepository,
                                                       final MatchedAtProvider matchedAtProvider,
                                                       final LeavedAtProvider leavedAtProvider) {
        return new OneToOneMatchingUseCase(requesterRepository, matchedOneToOneRepository, matchedAtProvider, leavedAtProvider);
    }

    @ApplicationScoped
    @Produces
    public MatchedAtProvider produceMatchedAtProvider() {
        return () -> new MatchedAt(ZonedDateTime.now());
    }

    @ApplicationScoped
    @Produces
    public LeavedAtProvider produceLeavedAtProvider() {
        return () -> new LeavedAt(ZonedDateTime.now());
    }

    @ApplicationScoped
    @Produces
    public RequestedAtProvider produceRequestedAtProvider() {
        return () -> new RequestedAt(ZonedDateTime.now());
    }
}