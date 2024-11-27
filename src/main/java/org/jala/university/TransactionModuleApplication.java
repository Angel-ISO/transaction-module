package org.jala.university;

import io.github.lemonsalve.sfxnavigator.Navigator;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.jala.university.presentation.Routes;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class TransactionModuleApplication extends Application {
	private ConfigurableApplicationContext context;
	private Navigator navigator;

	public static void run(String[] args) {
		launch(args);
	}

	@Override
	public void init() {
		context = SpringApplication.run(TransactionModuleApplication.class);
		navigator = context.getBean(Navigator.class);
	}

	@Override
	public void start(final @NotNull Stage stage) {
		navigator.init(stage);
		navigator.navigateTo(Routes.LOG_IN.getName());
		Image icono = new Image(getClass().getResourceAsStream("/assets/App.png"));
		stage.getIcons().add(icono);
	}

	@Override
	public void stop() {
		context.close();
		Platform.exit();
	}
}
