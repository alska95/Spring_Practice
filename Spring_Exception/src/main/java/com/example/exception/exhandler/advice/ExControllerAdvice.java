package com.example.exception.exhandler.advice;

import com.example.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST) //걍 컨트롤러에 붙이는거랑 같음 - 정상응답이기때문에 사용 가능한것!
    @ExceptionHandler(Exception.class)
    public ErrorResult illegalExHandler(Exception e){
        log.error("[exceptionHandler] ex" ,e);
        return new ErrorResult("ex" , e.getMessage());
    }
}
