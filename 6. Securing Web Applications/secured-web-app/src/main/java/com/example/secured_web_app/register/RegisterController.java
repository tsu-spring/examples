package com.example.secured_web_app.register;

import com.example.secured_web_app.db.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute("registerForm") @Valid RegisterForm registerForm, BindingResult result) {
        if (result.hasErrors()) {
            return "register"; // Return with errors
        }

        if (userService.existsUsername(registerForm.getUsername())) {
            result.rejectValue("username", "error.user", "Username is already taken.");
            return "register";
        }

        // Register new username
        userService.register(registerForm.getUsername(), registerForm.getPassword());
        return "redirect:/login?success"; // Redirect to login page with success message
    }
}
