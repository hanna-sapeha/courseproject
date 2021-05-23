package com.hanna.sapeha.app.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.hanna.sapeha.app.service",
        "com.hanna.sapeha.app.repository",
        "com.hanna.sapeha.app"})
public class AppConfig {

}
