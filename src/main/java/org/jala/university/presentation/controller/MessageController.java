package org.jala.university.presentation.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import org.jala.university.domain.entities.Notification;
import org.jala.university.domain.entities.User;
import org.jala.university.infrastructure.services.AccountService;
import org.jala.university.infrastructure.services.NotificationService;
import org.jala.university.infrastructure.services.UserService;
import org.jala.university.presentation.auth.UserSession;
import org.jala.university.presentation.commons.BaseController;
import org.jala.university.presentation.utils.NotificationsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class MessageController extends BaseController implements Initializable {

    public final NotificationService notificationService;
    public final UserService userService;
    public final AccountService accountService;

    @FXML
    public VBox notificationsContainer;


    @Autowired
    public MessageController (NotificationService notificationService, UserService userService, AccountService accountService) {
        this.notificationService = notificationService;
        this.userService = userService;
        this.accountService = accountService;
    }


    private void loadNotifications() {
        UserSession session = UserSession.getInstance();
        String loggedUserId = session.getLoggedUser().getId().toString();

        List<Notification> notifications = notificationService.getNotificationsForUser(loggedUserId);

        Map<String, String> userIdToNameMap = new HashMap<>();
        for (Notification notification : notifications) {
            userIdToNameMap.put(notification.getSourceAccountId(), null);
            userIdToNameMap.put(notification.getDestinationAccountId(), null);
        }

        Set<UUID> userIds = userIdToNameMap.keySet().stream()
                .map(UUID::fromString)
                .collect(Collectors.toSet());
        List<User> users = userService.getByIds(userIds);

        for (User user : users) {
            userIdToNameMap.put(user.getId().toString(), user.getUsername());
        }

        for (Notification notification : notifications) {
            boolean isSender = notification.getSourceAccountId().equals(loggedUserId);

            String formattedTimestamp = notification.getTimestamp().toString();

            double amount = notification.getAmount();
            String formattedAmount = String.format("%.2f", amount);

            String formattedCurrency = accountService.findCurrencyName(session.getLoggedUser().getId());

            VBox notificationBox = NotificationsTemplate.createNotification(
                    formattedTimestamp,
                    UserSession.getInstance().getLoggedUser().getUsername(),
                    formattedAmount,
                    formattedCurrency,
                    isSender ? userIdToNameMap.get(notification.getDestinationAccountId())
                            : userIdToNameMap.get(notification.getSourceAccountId()),
                    isSender
            );

            notificationsContainer.getChildren().add(notificationBox);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadNotifications();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MessageController that = (MessageController) o;
        return Objects.equals(notificationService, that.notificationService) && Objects.equals(userService, that.userService) && Objects.equals(accountService, that.accountService) && Objects.equals(notificationsContainer, that.notificationsContainer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), notificationService, userService, accountService, notificationsContainer);
    }
}
