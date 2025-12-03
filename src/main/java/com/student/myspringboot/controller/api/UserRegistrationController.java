package com.student.myspringboot.controller.api;

import com.student.myspringboot.entity.User;
import com.student.myspringboot.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Registration", description = "API для реєстрації користувачів")
public class UserRegistrationController {

    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Реєстрація нового користувача", description = "Створює нового користувача в системі")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user) {
        try {
            User savedUser = userService.saveUser(user);
            return ResponseEntity.ok().body("{\"message\": \"Користувача успішно зареєстровано\", \"id\": " + savedUser.getId() + "}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("{\"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("{\"message\": \"Помилка сервера\"}");
        }
    }
}
