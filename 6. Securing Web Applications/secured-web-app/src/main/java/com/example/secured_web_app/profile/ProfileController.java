package com.example.secured_web_app.profile;

import com.example.secured_web_app.post.PostDto;
import com.example.secured_web_app.post.PostService;
import com.example.secured_web_app.profile.form.PostCreateForm;
import com.example.secured_web_app.profile.form.PostEditForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/profile")
@Controller
@RequiredArgsConstructor
public class ProfileController {

    private final PostService postService;

    @GetMapping
    public String profile(Model model,
                          @RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "10") int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Page<PostDto> posts = postService.getAllPostsByAuthor(authentication.getName(), page, size);
        model.addAttribute("posts", posts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", posts.getTotalPages());
        model.addAttribute("totalElements", posts.getTotalElements());
        return "profile/index";
    }

    @GetMapping("/post/new")
    public String showCreateForm(Model model) {
        model.addAttribute("postCreateForm", new PostCreateForm());
        return "profile/post-create";
    }

    @PostMapping("/post/new")
    public String createPost(@Valid @ModelAttribute("postCreateForm") PostCreateForm form,
                             BindingResult bindingResult,
                             Principal principal) {
        if (bindingResult.hasErrors()) {
            return "profile/post-create";
        }
        String username = principal.getName();
        PostDto post = postService.createPost(username, form.getTitle(), form.getContent());
        return "redirect:/post/" + post.getId();
    }

    @GetMapping("/post/{id}/edit")
    public String showEditForm(@PathVariable Long id,
                               @RequestParam(defaultValue = "/") String redirectTo,
                               Model model) {
        PostDto post = postService.getPostById(id);
        model.addAttribute("postEditForm", new PostEditForm(post.getTitle(), post.getContent()));
        model.addAttribute("id", id);
        model.addAttribute("redirectTo", redirectTo);
        return "profile/post-edit";
    }

    @PostMapping("/post/{id}/edit")
    public String editPost(@PathVariable Long id,
                           @Valid @ModelAttribute("postEditForm") PostEditForm form,
                           BindingResult bindingResult,
                           Principal principal, // We can grap 'Principal' through injecting it here :)
                           Model model,
                           @RequestParam String redirectTo) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("postId", id);
            return "profile/post-edit";
        }
        postService.updatePost(id, principal.getName(), form.getTitle(), form.getContent());
        return "redirect:" + redirectTo;
    }

    @GetMapping("/post/{id}/delete")
    public String deletePost(@PathVariable Long id, @RequestParam(defaultValue = "/") String redirectTo) {
        postService.deletePostById(id);
        // Redirect user somewhere after deletion
        return "redirect:" + redirectTo;
    }
}
