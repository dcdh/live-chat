package com.damdamdeo.livechat.infrastructure.inmemory;

import com.damdamdeo.livechat.domain.pairing.request.RequestedBy;
import com.damdamdeo.livechat.domain.pairing.request.lifecycle.RequesterRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@ApplicationScoped
public class InMemoryRequesterRepository implements RequesterRepository {
    private static final Queue<RequestedBy> REQUESTERS = new ConcurrentLinkedQueue<>();

    @Override
    public RequestedBy poll() {
        return REQUESTERS.poll();
    }

    @Override
    public void add(final RequestedBy requestedBy) {
        REQUESTERS.add(requestedBy);
    }

    @Override
    public boolean contains(final RequestedBy requestedBy) {
        return REQUESTERS.contains(requestedBy);
    }

    @Override
    public void remove(final RequestedBy requestedBy) {
        REQUESTERS.remove(requestedBy);
    }
}
