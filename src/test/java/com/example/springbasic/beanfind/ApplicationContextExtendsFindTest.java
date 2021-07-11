package com.example.springbasic.beanfind;

import com.example.springbasic.discount.DiscountPolicy;
import com.example.springbasic.discount.FixDiscountPolicy;
import com.example.springbasic.discount.RateDiscountPolicy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

public class ApplicationContextExtendsFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

    @Test
    @DisplayName("search with parent , if more than two child , duplicated error")
    void findBeanByParentTypeDuplicate(){
        Assertions.assertThrows(NoUniqueBeanDefinitionException.class , () ->ac.getBean(DiscountPolicy.class));
    }

    @Test
    @DisplayName("search with parent , if more than two child , you should select by name")
    void findBeanByParentTypeDuplicateByName(){
        org.assertj.core.api.Assertions.assertThat(ac.getBean("rateDiscountPolicy" ,DiscountPolicy.class)).isInstanceOf(DiscountPolicy.class);
    }

    @Test
    @DisplayName("find with BeanObject")
    void findBeanByParentTypeDuplicatedByObject(){
        RateDiscountPolicy bean = ac.getBean(RateDiscountPolicy.class);
        org.assertj.core.api.Assertions.assertThat(bean).isInstanceOf(RateDiscountPolicy.class);
    }

    @Test
    @DisplayName("find all bean by parent")
    void findAllBeanByParent(){
        Map<String, DiscountPolicy> beansOfType = ac.getBeansOfType(DiscountPolicy.class);
        for (String s : beansOfType.keySet()) {
            System.out.println(s);
        }
        org.assertj.core.api.Assertions.assertThat(beansOfType.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("gets all Objects // 부모 타입으로 모두 조회하기 - Object")
    void findAllBeanByObjectType(){
        Map<String, Object> beansOfType = ac.getBeansOfType(Object.class);
        for (String s : beansOfType.keySet()) {
            System.out.println(s);
        }
    }


    @Configuration
    static class TestConfig{
        @Bean
        public DiscountPolicy rateDiscountPolicy(){
            return new RateDiscountPolicy();
        }

        @Bean
        public DiscountPolicy fixDiscountPolicy(){
            return new FixDiscountPolicy();
        }
    }
}
