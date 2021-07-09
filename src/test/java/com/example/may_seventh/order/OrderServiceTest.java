package com.example.may_seventh.order;

import com.example.may_seventh.AppConfig;
import com.example.may_seventh.member.Grade;
import com.example.may_seventh.member.Member;
import com.example.may_seventh.member.MemberService;
import com.example.may_seventh.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {
    AppConfig appConfig = new AppConfig();
    MemberService memberService = appConfig.memberService();
    OrderService orderService = appConfig.orderService();
    @Test
    void createOrder(){
        Long memberId = 1L;
        Member member = new Member(memberId , "memberA" , Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId , "ItemA" ,10000);
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}
