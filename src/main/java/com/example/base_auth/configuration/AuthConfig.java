package com.example.base_auth.configuration;

import com.example.base_auth.auth.JwtFilter;
import com.example.base_auth.auth.JwtProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.GenericFilterBean;

@Configuration
@ComponentScan("com.example.base_auth.auth")
public class AuthConfig {

    @Bean
    JwtProvider getJwtProvider(){
        return new JwtProvider();
    }

    @Bean
    GenericFilterBean getFilterBean(){
        return new JwtFilter();
    }

}
