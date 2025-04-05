package no.ntnu.gr10.bachelor_rest_api.controller;

import no.ntnu.gr10.bachelor_rest_api.entity.Ship;
import no.ntnu.gr10.bachelor_rest_api.repository.ShipRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
public class EchoController {

  private final ShipRepository shipRepository;

  public EchoController(ShipRepository shipRepository){
    this.shipRepository = shipRepository;
  }

  @PostMapping()
  public String echo(){
    return "This is a response from the echo rest server";
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
