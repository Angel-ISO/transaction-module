package org.jala.university.infrastructure.services;

import jakarta.mail.MessagingException;
import org.jala.university.domain.entities.Notification;
import org.jala.university.domain.repository.AccountRepository;
import org.jala.university.domain.repository.NotificationRepository;
import org.jala.university.infrastructure.services.mailer.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private EmailService emailService;

    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        notificationService = new NotificationService(notificationRepository, accountRepository, emailService);
    }



    @Test
    void testCreateNotification_InvalidAccounts() throws MessagingException {
        UUID sourceAccountId = UUID.randomUUID();
        UUID destinationAccountId = UUID.randomUUID();

        when(accountRepository.existsById(sourceAccountId)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                notificationService.createNotification(sourceAccountId, destinationAccountId, 100.0));

        assertEquals("One or both account IDs are invalid", exception.getMessage());
        verify(notificationRepository, never()).save(any(Notification.class));
        verify(emailService, never()).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    void testGetNotificationsForUser() {
        String userId = "user123";
        List<Notification> expectedNotifications = List.of(
                new Notification("source1", "destination1", 50.0),
                new Notification("source2", "destination2", 100.0)
        );

        when(notificationRepository.findBySourceAccountIdOrDestinationAccountId(userId, userId))
                .thenReturn(expectedNotifications);

        List<Notification> result = notificationService.getNotificationsForUser(userId);

        assertEquals(expectedNotifications, result, "Notifications should match the expected list");
        verify(notificationRepository, times(1))
                .findBySourceAccountIdOrDestinationAccountId(userId, userId);
    }
}
