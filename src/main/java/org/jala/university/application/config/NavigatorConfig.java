package org.jala.university.application.config;

import io.github.lemonsalve.sfxnavigator.Navigator;
import io.github.lemonsalve.sfxnavigator.analyzers.NavigationNodeAnalyzer;
import io.github.lemonsalve.sfxnavigator.contexts.ApplicationContext;
import io.github.lemonsalve.sfxnavigator.contexts.RoutesContext;
import io.github.lemonsalve.sfxnavigator.routes.RouteHistory;
import org.jala.university.TransactionModuleApplication;
import org.jala.university.application.util.NavigatorAppContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class NavigatorConfig {

    @Bean
    public ApplicationContext appContextProvider(final ConfigurableApplicationContext context) {
        return new NavigatorAppContext(context);
    }

    @Bean
    public RouteHistory routeHistoryProvider() {
        return new RouteHistory();
    }

    @Bean
    public NavigationNodeAnalyzer navigationNodeAnalyzerProvider(final RoutesContext routesContext) {
        return new NavigationNodeAnalyzer(routesContext);
    }

    @Bean
    public Navigator navigator(
            ApplicationContext applicationContext,
            RouteHistory routeHistory,
            NavigationNodeAnalyzer navigationNodeAnalyzer,
            RoutesContext routesContext
    )
    {
        return new Navigator(
                applicationContext,
                routeHistory,
                routesContext,
                navigationNodeAnalyzer,
                TransactionModuleApplication.class
        );
    }
}

