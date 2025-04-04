package com.example.secured_web_app;

import com.example.secured_web_app.post.PostService;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class SupplyDummyDataOnStartup {

    private final JdbcUserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;
    private final PostService postService;

    @SneakyThrows
    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {

        var users = List.of(
                // Administrator
                User.withUsername("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles("USER", "ADMIN")
                        .build(),
                // Sample users
                User.withUsername("vakho")
                        .password(passwordEncoder.encode("password"))
                        .roles("USER")
                        .build(),
                User.withUsername("gio")
                        .password(passwordEncoder.encode("password"))
                        .roles("USER")
                        .build(),
                User.withUsername("dato")
                        .password(passwordEncoder.encode("password"))
                        .roles("USER")
                        .build(),
                User.withUsername("mari")
                        .password(passwordEncoder.encode("password"))
                        .roles("USER")
                        .build(),
                User.withUsername("bob")
                        .password(passwordEncoder.encode("password"))
                        .roles("USER")
                        .build()
        );

        // Save these users in database
        users.forEach(userDetailsManager::createUser);

        // Create fake random posts for users
        Random rnd = new Random();
        Faker faker = new Faker();
        int numberOfPosts = rnd.nextInt(100, 500);
        for (int i = 0; i < numberOfPosts; i++) {
            postService.createPost(
                    users.get(rnd.nextInt(users.size())).getUsername(),
                    StringUtils.capitalize(
                            String.join(" ", faker.lorem().words(rnd.nextInt(3, 6)))
                    ),
                    StringUtils.capitalize(
                            String.join(" ", faker.lorem().words(rnd.nextInt(40, 100)))
                    )
            );
        }
    }
}
