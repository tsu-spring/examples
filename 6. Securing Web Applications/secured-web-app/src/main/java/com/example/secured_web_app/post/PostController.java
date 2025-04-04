package com.example.secured_web_app.post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping({"/", "/posts"})
    public String allPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {
        Page<PostDto> posts = postService.getAllPosts(page, size);
        model.addAttribute("posts", posts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", posts.getTotalPages());
        //model.addAttribute("totalItems", posts.getTotalElements()); // This we don't need ;)
        return "index";
    }

    @GetMapping("/author/{username}/posts")
    public String postByAuthor(
            @PathVariable String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {
        Page<PostDto> posts = postService.getAllPostsByAuthor(username, page, size);
        model.addAttribute("username", username);
        model.addAttribute("posts", posts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", posts.getTotalPages());
        return "author-posts";
    }

    @GetMapping("/post/{id}")
    public String post(@PathVariable Long id, Model model) {
        model.addAttribute("post", postService.getPostById(id));
        return "post";
    }
}
