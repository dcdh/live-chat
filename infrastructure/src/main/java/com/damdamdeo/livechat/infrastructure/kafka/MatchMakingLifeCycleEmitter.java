package com.damdamdeo.livechat.infrastructure.kafka;

import com.damdamdeo.livechat.domain.pairing.request.MatchMakingRequest;
import com.damdamdeo.livechat.domain.pairing.request.lifecycle.OneToOneMatchingUseCase;
import com.damdamdeo.livechat.domain.pairing.request.lifecycle.MatchedOneToOneLifecycle;
import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import java.util.Objects;

@ApplicationScoped
public class MatchMakingLifeCycleEmitter {
    private final OneToOneMatchingUseCase oneToOneMatchingUseCase;

    public MatchMakingLifeCycleEmitter(final OneToOneMatchingUseCase oneToOneMatchingUseCase) {
        this.oneToOneMatchingUseCase = Objects.requireNonNull(oneToOneMatchingUseCase);
    }

    @Incoming("one-to-one-matchmaking-request")
    @Outgoing("one-to-one-matchmaking-match-out")
    @Blocking
    public MatchedOneToOneLifecycle algo(final MatchMakingRequest matchMakingRequest) {
        return oneToOneMatchingUseCase.execute(matchMakingRequest);
    }

}
