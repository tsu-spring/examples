package com.example.traditional_deployment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TraditionalDeploymentApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TraditionalDeploymentApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(TraditionalDeploymentApplication.class, args);
    }
}
