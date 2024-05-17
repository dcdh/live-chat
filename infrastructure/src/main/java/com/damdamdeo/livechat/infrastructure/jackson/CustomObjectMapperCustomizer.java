package com.damdamdeo.livechat.infrastructure.jackson;

import com.damdamdeo.livechat.domain.pairing.request.lifecycle.MatchedOneToOneLifecycle;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.quarkus.jackson.ObjectMapperCustomizer;
import jakarta.inject.Singleton;

@Deprecated
@Singleton
public class CustomObjectMapperCustomizer implements ObjectMapperCustomizer {

    @Override
    public void customize(final ObjectMapper objectMapper) {
        final SimpleModule module = new SimpleModule();
        module.addSerializer(MatchedOneToOneLifecycle.class, new MatchedOneToOneLifecycleSerializer());
        objectMapper.registerModule(module);
    }
}
