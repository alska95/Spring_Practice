package com.example.jdbc.repository;

import com.example.jdbc.domain.Member;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.h2.engine.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.NoSuchElementException;

import static com.example.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
class MemberRepositoryV1Test {

    MemberRepositoryV1 repositoryV1;

    @BeforeEach
    void beforeEach(){
        //기본 DriverManager - 항상 새로운 커넥션을 획득
        //DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        //repositoryV1 = new MemberRepositoryV1(dataSource);

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setPassword(PASSWORD);
        dataSource.setUsername(USERNAME);

        repositoryV1 = new MemberRepositoryV1(dataSource);
    }

    @Test
    void crud() throws SQLException {
        //save
        Member member = new Member("memberV3", 10000);
        repositoryV1.save(member);

        //select
        Member findMember = repositoryV1.findById(member.getMemberId());
        System.out.println(findMember);
        assertThat(findMember).isEqualTo(member);

        //update: money: 10000 -> 20000
        repositoryV1.update(member.getMemberId(), 20000);
        Member updatedMember = repositoryV1.findById(member.getMemberId());
        assertThat(updatedMember.getMoney()).isEqualTo(20000);

        //delete
        repositoryV1.delete(member.getMemberId());
        assertThatThrownBy(()->repositoryV1.findById(member.getMemberId()))
                .isInstanceOf(NoSuchElementException.class);
    }
}