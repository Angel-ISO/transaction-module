package org.jala.university.presentation.controller;

import io.github.lemonsalve.sfxnavigator.Navigator;
import org.jala.university.presentation.commons.BaseController;
import org.jala.university.presentation.Routes;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DevController extends BaseController {
    private final Navigator navigator;


    public DevController(Navigator navigator) {
        this.navigator = navigator;
    }

    public void goToApp( ) {
        navigator.navigateTo(Routes.MAIN.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DevController that = (DevController) o;
        return Objects.equals(navigator, that.navigator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), navigator);
    }
}
