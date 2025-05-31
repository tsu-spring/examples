package com.example.application_with_tests.calculator;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.example.application_with_tests.calculator.Operation.*;

@RequiredArgsConstructor
@Controller
public class CalculatorController {

    private final CalculatorService calculatorService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("calculatorForm", new CalculatorForm());
        return "index";
    }

    @PostMapping("/")
    public String calculate(@Valid @ModelAttribute("calculatorForm") CalculatorForm calculatorForm,
                            BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "index";
        }
        redirectAttributes.addFlashAttribute("calculationResult", switch (calculatorForm.getOperation()) {
            case ADD -> calculatorService.add(calculatorForm.getA(), calculatorForm.getB());
            case SUBTRACT -> calculatorService.sub(calculatorForm.getA(), calculatorForm.getB());
            case MULTIPLY -> calculatorService.mul(calculatorForm.getA(), calculatorForm.getB());
            case DIVIDE -> calculatorService.div(calculatorForm.getA(), calculatorForm.getB());
        });
        return "redirect:/";
    }
}
