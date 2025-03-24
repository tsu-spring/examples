package com.example.restfull_web_app.bad_controllers;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bad")
public class ErroneousController {

    @SneakyThrows
    @GetMapping("/throw-exception")
    public String throwException() {
        throw new Exception("Something bad happened in this controller :(");
    }

    @SneakyThrows
    @PostMapping("/validate-input")
    public ResponseEntity<String> bad(@Valid @RequestBody DummyRequestObject requestObject) {
        return ResponseEntity.ok(
                String.format("Request object's text was: %s", requestObject.getText())
        );
    }
}
