package com.example.jdbc.service;

import com.example.jdbc.domain.Member;
import com.example.jdbc.repository.MemberRepositoryV1;
import com.example.jdbc.repository.MemberRepositoryV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


/**
* 트랜잭션 - 파라미터 연동
* */
@RequiredArgsConstructor
@Slf4j
public class MemberServiceV2 {

    private final DataSource dataSource;
    private final MemberRepositoryV2 memberRepository;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        Connection con = dataSource.getConnection();
        try{
            con.setAutoCommit(false);//트랜잭션 시작
            bizLogic(fromId, toId, money, con);
            con.commit();
        } catch (Exception e){
            con.rollback();
            throw new IllegalStateException(e);
        }finally {
            release(con);
        }

    }

    private void bizLogic(String fromId, String toId, int money, Connection con) throws SQLException {
        //비즈니스 로직
        Member fromMember = memberRepository.findById(fromId, con);
        Member toMember = memberRepository.findById(toId, con);

        memberRepository.update(fromId, fromMember.getMoney()- money, con);
        validation(toMember);
        memberRepository.update(toId, toMember.getMoney()- money, con);
    }

    private void release(Connection con) throws SQLException {
        if(con != null){
            try {
                con.setAutoCommit(true); //커넥션 풀을 고려
                con.close();
            }catch (Exception e){
                log.info("error", e);
            }
        }
        con.close();
    }

    private void validation(Member toMember) {
        if(toMember.getMemberId().equals("ex")){
            throw new IllegalStateException("이체중 에외 발생");
        }
    }
}
