package com.example.interactive_website.upload;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class UploadController {

    private final UploadService service;

    @GetMapping("/upload")
    public String index(Model model) {
        if (!model.containsAttribute("uploadForm")) {
            model.addAttribute("uploadForm", new UploadForm());
        }
        return "upload";
    }

    @PostMapping("/upload")
    public String upload(@Valid @ModelAttribute("uploadForm") UploadForm form,
                         BindingResult result,
                         RedirectAttributes redirectAttributes) {
        if (form.getImage() == null || form.getImage().isEmpty()) {
            result.rejectValue("image", "error.image", "File upload is required.");
        }

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("uploadForm", form);
            return "/upload";
        }

        try {
            service.saveImage(form.getImage(), form.getDate());
            redirectAttributes.addFlashAttribute("successMessage", "File uploaded successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", String.format("An error occurred: %s",
                    e.getMessage()));
        }
        return "redirect:/upload";
    }
}
