package no.ntnu.gr10.bachelor_rest_api.fisheryActivity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.gr10.bachelor_rest_api.dto.ErrorResponse;
import no.ntnu.gr10.bachelor_rest_api.excption.CompanyNotFoundException;
import no.ntnu.gr10.bachelor_rest_api.excption.FisheryActivityNotFoundException;
import no.ntnu.gr10.bachelor_rest_api.fisheryActivity.dto.CreateFisheryActivity;
import no.ntnu.gr10.bachelor_rest_api.fisheryActivity.dto.ResponseFisheryActivity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/fisheryActivities")
@Tag(name = "Fishery Activity", description = "APIs for managing fishery activities")
public class FisheryActivityController {

  private final FisheryActivityService fisheryActivityService;
  private static final Logger log = LoggerFactory.getLogger(FisheryActivityController.class);

  public FisheryActivityController(FisheryActivityService fisheryActivityService){
    this.fisheryActivityService = fisheryActivityService;
  }

  @Operation(summary = "Get all fishery activities",
          description = "Retrieve all fishery activities for the authenticated company.",
          security = @SecurityRequirement(name = "bearerAuth")
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "List of activities",
                  content = @Content(array = @ArraySchema(schema = @Schema(implementation = FisheryActivity.class)))),
          @ApiResponse(responseCode = "500", description = "Internal server error",
                  content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @GetMapping("/all")
  public ResponseEntity<?> getAllFisheryActivities(Authentication authentication){

    try {
      Integer companyId = (Integer) authentication.getPrincipal();

      List<ResponseFisheryActivity> responseFisheryActivities = fisheryActivityService
              .getAllFisheryActivitiesWithCompanyId(companyId)
              .stream()
              .map(ResponseFisheryActivity::fromEntity)
              .toList();

      return ResponseEntity.ok(responseFisheryActivities);
    }catch (Exception e){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("An error occurred while fetching Fishery Activities"));
    }
  }


  @Operation(summary = "Get fishery activity by ID",
          description = "Retrieve a single fishery activity by its ID for the authenticated company.",
          security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Found the activity",
                  content = @Content(schema = @Schema(implementation = FisheryActivity.class))),
          @ApiResponse(responseCode = "404", description = "Activity not found",
                  content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "Internal server error",
                  content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })


  @GetMapping("/{id}")
  public ResponseEntity<?> getById(
          @PathVariable
          @Schema(description = "ID of the fishery activity", example = "1") Long id,
          Authentication authentication) {
    try {
      Integer companyId = (Integer) authentication.getPrincipal();
      FisheryActivity fa = fisheryActivityService.getByIdAndCompanyId(id, companyId);

      return ResponseEntity.ok(ResponseFisheryActivity.fromEntity(fa));
    } catch (FisheryActivityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body(new ErrorResponse("FisheryActivity not found"));
    } catch (Exception e) {
      log.error("Error fetching FisheryActivity {}", id, e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(new ErrorResponse("An error occurred while fetching the FisheryActivity"));
    }
  }


  @Operation(summary = "Create a new fishery activity",
          description = "Create a fishery activity under the authenticated company.",
          security = @SecurityRequirement(name = "bearerAuth")
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Activity created",
                  content = @Content(schema = @Schema(implementation = FisheryActivity.class))),
          @ApiResponse(responseCode = "400", description = "Invalid request or company ID",
                  content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "Internal server error",
                  content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PreAuthorize("hasRole('2')")
  @PostMapping("/create")
  public ResponseEntity<?> addFisheryActivity(
          @RequestBody
          @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "CreateFisheryActivity payload") CreateFisheryActivity createFisheryActivity,
          Authentication authentication){

    try {
      Integer companyId = (Integer) authentication.getPrincipal();

      FisheryActivity fisheryActivity = fisheryActivityService.createFisheryActivity(createFisheryActivity, companyId);

      return ResponseEntity.ok(ResponseFisheryActivity.fromEntity(fisheryActivity));

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


  @Operation(summary = "Update an existing fishery activity",
          description = "Update a fishery activity by ID for the authenticated company.",
          security = @SecurityRequirement(name = "bearerAuth")
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Activity updated",
                  content = @Content(schema = @Schema(implementation = FisheryActivity.class))),
          @ApiResponse(responseCode = "404", description = "Activity not found",
                  content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(responseCode = "400", description = "Invalid request or company ID",
                  content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "Internal server error",
                  content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PutMapping("/{id}")
  @PreAuthorize("hasRole('2')")
  public ResponseEntity<?> updateFisheryActivity(
          @PathVariable Long id,
          @RequestBody CreateFisheryActivity cmd,
          Authentication authentication) {
    try {
      Integer companyId = (Integer) authentication.getPrincipal();
      FisheryActivity fisheryActivity = fisheryActivityService.updateForCompany(id, cmd, companyId);

      return ResponseEntity.ok(ResponseFisheryActivity.fromEntity(fisheryActivity));
    } catch (FisheryActivityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body(new ErrorResponse("FisheryActivity not found"));
    } catch (CompanyNotFoundException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body(new ErrorResponse("Invalid company ID"));
    } catch (Exception e) {
      log.error("Error updating FisheryActivity {}", id, e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(new ErrorResponse("An error occurred while updating the FisheryActivity"));
    }
  }

  @Operation(summary = "Delete a fishery activity",
          description = "Delete a fishery activity by ID for the authenticated company.",
          security = @SecurityRequirement(name = "bearerAuth")
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "204", description = "Activity deleted"),
          @ApiResponse(responseCode = "404", description = "Activity not found",
                  content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "Internal server error",
                  content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('2')")
  public ResponseEntity<?> deleteFisheryActivity(
          @PathVariable Long id,
          Authentication authentication) {
    try {
      Integer companyId = (Integer) authentication.getPrincipal();
      fisheryActivityService.deleteByIdAndCompanyId(id, companyId);

      return ResponseEntity.noContent().build();
    } catch (FisheryActivityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body(new ErrorResponse("FisheryActivity not found"));
    } catch (Exception e) {
      log.error("Error deleting FisheryActivity {}", id, e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(new ErrorResponse("An error occurred while deleting the FisheryActivity"));
    }
  }

}
