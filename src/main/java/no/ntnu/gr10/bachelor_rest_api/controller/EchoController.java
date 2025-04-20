package no.ntnu.gr10.bachelor_rest_api.controller;

import jakarta.annotation.security.RolesAllowed;
import no.ntnu.gr10.bachelor_rest_api.dto.JwtPayload;
import no.ntnu.gr10.bachelor_rest_api.entity.Ship;
import no.ntnu.gr10.bachelor_rest_api.repository.ShipRepository;
import no.ntnu.gr10.bachelor_rest_api.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
public class EchoController {

  private final ShipRepository shipRepository;
  private final JwtUtil jwtUtil;

  public EchoController(ShipRepository shipRepository, JwtUtil jwtUtil){
    this.shipRepository = shipRepository;
    this.jwtUtil = jwtUtil;
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
