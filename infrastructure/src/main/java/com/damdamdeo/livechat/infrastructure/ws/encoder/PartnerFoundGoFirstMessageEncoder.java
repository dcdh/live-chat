package com.damdamdeo.livechat.infrastructure.ws.encoder;

import com.damdamdeo.livechat.domain.pairing.PartnerFoundGoFirstMessage;
import com.damdamdeo.livechat.infrastructure.ws.decoder.MessageDecoder;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;

public final class PartnerFoundGoFirstMessageEncoder implements Encoder.Text<PartnerFoundGoFirstMessage> {
    @Override
    public String encode(final PartnerFoundGoFirstMessage message) throws EncodeException {
        final JsonObject jsonObject = Json.createObjectBuilder()
                .add(MessageDecoder.PAIRING_STEP, message.pairingStep().name())
                .build();
        return jsonObject.toString();
    }
}
