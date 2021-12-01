package com.example.advanced.trace.template;

import com.example.advanced.trace.template.code.AbstractTemplate;
import com.example.advanced.trace.template.code.SubClassLogic1;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TemplateMethodTest {

    @Test
    void templateMethodV0(){
        logic1();
        logic2();

    }

    private void logic1(){
        long startTime = System.currentTimeMillis();
        //실행
        log.info("비즈니스 로직 1 실행");
        //종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime-startTime;

        log.info("resultTime = {}" , resultTime);
    }

    private void logic2(){
        long startTime = System.currentTimeMillis();
        //실행
        log.info("비즈니스 로직 2 실행");
        //종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime-startTime;

        log.info("resultTime = {}" , resultTime);
    }

    @Test
    void templateMethodV1(){
        AbstractTemplate template1 = new SubClassLogic1();
        template1.execute();
    }

    @Test
    void templateMethodV2(){
        AbstractTemplate template1 = new AbstractTemplate(){ //객체를 생성할때 괄호를 열면 익명클래스로 바로 구현을 할 수 있음.

            @Override
            protected void call() {
                log.info("익명 클래스 구현체");
            }
        };
        template1.execute();
    }
}
