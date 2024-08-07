package com.MSP.shopydrop.Utils;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailUtils {

    private final JavaMailSender mailSender;

    public MailUtils(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void forgetPasswordVerificationCode(String toEmail, String userName, String verificationLink) {
        String subject = "Email Verification";
        String message = "Dear " + userName + ",\n\n" +
                "Please verify your email by clicking the link below:\n" +
                verificationLink + "\n\n" +
                "If you did not request this verification, please ignore this email.\n\n" +
                "Best regards,\n" +
                "MSP Solution";

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }
}
