package com.damdamdeo.livechat.infrastructure.ws;

import jakarta.websocket.Session;

import java.util.Objects;

public final class Exchange {
    private final Session a;
    private Session b;
    private int doneCount = 0;

    public Exchange(final Session a) {
        this.a = a;
    }

    public Session otherUser(final Session user) {
        return user.equals(a) ? b : a;
    }

    public void incrementDoneCount() {
        this.doneCount++;
    }

    public boolean belongsToSession(final Session session) {
        Objects.requireNonNull(session);
        return session.equals(a) || session.equals(b);
    }

    public boolean paired() {
        return a != null && b != null;
    }

    public boolean doneCountEquals2() {
        return doneCount == 2;
    }

    public void setB(final Session b) {
        this.b = Objects.requireNonNull(b);
    }

    public Session a() {
        return a;
    }

    public Session b() {
        return b;
    }

}
