package com.example.exception.api;


import com.example.exception.exception.BadRequestException;
import com.example.exception.exception.UserException;
import com.example.exception.exhandler.ErrorResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
public class ApiExceptionV2Controller {


    @GetMapping("/api2/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id){
        if(id.equals("ex")){
            throw new RuntimeException("잘못된 사용자");
        }
        if(id.equals("bad")){
            throw new IllegalStateException("잘못된 입력");
        }
        if(id.equals("user-ex")){
            throw new UserException("사용자 오류");
        }
        if(id.equals("user-ia")){
            throw new IllegalArgumentException("ExceptionHandler 실험");
        }

        return new MemberDto(id , "hello" + id);
    }

    @Data
    @AllArgsConstructor
    static class MemberDto{
        private String memberId;
        private String name;
    }
}
