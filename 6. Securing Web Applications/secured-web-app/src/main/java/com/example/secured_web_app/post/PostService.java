package com.example.secured_web_app.post;

import com.example.secured_web_app.db.entity.User;
import com.example.secured_web_app.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostDto getPostById(Long id) {
        return PostDto.fromPost(postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found")));
    }

    public Page<PostDto> getAllPosts(int page, int size) {
        return getAllPostsByAuthor(null, page, size);
    }

    public Page<PostDto> getAllPostsByAuthor(String authorUsername, int page, int size) {
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Order.desc("createdAt")) // Order by "createdAt" descending
        );
        if (authorUsername != null) {
            return postRepository.findAllByAuthorUsername(authorUsername, pageable)
                    .map(PostDto::fromPost); // We have map in repository that gives Page<PostDto> object! :)
        } else {
            return postRepository.findAll(pageable)
                    .map(PostDto::fromPost); // We have map in repository that gives Page<PostDto> object! :)
        }
    }

    @Transactional
    public PostDto createPost(String username, String title, String content) {
        // Find the user who will be the author of the post
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // Create the new post and set the user as the author
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setAuthor(user);
        // Save the post
        post = postRepository.save(post);
        return PostDto.fromPost(post);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or @postSecurity.isAuthor(#postId)")
    public void updatePost(Long postId, String username, String title, String content) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        // Check that the user updating is the author!
        if (!post.getAuthor().getUsername().equals(username)) {
            throw new RuntimeException("You are not authorized to update this post");
        }
        post.setTitle(title);
        post.setContent(content);
        post.setUpdatedAt(LocalDateTime.now()); // Update the timestamp
        postRepository.save(post);
    }

    @PreAuthorize("hasRole('ADMIN') or @postSecurity.isAuthor(#postId)")
    public void deletePostById(Long postId) {
        postRepository.deleteById(postId);
    }
}
