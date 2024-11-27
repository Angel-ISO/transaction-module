package org.jala.university.application.config;

import io.github.lemonsalve.sfxnavigator.contexts.RoutesContext;
import io.github.lemonsalve.sfxnavigator.guards.DefaultGuards;
import io.github.lemonsalve.sfxnavigator.routes.Route;
import io.github.lemonsalve.sfxnavigator.routes.RouteConfiguration;
import org.jala.university.presentation.Routes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RoutesContextConfig {
    private final List<Route> appRoutes = List.of(
           new Route(
                   Routes.LOG_IN.getName(),
                   "/templates/log-in-users.fxml",
                   "login",
                   new RouteConfiguration(732, 503)
           ),
            new Route(
                    Routes.ADMIN_VIEW.getName(),
                    "/templates/Admin/admin-view.fxml",
                    "admin",
                    DefaultGuards.unprotected(),
                    new RouteConfiguration(810, 551),
                    List.of(
                            new Route(
                                    Routes.ADMIN_HOME.getName(),
                                    "/templates/Admin/admin-home.fxml",
                                    "home",
                                    new RouteConfiguration(609, 487)
                            ),
                            new Route(
                                    Routes.ADMIN_SEND_MONEY.getName(),
                                    "/templates/Admin/ad-users-crud.fxml",
                                    "SendMoney",
                                    new RouteConfiguration(609, 487)
                            ),
                            new Route(
                                    Routes.ADMIN_MESSAGE.getName(),
                                    "/templates/Admin/ad-message.fxml",
                                    "Message",
                                    new RouteConfiguration(609, 487)
                            ),
                            new Route(
                                    Routes.ADMIN_SCAN_QR_CODE.getName(),
                                    "/templates/Admin/ad-dashboard.fxml",
                                    "ScanQRCode",
                                    new RouteConfiguration(609, 487)
                            ),
                            new Route(
                                    Routes.ADMIN_SETTINGS.getName(),
                                    "/templates/Admin/ad-settings.fxml",
                                    "Settings",
                                    new RouteConfiguration(609, 487)
                            ),
                            new Route(
                                    Routes.ADMIN_HISTORIAL_TRANSACTION.getName(),
                                    "/templates/Admin/ad-historial-transaction.fxml",
                                    "HistorialTransaction",
                                    new RouteConfiguration(609, 487)
                            )
                    )

            ),
            new Route(
                    Routes.MAIN.getName(),
                    "/templates/main.fxml",
                    "main",
                    DefaultGuards.unprotected(),
                    new RouteConfiguration(810, 551 ),
                    List.of(
                            new Route(
                                    Routes.HOME.getName(),
                                    "/templates/home.fxml",
                                    "home",
                                    new RouteConfiguration(609, 487)
                            ),
                            new Route(
                                    Routes.SEND_MONEY.getName(),
                                    "/templates/send-money.fxml",
                                    "SendMoney",
                                    new RouteConfiguration(609, 487)
                            ),
                            new Route(
                                    Routes.MESSAGE.getName(),
                                    "/templates/message.fxml",
                                    "Message",
                                    new RouteConfiguration(609, 487)
                            ),
                            new Route(
                                    Routes.SCAN_QR_CODE.getName(),
                                    "/templates/codeQr-scan.fxml",
                                    "ScanQRCode",
                                    new RouteConfiguration(609, 487)
                            ),
                            new Route(
                                    Routes.SETTINGS.getName(),
                                    "/templates/settings.fxml",
                                    "Settings",
                                    new RouteConfiguration(609, 487)
                            )
                    )
            )
    );

    @Bean
    public RoutesContext getRoutesContext() {
        return new RoutesContext(appRoutes);
    }

}
