package com.preventhub.analytics.config;

import com.preventhub.analytics.domain.AnalyticsInsight;
import com.preventhub.analytics.repository.AnalyticsInsightRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DemoDataConfig {

    @Bean
    CommandLineRunner analyticsSeeder(AnalyticsInsightRepository repository) {
        return args -> {
            if (repository.count() > 0) {
                return;
            }
            repository.saveAll(List.of(
                    AnalyticsInsight.builder().title("Tension arterielle").sectionName("Population health").narrative("La region Casablanca-Settat concentre la plus forte densite de profils a risque eleve en cardiologie preventive.").build(),
                    AnalyticsInsight.builder().title("Couverture territoriale").sectionName("Operations").narrative("Les campagnes combinant SMS et email obtiennent une meilleure ouverture dans les zones periurbaines.").build(),
                    AnalyticsInsight.builder().title("ROI sante").sectionName("Finance").narrative("Le depistage precoce des patients hypertendus montre le meilleur potentiel de reduction des couts evitables.").build()
            ));
        };
    }
}
