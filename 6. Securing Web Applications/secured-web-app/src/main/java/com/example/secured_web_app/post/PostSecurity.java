package com.example.secured_web_app.post;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("postSecurity")
@RequiredArgsConstructor
public class PostSecurity {

    private final PostRepository postRepository;

    public boolean isAuthor(Long postId) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return postRepository.findById(postId)
                .map(post -> post.getAuthor().getUsername().equals(currentUsername))
                .orElse(false);
    }
}
