package com.student.myspringboot.controller;

import com.student.myspringboot.entity.User;
import com.student.myspringboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordForm() {
        return "forgot_password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, Model model) {
        userService.createPasswordResetToken(email);
        model.addAttribute("message", "Якщо такий email існує, на нього буде надіслано лист для відновлення пароля.");
        return "forgot_password";
    }

    @GetMapping("/reset-password")
    public String resetPasswordForm(@RequestParam("token") String token, Model model) {
        Optional<User> optionalUser = userService.findByResetToken(token);
        if (optionalUser.isEmpty()) {
            model.addAttribute("error", "Недійсний або прострочений токен");
            return "reset_password";
        }
        User user = optionalUser.get();
        Long expiry = user.getResetTokenExpiry();
        if (expiry == null || Instant.now().toEpochMilli() > expiry) {
            model.addAttribute("error", "Токен прострочений");
            return "reset_password";
        }
        model.addAttribute("token", token);
        return "reset_password";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam("token") String token,
                                       @RequestParam("password") String password,
                                       @RequestParam("confirmPassword") String confirmPassword,
                                       Model model) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Паролі не співпадають");
            model.addAttribute("token", token);
            return "reset_password";
        }
        boolean ok = userService.resetPassword(token, password);
        if (!ok) {
            model.addAttribute("error", "Не вдалося скинути пароль (токен недійсний або прострочено)");
            return "reset_password";
        }
        model.addAttribute("message", "Пароль успішно змінено. Тепер увійдіть.");
        return "login";
    }
}
