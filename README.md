# hystrix-demo
A sample application which goes some way to highlighting which threads 
execution occurs on depending on the failure scenario.

This demo hopes to also demonstrate the effect structuring your Hystrix 
Commands has on your overall execution time. 

Unless specifically set in the start of the test, all Hystrix default 
configuration is used. i.e. 1000ms command timeout. Defaults described 
[here](https://github.com/Netflix/Hystrix/wiki/Configuration#fallback.isolation.semaphore.maxConcurrentRequests)

See more on the accompanying blog post [here](http://wp.me/sU2Qi-hystrix)

### Scenarios
 _All scenarios can be executed via tests in_ `HystrixControllerTest`

1. Immediate failure in `run()` and slow execution in fallback to simulate IO blocking call in `fallback()`
2. Slow `run()` which results in timing out, and `fallback()` return immediately
3. Slow `run()` which results in timing out, as well as a slow `fallback()`
4. Slow `run()` which results in timing out, and a deterministic `fallback()` by means of wrapping the call in a `HystrixCommand`
5. Slow `run()` which results in timing out, and a `fallback()` which will execute on calling thread by means of some lambda magic

## IntelliJ IDEA users
Installing the GrepConsole plugin is suggested if you would like to follow 
along with the diagram in the blog post mentioned above. The below grep console configuration
will highlight the appropriate thread executions which correlate to the diagram.

Diagram from blog:
![Diagram from blog]
(https://kevinpotgieter.files.wordpress.com/2016/10/hystrix-thread-execution.jpg)

GrepConsole configuration:
![GrepConsole configuration]
(https://kevinpotgieter.files.wordpress.com/2016/10/screen-shot-2016-10-31-at-09-34-30.png)