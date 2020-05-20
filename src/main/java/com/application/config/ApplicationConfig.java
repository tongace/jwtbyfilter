package com.application.config;

import com.application.authen.TokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ApplicationConfig {
    @Value("${jwt.expirationTime}")
    private int configuredExpirationTimeInMinute;

    @Bean
    public TokenUtil tokenUtil(){
        return new TokenUtil(configuredExpirationTimeInMinute);
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
