package com.example.jdbc.repository;

import com.example.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MemberRepositoryV0Test {

    MemberRepositoryV0 repositoryV0 = new MemberRepositoryV0();

    @Test
    void crud() throws SQLException {
        //save
        Member member = new Member("memberV2", 10000);
        repositoryV0.save(member);

        //select
        Member findMember = repositoryV0.findById(member.getMemberId());
        System.out.println(findMember);
        assertThat(findMember).isEqualTo(member);
    }
}