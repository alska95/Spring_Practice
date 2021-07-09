package com.example.may_seventh;

import com.example.may_seventh.member.Grade;
import com.example.may_seventh.member.Member;
import com.example.may_seventh.member.MemberService;
import com.example.may_seventh.member.MemberServiceImpl;
import com.example.may_seventh.order.Order;
import com.example.may_seventh.order.OrderService;
import com.example.may_seventh.order.OrderServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderApp {
    public static void main(String[] args) {
//        AppConfig appConfig = new AppConfig();
//
//        MemberService memberService = appConfig.memberService();
//        OrderService orderService = appConfig.orderService();

        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        OrderService orderService = ac.getBean("orderService" , OrderService.class);

        Long memberId = 1L;
        Member member = new Member(memberId , "memberA" , Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId , "ItemA" , 10000);
        System.out.println("order = " + order);
    }
}
