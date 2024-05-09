package com.damdamdeo.livechat.domain.pairing;

import com.damdamdeo.livechat.domain.pairing.data.PairingStartData;

public record PairingStartMessage() implements Message<DomainPairingStep, PairingStartData> {
    @Override
    public DomainPairingStep pairingStep() {
        return DomainPairingStep.PAIRING_START;
    }

    @Override
    public PairingStartData data() {
        return new PairingStartData();
    }
}
