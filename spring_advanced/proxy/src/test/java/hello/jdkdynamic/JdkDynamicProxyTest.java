package hello.jdkdynamic;

import hello.jdkdynamic.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

@Slf4j
public class JdkDynamicProxyTest {
    @Test
    void dynamicA(){
        AInterface target = new AImpl();

        TimeInvocationHandler handler = new TimeInvocationHandler(target); //동적 프록시 호출하는 로직 -> 변경되지않음.

        AInterface proxy = (AInterface) Proxy.newProxyInstance(AInterface.class.getClassLoader(), new Class[]{AInterface.class}, handler);

        System.out.println("proxy = " + proxy.call());
    }

    @Test
    void dynamicB(){
        BInterface target = new BImpl();

        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        BInterface proxy = (BInterface) Proxy.newProxyInstance(BInterface.class.getClassLoader(), new Class[]{BInterface.class}, handler);

        System.out.println("proxy = " + proxy.call());
    }
}
