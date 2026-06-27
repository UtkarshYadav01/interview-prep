package com.utkarsh;

import com.utkarsh2.CartService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.utkarsh")
public class AppConfig {

    @Bean
    public User CreateUser() {
        return new User("Utkarsh", 26);
    }

    @Bean
    public CartService getCartService() {
        return new CartService();
    }
}
