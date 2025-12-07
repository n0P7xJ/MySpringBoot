package com.student.myspringboot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestMailController {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.from:no-reply@example.com}")
    private String mailFrom;

    @GetMapping("/admin/send-test-mail")
    public String sendTestMail(@RequestParam(value = "to", required = false) String to) {
        String recipient = (to == null || to.isBlank()) ? mailFrom : to;
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom(mailFrom);
            msg.setTo(recipient);
            msg.setSubject("Test email from MySpringBoot");
            msg.setText("This is a test message from MySpringBoot at " + System.currentTimeMillis());
            mailSender.send(msg);
            return "OK: sent to " + recipient;
        } catch (Exception ex) {
            return "ERROR: " + ex.getMessage();
        }
    }
}
