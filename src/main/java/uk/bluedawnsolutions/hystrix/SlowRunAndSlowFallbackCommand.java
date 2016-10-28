package uk.bluedawnsolutions.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SlowRunAndSlowFallbackCommand extends HystrixCommand<String> {

    private static final String COMMAND_GROUP_KEY = "SLOW_BOTH";

    public SlowRunAndSlowFallbackCommand() {
        super(HystrixCommandGroupKey.Factory.asKey(COMMAND_GROUP_KEY));
    }

    @Override
    protected String run() throws Exception {
        int executionTime = 2500;
        log.info("Timing out in run() when attempting to execute for {}ms", executionTime);
        Thread.sleep(executionTime);
        return "Should have timed out by this point!";
    }

    @Override
    protected String getFallback() {
        int executionTime = 2500;
        log.info("Executing slow fallback for {}", executionTime);
        try {
            Thread.sleep(executionTime);
        } catch (InterruptedException e) {
            log.error("Fallback was interrupted!");
        }
        return COMMAND_GROUP_KEY;
    }
}
