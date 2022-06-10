package hello.springtx.apply;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@SpringBootTest
@Slf4j
public class TxLevelTest {

    @Autowired LevelService service;

    @Test
    void orderTest(){
        service.write();
        service.read();
    }

    @TestConfiguration
    static class Config {
        @Bean
        public LevelService levelService(){
            return new LevelService();
        }
    }


    @Slf4j
    @Transactional(readOnly = true)
    //트랜잭션은 더 구체적인것이 우선순위를 가진다. (Class < Method)
    // 메서드 > 클래스 > 인터페이스 메소드 > 인터페이스
    // 인터페이스에 @Transactional을 선언하는 것은 권장되지 않는다 (적용되지 않는 경우가 있다.)
    static class LevelService {

        @Transactional(readOnly = false)
        public void write(){
            log.info("call write");
            printTxInfo();
        }

        public void read(){
            log.info("call read");
            printTxInfo();
        }

        private void printTxInfo(){
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("tx active = {}", txActive);
            boolean readOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
            log.info("tx readOnly = {}", readOnly);
        }
    }
}
