package com.example.may_seventh.singletonTest;


public class SingletonService {
    private static final SingletonService instance = new SingletonService(); //자기 자신을 내부에 private static으로 가지고 있음. //자바 static

    public static SingletonService getInstance(){
        return instance;
    }

    private SingletonService(){

    } //생성자 막아버림.

    public void logic(){
        System.out.println("싱글톤 객체 로직 호출");
    }
}
