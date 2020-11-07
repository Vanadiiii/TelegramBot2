package ru.dexsys.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.dexsys.repository.UserTempDataRepository;

@Configuration
@EnableJpaRepositories(basePackageClasses = UserTempDataRepository.class)
public class UserTempRepConfig {
}
