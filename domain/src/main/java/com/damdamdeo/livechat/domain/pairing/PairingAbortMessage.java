package com.damdamdeo.livechat.domain.pairing;

import com.damdamdeo.livechat.domain.pairing.data.PairingAbortData;

public record PairingAbortMessage() implements Message<DomainPairingStep, PairingAbortData> {
    @Override
    public DomainPairingStep pairingStep() {
        return DomainPairingStep.PAIRING_ABORT;
    }

    @Override
    public PairingAbortData data() {
        return new PairingAbortData();
    }
}
