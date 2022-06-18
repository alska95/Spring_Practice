package hello.springtx.propagation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@SpringBootTest
class MemberServiceTest {

    @Autowired MemberRepository memberRepository;
    @Autowired MemberService memberService;
    @Autowired LogRepository logRepository;


    /**
     * memberService @Transactional:off
     * memberRepository @Transactional : on
     * logRepository @Transactional : on
     *
     * */
    @Test
    void outerTxOff_success(){
        String username = "outerTxOff_success";

        memberService.joinV1(username);

        Assertions.assertTrue(memberRepository.find(username).isPresent());
        Assertions.assertTrue(logRepository.find(username).isPresent());
    }

    /**
     * memberService @Transactional:off
     * memberRepository @Transactional : on
     * logRepository @Transactional : on Exception
     *
     * */
    @Test
    void outerTxOff_fail(){
        String username = "로그예외_outerTxOff_fail";

        assertThatThrownBy(()->memberService.joinV1(username))
                .isInstanceOf(RuntimeException.class);

        Assertions.assertTrue(memberRepository.find(username).isPresent()); //member 정상 커밋
        Assertions.assertTrue(logRepository.find(username).isEmpty()); //log는 롤백
    }
}