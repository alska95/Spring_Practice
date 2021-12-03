package hello.proxy.trace.logtrace;

import hello.proxy.trace.TraceStatus;
import org.springframework.stereotype.Component;


public interface LogTrace {

    TraceStatus begin(String message);
    void end(TraceStatus status);
    void exception(TraceStatus status, Exception e);
}
