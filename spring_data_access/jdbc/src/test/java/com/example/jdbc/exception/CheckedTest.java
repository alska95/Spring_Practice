package com.example.jdbc.exception;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


@Slf4j
public class CheckedTest{


    @Test
    void checked_catch(){
        Service service = new Service();
        service.callCatch();
    }

    @Test
    void checked_throw() throws MyCheckedException {
        Service service = new Service();
        Assertions.assertThatThrownBy(()->service.callThrow())
                .isInstanceOf(MyCheckedException.class);
    }
    /**
    * Exception을 상속받은 예외는 체크 예외가 된다.
     * RuntimeException을 상속받으면 언체크 예외가 된다.
    * */
    static class MyCheckedException extends Exception{
        public MyCheckedException(String message) {
            super(message);
        }
    }

    static class Service {
        Repository repository = new Repository();

        /**
         * 예외를 잡아서 처리하는 코드
         * */
        public void callCatch(){
            try {
                repository.call();
            } catch (MyCheckedException e) {
                log.info("예외 처리 , message = {}" , e.getMessage() , e);
                //마지막 파라미터에 exception넣으면 스텍트레이스를 출력해준다.
            }
        }

        /**
         * 체크 예외를 밖으로 던지는 코드
         * 체크 예외는 잡지 않고 밖으로 던지려면 throws 예외를 메서드에 필수로 선언해야 한다.
         * */
        public void callThrow() throws MyCheckedException {
            repository.call();
        }
    }

    static class Repository {
        public void call() throws MyCheckedException {
            /*
            checkedException은 throw하는걸 반드시 선언해줘야 한다. (컴파일러가 체크해줌 --> 체크 예외)
            예외란 내가 잡아서 처리하거나 밖으로 던져야하는데
            checked예외의 경우 밖으로 던질때 반드시 선언해줘야한다는것.
            * */
        }
    }
}