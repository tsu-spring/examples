package com.example.jms_activemq_demo.controller;

import com.example.jms_activemq_demo.controller.form.EmailForm;
import com.example.jms_activemq_demo.model.Email;
import com.example.jms_activemq_demo.producer.EmailSender;
import com.example.jms_activemq_demo.receiver.EmailReceiverOne;
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
public class EmailController {

    private final EmailSender emailProducer;
    private final EmailReceiverOne emailReceiver;

    @GetMapping("/")
    public String sendEmail(Model model) {
        if (!model.containsAttribute("emailForm")) {
            model.addAttribute("emailForm", new EmailForm());
        }
        return "index";
    }

    @PostMapping("/")
    public String sendEmail(@Valid @ModelAttribute("emailForm") EmailForm form,
                            BindingResult result,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "index";
        }
        try {
            emailProducer.sendEmail(new Email(form.getTo(), form.getSubject(), form.getBody()));
            redirectAttributes.addFlashAttribute("successMessage", "Email sent.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    String.format("An error occurred: %s", e.getMessage())
            );
        }
        return "redirect:/";
    }
}
