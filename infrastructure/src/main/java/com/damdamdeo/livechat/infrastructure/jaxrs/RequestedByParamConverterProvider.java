package com.damdamdeo.livechat.infrastructure.jaxrs;

import com.damdamdeo.livechat.domain.pairing.request.RequestedBy;
import jakarta.ws.rs.ext.ParamConverter;
import jakarta.ws.rs.ext.ParamConverterProvider;
import jakarta.ws.rs.ext.Provider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
public class RequestedByParamConverterProvider implements ParamConverterProvider {
    @Override
    public <T> ParamConverter<T> getConverter(final Class<T> rawType,
                                              final Type type,
                                              final Annotation[] annotations) {
        if (rawType.isAssignableFrom(RequestedBy.class)) {
            return (ParamConverter<T>) new RequestedByParamConverter();
        }
        return null;
    }
}
