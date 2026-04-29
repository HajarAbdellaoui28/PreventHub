package com.preventhub.campaign.config;

import com.preventhub.campaign.domain.Campaign;
import com.preventhub.campaign.domain.CampaignHistory;
import com.preventhub.campaign.domain.CampaignStatus;
import com.preventhub.campaign.repository.CampaignHistoryRepository;
import com.preventhub.campaign.repository.CampaignRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class DemoDataConfig {

    @Bean
    CommandLineRunner campaignSeeder(CampaignRepository campaignRepository, CampaignHistoryRepository historyRepository) {
        return args -> {
            if (campaignRepository.count() > 0) {
                return;
            }

            Campaign campaign = campaignRepository.save(Campaign.builder()
                    .title("CardioGuard 60+")
                    .objective("Depistage cardiovasculaire cible pour les habitants de plus de 60 ans en zones urbaines a forte prevalence.")
                    .campaignType("CARDIO_SCREENING")
                    .status(CampaignStatus.ACTIVE)
                    .minAge(60)
                    .maxAge(85)
                    .gender("ALL")
                    .targetCities(List.of("Casablanca", "Rabat"))
                    .targetRegions(List.of("Casablanca-Settat", "Rabat-Sale-Kenitra"))
                    .targetConditions(List.of("hypertension", "obesity"))
                    .scheduledDate(LocalDate.now().plusDays(3))
                    .expectedPopulation(2400)
                    .currentAudienceSize(1845)
                    .ownerOrganization("Ministere de la Sante")
                    .createdAt(LocalDateTime.now().minusDays(7))
                    .updatedAt(LocalDateTime.now().minusHours(8))
                    .build());

            historyRepository.save(CampaignHistory.builder()
                    .campaignId(campaign.getId())
                    .actionType("CREATED")
                    .actor("admin@preventhub.io")
                    .description("Initial campaign created.")
                    .occurredAt(LocalDateTime.now().minusDays(7))
                    .build());
        };
    }
}
