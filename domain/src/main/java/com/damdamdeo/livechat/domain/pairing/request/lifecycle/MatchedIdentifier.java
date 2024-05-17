package com.damdamdeo.livechat.domain.pairing.request.lifecycle;

import com.damdamdeo.livechat.domain.pairing.request.ParticipantIdentifier;
import com.damdamdeo.livechat.domain.pairing.request.RequestedBy;

import java.util.Objects;

public record MatchedIdentifier(InitiatedBy initiatedBy,
                                JoinedBy joinedBy) {
    public MatchedIdentifier {
        Objects.requireNonNull(initiatedBy);
        Objects.requireNonNull(joinedBy);
    }

    public boolean belongsTo(final ParticipantIdentifier participantIdentifier) {
        return initiatedBy.is(participantIdentifier)
               || joinedBy.is(participantIdentifier);
    }

    public boolean isRequestedBy(final ParticipantIdentifier participantIdentifier) {
        return initiatedBy.equals(participantIdentifier);
    }

    public boolean isJoinedBy(final ParticipantIdentifier participantIdentifier) {
        return joinedBy.equals(participantIdentifier);
    }
}
