package com.damdamdeo.livechat.domain.pairing.usecase;

public interface UseCase<R, C extends UseCaseCommand> {
    R execute(C command);
}
