package com.damdamdeo.livechat.infrastructure.pairing.data;

import com.damdamdeo.livechat.domain.pairing.data.PairingData;
import org.apache.commons.lang3.Validate;

import java.util.Objects;

public record SdpOfferData(String type, String sdp) implements PairingData {
    public SdpOfferData {
        Objects.requireNonNull(type);
        Objects.requireNonNull(sdp);
        Validate.validState(sdp.equals("offer"));
    }
}
