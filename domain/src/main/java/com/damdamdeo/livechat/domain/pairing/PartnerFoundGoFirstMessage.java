package com.damdamdeo.livechat.domain.pairing;

import com.damdamdeo.livechat.domain.pairing.data.PartnerFoundGoFirstData;

public record PartnerFoundGoFirstMessage() implements Message<DomainPairingStep, PartnerFoundGoFirstData> {
    @Override
    public DomainPairingStep pairingStep() {
        return DomainPairingStep.PARTNER_FOUND_GO_FIRST;
    }

    @Override
    public PartnerFoundGoFirstData data() {
        return new PartnerFoundGoFirstData();
    }
}
