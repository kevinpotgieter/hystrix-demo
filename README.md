# hystrix-demo
A sample repo to demonstrate how hystrix works as well as show the threads
that the Hystrix Commands are execute on under different conditions.

This demo hopes to demonstrate both the time requests take to execute as
well as show (via the logging) which threads operations occur on in order
for the user to better understand what goes on when using Hystrix in 
your application.

See more on the accompanying blog post here: 

### Scenarios
 _All scenarios can be executed via tests in_ `HystrixControllerTest`

1. Immediate failure in `run()` and slow execution in fallback to simulate IO blocking call in `fallback()`
2. Slow `run()` which results in timing out, and `fallback()` return immediately
3. Slow `run()` which results in timing out, as well as a slow `fallback()`
4. Slow `run()` which results in timing out, and a deterministic `fallback()` by means of wrapping the call in a `HystrixCommand`
5. *Interesting* - Slow `run()` which results in timing out, and a `fallback()` which will execute on calling thread by means of some Lambda Magic