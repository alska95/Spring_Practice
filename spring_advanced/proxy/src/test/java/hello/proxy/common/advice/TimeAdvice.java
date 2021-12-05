package hello.proxy.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
public class TimeAdvice implements MethodInterceptor { //AOP꺼 MethodInterceptor을 사용해야 한다.

    //차이점 target을 안넣어줘도 된다. 프록시 팩토리에서 미리 타겟을 넣어준다.
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("TimeProxy 실행");
        long startTime = System.currentTimeMillis();

        Object result = invocation.proceed();
        //invocation안에 method등등 다 들어있다. proceed를 하면 타겟을 찾아서 타겟에 있는 구현체를 호출해준다.

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeProxy 종료 resultTime={}", resultTime);
        return result;
    }
}
