package com.example.springbasic;

import com.example.springbasic.discount.DiscountPolicy;
import com.example.springbasic.discount.RateDiscountPolicy;
import com.example.springbasic.member.MemberService;
import com.example.springbasic.member.MemberServiceImpl;
import com.example.springbasic.member.MemoryMemberRepository;
import com.example.springbasic.order.OrderService;
import com.example.springbasic.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig { //설정 정보

    @Bean
    public MemberService memberService(){
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
        //어떤 서비스를 쓸지 꽂아주는 느낌
    }
    @Bean
    public OrderService orderService(){
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(
                memberRepository(),
                discountPolicy());
    }
    @Bean
    public MemoryMemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public DiscountPolicy discountPolicy(){
//        return new FixDiscountPolicy(); // 얘만 바꾸면 된다.
        return new RateDiscountPolicy();
    }
}
