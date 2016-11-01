package uk.bluedawnsolutions.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SlowRunAndImmediateFallbackCommand extends HystrixCommand<String> {

    private static final String COMMAND_GROUP_KEY = "SLOW_RUN_IMMEDIATE_FALLBACK";

    public SlowRunAndImmediateFallbackCommand() {
        super(HystrixCommandGroupKey.Factory.asKey(COMMAND_GROUP_KEY));
    }

    @Override
    protected String run() throws Exception {
        int executionTime = 2500;
        log.info("About to execute run() for {}ms which should be timed out", executionTime);
        Thread.sleep(executionTime);
        log.info("Should have timed out by this point!");
        return COMMAND_GROUP_KEY;
    }

    @Override
    protected String getFallback() {
        log.info("Executing fallback immediately");
        return COMMAND_GROUP_KEY;
    }
}
