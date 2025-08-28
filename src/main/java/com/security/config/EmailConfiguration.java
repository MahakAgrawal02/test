package com.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.util.Properties;

@Configuration
public class EmailConfiguration {

    @Value("${spring.mail.username:}")
    private String emailUsername;

    @Value("${spring.mail.password:}")
    private String emailPassword;

    @Bean
    @ConditionalOnProperty(name = "spring.mail.username", havingValue = "", matchIfMissing = false)
    public JavaMailSender javaMailSender() {
        // Only create mail sender if credentials are provided
        if (emailUsername == null || emailUsername.isEmpty() || 
            emailPassword == null || emailPassword.isEmpty() ||
            emailUsername.equals("your-email@gmail.com") || 
            emailPassword.equals("your-app-password")) {
            
            throw new IllegalStateException(
                "Email credentials not configured. Please set spring.mail.username and spring.mail.password " +
                "in application.properties or use environment variables MAIL_USERNAME and MAIL_PASSWORD. " +
                "For Gmail, use App Passwords, not your regular password."
            );
        }
        
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(emailUsername);
        mailSender.setPassword(emailPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}