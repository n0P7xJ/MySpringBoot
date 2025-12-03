package com.student.myspringboot.controller.api;

import com.student.myspringboot.dto.UserRegistrationDto;
import com.student.myspringboot.entity.User;
import com.student.myspringboot.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Registration", description = "API для реєстрації користувачів")
public class UserRegistrationController {

    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Реєстрація нового користувача", description = "Створює нового користувача в системі")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationDto dto, BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        
        // Validation errors from annotations
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
        }
        
        // Password confirmation check
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            errors.put("confirmPassword", "Паролі не співпадають");
        }
        
        // Check if email exists
        if (userService.existsByEmail(dto.getEmail())) {
            errors.put("email", "Користувач з таким email вже існує");
        }
        
        // Check if phone exists
        if (dto.getPhone() != null && !dto.getPhone().isEmpty() && userService.existsByPhone(dto.getPhone())) {
            errors.put("phone", "Користувач з таким телефоном вже існує");
        }
        
        // Return errors if any
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }
        
        try {
            // Create user entity from DTO
            User user = new User();
            user.setFirstName(dto.getFirstName());
            user.setLastName(dto.getLastName());
            user.setEmail(dto.getEmail());
            user.setPhone(dto.getPhone());
            user.setImage(dto.getImage());
            user.setPassword(dto.getPassword());
            
            User savedUser = userService.saveUser(user);
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Користувача успішно зареєстровано");
            response.put("id", savedUser.getId());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            errors.put("general", "Помилка сервера: " + e.getMessage());
            return ResponseEntity.internalServerError().body(errors);
        }
    }
}
