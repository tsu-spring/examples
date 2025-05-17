package com.example.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class SettingsControllerAdvice {

    @Autowired
    private AppProperties appProperties;

    @ModelAttribute("appName")
    public String appName() { // HttpServletRequest will be injected automatically!
        return appProperties.getName();
    }
}
