package hello.proxy.config.v6_aop;

import hello.proxy.config.AppV1Config;
import hello.proxy.config.AppV2Config;
import hello.proxy.config.v6_aop.aspect.LogTraceAspect;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class AopConfig {

    @Bean//@Aspect를 반환하면 자동으로 어드바이저로 변환해서 저장하게됨.
    public LogTraceAspect logTraceAspect(LogTrace logTrace){
        return new LogTraceAspect(logTrace);
    }
}
