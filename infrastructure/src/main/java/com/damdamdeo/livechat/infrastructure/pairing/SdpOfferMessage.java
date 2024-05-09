package com.damdamdeo.livechat.infrastructure.pairing;

import com.damdamdeo.livechat.domain.pairing.Message;
import com.damdamdeo.livechat.infrastructure.pairing.data.SdpOfferData;

public record SdpOfferMessage(String type, String sdp) implements Message<TechnicalPairingStep, SdpOfferData> {
    @Override
    public TechnicalPairingStep pairingStep() {
        return TechnicalPairingStep.SDP_OFFER;
    }

    @Override
    public SdpOfferData data() {
        return new SdpOfferData(type, sdp);
    }
}
