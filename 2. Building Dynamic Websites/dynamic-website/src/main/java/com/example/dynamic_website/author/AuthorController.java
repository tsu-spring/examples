package com.example.dynamic_website.author;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService service;

    @GetMapping("/authors")
    public String all(Model model) {
        model.addAttribute("authors", service.getAllAuthors());
        return "author/all";
    }

    @GetMapping("/author")
    public String single(@RequestParam("id") Integer authorId, Model model) {
        model.addAttribute("author", service.getAuthorById(authorId));
        return "author/single";
    }
}
