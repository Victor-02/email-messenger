package com.messenger.email.services;

import com.messenger.email.models.EmailModel;
import com.messenger.email.models.enums.StatusEmail;
import com.messenger.email.repositories.EmailRepository;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {

    final EmailRepository repository;
    final JavaMailSender emailSender;

    public EmailService(EmailRepository repository, JavaMailSender emailSender) {
        this.repository = repository;
        this.emailSender = emailSender;
    }

    public EmailModel sendEmail(EmailModel email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        email.setSendDateEmail(LocalDateTime.now());

        try {
            mailMessage.setFrom(email.getEmailFrom());
            mailMessage.setTo(email.getEmailTo());
            mailMessage.setSubject(email.getSubject());
            mailMessage.setText(email.getText());

            emailSender.send(mailMessage);
            email.setStatusEmail(StatusEmail.SENT);
        } catch (MailException e) {
            email.setStatusEmail(StatusEmail.ERROR);
        } finally {
            return repository.save(email);
        }


    }
}
