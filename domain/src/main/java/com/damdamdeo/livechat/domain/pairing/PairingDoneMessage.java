package com.damdamdeo.livechat.domain.pairing;

import com.damdamdeo.livechat.domain.pairing.data.PairingDoneData;

public record PairingDoneMessage() implements Message<DomainPairingStep, PairingDoneData> {
    @Override
    public DomainPairingStep pairingStep() {
        return DomainPairingStep.PAIRING_DONE;
    }

    @Override
    public PairingDoneData data() {
        return new PairingDoneData();
    }
}
