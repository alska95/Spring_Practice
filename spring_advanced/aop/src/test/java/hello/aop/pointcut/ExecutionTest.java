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
 * execution(접근제어자? 반환타입 선언타입?메서드이름(파라미터) 예외?)
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
        pointcut.setExpression("execution(String hel*(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }


    @Test
    void nameMatch2(){
        pointcut.setExpression("execution(String *el*(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }


    @Test
    void nameMatch3(){
        pointcut.setExpression("execution(private String hel*(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }


    @Test
    void packageExactMatch1(){
        pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.hello(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageExactMatch2(){
        pointcut.setExpression("execution(* hello.aop.member.*.*(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageExactMatch3(){
        pointcut.setExpression("execution(* hello.aop.member..*(..))"); // ..을 써서 서브패키지까지 메칭할 수 있다.
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeExactMatch(){
        pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.*(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeMatchSuperType(){
        pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))"); // 부모 타입을 매칭시켜도 다형성에서 부모 = 자식이 성립하기때문에 매칭된다.
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeMatchInternal() throws NoSuchMethodException {
        pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))");
        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class); //자식 클래스의 메소드는 매칭이 안된다.
        Assertions.assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    void typeMatchInternal2() throws NoSuchMethodException {
        pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.*(..))");
        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class); //내 메소드기 때문에 true!
        Assertions.assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isTrue();
    }

    //String 타입의 파라미터 허용
    //(String)
    @Test
    void argsMatch() throws NoSuchMethodException {
        pointcut.setExpression("execution(* *(String))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    //파라미터 없음
    //()
    @Test
    void argsMatchNoArgs() throws NoSuchMethodException {
        pointcut.setExpression("execution(* *())");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    //정확히 하나의 파라미터만 허용, 모든 타입 허용
    //(단하나!)
    @Test
    void oneArgsMatch() throws NoSuchMethodException {
        pointcut.setExpression("execution(* *(*))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    //숫자 무관, 모든 타입 허용
    //(),(단하나!),(두,개)
    @Test
    void allArgsMatch() throws NoSuchMethodException {
        pointcut.setExpression("execution(* *(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    //String 타입으로 시작, 숫자와 무관하게 모든 파라미터 허용
    @Test
    void stringAndAllArgsMatch() throws NoSuchMethodException {
        pointcut.setExpression("execution(* *(String, ..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
}

