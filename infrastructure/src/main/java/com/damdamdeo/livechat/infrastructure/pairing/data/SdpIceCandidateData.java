package com.damdamdeo.livechat.infrastructure.pairing.data;

import com.damdamdeo.livechat.domain.pairing.data.PairingData;

import java.util.Objects;

public record SdpIceCandidateData(String candidate,
                                  String sdpMid,
                                  Integer sdpMLineIndex,
                                  String usernameFragment) implements PairingData {
    public SdpIceCandidateData {
        Objects.requireNonNull(candidate);
        Objects.requireNonNull(sdpMid);
        Objects.requireNonNull(sdpMLineIndex);
        Objects.requireNonNull(usernameFragment);
    }
}
