package com.damdamdeo.livechat.domain.pairing.request.lifecycle;

import com.damdamdeo.livechat.domain.pairing.request.RequestedBy;

public interface RequesterRepository {
    RequestedBy poll();

    void add(RequestedBy requestedBy);

    boolean contains(RequestedBy requestedBy);

    void remove(RequestedBy requestedBy);
}
