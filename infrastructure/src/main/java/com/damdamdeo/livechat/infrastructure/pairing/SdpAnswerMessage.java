package com.damdamdeo.livechat.infrastructure.pairing;

import com.damdamdeo.livechat.domain.pairing.Message;
import com.damdamdeo.livechat.infrastructure.pairing.data.SdpAnswerData;

public record SdpAnswerMessage(String type, String sdp) implements Message<TechnicalPairingStep, SdpAnswerData> {
    @Override
    public TechnicalPairingStep pairingStep() {
        return TechnicalPairingStep.SDP_ANSWER;
    }

    @Override
    public SdpAnswerData data() {
        return new SdpAnswerData(type, sdp);
    }
}
