package com.example.may_seventh.discount;

import com.example.may_seventh.member.Grade;
import com.example.may_seventh.member.Member;

import org.springframework.stereotype.Component;

@Component
public class RateDiscountPolicy implements DiscountPolicy{
    private int discountPercent = 10;
    @Override
    public int discount(Member member, int price) {
        if(member.getGrade() == Grade.VIP){
            return price/100*discountPercent;
        }
        return 0;
    }
}
