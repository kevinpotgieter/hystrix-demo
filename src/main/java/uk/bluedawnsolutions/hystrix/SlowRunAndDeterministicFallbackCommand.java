package uk.bluedawnsolutions.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

@Slf4j
public class SlowRunAndDeterministicFallbackCommand extends HystrixCommand<String> {

    private static final String COMMAND_GROUP_KEY = "SLOW_RUN_DETERMINISTIC_FALLBACK";

    public SlowRunAndDeterministicFallbackCommand() {
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
        try {
            log.info("Executing deterministic fallback by using a HystrixCommand");
            return new SlowRunAndImmediateFallbackCommand().queue().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return COMMAND_GROUP_KEY;
    }
}
