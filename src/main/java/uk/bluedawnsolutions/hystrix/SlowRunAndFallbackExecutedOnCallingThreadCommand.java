package uk.bluedawnsolutions.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SlowRunAndFallbackExecutedOnCallingThreadCommand extends HystrixCommand<Result<String>> {

    private static final String COMMAND_GROUP_KEY = "SLOW_RUN_FALLBACK_IN_LAMBDA";

    public SlowRunAndFallbackExecutedOnCallingThreadCommand() {
        super(HystrixCommandGroupKey.Factory.asKey(COMMAND_GROUP_KEY));
    }

    @Override
    protected Result<String> run() throws Exception {
        int executionTime = 2500;
        log.info("About to execute run() for {}ms which should be timed out", executionTime);
        Thread.sleep(executionTime);
        log.info("Should have timed out by this point!");
        return () -> "Should have timed out by this point!";
    }

    @Override
    protected Result<String> getFallback() {
        log.info("Returning a lambda to execute the fallback");
        return new DeferredResult<>(() -> {
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("Returning result from the fallback...");
            return COMMAND_GROUP_KEY;
        });
    }
}
