package com.example.review;

import com.example.db.entity.User;
import com.example.db.repository.UserRepository;
import com.example.manga.Manga;
import com.example.manga.MangaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MangaRepository mangaRepository;
    private final UserRepository userRepository;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Transactional
    public Review saveReview(Long mangaId, String username, String text, Integer stars) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Manga manga = mangaRepository.findById(mangaId)
                .orElseThrow(() -> new IllegalArgumentException("Manga not found with ID: " + mangaId));
        Review review = new Review();
        review.setText(text);
        review.setStars(stars);
        review.setUser(user);
        review.setManga(manga);
        return reviewRepository.save(review);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or @reviewSecurity.isAuthor(#reviewId)")
    public Review updateReview(Long reviewId, String username, String content, Integer stars) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found with ID: " + reviewId));
        review.setText(content);
        review.setStars(stars);
        review.setUpdatedAt(LocalDateTime.now()); // Update the timestamp
        return reviewRepository.save(review);
    }

    @PreAuthorize("hasRole('ADMIN') or @reviewSecurity.isAuthor(#reviewId)")
    public void deleteReviewById(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}
