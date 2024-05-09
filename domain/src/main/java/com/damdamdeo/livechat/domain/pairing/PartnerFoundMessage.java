package com.damdamdeo.livechat.domain.pairing;

import com.damdamdeo.livechat.domain.pairing.data.PartnerFoundData;

public record PartnerFoundMessage() implements Message<DomainPairingStep, PartnerFoundData> {
    @Override
    public DomainPairingStep pairingStep() {
        return DomainPairingStep.PARTNER_FOUND;
    }

    @Override
    public PartnerFoundData data() {
        return new PartnerFoundData();
    }
}
