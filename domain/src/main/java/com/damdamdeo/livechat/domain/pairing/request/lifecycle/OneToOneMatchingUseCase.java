package com.damdamdeo.livechat.domain.pairing.request.lifecycle;

import com.damdamdeo.livechat.domain.pairing.request.MatchMakingRequest;
import com.damdamdeo.livechat.domain.pairing.request.RequestedBy;
import com.damdamdeo.livechat.domain.pairing.usecase.UseCase;

import java.util.Objects;

public final class OneToOneMatchingUseCase implements UseCase<MatchedOneToOneLifecycle, MatchMakingRequest> {
    private final RequesterRepository requesterRepository;
    private final MatchedOneToOneRepository matchedOneToOneRepository;
    private final MatchedAtProvider matchedAtProvider;
    private final LeavedAtProvider leavedAtProvider;

    public OneToOneMatchingUseCase(final RequesterRepository requesterRepository,
                                   final MatchedOneToOneRepository matchedOneToOneRepository,
                                   final MatchedAtProvider matchedAtProvider,
                                   final LeavedAtProvider leavedAtProvider) {
        this.requesterRepository = Objects.requireNonNull(requesterRepository);
        this.matchedOneToOneRepository = Objects.requireNonNull(matchedOneToOneRepository);
        this.matchedAtProvider = Objects.requireNonNull(matchedAtProvider);
        this.leavedAtProvider = Objects.requireNonNull(leavedAtProvider);
    }

    @Override
    public MatchedOneToOneLifecycle execute(final MatchMakingRequest matchMakingRequest) {
        Objects.requireNonNull(matchMakingRequest);
        final MatchedOneToOneLifecycle matchedOneToOneLifecycle = switch (matchMakingRequest.matchMakingRequestType()) {
            case FIND -> {
                final RequestedBy requestedBy = matchMakingRequest.requestedBy();
                final RequestedBy previousRequestedBy = requesterRepository.poll();
                if (previousRequestedBy != null
                    && !previousRequestedBy.equals(requestedBy) // do not match our self
                    && !matchedOneToOneRepository.has(requestedBy)) { // do not match again with same stranger
                    yield new MatchedOneToOneLifecycle(
                            new MatchedIdentifier(
                                    new InitiatedBy(previousRequestedBy),
                                    new JoinedBy(requestedBy)
                            ),
                            matchedAtProvider.now()
                    );
                } else if (!requesterRepository.contains(requestedBy)) {
                    requesterRepository.add(requestedBy);
                    yield null;
                } else {
                    yield null;
                }
            }
            case CANCEL -> {
                final RequestedBy requestedBy = matchMakingRequest.requestedBy();
                requesterRepository.remove(requestedBy);
                yield null;
            }
            case LEAVE -> {
                final RequestedBy requestedBy = matchMakingRequest.requestedBy();
                requesterRepository.remove(requestedBy);// should not be present

                yield matchedOneToOneRepository.findMatchedByRequester(requestedBy)
                        .map(matchedByRequesterToLeave -> matchedByRequesterToLeave.leave(requestedBy, leavedAtProvider.now()))
                        .orElse(null);
            }
        };
        if (matchedOneToOneLifecycle != null) {
            matchedOneToOneRepository.add(matchedOneToOneLifecycle);
        }
        return matchedOneToOneLifecycle;
    }
}
