package com.damdamdeo.livechat.domain.pairing;

import com.damdamdeo.livechat.domain.pairing.data.PairingData;

public interface Message<P extends PairingStep, T extends PairingData> {
    P pairingStep();

    T data();
}
