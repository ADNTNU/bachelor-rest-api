package no.ntnu.gr10.bachelor_rest_api.fishingFacilitty;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.gr10.bachelor_rest_api.dto.ErrorResponse;
import no.ntnu.gr10.bachelor_rest_api.excption.FishingFacilityNotFoundException;
import no.ntnu.gr10.bachelor_rest_api.fisheryActivity.FisheryActivity;
import no.ntnu.gr10.bachelor_rest_api.fishingFacilitty.dto.ResponseFishingFacility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/fishingFacilities")
@Tag(name = "Fishing Facility", description = "APIs for getting fishing facilities")
public class FishingFacilityController {

  private final FishingFacilityService fishingFacilityService;

  private static final Logger log = LoggerFactory.getLogger(FishingFacilityController.class);

  public FishingFacilityController(FishingFacilityService fishingFacilityService){
    this.fishingFacilityService = fishingFacilityService;
  }

  @Operation(summary = "Get all fishing facilities",
          description = "Retrieve all fishing facilities for the authenticated company.",
          security = @SecurityRequirement(name = "bearerAuth")
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "List of facilities",
                  content = @Content(array = @ArraySchema(schema = @Schema(implementation = FisheryActivity.class)))),
          @ApiResponse(responseCode = "500", description = "Internal server error",
                  content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @GetMapping()
  public ResponseEntity<?> getAllFishingFacilities(Authentication authentication){

    try {
      Integer companyId = (Integer) authentication.getPrincipal();

      List<ResponseFishingFacility> responseFishingFacilities = fishingFacilityService
              .getAllFishingFacilitiesWithCompanyId(companyId)
              .stream()
              .map(ResponseFishingFacility::fromEntity)
              .toList();

      return ResponseEntity.ok(responseFishingFacilities);
    }catch (Exception e){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("An error occurred while fetching Fishery Activities"));
    }
  }

  @Operation(summary = "Get fishing facility by ID",
          description = "Retrieve a single fishing facility by its ID for the authenticated company.",
          security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Found the facility",
                  content = @Content(schema = @Schema(implementation = FisheryActivity.class))),
          @ApiResponse(responseCode = "404", description = "Facility not found",
                  content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "Internal server error",
                  content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @GetMapping("/{id}")
  public ResponseEntity<?> getById(
          @PathVariable
          @Schema(description = "ID of the fishing facility", example = "1") Long id,
          Authentication authentication) {
    try {
      Integer companyId = (Integer) authentication.getPrincipal();
      FishingFacility fa = fishingFacilityService.getByIdAndCompanyId(id, companyId);

      return ResponseEntity.ok(ResponseFishingFacility.fromEntity(fa));
    } catch (FishingFacilityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body(new ErrorResponse("FishingFacility not found"));
    } catch (Exception e) {
      log.error("Error fetching FishingFacility {}", id, e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(new ErrorResponse("An error occurred while fetching the FisheryActivity"));
    }
  }

}
