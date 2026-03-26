package ge.edu.sangu.bookinist.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

    @Value("${app.title}")
    private String appTitle;

    @ModelAttribute("appTitle")
    public String appTitle() {
        return appTitle;
    }
}
