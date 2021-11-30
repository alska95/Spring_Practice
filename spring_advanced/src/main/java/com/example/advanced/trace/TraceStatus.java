package com.example.advanced.trace;

import lombok.Getter;

@Getter
public class TraceStatus { //로그의 상태를 나타냄 --> 로그를 시작/종료할때 필요한 정보들
    private TraceId traceId;
    private Long startTimeMs;
    private String message;

    public TraceStatus(TraceId traceId, Long startTimeMs, String message) {
        this.traceId = traceId;
        this.startTimeMs = startTimeMs;
        this.message = message;
    }
}
