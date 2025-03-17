package no.ntnu.gr10.bachelor_rest_api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class EchoController {

  @PostMapping()
  public String echo(){
    return "This is a response from the echo rest server";
  }

  @PostMapping("Response")
  public String echoResponse(@RequestBody String input){
    return input;
  }

}
