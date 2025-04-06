package com.example.secured_web_app.rest;

import com.example.secured_web_app.post.PostDto;
import com.example.secured_web_app.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ApiController {

    private final PostService postService;

    /**
     * We provide RESTful API to retrieve posts... for registered users only :)
     * Don't forget to call 'https://localhost:8443/api/post?page=0&size=5' with Authorization header!
     */
    @GetMapping("/api/post")
    public Page<PostDto> post(Pageable pageable) {
        return postService.getAllPosts(pageable.getPageNumber(), pageable.getPageSize());
    }
}
