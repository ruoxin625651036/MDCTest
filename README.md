# MDC 用于分布式系统跟踪

首先logback和log4j都支持MDC，接口在org.slf4j.MDC中。

跟踪过程分为两部分：

 一、通过MDC将业务相关的信息输出（在logback中使用%X符号输出自定义的变量信息）
 
 二、利用ThreadLocal将业务信息传入到下一个业务系统中
 
 springMVC中可使用拦截器和RestTemplate来完成整个RPC过程。
