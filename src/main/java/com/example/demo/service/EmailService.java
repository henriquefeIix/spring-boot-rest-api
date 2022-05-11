package com.example.demo.service;

import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.example.demo.model.Loan;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Service
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "application.mail")
public class EmailService {

    private Map<String, String> properties;
    private final SpringTemplateEngine templateEngine;
    private final JavaMailSender mailSender;

    public void sendEmails(List<Loan> loans) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        
        for (Loan loan : loans) {
            Context context = new Context();
            context.setVariable("user", loan.getUser().getFirstName());
            String htmlBody = templateEngine.process("email-template.html", context);

            helper.setFrom(properties.get("sender"));
            helper.setTo(loan.getUser().getEmail());
            helper.setSubject(properties.get("subject"));
            helper.setText(htmlBody, true);
            mailSender.send(message);
        }
    }

}
