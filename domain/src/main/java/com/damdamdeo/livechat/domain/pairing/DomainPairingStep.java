package com.damdamdeo.livechat.domain.pairing;

public enum DomainPairingStep implements PairingStep {
    PAIRING_START,
    PAIRING_ABORT,
    PAIRING_DONE,
    PARTNER_FOUND,
    PARTNER_FOUND_GO_FIRST,
    PARTNER_LEFT
}
