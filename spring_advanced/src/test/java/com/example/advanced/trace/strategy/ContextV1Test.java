package com.example.advanced.trace.strategy;

import com.example.advanced.trace.strategy.code.strategy.ContextV1;
import com.example.advanced.trace.strategy.code.strategy.Strategy;
import com.example.advanced.trace.strategy.code.strategy.StrategyLogic1;
import com.example.advanced.trace.strategy.code.strategy.StrategyLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV1Test {

    @Test
    void strategyV0(){
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
    void strategyV1(){
        StrategyLogic1 strategyLogic1 = new StrategyLogic1();
        ContextV1 contextV1 = new ContextV1(strategyLogic1);
        contextV1.execute();

        StrategyLogic2 strategyLogic2 = new StrategyLogic2();
        ContextV1 context2 = new ContextV1(strategyLogic2);
        context2.execute();
    }

    @Test
    void strategyV2(){
        Strategy strategy = new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직 1 실햏");
            }
        };
        ContextV1 context = new ContextV1(strategy);
        log.info("익명 클래스 명 = "+ strategy.getClass());
        context.execute();
    }

    /**
     * 선 조립 후 실행
     * context와 strategy를 실행하기 전에 미리 조립을 해둬야한다.
     *
     * 단점 : 조립을 해 둔뒤에는 변경을 하기가 좀 까다롭다.
     * 전략을 실시간으로 변경해야하면, 싱글톤을 사용할때 매우 번거로워진다.
     * 그래서 실시간으로 변경을 해야되면 그냥 context를 하나 더 생성하고 그곳에 다른 strategy를 주입하는 것이 더 효율적일 수 있다.
     * */
    @Test
    void strategyV3(){
        Strategy strategy = () -> log.info("비즈니스 로직 1 실햏");
        ContextV1 context = new ContextV1(strategy);
        log.info("익명 클래스 명 = "+ strategy.getClass());
        context.execute();
    }


}
