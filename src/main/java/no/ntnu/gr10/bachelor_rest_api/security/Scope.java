package no.ntnu.gr10.bachelor_rest_api.security;

import org.springframework.security.core.GrantedAuthority;

// TODO Experiment if this can be used for the hard coded Scopes keys.
public enum Scope implements GrantedAuthority {
  FISHERY,
  CREATE_FISHERY;


  @Override
  public String getAuthority() {
    // Prefixing with SCOPE_ is conventional for OAuth2ResourceServer
    return "SCOPE_" + name();
  }

  public static Scope fromTokenValue(String tokenVal) {
    return Scope.valueOf(tokenVal);
  }
}
