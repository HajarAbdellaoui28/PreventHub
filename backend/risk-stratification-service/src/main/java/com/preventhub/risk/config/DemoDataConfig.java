package com.preventhub.risk.config;

import com.preventhub.risk.domain.CitizenProfile;
import com.preventhub.risk.repository.CitizenProfileRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class DemoDataConfig {

    @Bean
    CommandLineRunner citizenSeeder(CitizenProfileRepository repository) {
        return args -> {
            if (repository.count() > 0) {
                return;
            }

            repository.saveAll(List.of(
                    CitizenProfile.builder()
                            .nationalId("MA1001")
                            .fullName("Fatima Zahra El Amrani")
                            .age(67)
                            .gender("FEMALE")
                            .city("Casablanca")
                            .region("Casablanca-Settat")
                            .socioEconomicBand("MIDDLE")
                            .smoker(false)
                            .diabetic(true)
                            .hypertensive(true)
                            .sedentary(true)
                            .bmi(31.2)
                            .lastScreeningDate(LocalDate.now().minusYears(3))
                            .chronicConditions(List.of("diabetes", "hypertension", "obesity"))
                            .build(),
                    CitizenProfile.builder()
                            .nationalId("MA1002")
                            .fullName("Omar Benhaddou")
                            .age(54)
                            .gender("MALE")
                            .city("Rabat")
                            .region("Rabat-Sale-Kenitra")
                            .socioEconomicBand("MIDDLE")
                            .smoker(true)
                            .diabetic(false)
                            .hypertensive(true)
                            .sedentary(false)
                            .bmi(28.0)
                            .lastScreeningDate(LocalDate.now().minusYears(1))
                            .chronicConditions(List.of("hypertension"))
                            .build(),
                    CitizenProfile.builder()
                            .nationalId("MA1003")
                            .fullName("Salma Chraibi")
                            .age(39)
                            .gender("FEMALE")
                            .city("Fes")
                            .region("Fes-Meknes")
                            .socioEconomicBand("LOW")
                            .smoker(false)
                            .diabetic(false)
                            .hypertensive(false)
                            .sedentary(true)
                            .bmi(26.4)
                            .lastScreeningDate(LocalDate.now().minusYears(4))
                            .chronicConditions(List.of("sedentary-lifestyle"))
                            .build(),
                    CitizenProfile.builder()
                            .nationalId("MA1004")
                            .fullName("Hassan Lahlou")
                            .age(72)
                            .gender("MALE")
                            .city("Casablanca")
                            .region("Casablanca-Settat")
                            .socioEconomicBand("HIGH")
                            .smoker(false)
                            .diabetic(true)
                            .hypertensive(true)
                            .sedentary(true)
                            .bmi(33.1)
                            .lastScreeningDate(LocalDate.now().minusYears(5))
                            .chronicConditions(List.of("diabetes", "hypertension", "obesity"))
                            .build(),
                    CitizenProfile.builder()
                            .nationalId("MA1005")
                            .fullName("Karim Bensaid")
                            .age(28)
                            .gender("MALE")
                            .city("Agadir")
                            .region("Souss-Massa")
                            .socioEconomicBand("MIDDLE")
                            .smoker(false)
                            .diabetic(false)
                            .hypertensive(false)
                            .sedentary(false)
                            .bmi(23.3)
                            .lastScreeningDate(LocalDate.now().minusMonths(10))
                            .chronicConditions(List.of("none"))
                            .build()
            ));
        };
    }
}
