package com.damdamdeo.livechat.infrastructure.pairing;

import com.damdamdeo.livechat.domain.pairing.PairingStep;

public enum TechnicalPairingStep implements PairingStep {
    SDP_OFFER,
    SDP_ANSWER,
    SDP_ICE_CANDIDATE,
}
