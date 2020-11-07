package ru.dexsys.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@Configuration
@PropertySource("classpath:application-dev.properties")
public class UserTempRepConfig {
}
