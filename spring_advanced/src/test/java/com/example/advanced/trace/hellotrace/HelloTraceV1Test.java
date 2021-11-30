package com.example.advanced.trace.hellotrace;

import com.example.advanced.trace.TraceStatus;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HelloTraceV1Test {

    /**
     * 이것은 온전한 테스트 코드가 아니다. 일반적으로 테스트라고 함은 자동으로 검증하는 과정이 필요하다.
     * 이 테스트는 과정이 없고 결과를 콘솔로 직접 확인해야 한다.
     * 본 테스트는 응답값이 없음.
     * */
    @Test
    void begin_end(){
        HelloTraceV1 trace = new HelloTraceV1();
        TraceStatus status = trace.begin("hello");
        trace.end(status);
    }

    @Test
    void begin_exception(){
        HelloTraceV1 trace = new HelloTraceV1();
        TraceStatus status = trace.begin("hello");
        trace.exception(status, new IllegalStateException());
    }

}