package com.example.may_seventh.order;

import com.example.may_seventh.discount.DiscountPolicy;
import com.example.may_seventh.discount.FixDiscountPolicy;
import com.example.may_seventh.discount.RateDiscountPolicy;
import com.example.may_seventh.member.Member;
import com.example.may_seventh.member.MemoryMemberRepository;
import com.example.may_seventh.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService{
    private final MemberRepository memberRepository;
    private DiscountPolicy discountPolicy;

    @Autowired // ac.getBean(MemberRepository.class)
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member ,itemPrice);

        return new Order(memberId , itemName , itemPrice , discountPrice);
    }

    //테스트용
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
