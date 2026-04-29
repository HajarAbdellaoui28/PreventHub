package com.preventhub.engagement.config;

import com.preventhub.engagement.domain.EngagementRecord;
import com.preventhub.engagement.repository.EngagementRecordRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class DemoDataConfig {

    @Bean
    CommandLineRunner engagementSeeder(EngagementRecordRepository repository) {
        return args -> {
            if (repository.count() > 0) {
                return;
            }

            repository.saveAll(List.of(
                    EngagementRecord.builder().campaignId(1L).citizenId(1L).citizenName("Fatima Zahra El Amrani").region("Casablanca-Settat").channel("EMAIL").opened(true).enrolled(true).participated(true).droppedOut(false).engagementScore(90).eventTimestamp(LocalDateTime.now().minusDays(2)).build(),
                    EngagementRecord.builder().campaignId(1L).citizenId(2L).citizenName("Omar Benhaddou").region("Rabat-Sale-Kenitra").channel("SMS").opened(true).enrolled(true).participated(false).droppedOut(false).engagementScore(50).eventTimestamp(LocalDateTime.now().minusDays(1)).build(),
                    EngagementRecord.builder().campaignId(1L).citizenId(4L).citizenName("Hassan Lahlou").region("Casablanca-Settat").channel("PUSH").opened(true).enrolled(false).participated(false).droppedOut(true).engagementScore(10).eventTimestamp(LocalDateTime.now().minusHours(12)).build()
            ));
        };
    }
}
