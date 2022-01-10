package com.example.lab2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.xslt.XsltViewResolver;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        CorsRegistration registration = registry.addMapping("/**");
        registration.allowedOrigins("http://localhost:8081");
        registration.allowedMethods("*");
        WebMvcConfigurer.super.addCorsMappings(registry);
    }

    @Bean
    public XsltViewResolver xsltViewResolver() {
        XsltViewResolver resolver = new XsltViewResolver();
        resolver.setPrefix("classpath:/xslt/");
        resolver.setSuffix(".xslt");
        return resolver;
    }

}
