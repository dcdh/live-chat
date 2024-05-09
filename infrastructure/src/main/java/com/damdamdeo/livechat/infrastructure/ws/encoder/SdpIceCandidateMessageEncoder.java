package com.damdamdeo.livechat.infrastructure.ws.encoder;

import com.damdamdeo.livechat.infrastructure.pairing.SdpIceCandidateMessage;
import com.damdamdeo.livechat.infrastructure.ws.decoder.MessageDecoder;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;

public final class SdpIceCandidateMessageEncoder implements Encoder.Text<SdpIceCandidateMessage> {
    @Override
    public String encode(final SdpIceCandidateMessage message) throws EncodeException {
        final JsonObject jsonObject = Json.createObjectBuilder()
                .add(MessageDecoder.PAIRING_STEP, message.pairingStep().name())
                .add(MessageDecoder.DATA,
                        Json.createObjectBuilder()
                                .add(MessageDecoder.CANDIDATE, message.candidate())
                                .add(MessageDecoder.SDP_MID, message.sdpMid())
                                .add(MessageDecoder.SDP_MLINE_INDEX, message.sdpMLineIndex())
                                .add(MessageDecoder.USERNAME_FRAGMENT, message.usernameFragment())
                                .build())
                .build();
        return jsonObject.toString();
    }
}
