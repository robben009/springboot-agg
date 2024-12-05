# spring知识点

## 优雅关闭
```
server.shutdown=graceful
spring.lifecycle.timeout-per-shutdown-phase=20s
```
Spring Boot的优雅关机过程分为两个阶段：first和second。

在first阶段，所有声明了@PreDestroy注解的bean将被调用，并且这个阶段将会等待所有当前执行的请求完成。

在second阶段，Spring Boot将关闭所有的线程池。主要是通过调用Java线程池的 shutdown() 或 shutdownNow() 方法实现的。

shutdown() 方法将初始化一个关闭序列。这个序列不接受新的任务，等待所有已提交的任务（包括正在进行的和队列中等待的）执行完成后再关闭。

shutdownNow() 方法会尝试停止所有正在执行的任务，停止队列中等待的任务的处理，并返回正在等待执行的任务列表。

特别需要注意的是，shutdownNow() 并不能保证一定能停止正在执行的任务，而是通过中断线程的方式去尝试停止。

具体Spring Boot 使用哪种方式来关闭线程池，取决于你的配置。如果你在配置文件中设置了 spring.lifecycle.timeout-per-shutdown-phase，那么Spring Boot 将使用 shutdown() 方法关闭线程池。如果超过设定的时间后，线程池中的任务还未执行完，那么Spring Boot 将使用 shutdownNow() 方法尝试强制关闭线程池。

如果你没有设置 spring.lifecycle.timeout-per-shutdown-phase 或者设置的时间超过了 30s，那么Spring Boot 将直接使用 shutdownNow() 方法关闭线程池。

因此，如果你的应用中有线程池中的任务不能被中断，那么你需要在配置文件中设置 spring.lifecycle.timeout-per-shutdown-phase，并且保证设定的时间足够你的任务执行完成。

所以spring.lifecycle.timeout-per-shutdown-phase=20s 这个配置项就是设定在每个阶段Spring Boot最多等待的时间。超过设定的时间后，如果还有未完成的工作，那么Spring Boot将会强制关闭应用。

默认情况下，这个配置项的值为30秒。



