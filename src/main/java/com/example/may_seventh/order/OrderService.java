package com.example.may_seventh.order;

public interface OrderService {
    Order createOrder(Long memberId , String itemName , int itemPrice);
}
