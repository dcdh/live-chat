package com.damdamdeo.livechat.infrastructure;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.util.concurrent.ConcurrentLinkedQueue;

@ServerEndpoint(value = "/api/matchmaking",
        encoders = {MessageEncoder.class},
        decoders = {MessageDecoder.class})
@ApplicationScoped
public class MatchmakingWebSocket {
    private static final ConcurrentLinkedQueue<Exchange> queue = new ConcurrentLinkedQueue<>();

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
    public void onMessage(final Session session, final Message message) {
        Log.infof("Received message: '%s'", message);
        switch (message.name()) {
            case "PAIRING_START" -> pairingStart(session);
            case "PAIRING_ABORT" -> pairingAbort(session);
            case "PAIRING_DONE" -> pairingDone(session);
            case "SDP_OFFER", "SDP_ANSWER", "SDP_ICE_CANDIDATE" -> {
                final Exchange exchange = findExchange(session);
                if (exchange != null && exchange.paired()) {
                    send(exchange.otherUser(session), message); // forward message to other user
                } else {
                    Log.warn("Received SDP message from unpaired user");
                }
            }
        }
    }

    private static Exchange findExchange(final Session session) {
        return queue.stream()
                .filter(element -> element.belongsToSession(session))
                .findFirst()
                .orElse(null);
    }

    private static void pairingStart(final Session session) {
        queue.removeIf(ex -> ex.belongsToSession(session)); // prevent double queueing
        var exchange = queue.stream()
                .filter(ex -> ex.b() == null)
                .findFirst()
                .orElse(null);
        if (exchange != null) {
            exchange.setB(session);
            send(exchange.a(), new Message("PARTNER_FOUND", "GO_FIRST"));
            send(exchange.b(), new Message("PARTNER_FOUND"));
        } else {
            queue.add(new Exchange(session));
        }
    }

    private static void pairingAbort(final Session session) {
        var exchange = findExchange(session);
        if (exchange != null) {
            send(exchange.otherUser(session), new Message("PARTNER_LEFT"));
            queue.remove(exchange);
        }
    }

    private static void pairingDone(final Session session) {
        final Exchange exchange = findExchange(session);
        if (exchange != null) {
            exchange.incrementDoneCount();
        }
        queue.removeIf(Exchange::doneCountEquals2);
    }

    private static void send(final Session session, final Message message) { // null safe send method
        if (session != null) {
            session.getAsyncRemote().sendObject(message, result -> {
                if (result.getException() != null) {
                    Log.error("Unable to send message: " + result.getException());
                }
            });
        }
    }
}
