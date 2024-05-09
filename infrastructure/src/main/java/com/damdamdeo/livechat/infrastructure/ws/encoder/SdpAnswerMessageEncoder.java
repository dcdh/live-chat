package com.damdamdeo.livechat.infrastructure.ws.encoder;

import com.damdamdeo.livechat.infrastructure.pairing.SdpAnswerMessage;
import com.damdamdeo.livechat.infrastructure.ws.decoder.MessageDecoder;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;

public final class SdpAnswerMessageEncoder implements Encoder.Text<SdpAnswerMessage> {
    @Override
    public String encode(final SdpAnswerMessage message) throws EncodeException {
        final JsonObject jsonObject = Json.createObjectBuilder()
                .add(MessageDecoder.PAIRING_STEP, message.pairingStep().name())
                .add(MessageDecoder.DATA,
                        Json.createObjectBuilder()
                                .add(MessageDecoder.TYPE, message.type())
                                .add(MessageDecoder.SDP, message.sdp())
                                .build())
                .build();
        return jsonObject.toString();
    }
}