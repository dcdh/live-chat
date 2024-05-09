package com.damdamdeo.livechat.domain.pairing;

import com.damdamdeo.livechat.domain.pairing.data.PartnerLeftData;

public record PartnerLeftMessage() implements Message<DomainPairingStep, PartnerLeftData> {
    @Override
    public DomainPairingStep pairingStep() {
        return DomainPairingStep.PARTNER_LEFT;
    }

    @Override
    public PartnerLeftData data() {
        return new PartnerLeftData();
    }
}
