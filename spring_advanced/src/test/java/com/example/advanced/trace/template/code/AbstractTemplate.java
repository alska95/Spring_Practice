package com.example.advanced.trace.template.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractTemplate {

    public void execute() { //이 부분이 하나의 탬플릿이 된다.

        long startTime = System.currentTimeMillis();
        //실행
        call();
        //종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;

        log.info("resultTime = {}", resultTime);

    }

    protected abstract void call();
}
