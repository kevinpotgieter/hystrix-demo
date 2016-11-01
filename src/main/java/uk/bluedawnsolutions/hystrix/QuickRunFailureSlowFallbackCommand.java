package uk.bluedawnsolutions.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QuickRunFailureSlowFallbackCommand extends HystrixCommand<String> {

    private static final String COMMAND_GROUP_KEY = "SLOW_FALLBACK";

    public QuickRunFailureSlowFallbackCommand() {
        super(HystrixCommandGroupKey.Factory.asKey(COMMAND_GROUP_KEY));
    }

    @Override
    protected String run() throws Exception {
        log.info("Throwing exception in run() immediately");
        throw new RuntimeException("Throwing exception to initiate fallback");
    }

    @Override
    protected String getFallback() {
        int executionTime = 2500;
        log.info("Executing fallback for {}ms", executionTime);
        try {
            Thread.sleep(executionTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("Returning result from the fallback...");
        return COMMAND_GROUP_KEY;
    }
}
