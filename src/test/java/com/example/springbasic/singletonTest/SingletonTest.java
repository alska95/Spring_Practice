package com.example.springbasic.singletonTest;

import com.example.springbasic.AppConfig;
import com.example.springbasic.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SingletonTest {
    @Test
    @DisplayName("non-spring pure DI container // problems")
    void pureContainer(){
        AppConfig appConfig = new AppConfig();
        //1. 조회 : 호출할때마다 객체 생성
        MemberService memberService1 = appConfig.memberService();
        //1. 조회 : 호출할때마다 객체 생성
        MemberService memberService2 = appConfig.memberService();

        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2); //다른게 생성된다.

        Assertions.assertThat(memberService1).isNotSameAs(memberService2);

    }

    @Test
    @DisplayName("singleTon object")
    void singletonServiceTest(){
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        System.out.println("singletonService1 : " + singletonService1);
        System.out.println("singletonService2 : " + singletonService2);

        Assertions.assertThat(singletonService1).isSameAs(singletonService2);
        //same ==
        //equal (string)
    }

    @Test
    @DisplayName("spring container and singleTon")
    void springContainer(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        //1. 조회 : 호출할때마다 객체 생성
        MemberService memberService1 = ac.getBean("memberService" , MemberService.class);
        MemberService memberService2 = ac.getBean("memberService" , MemberService.class);
        //1. 조회 : 호출할때마다 객체 생성

        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2); //다른게 생성된다.

        Assertions.assertThat(memberService1).isSameAs(memberService2);
    }


}
