package hello.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

@Slf4j
public class RefectionTest {

    @Test
    void reflection0(){
        Hello target = new Hello();

        //공통 로직 1 시작
        log.info("start");
        String result1 = target.callA();
        log.info("result={}", result1);
        // 1 종료

        //공통 로직 2 시작
        log.info("start");
        String result2 = target.callB(); //호출 메서드만 다르다.
        log.info("result={}", result2);
        // 2 종료
    }

//    Function<Hello, String> callA = Hello::callA; 이와같은 방법으로 람다로도 가능한듯.
    @Test
    void reflection1() throws Exception {
        //클래스 정보 획득하는 방법
        Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");


        Hello target = new Hello();
        //callA메서드 정보
        Method methodCallA = classHello.getMethod("callA"); //문자로 바뀌었기 때문에 파라미터로 넘길 수 있음.
        Object result1 = methodCallA.invoke(target);
        log.info("result1 = {}" ,result1);

        //callA메서드 정보
        Method methodCallB = classHello.getMethod("callB");
        Object result2 = methodCallA.invoke(target);
        log.info("result2 = {}" ,result2);
    }


    @Test
    void reflection2() throws Exception {
        //클래스 정보 획득하는 방법
        Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");


        Hello target = new Hello();
        //callA메서드 정보
        Method methodCallA = classHello.getMethod("callA"); //문자로 바뀌었기 때문에 파라미터로 넘길 수 있음.
        dynamicCall(methodCallA, target);
        //callA메서드 정보
        Method methodCallB = classHello.getMethod("callB");
        dynamicCall(methodCallB, target);

    }

    private void dynamicCall(Method method, Object target) throws InvocationTargetException, IllegalAccessException {
        log.info("start");
        Object result1 = method.invoke(target);
        log.info("result={}", result1);
    }

    @Slf4j
    static class Hello{
        public String callA(){
            log.info("callA");
            return "A";
        }
        public String callB(){
            log.info("callB");
            return "b";
        }
    }
}
