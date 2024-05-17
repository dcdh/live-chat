package com.damdamdeo.livechat.domain.pairing.request;

import com.damdamdeo.livechat.domain.pairing.usecase.UseCaseCommand;

import java.util.Objects;

public record MatchMakingRequest(RequestedBy requestedBy,
                                 RequestedAt requestedAt,
                                 MatchMakingRequestType matchMakingRequestType) implements UseCaseCommand {
    public MatchMakingRequest {
        Objects.requireNonNull(requestedBy);
        Objects.requireNonNull(requestedAt);
        Objects.requireNonNull(matchMakingRequestType);
    }
}