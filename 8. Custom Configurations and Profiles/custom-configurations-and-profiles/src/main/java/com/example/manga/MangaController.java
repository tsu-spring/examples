package com.example.manga;

import com.example.review.ReviewForm;
import com.example.review.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class MangaController {

    private final MangaService mangaService;
    private final ReviewService reviewService;

    @GetMapping({"/", "/mangas"})
    public String allPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "24") int size,
            Model model
    ) {
        Page<MangaDto> posts = mangaService.getAllMangas(page, size);
        model.addAttribute("mangas", posts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", posts.getTotalPages());
        return "index";
    }

    @GetMapping("/manga/{id}")
    public String manga(@PathVariable Long id, Model model) {
        model.addAttribute("manga", mangaService.getPostById(id));
        if (!model.containsAttribute("reviewForm")) {
            model.addAttribute("reviewForm", new ReviewForm(id));
        }
        return "manga";
    }

    @PostMapping("/review/new")
    public String writeReview(@Valid @ModelAttribute("reviewForm") ReviewForm form,
                              BindingResult result,
                              RedirectAttributes redirectAttributes,
                              Authentication authentication,
                              Model model) {
        if (result.hasErrors()) {
            return manga(form.getMangaId(), model);
        }

        try {
            reviewService.saveReview(form.getMangaId(), authentication.getName(), form.getText(), form.getStars());
            redirectAttributes.addFlashAttribute("successMessage", "You wrote a review");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", String.format("An error occurred: %s",
                    e.getMessage()));
        }
        return "redirect:/manga/" + form.getMangaId();
    }
}
