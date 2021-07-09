package com.example.may_seventh.discount;

import com.example.may_seventh.member.Grade;
import com.example.may_seventh.member.Member;

public class FixDiscountPolicy implements DiscountPolicy{
    private int discountFixAmount = 1000;
    @Override
    public int discount(Member member, int price) {
        if(member.getGrade() == Grade.VIP){
            return discountFixAmount;
        }
        return 0;
    }

}
