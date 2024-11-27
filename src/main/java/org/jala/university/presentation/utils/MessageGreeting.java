package org.jala.university.presentation.utils;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.jala.university.domain.entities.User;
import org.jala.university.presentation.auth.UserSession;

import java.time.LocalTime;

public class MessageGreeting {


    private MessageGreeting() {
    }

    public static void updateWelcomeMessage(Label welcomeLabel) {
        User loggedUser = UserSession.getInstance().getLoggedUser();
        LocalTime currentTime = LocalTime.now();
        String greeting;

        if (currentTime.isBefore(LocalTime.of(12, 0))) {
            greeting = "Good Morning";
        } else if (currentTime.isBefore(LocalTime.of(18, 0))) {
            greeting = "Good Afternoon";
        } else {
            greeting = "Good Evening";
        }

        if (loggedUser != null) {
            String welcomeMessage = greeting + ", " + loggedUser.getUsername() + "!";
            welcomeLabel.setText(welcomeMessage);
        } else {
            welcomeLabel.setText(greeting + ", unknown user.");
        }
    }

    public static void setBackgroundBasedOnTime(Label welcomeLabel, BorderPane rootPane) {
        String backgroundImageUrl;
        LocalTime currentTime = LocalTime.now();
        LocalTime morningStart = LocalTime.of(6, 0);
        LocalTime eveningStart = LocalTime.of(18, 0);

        if (currentTime.isAfter(morningStart) && currentTime.isBefore(eveningStart)) {
            backgroundImageUrl = "assets/morning.png";
            welcomeLabel.setStyle("-fx-text-fill: black");
        } else {
            backgroundImageUrl = "assets/night.png";
            welcomeLabel.setStyle("-fx-text-fill: white");
        }

        rootPane.setStyle("-fx-background-image: url('" + backgroundImageUrl + "'); " +
                "-fx-background-size: cover;");

    }
}
