package com.aleprimo.nova_store.audit;

import com.aleprimo.nova_store.Nova_Store;
import com.aleprimo.nova_store.models.UserEntity;
import com.aleprimo.nova_store.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {
                Nova_Store.class,
                AuditingIntegrationTest.TestSecurityConfiguration.class
        }
)
@ActiveProfiles("test")
@Transactional
class AuditingIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void whenSavingUser_thenAuditingFieldsShouldBeSet() {

        UserEntity user = UserEntity.builder()
                .username("audittest")
                .email("audittest@example.com")
                .password("securepass")
                .enabled(true)
                .roles(Set.of())
                .build();

        UserEntity savedUser = userRepository.save(user);

        assertThat(savedUser.getCreatedDate()).isNotNull();
        assertThat(savedUser.getCreatedBy()).isNotNull();
        assertThat(savedUser.getLastModifiedDate()).isNotNull();
    }

    @Test
    void whenUpdatingUser_thenLastModifiedFieldsShouldBeUpdated() {
        UserEntity user = UserEntity.builder()
                .username("audittest2")
                .email("audittest2@example.com")
                .password("securepass")
                .enabled(true)
                .roles(Set.of())
                .build();

        UserEntity savedUser = userRepository.save(user);

        savedUser.setUsername("audittest2_updated");
        UserEntity updatedUser = userRepository.save(savedUser);

        assertThat(updatedUser.getLastModifiedDate())
                .isAfterOrEqualTo(updatedUser.getCreatedDate());

        assertThat(updatedUser.getLastModifiedBy()).isEqualTo("test-user");
    }


    @Configuration
    static class TestSecurityConfiguration {
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
            return config.getAuthenticationManager();
        }

        @Bean
        public ServletWebServerFactory servletWebServerFactory() {
            return new TomcatServletWebServerFactory();
        }

        @Bean
        public AuditorAware<String> auditorProvider() {
            return () -> Optional.of("test-user");
        }

    }
}
