package com.example.jdbc.service;

import com.example.jdbc.domain.Member;
import com.example.jdbc.repository.MemberRepositoryV3;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

import static com.example.jdbc.connection.ConnectionConst.*;


/**
 * 기본 동작 - 트랜잭션이 없는 경우
 * */
@Slf4j
@SpringBootTest
class MemberServiceV3_3Test {
    public static final String MEMBER_A = "memberA";
    public static final String MEMBER_B = "memberB";
    public static final String MEMBER_EX = "ex";

    @Autowired
    private MemberRepositoryV3 memberRepository;

    @Autowired
    private MemberServiceV3_3 memberService;

    @TestConfiguration
    static class TestConfig {
        @Bean
        DataSource dataSource(){
            return new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        }
        @Bean
        PlatformTransactionManager transactionManager(){
            return new DataSourceTransactionManager(dataSource());
        }

        @Bean
        MemberRepositoryV3 memberRepositoryV3(){
            return new MemberRepositoryV3(dataSource());
        }

        @Bean
        MemberServiceV3_3 memberServiceV3_3(){
            return new MemberServiceV3_3(memberRepositoryV3());
        }
    }

    @Test
    void AopCheck(){
        log.info("memberService class = {}", memberService.getClass());
        log.info("memberRepository class = {}", memberRepository.getClass());
        Assertions.assertThat(AopUtils.isAopProxy(memberService)).isTrue();
    }

    @AfterEach
    void afterEach() throws SQLException {
        memberRepository.delete("memberA");
        memberRepository.delete("memberB");
        memberRepository.delete("memberEX");
    }


    @Test
    @DisplayName("정상 이체")
    void accountTransfer() throws SQLException {
        //given - 데이터 준비
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberB = new Member(MEMBER_B, 10000);
        memberRepository.save(memberA);
        memberRepository.save(memberB);
        //when - 수행
        memberService.accountTransfer(memberA.getMemberId(), memberB.getMemberId(), 2000);
        //then - 검증
        Member findMemberA = memberRepository.findById(memberA.getMemberId());
        Member findMemberB = memberRepository.findById(memberA.getMemberId());
        Assertions.assertThat(findMemberA.getMoney()).isEqualTo(8000);
    }

    @Test
    @DisplayName("이체 오류")
    void accountTransferEx() throws SQLException {
        //given - 데이터 준비
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberB = new Member(MEMBER_EX, 10000);
        memberRepository.save(memberA);
        memberRepository.save(memberB);
        //when - 수행
        memberService.accountTransfer(memberA.getMemberId(), memberB.getMemberId(), 2000);
        //then - 검증
        Member findMemberA = memberRepository.findById(memberA.getMemberId());
        Member findMemberB = memberRepository.findById(memberA.getMemberId());
        Assertions.assertThat(findMemberA.getMoney()).isEqualTo(10000);
    }
}