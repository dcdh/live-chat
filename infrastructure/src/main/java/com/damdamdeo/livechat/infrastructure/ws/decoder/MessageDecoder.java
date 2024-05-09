package com.damdamdeo.livechat.infrastructure.ws.decoder;

import com.damdamdeo.livechat.domain.pairing.*;
import com.damdamdeo.livechat.infrastructure.pairing.SdpAnswerMessage;
import com.damdamdeo.livechat.infrastructure.pairing.SdpIceCandidateMessage;
import com.damdamdeo.livechat.infrastructure.pairing.SdpOfferMessage;
import com.damdamdeo.livechat.infrastructure.pairing.TechnicalPairingStep;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;

import java.io.StringReader;

public class MessageDecoder implements Decoder.Text<Message<?, ?>> {
    public static final String PAIRING_STEP = "pairingStep";
    public static final String DATA = "data";
    public static final String TYPE = "type";
    public static final String SDP = "sdp";
    public static final String CANDIDATE = "candidate";
    public static final String SDP_MID = "sdpMid";
    public static final String SDP_MLINE_INDEX = "sdpMLineIndex";
    public static final String USERNAME_FRAGMENT = "usernameFragment";

    @Override
    public Message<?, ?> decode(final String jsonMessage) throws DecodeException {
        final JsonObject jsonObject = Json.createReader(new StringReader(jsonMessage)).readObject();
        final String pairingStep = jsonObject.getString(PAIRING_STEP);
        if (DomainPairingStep.PAIRING_START.name().equals(pairingStep)) {
            return new PairingStartMessage();
        } else if (DomainPairingStep.PAIRING_ABORT.name().equals(pairingStep)) {
            return new PairingAbortMessage();
        } else if (DomainPairingStep.PAIRING_DONE.name().equals(pairingStep)) {
            return new PairingDoneMessage();
        } else if (TechnicalPairingStep.SDP_ANSWER.name().equals(pairingStep)) {
            final JsonObject data = jsonObject.getJsonObject(DATA);
            return new SdpAnswerMessage(
                    data.getString(TYPE),
                    data.getString(SDP));
        } else if (TechnicalPairingStep.SDP_ICE_CANDIDATE.name().equals(pairingStep)) {
            final JsonObject data = jsonObject.getJsonObject(DATA);
            return new SdpIceCandidateMessage(
                    data.getString(CANDIDATE),
                    data.getString(SDP_MID),
                    data.getInt(SDP_MLINE_INDEX),
                    data.getString(USERNAME_FRAGMENT));
        } else if (TechnicalPairingStep.SDP_OFFER.name().equals(pairingStep)) {
            final JsonObject data = jsonObject.getJsonObject(DATA);
            return new SdpOfferMessage(
                    data.getString(TYPE),
                    data.getString(SDP));
        } else {
            throw new IllegalStateException("Should not be here");
        }
    }

    @Override
    public boolean willDecode(final String jsonMessage) {
        try {
            final JsonObject jsonObject = Json.createReader(new StringReader(jsonMessage)).readObject();
            final String pairingStep = jsonObject.getString(PAIRING_STEP, null);
            return pairingStep != null;
        } catch (Exception e) {
            return false;
        }
    }
}
