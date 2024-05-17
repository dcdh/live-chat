package com.damdamdeo.livechat.infrastructure.jaxrs;

import com.damdamdeo.livechat.domain.pairing.request.RequestedBy;
import jakarta.ws.rs.ext.ParamConverter;

public class RequestedByParamConverter implements ParamConverter<RequestedBy> {
    @Override
    public RequestedBy fromString(final String uuidIdentifier) {
        return new RequestedBy(uuidIdentifier);
    }

    @Override
    public String toString(final RequestedBy requestedBy) {
        return requestedBy.identifierAsString();
    }
}
