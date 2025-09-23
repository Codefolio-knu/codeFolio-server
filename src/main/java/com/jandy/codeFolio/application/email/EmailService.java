package com.jandy.codeFolio.application.email;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final Map<String, String> verificationStore = new ConcurrentHashMap<>();

    public void sendVerificationEmail(String email) {
        if (!email.endsWith("@knu.ac.kr")) {
            throw new IllegalArgumentException("Only @knu.ac.kr emails are allowed.");
        }

        String code = String.valueOf((int)(Math.random() * 900000) + 100000);
        verificationStore.put(email, code);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("CodeFolio 인증번호");
        message.setText("CodeFolio 인증번호는: " + code + "입니다.");

        mailSender.send(message);
    }

    public boolean verifyCode(String email, String code) {
        return code.equals(verificationStore.get(email));
    }
}

