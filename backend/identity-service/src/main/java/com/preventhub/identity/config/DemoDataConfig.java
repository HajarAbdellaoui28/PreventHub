package com.preventhub.identity.config;

import com.preventhub.identity.domain.UserAccount;
import com.preventhub.identity.domain.UserRole;
import com.preventhub.identity.repository.UserAccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DemoDataConfig {

    @Bean
    CommandLineRunner identitySeeder(UserAccountRepository repository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (repository.count() > 0) {
                return;
            }

            repository.save(UserAccount.builder()
                    .fullName("Amina El Idrissi")
                    .email("admin@preventhub.io")
                    .organization("Ministere de la Sante")
                    .passwordHash(passwordEncoder.encode("PreventHub!2026"))
                    .roles(Set.of(UserRole.ROLE_ADMIN))
                    .build());

            repository.save(UserAccount.builder()
                    .fullName("Dr. Youssef Berrada")
                    .email("doctor@preventhub.io")
                    .organization("CHU Rabat")
                    .passwordHash(passwordEncoder.encode("PreventHub!2026"))
                    .roles(Set.of(UserRole.ROLE_HEALTH_PROFESSIONAL))
                    .build());

            repository.save(UserAccount.builder()
                    .fullName("Nadia Tazi")
                    .email("agency@preventhub.io")
                    .organization("Agence regionale de sante")
                    .passwordHash(passwordEncoder.encode("PreventHub!2026"))
                    .roles(Set.of(UserRole.ROLE_AGENCY))
                    .build());

            repository.save(UserAccount.builder()
                    .fullName("Commune de Casablanca")
                    .email("collectivite@preventhub.io")
                    .organization("Collectivite territoriale")
                    .passwordHash(passwordEncoder.encode("PreventHub!2026"))
                    .roles(Set.of(UserRole.ROLE_COLLECTIVITY))
                    .build());
        };
    }
}
