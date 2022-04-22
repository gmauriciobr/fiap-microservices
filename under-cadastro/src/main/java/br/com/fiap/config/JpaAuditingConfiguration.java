package br.com.fiap.config;

import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/** JPA auditing configuration. */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfiguration {
  /**
   * Create audtorwaware bean.
   *
   * @return AuditorAware
   */
  @Bean
  public AuditorAware<String> auditorProvider() {
    return () -> Optional.of("cadastro");
  }
}
