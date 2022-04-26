package com.example.jdbc.service;

import com.example.jdbc.domain.Member;
import com.example.jdbc.repository.MemberRepositoryV1;
import com.example.jdbc.repository.MemberRepositoryV3;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.Platform;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import java.sql.SQLException;

import static com.example.jdbc.connection.ConnectionConst.*;


/**
 * 기본 동작 - 트랜잭션이 없는 경우
 * */
class MemberServiceV3_1Test {
    public static final String MEMBER_A = "memberA";
    public static final String MEMBER_B = "memberB";
    public static final String MEMBER_EX = "ex";

    private MemberRepositoryV3 memberRepository;
    private MemberServiceV3_1 memberService;


    @BeforeEach
    void beforeEach(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        memberRepository = new MemberRepositoryV3(dataSource);
        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        memberService = new MemberServiceV3_1(transactionManager, memberRepository);
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