package org.jala.university.presentation.controller;

import io.github.lemonsalve.sfxnavigator.Navigator;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import org.jala.university.domain.entities.Account;
import org.jala.university.domain.entities.User;
import org.jala.university.infrastructure.services.AccountService;
import org.jala.university.infrastructure.services.UserService;
import org.jala.university.presentation.auth.UserSession;
import org.jala.university.presentation.commons.BaseController;
import org.jala.university.presentation.Routes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
public class LogInController extends BaseController implements Initializable {


    @FXML
    private Label logOut;

    @FXML
    public ComboBox<String> listUser;

    private final UserService userService;
    private final Navigator navigator;

    private final AccountService accountService;

    @Autowired
    public LogInController(UserService userService, Navigator navigator, AccountService accountService) {
        this.accountService = accountService;
        this.userService = userService;
        this.navigator = navigator;
    }


    @FXML
    public void handleUserSelection() {
        String selectUser = listUser.getValue();
        if (selectUser == null) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please select a user.");
                alert.showAndWait();
            });
        } else {
            User selectedUser = userService.getUserByUsername(selectUser);
            UserSession.getInstance().setLoggedUser(selectedUser);

            List<Account> accounts = accountService.getAccountsByUserId(selectedUser.getId());
            boolean hasMasterAccount = accounts.stream()
                    .anyMatch(account -> "Master".equalsIgnoreCase(account.getAccountType().getTypeName()));

            if (hasMasterAccount) {
                navigator.navigateTo(Routes.ADMIN_HOME.getName());
            } else {
                navigator.navigateTo(Routes.HOME.getName());
            }
        }
    }

    public void listUserName() {
        if (userService != null) {
            userService.getAllNameUser().forEach(user -> listUser.getItems().add(user));
        } else {
            System.err.println("UserService no está disponible.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listUserName();
        logOut.setOnMouseClicked(event -> System.exit(0));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LogInController that = (LogInController) o;
        return Objects.equals(logOut, that.logOut) && Objects.equals(listUser, that.listUser) && Objects.equals(userService, that.userService) && Objects.equals(navigator, that.navigator) && Objects.equals(accountService, that.accountService);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), logOut, listUser, userService, navigator, accountService);
    }
}
