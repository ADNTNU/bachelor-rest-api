package no.ntnu.gr10.bachelor_rest_api.excption;

import jakarta.persistence.EntityNotFoundException;

@SuppressWarnings("squid:S110")
public class CompanyNotFoundException extends EntityNotFoundException {
  public CompanyNotFoundException(String message) {
    super(message);
  }
}