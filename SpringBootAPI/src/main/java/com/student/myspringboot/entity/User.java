package com.student.myspringboot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Ім'я обов'язкове")
    @Size(min = 2, max = 50, message = "Ім'я: 2-50 символів")
    private String firstName;

    @NotBlank(message = "Прізвище обов'язкове")
    @Size(min = 2, max = 50, message = "Прізвище: 2-50 символів")
    private String lastName;

    @NotBlank(message = "Email обов'язковий")
    @Email(message = "Невірний формат email")
    @Column(unique = true)
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Невірний формат телефону")
    @Column(unique = true)
    private String phone;

    private String image;

    @NotBlank(message = "Пароль обов'язковий")
    @Size(min = 6, message = "Пароль: мінімум 6 символів")
    private String password;

    @Column(name = "reset_token")
    private String resetToken;

    @Column(name = "reset_token_expiry")
    private Long resetTokenExpiry; // epoch millis
}
