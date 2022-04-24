package com.example.jdbc.service;

import com.example.jdbc.connection.DBConnectionUtil;
import com.example.jdbc.domain.Member;
import com.example.jdbc.repository.MemberRepositoryV1;
import com.example.jdbc.repository.MemberRepositoryV2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static com.example.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.*;


/**
 * 기본 동작 - 트랜잭션 적용
 * */
class MemberServiceV2Test {
    public static final String MEMBER_A = "memberA";
    public static final String MEMBER_B = "memberB";
    public static final String MEMBER_EX = "ex";

    private MemberRepositoryV2 memberRepository;
    private MemberRepositoryV1 memberRepositoryV1;
    private MemberServiceV2 memberService;


    @BeforeEach
    void beforeEach(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        memberRepository = new MemberRepositoryV2(dataSource);
        memberRepositoryV1 = new MemberRepositoryV1(dataSource);
        memberService = new MemberServiceV2(dataSource, memberRepository);
    }

    @AfterEach
    void afterEach() throws SQLException {
        memberRepositoryV1.delete("memberA");
        memberRepositoryV1.delete("memberB");
    }


    @Test
    @DisplayName("정상 이체")
    void accountTransfer() throws SQLException {
        //given - 데이터 준비
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberB = new Member(MEMBER_B, 10000);
        memberRepositoryV1.save(memberA);
        memberRepositoryV1.save(memberB);
        //when - 수행
        memberService.accountTransfer(memberA.getMemberId(), memberB.getMemberId(), 2000);
        //then - 검증
        Member findMemberA = memberRepositoryV1.findById(memberA.getMemberId());
        Member findMemberB = memberRepositoryV1.findById(memberA.getMemberId());
        assertThat(findMemberA.getMoney()).isEqualTo(8000);
    }

    @Test
    @DisplayName("이체 중 예외 발생")
    void accountTransferEx() throws SQLException {
        //given - 데이터 준비
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberEX = new Member(MEMBER_EX, 10000);
        memberRepositoryV1.save(memberA);
        memberRepositoryV1.save(memberEX);
        //when - 수행
        assertThatThrownBy(()->memberService.accountTransfer(memberA.getMemberId(), memberEX.getMemberId(), 2000)).isInstanceOf(IllegalStateException.class);
        //then - 검증
        Member findMemberA = memberRepositoryV1.findById(memberA.getMemberId());
        Member findMemberB = memberRepositoryV1.findById(memberA.getMemberId());
        assertThat(findMemberA.getMoney()).isEqualTo(10000);
    }
}