package com.example.may_seventh.discount;

import com.example.may_seventh.member.Member;

public interface DiscountPolicy {
    /*
    * return 할인 대상 금액
    *
    * */
    int discount(Member member , int price);
}
