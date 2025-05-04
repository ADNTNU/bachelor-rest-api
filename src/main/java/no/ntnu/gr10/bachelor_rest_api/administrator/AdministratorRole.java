package no.ntnu.gr10.bachelor_rest_api.administrator;

import no.ntnu.gr10.bachelor_rest_api.excption.InvalidRoleException;

public enum AdministratorRole {
  OWNER,
  ADMINISTRATOR;

  public static AdministratorRole fromString(String value) throws InvalidRoleException {
    try {
      return AdministratorRole.valueOf(value.toUpperCase());
    } catch (IllegalArgumentException ex) {
      throw new InvalidRoleException("Invalid role: " + value);
    }
  }
}
