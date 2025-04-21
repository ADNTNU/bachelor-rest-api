package no.ntnu.gr10.bachelor_rest_api.fisheryActivity;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/fisheryActivities")
public class FisheryActivityController {

  private final FisheryActivityRepository fisheryActivityRepository;

  public FisheryActivityController(FisheryActivityRepository fisheryActivityRepository){
    this.fisheryActivityRepository = fisheryActivityRepository;
  }

  @GetMapping("/all")
  public ResponseEntity<List<FisheryActivity>> echo(Authentication authentication){
    Integer companyId = (Integer) authentication.getPrincipal();

    List<FisheryActivity> fisheryActivity = fisheryActivityRepository.findAllByCompany_Id(companyId.longValue());

    return ResponseEntity.ok(fisheryActivity);

  }

}
