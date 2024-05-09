package com.damdamdeo.livechat.infrastructure.pairing.data;

import com.damdamdeo.livechat.domain.pairing.data.PairingData;
import org.apache.commons.lang3.Validate;

import java.util.Objects;

public record SdpAnswerData(String type, String sdp) implements PairingData {
    public SdpAnswerData {
        Objects.requireNonNull(type);
        Objects.requireNonNull(sdp);
        Validate.validState(sdp.equals("answer"));
    }
}
