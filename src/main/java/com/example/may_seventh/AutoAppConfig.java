package com.example.may_seventh;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "com.example.may_seventh", //지정 안하면 AutoAppConfig패키지 이름부터 뒤짐.
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION , classes = Configuration.class)
)
public class AutoAppConfig {

}
