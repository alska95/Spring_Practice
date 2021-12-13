package hello.aop.order.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class AspectV4Pointcut {

    @Around(("hello.aop.order.aop.Pointcuts.allOrder()"))
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}" , joinPoint.getSignature());
        return joinPoint.proceed();
    }

    //hello.aop.order 패키지와 하위 패키지 이면서 클래스 이름 패턴이 *Service
    @Around("hello.aop.order.aop.Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable{
        try{
            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        }catch (Exception e){
            log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        }finally {
            log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }

    /**
     * 어드바이저 순서를 바꾸려면 어떻게 해야할까??
     * 어드바이스는 기본적으로 순서를 보장하지 않는다.
     * 적용하고 싶으면 @Aspect 적용 단위로 org.springframework.core.annotation.@Order
     * 어노테이션을 적용해야 한다.문제는 이것을 어드바이스 단위가 아니라 클래스 단위로 적용할 수 있다는 점이다.
     * 그래서 지금처럼 하나의 애스팩트에 여러 어드바이스가 있으면 순서를 보장 받을 수 없다.
     * 따라서 애스팩트를 별도의 클래스로 분리해야 한다.*/
}
