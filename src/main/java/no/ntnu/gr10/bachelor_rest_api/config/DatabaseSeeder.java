package no.ntnu.gr10.bachelor_rest_api.config;

import no.ntnu.gr10.bachelor_rest_api.entity.Ship;
import no.ntnu.gr10.bachelor_rest_api.repository.ShipRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseSeeder {

  @Bean
  CommandLineRunner initDatabase(ShipRepository shipRepository){
    return args -> {

      shipRepository.deleteAll();

      Ship ship1 = new Ship("Island Contender", "PSV", "Equinor UK", "Supply duties", "Bergen");
      Ship ship2 = new Ship("Island Vanguard", "AHTS", "AkerPB", "All duties incl ROV", "Bergen");

      shipRepository.save(ship1);
      shipRepository.save(ship2);

    };
  }
}
