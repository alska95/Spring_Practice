package com.example.advanced.trace.strategy.code.template;

import com.example.advanced.trace.strategy.code.strategy.Strategy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeLogTemplate {

    public void execute(Callback callback){
        long startTime = System.currentTimeMillis();
        //실행
        callback.call();
        //종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime-startTime;

        log.info("resultTime = {}" , resultTime);
    }
}

