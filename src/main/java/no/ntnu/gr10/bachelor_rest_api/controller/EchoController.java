package no.ntnu.gr10.bachelor_rest_api.controller;

import no.ntnu.gr10.bachelor_rest_api.entity.Ship;
import no.ntnu.gr10.bachelor_rest_api.repository.ShipRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
public class EchoController {

  private final ShipRepository shipRepository;

  public EchoController(ShipRepository shipRepository){
    this.shipRepository = shipRepository;
  }

  @GetMapping("/test")
  @PreAuthorize("hasRole('2')")
  public ResponseEntity<String> echo(Authentication authentication){
    Integer companyId = (Integer) authentication.getPrincipal();
    return ResponseEntity.ok("Hey grabben, your company id is: " + companyId);
  }

  @PostMapping("Response")
  public String echoResponse(@RequestBody String input){
    return input;
  }

  @GetMapping("ships")
  public List<Ship> allShips()
  {
    return shipRepository.findAll();
  }

}
