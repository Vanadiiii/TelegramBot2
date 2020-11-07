package ru.dexsys.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.dexsys.repository.UserTempDataRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Configuration
@EnableJpaAuditing(
        auditorAwareRef = "auditorAwareImpl",
        dateTimeProviderRef = "dateTimeProvider"
)
@EnableJpaRepositories(basePackageClasses = UserTempDataRepository.class)
public class UserTempRepConfig {

    @Bean
    public DateTimeProvider dateTimeProvider() {
        return () -> Optional.of(LocalDateTime.now());
    }

    @Bean
    public AuditorAware<String> auditorAwareImpl() {
        return () -> Optional.of("some_user");
    }
}
