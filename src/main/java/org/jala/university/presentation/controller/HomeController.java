package org.jala.university.presentation.controller;

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
public class HomeController extends BaseController implements Initializable {

    @FXML
    private Label welcomeLabel;

    @FXML
    private BorderPane rootPane;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        HomeController that = (HomeController) o;
        return Objects.equals(welcomeLabel, that.welcomeLabel) && Objects.equals(rootPane, that.rootPane);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), welcomeLabel, rootPane);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MessageGreeting.updateWelcomeMessage(welcomeLabel);
        MessageGreeting.setBackgroundBasedOnTime(welcomeLabel, rootPane);
    }

}
