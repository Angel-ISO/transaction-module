package org.jala.university.application.util;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.ConfigurableApplicationContext;
import io.github.lemonsalve.sfxnavigator.contexts.ApplicationContext;

public record NavigatorAppContext(ConfigurableApplicationContext context)
        implements ApplicationContext {

    @Override
    public <T> @NotNull T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }
}

