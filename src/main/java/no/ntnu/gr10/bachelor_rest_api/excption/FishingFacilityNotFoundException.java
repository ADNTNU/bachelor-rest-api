package no.ntnu.gr10.bachelor_rest_api.excption;

import jakarta.persistence.EntityNotFoundException;

public class FishingFacilityNotFoundException extends EntityNotFoundException {
  public FishingFacilityNotFoundException(String message) {
    super(message);
  }
}
