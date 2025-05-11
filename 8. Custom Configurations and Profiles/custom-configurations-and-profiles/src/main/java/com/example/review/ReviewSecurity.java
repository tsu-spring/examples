package com.example.review;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("reviewSecurity")
@RequiredArgsConstructor
public class ReviewSecurity {

    private final ReviewRepository reviewRepository;

    public boolean isAuthor(Long postId) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return reviewRepository.findById(postId)
                .map(post -> post.getUser().getUsername().equals(currentUsername))
                .orElse(false);
    }
}
