package org.jala.university.infrastructure.services;

import jakarta.mail.MessagingException;
import org.jala.university.domain.entities.Notification;
import org.jala.university.domain.repository.AccountRepository;
import org.jala.university.domain.repository.NotificationRepository;
import org.jala.university.infrastructure.services.mailer.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final AccountRepository accountRepository;
    private final EmailService emailService;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository,
                               AccountRepository accountRepository,
                               EmailService emailService) {
        this.notificationRepository = notificationRepository;
        this.accountRepository = accountRepository;
        this.emailService = emailService;
    }

    public Notification createNotification(UUID sourceAccountId, UUID destinationAccountId, double amount) {
        validateAccountsExist(sourceAccountId, destinationAccountId);

        Notification notification = new Notification(sourceAccountId.toString(), destinationAccountId.toString(), amount);
        notificationRepository.save(notification);

        sendNotificationEmail(sourceAccountId, destinationAccountId, amount);

        return notification;
    }

    private void validateAccountsExist(UUID sourceAccountId, UUID destinationAccountId) {
        if (!accountRepository.existsById(sourceAccountId) || !accountRepository.existsById(destinationAccountId)) {
            throw new IllegalArgumentException("One or both account IDs are invalid");
        }
    }

    private void sendNotificationEmail(UUID sourceAccountId, UUID destinationAccountId, double amount) {
        Optional<String> sourceEmail = accountRepository.findEmailByAccountId(sourceAccountId);
        Optional<String> destinationEmail = accountRepository.findEmailByAccountId(destinationAccountId);

        String sourceName = accountRepository.findNameUserByAccountId(sourceAccountId).orElse("Unknown Sender");
        String destinationName = accountRepository.findNameUserByAccountId(destinationAccountId).orElse("Unknown Recipient");

        String senderMessage = String.format(
                "You sent <strong>$%.2f</strong> to <strong>%s</strong>.",
                amount, destinationName);

        String recipientMessage = String.format(
                "You received <strong>$%.2f</strong> from <strong>%s</strong>.",
                amount, sourceName);

        sourceEmail.ifPresent(email -> {
            try {
                emailService.sendEmail(email, "Transaction Sent", senderMessage);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });

        destinationEmail.ifPresent(email -> {
            try {
                emailService.sendEmail(email, "Transaction Received", recipientMessage);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });

        }

        public List<Notification> getNotificationsForUser(String userId) {
            return notificationRepository.findBySourceAccountIdOrDestinationAccountId(userId, userId);
        }





}
