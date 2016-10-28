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
        log.info("Timing out in run() when attempting to execute for {}ms", executionTime);
        Thread.sleep(executionTime);
        return COMMAND_GROUP_KEY;
    }

    @Override
    protected String getFallback() {
        log.info("Executing fallback immediately");
        return COMMAND_GROUP_KEY;
    }
}
