package com.example.jdbc.service;

import com.example.jdbc.connection.ConnectionConst;
import com.example.jdbc.domain.Member;
import com.example.jdbc.repository.MemberRepositoryV1;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static com.example.jdbc.connection.ConnectionConst.*;
import static org.junit.jupiter.api.Assertions.*;


/**
 * 기본 동작 - 트랜잭션이 없는 경우
 * */
class MemberServiceV1Test {
    public static final String MEMBER_A = "memberA";
    public static final String MEMBER_B = "memberB";
    public static final String MEMBER_EX = "memberEX";

    private MemberRepositoryV1 memberRepository;
    private MemberServiceV1 memberService;


    @BeforeEach
    void beforeEach(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        memberRepository = new MemberRepositoryV1(dataSource);
        memberService = new MemberServiceV1(memberRepository);
    }

    @AfterEach
    void afterEach() throws SQLException {
        memberRepository.delete("memberA");
        memberRepository.delete("memberB");
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
}