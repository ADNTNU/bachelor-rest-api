package no.ntnu.gr10.bachelor_rest_api.repository;

import no.ntnu.gr10.bachelor_rest_api.entity.Ship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipRepository extends JpaRepository<Ship, Long> {

}
