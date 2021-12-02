package com.example.advanced.app.v5;

import com.example.advanced.trace.callback.TraceCallback;
import com.example.advanced.trace.callback.TraceTemplate;
import com.example.advanced.trace.logtrace.LogTrace;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceV5 {

    private final OrderRepositoryV5 orderRepository;
    private final TraceTemplate traceTemplate;

    public OrderServiceV5(OrderRepositoryV5 orderRepository, LogTrace logTrace) {
        this.orderRepository = orderRepository;
        this.traceTemplate = new TraceTemplate(logTrace);
    }


    public void orderItem(String itemId) {
        traceTemplate.execute("service ok" , ()->{
           orderRepository.save(itemId);
            return "call order Repository";
        });

    }
}
