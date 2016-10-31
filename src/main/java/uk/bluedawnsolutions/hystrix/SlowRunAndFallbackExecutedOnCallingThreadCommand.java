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
        log.info("Timing out in run() when attempting to execute for {}ms", executionTime);
        Thread.sleep(executionTime);

        return () -> "Should have timed out by this point!";
    }

    @Override
    protected Result<String> getFallback() {
        log.info("Returning Lambda to be executed by the actual caller...");
        return new DeferredResult<>(() -> {
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return COMMAND_GROUP_KEY;
        });
    }
}
