package hello.aop.pointcut;

import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

/**
 * execution(modifiers-pattern?
 * ret-type-pattern declaring-type-pattern?name-pattern(param-pattern)
 * throws-pattern?)
 * execution(접근베어자? 반환타입 선언타입?메서드이름(파라미터) 예외?)
 *
 *
 * */
@Slf4j
public class ExecutionTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
         helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void printMethod(){
        log.info("helloMethod ={}" , helloMethod);
    }

    @Test
    void exactMatch(){
        pointcut.setExpression("execution(public String hello.aop.member.MemberServiceImpl.hello(String))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();

    }

    @Test
    void allMatch(){
        pointcut.setExpression("execution(* *(..))"); //반환타입 전부, 메서드 이름 전부, 파라미터(..)-->파라미터 종류와 수가 상관이 없다는 뜻이다.
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatch(){
        pointcut.setExpression("execution(String hel*(..))"); //반환타입 전부, 메서드 이름 전부, 파라미터(..)-->파라미터 종류와 수가 상관이 없다는 뜻이다.
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }


    @Test
    void nameMatch2(){
        pointcut.setExpression("execution(String *el*(..))"); //반환타입 전부, 메서드 이름 전부, 파라미터(..)-->파라미터 종류와 수가 상관이 없다는 뜻이다.
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }


    @Test
    void nameMatch3(){
        pointcut.setExpression("execution(private String hel*(..))"); //반환타입 전부, 메서드 이름 전부, 파라미터(..)-->파라미터 종류와 수가 상관이 없다는 뜻이다.
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }


    @Test
    void packageExactMatch1(){
        pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.hello(..))"); //반환타입 전부, 메서드 이름 전부, 파라미터(..)-->파라미터 종류와 수가 상관이 없다는 뜻이다.
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageExactMatch2(){
        pointcut.setExpression("execution(* hello.aop.member.*.*(..))"); //반환타입 전부, 메서드 이름 전부, 파라미터(..)-->파라미터 종류와 수가 상관이 없다는 뜻이다.
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageExactMatch3(){
        pointcut.setExpression("execution(* hello.aop.member..(..))"); // ..을 써서 서브패키지까지 메칭할 수 있다.
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
}

