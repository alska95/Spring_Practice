package com.example.may_seventh.singletonTest;

import com.example.may_seventh.AppConfig;
import com.example.may_seventh.member.MemberServiceImpl;
import com.example.may_seventh.member.MemoryMemberRepository;
import com.example.may_seventh.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonTest {
    @Test
    void configurationTest(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemoryMemberRepository memberRepository = ac.getBean("memberRepository", MemoryMemberRepository.class);
        System.out.println("memberRepository = " + memberRepository);


        Assertions.assertThat(memberService.getMemberRepository()).isEqualTo(orderService.getMemberRepository()).isEqualTo(memberRepository);
    }
    @Test
    void configurationDeepTest(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);
        System.out.println("bean = " + bean);
    }
}
