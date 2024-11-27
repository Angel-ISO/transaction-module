package org.jala.university.presentation.controller.admin;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.jala.university.presentation.commons.BaseController;
import org.jala.university.presentation.utils.MessageGreeting;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
public class AdminHomeController extends BaseController implements Initializable {

    @FXML
    public BorderPane rootPane;
    @FXML
    private Label welcomeLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MessageGreeting.updateWelcomeMessage(welcomeLabel);
        MessageGreeting.setBackgroundBasedOnTime(welcomeLabel, rootPane);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AdminHomeController that = (AdminHomeController) o;
        return Objects.equals(rootPane, that.rootPane) && Objects.equals(welcomeLabel, that.welcomeLabel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), rootPane, welcomeLabel);
    }
}
