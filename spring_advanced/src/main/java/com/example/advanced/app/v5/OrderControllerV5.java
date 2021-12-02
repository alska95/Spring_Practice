package com.example.advanced.app.v5;


import com.example.advanced.trace.callback.TraceCallback;
import com.example.advanced.trace.callback.TraceTemplate;
import com.example.advanced.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OrderControllerV5 {

    private final OrderServiceV5 orderService;
    private final TraceTemplate traceTemplate;

    public OrderControllerV5(OrderServiceV5 orderService, LogTrace logTrace) {
        this.orderService = orderService;
        this.traceTemplate = new TraceTemplate(logTrace); //싱글톤이기 때문에 하나만 존재 / 생성자가 한번 호출되기 때문.
        //물론 빈으로 등록해두고 주입받아도 된다.
    }

    @GetMapping("/v5/request")
    public String request(String itemId) {
        TraceCallback<String> traceCallback = () ->{
            orderService.orderItem(itemId);
            return "ok";
        };
        return traceTemplate.execute("controller start" , traceCallback);
    }
}
