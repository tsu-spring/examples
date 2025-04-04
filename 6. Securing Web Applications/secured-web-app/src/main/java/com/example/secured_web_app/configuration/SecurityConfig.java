package com.example.secured_web_app.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableMethodSecurity(
        // We will only use @PreAuthorize, so we don't need others :)
//        prePostEnabled = true, // For @PreAuthorize (true by default)
//        securedEnabled = true, // For @Secured annotation
//        jsr250Enabled = true // For @RolesAllowed annotation
)
@RequiredArgsConstructor
public class SecurityConfig {

    private final DataSource dataSource;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // NOTE: It would be better to secure everything except few pages (this would ensure
                // that we won't forget what pages to secure when we add new ones)
                .authorizeHttpRequests(auth -> auth
                        // Private pages
                        .requestMatchers("/profile/**").authenticated()
                        // Public pages
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        // When the login page is specified in the Spring Security configuration,
                        // you are responsible for rendering the page!
                        .loginPage("/login")
                        .defaultSuccessUrl("/profile") // Where user will be redirected after authenticating
                        .permitAll() // Already permitted by our design
                )
                // Default behaviour is enough, but "/logout" needs POST request
                // with CSRF token (which Thymeleaf automatically provides in each form)
                .logout(Customizer.withDefaults())
                // Upon authenticating if the HTTP parameter named "remember-me" exists, then
                // the user will be remembered even after their jakarta.servlet.http.HttpSession expires.
                .rememberMe(Customizer.withDefaults());

        http.csrf(customizer -> customizer
                // Only ignore CSRF for "/h2-console/**" endpoints
                .ignoringRequestMatchers("/h2-console/**"))
        ;

        // Allow frames for the H2 console (required to display H2's console in a browser)
        http.headers(customizer -> customizer
                .frameOptions(Customizer.withDefaults())
                .disable()
        );

        return http.build();
    }

    @Bean
    public JdbcUserDetailsManager userDetailsService() {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return encoder;
    }
}
