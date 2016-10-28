package uk.bluedawnsolutions.hystrix;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

@Slf4j
class DeferredResult<T> implements Result {

    private final Supplier<T> resultProvider;

    DeferredResult(Supplier<T> resultProvider) {
        this.resultProvider = resultProvider;
    }

    @Override
    public T get() {
        log.info("Executing fallback by using a Lamda");
        return resultProvider.get();
    }
}
