package com.example.multilingual_website.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.time.Duration;
import java.util.Locale;

@Configuration
public class LocalizationConfig {

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver(); // We can pass cookie name also
        localeResolver.setDefaultLocale(Locale.ENGLISH); // Set default locale
        localeResolver.setCookieMaxAge(Duration.ofMinutes(60)); // Cookie expiration time in seconds
        localeResolver.setCookiePath("/"); // "/" means that cookie is accessible for the entire application
        return localeResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang"); // Parameter name to switch locale
        return interceptor;
    }
}
