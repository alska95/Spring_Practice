package hello.springtx.apply;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;


/*
 * AOP를 적용하면 실제 객체 대신 proxy를 스프링 빈으로 등록한다.
 * transaction을 적용 시키려면 proxy가 적용된 객체가 필요하다.
 * Autowired걸면 항상 필요하다면 proxy가 적용되기 때문에 일반적으로는 항상 proxy가 들어온다.
 * 하지만 내부 호출의 경우에는 실제 객체를 가져오기 때문에 transaction이 적용되지 않는다.
 * */
@Slf4j
@SpringBootTest
public class InternalCallV1Test {

    @Autowired
    CallService callService;

    @Test
    void printProxy() {
        log.info("callService class ={}", callService.getClass());
    }

    @Test
    void internalCall() {
        callService.internal();
    }

    @Test
    void externalCall() {
        callService.external();
    }

    @TestConfiguration
    static class InternalCallV1TestConfig {
        @Bean
        CallService callService() {
            return new CallService();
        }
    }

    static class CallService {
        public void external() {
            log.info("call external");
            printTxInfo();
            internal();
            //내부에서 호출하기때문에 실제 CallService객체를 호출하게 되어 aop기반 트랜잭션이 적용 안됨.
            //해결방법 1.internal을 다른 클래스로 분리 -> 호출될때 proxy생성됨 -> 해결
            //       2.class가 컴파일 될때 tx를 적용시키기
        }

        @Transactional
        public void internal() {
            log.info("call internal");
            printTxInfo();
        }

        private void printTxInfo() {
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("tx active = {}", txActive);
            boolean readOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
            log.info("tx readOnly = {}", readOnly);
        }
    }
}
