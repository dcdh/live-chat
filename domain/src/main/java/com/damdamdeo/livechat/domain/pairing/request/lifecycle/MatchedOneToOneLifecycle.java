package com.damdamdeo.livechat.domain.pairing.request.lifecycle;

import com.damdamdeo.livechat.domain.pairing.request.ParticipantIdentifier;
import org.apache.commons.lang3.Validate;

import java.util.Objects;

public final class MatchedOneToOneLifecycle {

    private final MatchedIdentifier matchedIdentifier;
    private final MatchedAt matchedAt;
    private LeavedAt leavedAt;
    private MatchedStatus matchedStatus;

    public MatchedOneToOneLifecycle(final MatchedIdentifier matchedIdentifier,
                                    final MatchedAt matchedAt,
                                    final LeavedAt leavedAt,
                                    final MatchedStatus matchedStatus) {
        this.matchedIdentifier = Objects.requireNonNull(matchedIdentifier);
        this.matchedAt = Objects.requireNonNull(matchedAt);
        this.leavedAt = leavedAt;
        this.matchedStatus = Objects.requireNonNull(matchedStatus);
        if (MatchedStatus.MATCHED.equals(matchedStatus)) {
            Validate.validState(leavedAt == null);
        } else {
            Validate.validState(leavedAt != null);
        }
    }

    public MatchedOneToOneLifecycle(final MatchedIdentifier matchedIdentifier, final MatchedAt matchedAt) {
        this(matchedIdentifier, matchedAt, null, MatchedStatus.MATCHED);
    }

    public MatchedOneToOneLifecycle leave(final ParticipantIdentifier participantIdentifier,
                                          final LeavedAt leavedAt) {
        Objects.requireNonNull(participantIdentifier);
        Validate.validState(matchedIdentifier.belongsTo(participantIdentifier));
        Objects.requireNonNull(leavedAt);
        if (matchedIdentifier.isRequestedBy(participantIdentifier)) {
            this.leavedAt = leavedAt;
            this.matchedStatus = MatchedStatus.LEAVED_INITIATOR;
            return this;
        } else if (matchedIdentifier.isJoinedBy(participantIdentifier)) {
            this.leavedAt = leavedAt;
            this.matchedStatus = MatchedStatus.LEAVED_STRANGER;
            return this;
        } else {
            throw new IllegalStateException("Should not be here");
        }
    }

    public boolean belongsTo(final ParticipantIdentifier participantIdentifier) {
        return matchedIdentifier.belongsTo(participantIdentifier);
    }

    public MatchedIdentifier matchedIdentifier() {
        return matchedIdentifier;
    }

    public MatchedAt matchedAt() {
        return matchedAt;
    }

    public LeavedAt leavedAt() {
        return leavedAt;
    }

    public MatchedStatus matchedStatus() {
        return matchedStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MatchedOneToOneLifecycle that = (MatchedOneToOneLifecycle) o;
        return Objects.equals(matchedIdentifier, that.matchedIdentifier)
               && Objects.equals(matchedAt, that.matchedAt)
               && Objects.equals(leavedAt, that.leavedAt)
               && matchedStatus == that.matchedStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(matchedIdentifier, matchedAt, leavedAt, matchedStatus);
    }

    @Override
    public String toString() {
        return "MatchedOneToOneLifecycle{" +
               "matchedIdentifier=" + matchedIdentifier +
               ", matchedAt=" + matchedAt +
               ", leavedAt=" + leavedAt +
               ", matchedStatus=" + matchedStatus +
               '}';
    }
}
