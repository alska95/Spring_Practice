package com.example.may_seventh;

import com.example.may_seventh.discount.DiscountPolicy;
import com.example.may_seventh.discount.RateDiscountPolicy;
import com.example.may_seventh.member.MemberService;
import com.example.may_seventh.member.MemberServiceImpl;
import com.example.may_seventh.member.MemoryMemberRepository;
import com.example.may_seventh.order.OrderService;
import com.example.may_seventh.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig { //설정 정보

    @Bean
    public MemberService memberService(){
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
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
