package com.example.springmvc.basic.requestmapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class MappingController {

    @RequestMapping(value = "/hello-basic" , method = RequestMethod.GET)
    public String helloBasic(){
        log.debug("hello basic");
        return "iok";
    }
    @GetMapping(value = "/hello-basic2")
    public String helloBasic2(){
        log.debug("hello basic2");
        return "iokPOost";
    }
/*
* pathVariable -> requestmapping  템플릿화*/
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable("userId") String data){
        log.info("mappingPath userId = {}" , data);
        return "okok";
    }
    /**
     * pathVariable 다중 사용*/
    @GetMapping("/mapping/user/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId , @PathVariable String orderId){
        log.debug("mappingPath userId = {} , orderId = {}", userId , orderId );
        return "ok";
    }
}
