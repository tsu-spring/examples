package com.example.website_with_database.topic;

import com.example.website_with_database.comment.CommentForm;
import com.example.website_with_database.comment.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;
    private final CommentService commentService;

    @GetMapping("/topics")
    public String page() {
        return "redirect:/topics/0";
    }

    @GetMapping("/topics/{page}")
    public String page(@PathVariable(name = "page") Integer page, Model model) {
        Page<TopicDTO> topicsPage = topicService.getTopics(page);
        model.addAttribute("topics", topicsPage);
        model.addAttribute("page", page);
        model.addAttribute("totalPages", topicsPage.getTotalPages());
        return "topic/page";
    }

    @GetMapping("/topic/{id}")
    public String single(@PathVariable("id") long id, Model model) {
        if (!model.containsAttribute("commentForm")) {
            model.addAttribute("commentForm", new CommentForm(id));
        }
        model.addAttribute("topic", topicService.getTopicById(id));
        return "topic/single";
    }

    @PostMapping("/topic/{id}")
    public String writeComment(@Valid @ModelAttribute("commentForm") CommentForm form,
                                BindingResult result,
                               @PathVariable("id") long id,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("commentForm", form);
            return single(id, model);
        }
        try {
            commentService.saveComment(id, form.getAuthor(), form.getText());
            redirectAttributes.addFlashAttribute("successMessage", "Comment created!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", String.format("An error occurred: %s",
                    e.getMessage()));
        }
        return String.format("redirect:/topic/%d", form.getTopicId());
    }

    @GetMapping("/topic/{id}/like")
    public String like(@PathVariable("id") long id) {
        topicService.likeTopic(id);
        return String.format("redirect:/topic/%d", id);
    }

    @GetMapping("/topic/{id}/delete")
    public String delete(@PathVariable("id") long id) {
        topicService.deleteTopicById(id);
        return "redirect:/topics";
    }

    @GetMapping("/topic/new")
    public String writeNewTopic(Model model) {
        if (!model.containsAttribute("topicForm")) {
            model.addAttribute("topicForm", new TopicForm());
        }
        return "topic/new";
    }

    @PostMapping("/topic/new")
    public String writeNewTopic(@Valid @ModelAttribute("topicForm") TopicForm form,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("topicForm", form);
            return "topic/new";
        }
        try {
            TopicDTO newTopic = topicService.save(form.getTitle(), form.getText());
            redirectAttributes.addFlashAttribute("successMessage", "Topic created!");

            // Also, construct URL for newly created topic
            String newTopicURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/topic/{id}")
                    .buildAndExpand(newTopic.getId())
                    .toUriString();
            redirectAttributes.addFlashAttribute("newTopicURL", newTopicURL);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", String.format("An error occurred: %s",
                    e.getMessage()));
        }
        return "redirect:/topic/new";
    }
}
