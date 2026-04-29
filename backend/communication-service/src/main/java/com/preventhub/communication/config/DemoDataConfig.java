package com.preventhub.communication.config;

import com.preventhub.communication.domain.CommunicationLog;
import com.preventhub.communication.domain.MessageTemplate;
import com.preventhub.communication.repository.CommunicationLogRepository;
import com.preventhub.communication.repository.MessageTemplateRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class DemoDataConfig {

    @Bean
    CommandLineRunner communicationSeeder(MessageTemplateRepository templateRepository, CommunicationLogRepository logRepository) {
        return args -> {
            
            // 1. Initialisation de la librairie de messages
            if (templateRepository.count() == 0) {
                templateRepository.saveAll(List.of(
                        MessageTemplate.builder().title("Invitation depistage cardiovasculaire").category("Cardiovascular").channel("EMAIL").medicalValidationLevel("Validated by medical committee").body("Vous etes invite a realiser un bilan cardiovasculaire gratuit dans votre centre de sante de proximite.").build(),
                        MessageTemplate.builder().title("Rappel vaccination grippe").category("Vaccination").channel("SMS").medicalValidationLevel("Validated by vaccination program").body("Rappel: la campagne de vaccination contre la grippe est ouverte cette semaine dans votre quartier.").build(),
                        MessageTemplate.builder().title("Conseils nutrition et diabete").category("Chronic diseases").channel("PUSH").medicalValidationLevel("Validated by endocrinology board").body("Retrouvez vos recommandations nutrition personnalisees pour limiter le risque diabetique.").build()
                ));
            }

            // 2. Initialisation de l'historique d'envoi (Dispatch history)
            if (logRepository.count() == 0) {
                logRepository.saveAll(List.of(
                        CommunicationLog.builder()
                                .campaignId(1L) // ID de la campagne
                                .subject("Invitation depistage cardiovasculaire")
                                .channel("EMAIL")
                                .recipientCount(1250) // Nom exact de ton champ
                                .status("DELIVERED")
                                .sentAt(LocalDateTime.now().minusDays(2)) // Date d'envoi simulée
                                .build(),
                        CommunicationLog.builder()
                                .campaignId(2L)
                                .subject("Rappel vaccination grippe")
                                .channel("SMS")
                                .recipientCount(840)
                                .status("SENT")
                                .sentAt(LocalDateTime.now().minusHours(5))
                                .build(),
                        CommunicationLog.builder()
                                .campaignId(3L)
                                .subject("Conseils nutrition et diabete")
                                .channel("PUSH")
                                .recipientCount(320)
                                .status("IN_PROGRESS")
                                .sentAt(LocalDateTime.now().minusMinutes(30))
                                .build()
                ));
            }
        };
    }
}