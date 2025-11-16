package com.student.myspringboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello from Spring Boot! Проект запущено успішно.";
    }

    @GetMapping("/")
    public String home() {
        return "Вітаємо! Це головна сторінка To-Do App. Перейдіть на /tasks для API.";
    }
}
