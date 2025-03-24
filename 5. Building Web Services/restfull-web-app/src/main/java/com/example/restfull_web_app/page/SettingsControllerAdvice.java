package com.example.restfull_web_app.page;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice(assignableTypes = HomeController.class)
public class SettingsControllerAdvice {

    @ModelAttribute("appPages")
    public List<Navigation> appPages() {
        return List.of(
                new Navigation("Home", "/"),
                new Navigation("OpenAPI", "/v3/api-docs"),
                new Navigation("Swagger UI", "/swagger-ui/index.html")
        );
    }

    @ModelAttribute("activeLink")
    public String activeLink(HttpServletRequest request) { // HttpServletRequest will be injected automatically!
        return request.getRequestURI();
    }

    public record Navigation(String name, String link) {
    }
}
