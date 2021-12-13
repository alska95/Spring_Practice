package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class AspectV1 {


    /**
     * Around어노테이션의 value가 포인트 컷이 된다.
     * 그리고 구현 함수가 advice가 된다.
     * hello.aop.order..*(..)는
     * hello.aop.order그 자체와 하위 패키지(..)를 지칭하는 AspectJ 포인트컷 표현식이다.*/
    @Around("execution(* hello.aop.order..*(..))")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable{
        log.info("[log] {}", joinPoint.getSignature()); //join ponit 시그니쳐
        return joinPoint.proceed();
    }
}
