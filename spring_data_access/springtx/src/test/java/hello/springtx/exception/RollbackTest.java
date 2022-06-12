package hello.springtx.exception;


import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class RollbackTest {

    @Autowired RollbackService service;

    @Test
    void runtimeException(){
        service.runtimeException();
    }

    @Test
    void checkedException() {
        Assertions.assertThatThrownBy(()->service.checkedException())
                .isInstanceOf(MyException.class);
    }


    @Test
    void rollbackFor() {
        Assertions.assertThatThrownBy(()->service.rollbackFor())
                .isInstanceOf(MyException.class);
    }

    @TestConfiguration
    static class RollbackTestConfig {
        @Bean
        RollbackService rollbackService(){
            return new RollbackService();
        }
    }

    @Slf4j
    static class RollbackService {
        //런타임 예외 발생 : 롤백
        @Transactional
        public void runtimeException() {
            log.info("call runtimeException");
            throw new RuntimeException();
        }

        //체크 예외 발생 : 커밋
        /**
         * 왜 체크 예외는 롤백을 안할까
         * 기본적으로 체크 예외는 비즈니스 의미가 있을 때 사용하고, 런타임 예외는 복구 불가능한 예외로 가정한다.
         * 즉 체크 예외란 시스템 상 문제가 되어서 발생하는 예외가 아니라, 비즈니스 로직 상 문제가 되기 때문에 발생하는예외라고 가정하는 것.
         * 예를 들면 잔고 부족 상황에서 고객에게 계좌로 추가 입금을 요구했는데 데이터가 다 날아가버리면 낭폐를 보는 상황에서는
         * 데이터를 롤백해서는 안된다.
         *
         * */
        @Transactional
        public void checkedException() throws MyException {
            log.info("call checkedException");
            throw new MyException();
        }
        //체크 예외 rollbackFor 지정 : 롤백
        @Transactional(rollbackFor = MyException.class)
        public void rollbackFor() {
            log.info("call rollbackFor");
            throw new RuntimeException();
        }

    }

    static class MyException extends Exception{
    }
}
