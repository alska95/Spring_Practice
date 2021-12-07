package hello.proxy;

import hello.proxy.config.AppV1Config;
import hello.proxy.config.AppV2Config;
import hello.proxy.config.v1_proxy.ConcreteProxyConfig;
import hello.proxy.config.v1_proxy.InterfaceProxyConfig;
import hello.proxy.config.v2_dynamicproxy.DynamicProxyBasicConfig;
import hello.proxy.config.v2_dynamicproxy.DynamicProxyFilterConfig;
import hello.proxy.config.v3_proxyfactory.ProxyFactoryConfigV1;
import hello.proxy.config.v4_postprocessor.BeanPostProcessorConfig;
import hello.proxy.config.v5_autoproxy.AutoProxyConfig;
import hello.proxy.trace.logtrace.FieldLogTrace;
import hello.proxy.trace.logtrace.LogTrace;
import hello.proxy.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;


//@Import({AppV1Config.class, AppV2Config.class, InterfaceProxyConfig.class})
//@Import({ConcreteProxyConfig.class, DynamicProxyFilterConfig.class})
//@Import({ProxyFactoryConfigV1.class, BeanPostProcessorConfig.class})
@Import(AutoProxyConfig.class)
@SpringBootApplication (scanBasePackages = "hello.proxy.app") //주의
// 특정 페키지만 제한을 해둬야 우리가 만들어둔 빈 후처리기에 원하는 빈만 들어옴. 아니면 스프링이 쓰는 빈 전부 들어온다..
public class ProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyApplication.class, args);
	}

	@Bean
	public LogTrace logTrace2(){
		return new ThreadLocalLogTrace();
	}
	@Bean
	public LogTrace logTrace(){
		return new FieldLogTrace();
	}




}
