package com.student.myspringboot.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRegistrationDto {
    
    @NotBlank(message = "Ім'я обов'язкове")
    @Size(min = 2, max = 50, message = "Ім'я: 2-50 символів")
    private String firstName;

    @NotBlank(message = "Прізвище обов'язкове")
    @Size(min = 2, max = 50, message = "Прізвище: 2-50 символів")
    private String lastName;

    @NotBlank(message = "Email обов'язковий")
    @Email(message = "Невірний формат email")
    private String email;

    @NotBlank(message = "Телефон обов'язковий")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Невірний формат телефону. Використовуйте 10-15 цифр")
    private String phone;

    private String image;

    @NotBlank(message = "Пароль обов'язковий")
    @Size(min = 6, message = "Пароль: мінімум 6 символів")
    private String password;

    @NotBlank(message = "Підтвердження пароля обов'язкове")
    private String confirmPassword;
}
