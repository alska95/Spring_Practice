package com.example.springmvc.basic.response;


import com.example.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
public class ResponseBodyController {
    @GetMapping("/response-body-string-v1")
    public void responseBodyV1(HttpServletResponse response) throws IOException {
        response.getWriter().write("Okd");
    }

    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyV2(){
        return new ResponseEntity<>("ok" , HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/response-body-string-v3")
    public String responseBodyV3(@RequestBody String body){

        return "Ok";
    }

    @ResponseStatus(HttpStatus.OK) // 어노테이션으로 추가응답가능.
    @ResponseBody
    @GetMapping("/response-body-json-v1")
    public HelloData responseBodyJsonV1(@RequestBody HelloData data){
        return data;
    }

    @GetMapping("/response-body-json-v2")
    public ResponseEntity<HelloData> responseBodyJsonV2(){
        return new ResponseEntity<>(new HelloData() , HttpStatus.OK);
    }


}
