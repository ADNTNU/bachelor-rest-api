package no.ntnu.gr10.bachelor_rest_api.fisheryActivity;

import no.ntnu.gr10.bachelor_rest_api.company.Company;
import no.ntnu.gr10.bachelor_rest_api.company.CompanyService;
import no.ntnu.gr10.bachelor_rest_api.dto.ErrorResponse;
import no.ntnu.gr10.bachelor_rest_api.excption.CompanyNotFoundException;
import no.ntnu.gr10.bachelor_rest_api.fisheryActivity.dto.CreateFisheryActivity;
import no.ntnu.gr10.bachelor_rest_api.security.JwtAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fisheryActivities")
public class FisheryActivityController {

  private final FisheryActivityService fisheryActivityService;
  private final CompanyService companyService;
  private static final Logger log = LoggerFactory.getLogger(FisheryActivityController.class);

  public FisheryActivityController(FisheryActivityService fisheryActivityService, CompanyService companyService){
    this.fisheryActivityService = fisheryActivityService;
    this.companyService = companyService;
  }

  @GetMapping("/all")
  public ResponseEntity<?> getAllFisheryActivities(Authentication authentication){

    try {
      Integer companyId = (Integer) authentication.getPrincipal();

      List<FisheryActivity> fisheryActivity = fisheryActivityService.getAllFisheryActivitiesWithCompanyId(companyId);

      // TODO use a DTO that is more clean, dont send them the id´s for company nor this entity.

      return ResponseEntity.ok(fisheryActivity);
    }catch (Exception e){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("An error occurred while fetching Fishery Activities"));
    }
  }


  @PostMapping("/create")
  public ResponseEntity<?> addFisheryActivity(
          @RequestBody CreateFisheryActivity createFisheryActivity,
          Authentication authentication){

    try {
      Integer companyId = (Integer) authentication.getPrincipal();

      Company company = companyService.getCompanyById(companyId.longValue());

      //TODO Use a builder
      FisheryActivity fa = new FisheryActivity();
      fa.setCompany(company);
      fa.setSetupDateTime(createFisheryActivity.setupDateTime());
      fa.setToolTypeCode(createFisheryActivity.toolTypeCode());
      fa.setToolTypeName(createFisheryActivity.toolTypeName());
      fa.setToolId(createFisheryActivity.toolId());
      fa.setLastChangedDateTime(createFisheryActivity.lastChangedDateTime());
      fa.setStartingPointLat(createFisheryActivity.startingPointLat());
      fa.setStartingPointLon(createFisheryActivity.startingPointLon());
      fa.setLength(createFisheryActivity.length());
      fa.setGeometry(createFisheryActivity.geometry());

      fisheryActivityService.createFisheryActivity(fa);

      return ResponseEntity.ok(fa);

    } catch (CompanyNotFoundException e){
      log.error("Valid token has an invalid company id.");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(new ErrorResponse("An error occurred, no company with that id"));
    } catch (Exception e){
      log.error("Error creating Fishery Activity");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(new ErrorResponse("An error occurred while creating the company"));
    }
  }

}
