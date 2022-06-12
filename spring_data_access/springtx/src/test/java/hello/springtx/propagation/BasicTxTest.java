package hello.springtx.propagation;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import javax.sql.DataSource;

@Slf4j
@SpringBootTest
public class BasicTxTest {

    @Autowired
    PlatformTransactionManager txManager;

    @TestConfiguration
    static class Config {
        @Bean
        public PlatformTransactionManager transactionManager(DataSource dataSource){
            return new DataSourceTransactionManager(dataSource);
        }
    }

    @Test
    void commit(){
        log.info("트랜잭션 시작");
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionAttribute());

        log.info("트랜잭션 커밋 시작");
        txManager.commit(status);
        log.info("트랜잭션 커밋 완료");
    }

    @Test
    void rollBack(){
        log.info("트랜잭션 시작");
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionAttribute());

        log.info("트랜잭션 롤백 시작");
        txManager.rollback(status);
        log.info("트랜잭션 롤백 완료");
    }

    @Test
    void double_commit(){
        /**
         * 1 트랜잭션 종료 후 2 실행
         * 당연하지만 서로 간섭하지 않음.
         * 둘은 트랜잭션 풀에서 제공하는 같은 커넥션을 사용한다. (반환 후 재사용)
        * */
        log.info("트랜잭션1 시작");
        TransactionStatus tx1 = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("트랜잭션1 커밋");
        txManager.commit(tx1);

        log.info("트랜잭션2 시작");
        TransactionStatus tx2 = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("트랜잭션2 커밋");
        txManager.rollback(tx2);

    }

    @Test
    void inner_commit() {

        /**
         * 트랜잭션 안에서 동작하는 내부 트랜잭션이 생길경우
         * 외부 트랜잭션은 물리, 내부 트랜잭션은 논리 트랜잭션이라는 개념을 갖는다. --> required 전파 옵션을 사용할 때 나타난다.
         * * 원칙 *
         * 모든 논리 트랜잭션이 커밋되어야 물리 트랜잭션이 커밋된다.
         * 하나의 논리 트랜잭션이라도 롤백 되면 물리 트랜잭션은 롤백된다.
         *      --> 물리적으로는 논리가 모두 만족해야 동작
         * */

        log.info("외부 트랜잭션 시작");
        TransactionStatus outer = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("outer.isNewTransaction()={}", outer.isNewTransaction());

        /**
         * 외부 트랜잭션 적용중 --> 여기에 참여한다. (아무것도 안하겠다는 뜻 - 참여하겠다는 로그만 남김)
         *                  --> 참여한다는 것은 그냥 그대로 적용받는다는것. 외부 트랜잭션 범위 확장
         * */
        log.info("내부 트랜잭션 시작");
        TransactionStatus inner = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("inner.isNewTransaction()={}", inner.isNewTransaction()); // --> false
        log.info("내부 트랜잭션 커밋");
        //커밋 했는대도 아무 로그도 안나옴. -> 바로 외부 트랜잭션 커밋 --> 아무일도 안한다는 뜻이다.
        txManager.commit(inner);
        //하나의 커넥션은 하나만 커밋할 수 있지만, 스프링이 하나의 물리 트랜잭션으로 묶어서 동작하게 한다.

        log.info("외부 트랜잭션 커밋");
        txManager.commit(outer);
    }


}
