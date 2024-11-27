package org.jala.university.infrastructure.services.mailer;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String toEmail, String subject, String message) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(toEmail);
        helper.setSubject(subject);

        String htmlMessage = "<html><body style='font-family: Arial, sans-serif;'>" +
                "<div style='text-align: center;'>" +
                "<img src='cid:logo' alt='AVT-Bank' style='max-width: 200px;'>" +
                "<h1 style='color: #0056b3;'>AVT-Bank Reminder</h1>" +
                "<p>" + message + "</p>" +
                "</div>" +
                "</body></html>";

        helper.setText(htmlMessage, true);

         helper.addInline("logo", new ClassPathResource("assets/app.png"));

        mailSender.send(mimeMessage);
    }

}
