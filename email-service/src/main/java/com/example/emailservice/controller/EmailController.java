package com.example.emailservice.controller;

import com.example.emailservice.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email")
@Slf4j
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/{email}/{msg}")
    public String sendMail(@PathVariable("email") String email,
                           @PathVariable("msg") String msg) {
        log.info("Received Email Request::::: {}", email);
        return this.emailService.sendMail(email, msg);
    }
}
