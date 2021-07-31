package com.example.springbasic;

import com.example.springbasic.member.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    public static void main(String[] args) {
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class); //Bean 들을 관리한다. appConfig환경 설정 정보를 가지고
//        스프링 빈에 전부 집어넣고 관리한다.
        MemberService memberService = applicationContext.getBean("memberService" , MemberService.class);

        Member member = new Member(1L , "memberA" , Grade.VIP);
        memberService.join(member);

        Member fm = memberService.findMember(1L);
        System.out.println("new : " + member.getName());
        System.out.println("find : " + fm.getName());
    }
}
