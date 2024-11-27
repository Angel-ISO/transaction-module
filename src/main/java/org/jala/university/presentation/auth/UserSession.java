package org.jala.university.presentation.auth;

import lombok.Getter;
import lombok.Setter;
import org.jala.university.domain.entities.User;

@Setter
@Getter
public class UserSession {
    private static UserSession instance;
    private User loggedUser;
    private boolean isVerified;
    private boolean isOtpVerified = false;

    private UserSession() {}

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void clearSession() {
        loggedUser = null;
    }
}
