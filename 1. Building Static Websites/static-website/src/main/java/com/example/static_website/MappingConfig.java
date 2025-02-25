package com.example.static_website;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MappingConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/about").setViewName("about-us");
        registry.addViewController("/products").setViewName("products");
        registry.addViewController("/contact").setViewName("contact-us");

        // Register redirects
        registry.addRedirectViewController("/home", "/");
        registry.addRedirectViewController("/about-us", "/about");
        registry.addRedirectViewController("/contact-us", "/contact");
    }
}
