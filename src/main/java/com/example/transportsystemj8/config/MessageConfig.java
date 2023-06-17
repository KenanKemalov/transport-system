package com.example.transportsystemj8.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Locale;

public class MessageConfig implements WebMvcConfigurer {
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("/messages");
        //messageSource.setDefaultEncoding("UTF-8");
        //messageSource.setDefaultLocale(new Locale("bg"));
        return messageSource;
    }
}
