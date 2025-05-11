package com.example.configuration.dev;

import com.example.db.service.UserService;
import com.example.jikan.moe.JikanMoeService;
import com.example.manga.MangaDto;
import com.example.manga.MangaService;
import com.example.review.Review;
import com.example.review.ReviewSecurity;
import com.example.review.ReviewService;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.List;

@Profile("dev")
@Component
@RequiredArgsConstructor
public class DummyDataSupplier {

    private final SecureRandom rnd = new SecureRandom();
    private final Faker faker = new Faker();

    private final JdbcUserDetailsManager userDetailsManager;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    private final JikanMoeService jikanMoeService;
    private final MangaService mangaService;
    private final ReviewService reviewService;
    private final ReviewSecurity reviewSecurity;

    @SneakyThrows
    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        var fakePeople = fakerUser();
        if (userService.numberOfUsers() == 0) {
            // Save these users in database
            fakePeople.forEach(userDetailsManager::createUser);
        }
        if (mangaService.numberOfMangas() == 0) {
            for (int i = 1; i <= 4; i++) { // 25 per page (100 total)
                createDummyTopMangasWithFakeReviews(i, fakePeople);
            }
        }
    }

    private List<UserDetails> fakerUser() {
        return List.of(
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
    }

    private List<MangaDto> createDummyTopMangasWithFakeReviews(int page, List<UserDetails> users) {

        return jikanMoeService.topManga(page).stream()
                .map(mangaDto -> {
                            var manga = mangaService.createManga(
                                    mangaDto.getTitle(),
                                    mangaDto.getImageUrl(),
                                    mangaDto.getAuthor(),
                                    mangaDto.getDescription(),
                                    mangaDto.getPublicationYear(),
                                    mangaDto.getVolumes(),
                                    mangaDto.getChapters()
                            );

                            // Fake numbers of reviews
                            int reviewsAmount = rnd.nextInt(3, 25);
                            for (int i = 0; i < reviewsAmount; i++) {
                                reviewService.saveReview(
                                        manga.getId(),
                                        users.get(rnd.nextInt(users.size())).getUsername(),
                                        String.join(" ", faker.lorem().words(rnd.nextInt(5, 50))),
                                        rnd.nextInt(1, 6)
                                );
                            }
                            return manga;
                        }
                ).toList();
    }

    private List<Review> fakeReviews(List<UserDetails> users) {

        return null;
    }
}
