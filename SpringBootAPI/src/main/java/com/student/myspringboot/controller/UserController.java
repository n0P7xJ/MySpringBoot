package com.student.myspringboot.controller;

import com.student.myspringboot.entity.User;
import com.student.myspringboot.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "users/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user, 
                               BindingResult result, 
                               Model model, 
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "users/register";
        }
        try {
            userService.saveUser(user);
            redirectAttributes.addFlashAttribute("successMessage", "Користувача успішно зареєстровано!");
            return "redirect:/users/list";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "users/register";
        }
    }

    @GetMapping("/list")
    public String showUsersList(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users/list";
    }
}
