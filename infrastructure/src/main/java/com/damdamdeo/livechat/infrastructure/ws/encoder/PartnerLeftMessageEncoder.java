package com.damdamdeo.livechat.infrastructure.ws.encoder;

import com.damdamdeo.livechat.domain.pairing.PartnerLeftMessage;
import com.damdamdeo.livechat.infrastructure.ws.decoder.MessageDecoder;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;

public final class PartnerLeftMessageEncoder implements Encoder.Text<PartnerLeftMessage> {
    @Override
    public String encode(final PartnerLeftMessage message) throws EncodeException {
        final JsonObject jsonObject = Json.createObjectBuilder()
                .add(MessageDecoder.PAIRING_STEP, message.pairingStep().name())
                .build();
        return jsonObject.toString();
    }
}
