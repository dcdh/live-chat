package com.damdamdeo.livechat.infrastructure.pairing;

import com.damdamdeo.livechat.domain.pairing.Message;
import com.damdamdeo.livechat.infrastructure.pairing.data.SdpIceCandidateData;

public record SdpIceCandidateMessage(String candidate,
                                     String sdpMid,
                                     Integer sdpMLineIndex,
                                     String usernameFragment) implements Message<TechnicalPairingStep, SdpIceCandidateData> {
    @Override
    public TechnicalPairingStep pairingStep() {
        return TechnicalPairingStep.SDP_ICE_CANDIDATE;
    }

    @Override
    public SdpIceCandidateData data() {
        return new SdpIceCandidateData(candidate, sdpMid, sdpMLineIndex, usernameFragment);
    }
}
