package com.student.myspringboot.service;

import com.student.myspringboot.entity.User;
import com.student.myspringboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    @Value("${spring.mail.from:no-reply@example.com}")
    private String mailFrom;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }

    public User saveUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Користувач з email '" + user.getEmail() + "' вже існує");
        }
        if (user.getPhone() != null && !user.getPhone().isEmpty() && userRepository.existsByPhone(user.getPhone())) {
            throw new IllegalArgumentException("Користувач з телефоном '" + user.getPhone() + "' вже існує");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Користувача з ID " + id + " не знайдено"));
    }

    public void createPasswordResetToken(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            // For security don't reveal whether email exists; just return silently
            return;
        }
        User user = optionalUser.get();
        String token = UUID.randomUUID().toString();
        long expiry = Instant.now().plusSeconds(3600).toEpochMilli(); // 1 hour
        user.setResetToken(token);
        user.setResetTokenExpiry(expiry);
        userRepository.save(user);

        // send email
        String resetUrl = "http://localhost:8080/reset-password?token=" + token;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailFrom);
        message.setTo(user.getEmail());
        message.setSubject("Відновлення пароля");
        message.setText("Щоб відновити пароль, перейдіть за посиланням:\n" + resetUrl + "\nПосилання дійсне 1 годину.");
        mailSender.send(message);
    }

    public Optional<User> findByResetToken(String token) {
        return userRepository.findByResetToken(token);
    }

    public boolean resetPassword(String token, String newPassword) {
        Optional<User> optionalUser = userRepository.findByResetToken(token);
        if (optionalUser.isEmpty()) return false;
        User user = optionalUser.get();
        Long expiry = user.getResetTokenExpiry();
        if (expiry == null || Instant.now().toEpochMilli() > expiry) {
            return false; // expired
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepository.save(user);
        return true;
    }
}
