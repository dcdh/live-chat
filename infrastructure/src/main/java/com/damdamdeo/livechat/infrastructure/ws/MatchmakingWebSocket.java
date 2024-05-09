package com.damdamdeo.livechat.infrastructure.ws;

import com.damdamdeo.livechat.domain.pairing.*;
import com.damdamdeo.livechat.infrastructure.pairing.TechnicalPairingStep;
import com.damdamdeo.livechat.infrastructure.ws.decoder.MessageDecoder;
import com.damdamdeo.livechat.infrastructure.ws.encoder.*;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;

@ServerEndpoint(value = "/api/matchmaking",
        encoders = {
                PairingAbortMessageEncoder.class,
                PairingDoneMessageEncoder.class,
                PairingStartMessageEncoder.class,
                PartnerFoundGoFirstMessageEncoder.class,
                PartnerFoundMessageEncoder.class,
                PartnerLeftMessageEncoder.class,
                SdpAnswerMessageEncoder.class,
                SdpIceCandidateMessageEncoder.class,
                SdpOfferMessageEncoder.class},
        decoders = {
                MessageDecoder.class
        })
@ApplicationScoped
public class MatchmakingWebSocket {
    private static final ConcurrentLinkedQueue<Exchange> EXCHANGES = new ConcurrentLinkedQueue<>();

    @OnOpen
    public void onOpen(final Session session) {
        Log.infof("onOpen> " + session.getId());
    }

    @OnClose
    public void onClose(final Session session) {
        pairingAbort(session);
    }

    @OnError
    public void onError(final Session session, final Throwable throwable) {
        Log.error("onError> " + session.getId(), throwable);
    }

    @OnMessage
    public void onMessage(final Session session, final Message<?, ?> message) {
        Log.infof("Received message: '%s'", message);
        final PairingStep pairingStep = message.pairingStep();
        if (pairingStep.equals(DomainPairingStep.PAIRING_START)) {
            pairingStart(session);
        } else if (pairingStep.equals(DomainPairingStep.PAIRING_ABORT)) {
            pairingAbort(session);
        } else if (pairingStep.equals(DomainPairingStep.PAIRING_DONE)) {
            pairingDone(session);
        } else if (pairingStep.equals(TechnicalPairingStep.SDP_OFFER)
                   || pairingStep.equals(TechnicalPairingStep.SDP_ANSWER)
                   || pairingStep.equals(TechnicalPairingStep.SDP_ICE_CANDIDATE)) {
            final Exchange exchange = findExchange(session);
            if (exchange != null && exchange.paired()) {
                send(exchange.otherUser(session), message); // forward message to other user
            } else {
                Log.warn("Received SDP message from unpaired user");
            }
        }
    }

    private static Exchange findExchange(final Session session) {
        return EXCHANGES.stream()
                .filter(element -> element.belongsToSession(session))
                .findFirst()
                .orElse(null);
    }

    private static void pairingStart(final Session session) {
        EXCHANGES.removeIf(ex -> ex.belongsToSession(session)); // prevent double queueing
        var exchange = EXCHANGES.stream()
                .filter(ex -> ex.b() == null)
                .findFirst()
                .orElse(null);
        if (exchange != null) {
            exchange.setB(session);
            send(exchange.a(), new PartnerFoundGoFirstMessage());
            send(exchange.b(), new PartnerFoundMessage());
        } else {
            EXCHANGES.add(new Exchange(session));
        }
    }

    private static void pairingAbort(final Session session) {
        var exchange = findExchange(session);
        if (exchange != null) {
            send(exchange.otherUser(session), new PartnerLeftMessage());
            EXCHANGES.remove(exchange);
        }
    }

    private static void pairingDone(final Session session) {
        final Exchange exchange = findExchange(session);
        if (exchange != null) {
            exchange.incrementDoneCount();
        }
        EXCHANGES.removeIf(Exchange::doneCountEquals2);
    }

    private static void send(final Session session, final Message<?, ?> message) {
        if (session != null) {
            Objects.requireNonNull(message);
            session.getAsyncRemote().sendObject(message, result -> {
                if (result.getException() != null) {
                    Log.error("Unable to send message: " + result.getException());
                }
            });
        }
    }
}
