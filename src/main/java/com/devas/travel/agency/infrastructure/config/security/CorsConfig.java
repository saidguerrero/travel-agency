package com.devas.travel.agency.infrastructure.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
//                registry
                        // Enable cross-origin request handling for the specified path pattern.
                        // Exact path mapping URIs (such as "/admin") are supported as well as Ant-style path patterns (such as "/admin/**").
//                        .addMapping("/login")
//                        .allowedOrigins("http://localhost:3000/")
//                        .allowedMethods("POST")
//                        .exposedHeaders("*");


                registry.addMapping("/api/v1/**")
                        .allowedOrigins("*")
                        .allowedHeaders("*")
                        .exposedHeaders("*")
                        .allowedMethods("*");
            }
        };
    }
}
