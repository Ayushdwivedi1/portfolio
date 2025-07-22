package com.theayushdwivedi.portfolio.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {
    "com.theayushdwivedi.portfolio"
})
@EntityScan("com.theayushdwivedi.portfolio.entity")
@EnableJpaRepositories("com.theayushdwivedi.portfolio.repository")
public class AppConfig {
    
} 