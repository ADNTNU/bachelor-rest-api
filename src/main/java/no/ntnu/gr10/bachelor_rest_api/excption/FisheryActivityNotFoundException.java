package no.ntnu.gr10.bachelor_rest_api.excption;

import jakarta.persistence.EntityNotFoundException;

public class FisheryActivityNotFoundException extends EntityNotFoundException {
  public FisheryActivityNotFoundException(String message) {
    super(message);
  }
}