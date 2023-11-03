package ru.kviak.cloudstorage.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TestEmailSenderService {

    @Mock
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    private EmailSenderService emailSenderService;

    @BeforeEach
    public void setup() {
        emailSenderService = new EmailSenderService(mailSender);
    }

    @Test
    public void testSendEmail() {
        SimpleMailMessage expectedMailMessage = new SimpleMailMessage();
        expectedMailMessage.setFrom(username);
        expectedMailMessage.setTo("recipient@example.com");
        expectedMailMessage.setSubject("Activation Email");
        expectedMailMessage.setText("Activation link for Cloud-Storage-Kviak: \nActivationLink123");

        emailSenderService.send("recipient@example.com", "Activation Email", "ActivationLink123");

        verify(mailSender).send(expectedMailMessage);
    }
}

