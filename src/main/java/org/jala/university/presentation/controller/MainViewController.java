package org.jala.university.presentation.controller;

import io.github.lemonsalve.sfxnavigator.Navigator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.jala.university.presentation.auth.UserSession;
import org.jala.university.presentation.commons.BaseController;
import org.jala.university.presentation.Routes;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
public class MainViewController extends BaseController implements Initializable {

    @FXML
    public BorderPane routeRenderer;

    @FXML
    private Label logOut;

    private final Navigator navigator;

    public MainViewController(Navigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logOut.setOnMouseClicked(event -> {
            UserSession.getInstance().clearSession();

            navigator.navigateTo(Routes.LOG_IN.getName());
        });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MainViewController that = (MainViewController) o;
        return Objects.equals(routeRenderer, that.routeRenderer) && Objects.equals(logOut, that.logOut) && Objects.equals(navigator, that.navigator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), routeRenderer, logOut, navigator);
    }
}


