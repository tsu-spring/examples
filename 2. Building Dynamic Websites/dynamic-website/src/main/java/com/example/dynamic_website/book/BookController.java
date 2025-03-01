package com.example.dynamic_website.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService service;

    @GetMapping(value = {"/", "/books"})
    public String page(@RequestParam(value = "page", required = false) Integer page, Model model) {
        if (page == null) {
            page = 1;
        }
        model.addAttribute("books", service.getBooksWithAuthorsByPage(page));
        model.addAttribute("page", page);
        model.addAttribute("totalPages", service.getTotalNumberOfPages());
        return "book/page";
    }

    @GetMapping("/book")
    public String single(@RequestParam("id") Integer bookId, Model model) {
        model.addAttribute("book", service.getBookWithAuthorById(bookId));
        return "book/single";
    }
}
