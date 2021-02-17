package ru.home.itbooks.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@EnableJpaRepositories(basePackages = {"ru.home.itbooks.repository"})
public class JpaConfig {
}
