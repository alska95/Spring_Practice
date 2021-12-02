package com.example.advanced.trace.template.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SubClassLogic1 extends AbstractTemplate{

    @Override
    protected void call() {
      log.info("로직 실행");
    }
}
