package com.example.website_with_database.home;

import com.example.website_with_database.topic.TopicController;
import com.example.website_with_database.topic.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final TopicService topicService;
    private final TopicController topicController;

    @GetMapping({"/", "/home"})
    public String index(Model model) {
        topicController.page(0, model);
        model.addAttribute("top5LikedTopics", topicService.getTop5ByLikes());
        model.addAttribute("top5HottestTopics", topicService.getTop5HottestTopics());
        return "index";
    }
}
