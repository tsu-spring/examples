package com.example.restfull_web_app.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping({"/", "/home"})
    public String index() {
        return "index";
    }
}
