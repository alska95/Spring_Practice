package com.example.advanced.trace.strategy.code.strategy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContextV2 {

    public void execute(Strategy strategy){
        long startTime = System.currentTimeMillis();
        //실행
        strategy.call();
        //종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime-startTime;

        log.info("resultTime = {}" , resultTime);
    }

}
