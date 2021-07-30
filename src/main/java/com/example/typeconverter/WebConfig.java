package com.example.typeconverter;


import com.example.typeconverter.converter.IntegerToStringConverter;
import com.example.typeconverter.converter.IpPortToStringConverter;
import com.example.typeconverter.converter.StringToIntegerConverter;
import com.example.typeconverter.converter.StringToIpPortConverter;
import com.example.typeconverter.formatter.MyNumberFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToIpPortConverter());
        registry.addConverter(new IpPortToStringConverter());
//        registry.addConverter(new StringToIntegerConverter());
//        registry.addConverter(new IntegerToStringConverter());


        //추가
        registry.addFormatter(new MyNumberFormatter());

    }
}
