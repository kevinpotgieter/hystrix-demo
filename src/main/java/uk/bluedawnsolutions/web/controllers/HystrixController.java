package uk.bluedawnsolutions.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import uk.bluedawnsolutions.hystrix.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/hystrix")
@Slf4j
public class HystrixController {

    @RequestMapping(value = "/delay-in-fallback", method = RequestMethod.GET)
    @ResponseBody
    public String delayinFallback() throws ExecutionException, InterruptedException {
        log.info("Entering controller...");
        String result = new QuickRunFailureSlowFallbackCommand().queue().get();
        log.info("Exiting controller...");
        return result;
    }

    @RequestMapping(value = "/timeout-in-run", method = RequestMethod.GET)
    @ResponseBody
    public String timeoutInDoRun() throws ExecutionException, InterruptedException {
        log.info("Entering controller...");
        String reksult = new SlowRunAndImmediateFallbackCommand().queue().get();
        log.info("Exiting controller...");
        return reksult;
    }

    @RequestMapping(value = "/slow-run-and-slow-fallback", method = RequestMethod.GET)
    @ResponseBody
    public String slowExecutionAndFallback() throws ExecutionException, InterruptedException {
        log.info("Entering controller...");
        String result = new SlowRunAndSlowFallbackCommand().queue().get();
        log.info("Exiting controller...");
        return result;
    }

    @RequestMapping(value = "/slow-run-and-deterministic-fallback", method = RequestMethod.GET)
    @ResponseBody
    public String slowExecutionAndDeterministicFallback() throws ExecutionException, InterruptedException {
        log.info("Entering controller...");
        String result = new SlowRunAndDeterministicFallbackCommand().queue().get();
        log.info("Exiting controller...");
        return result;
    }

    @RequestMapping(value = "/slow-run-and-fallback-occurring-on-calling-thread", method = RequestMethod.GET)
    @ResponseBody
    public String slowExecutionAndFallbackOccurringOnCallingThread() throws ExecutionException, InterruptedException {
        log.info("Entering controller...");
        String result = new SlowRunAndFallbackExecutedOnCallingThreadCommand().queue().get().get();
        log.info("Exiting controller...");
        return result;
    }

}
