package com.example.jdbc.repository;

import com.example.jdbc.domain.Member;

import java.sql.SQLException;

public interface MemberRepositoryEx {
    Member save(Member member) throws SQLException; //인터페이스가 예외에 종속적이 되어서 제기능을 못하게된다. --> 런타임 예외로 변경해서 해결 가능
    Member findById(String memberId) throws SQLException;
    void update(String memberId, int money) throws SQLException;
    void delete(String memberId) throws SQLException;
}
