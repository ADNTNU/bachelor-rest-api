package no.ntnu.gr10.bachelor_rest_api.administrator;

import jakarta.persistence.*;
import no.ntnu.gr10.bachelor_rest_api.company.Company;

@Entity
@Table(name = "administrator_company")
public class AdministratorCompany {

  @EmbeddedId
  private AdministratorCompanyId id = new AdministratorCompanyId();

  @Column
  private boolean enabled = true;

  @Column
  private boolean accepted = false;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("administratorId")
  private Administrator administrator;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("companyId")
  private Company company;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private AdministratorRole role;

  public AdministratorCompany() {
    // Default constructor for JPA
  }

  public AdministratorCompanyId getId() {
    return id;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public boolean isAccepted() {
    return accepted;
  }

  public void setAccepted(boolean accepted) {
    this.accepted = accepted;
  }

  public Administrator getAdministrator() {
    return administrator;
  }

  public void setAdministrator(Administrator administrator) {
    this.administrator = administrator;
  }

  public Company getCompany() {
    return company;
  }

  public void setCompany(Company company) {
    this.company = company;
  }

  public AdministratorRole getRole() {
    return role;
  }

  public void setRole(AdministratorRole role) {
    this.role = role;
  }
}
