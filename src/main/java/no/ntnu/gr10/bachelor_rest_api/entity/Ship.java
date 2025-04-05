package no.ntnu.gr10.bachelor_rest_api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "shipDetails")
public class Ship {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String vesselName;
  private String vesselType;
  private String Charterer;
  private String workRole;
  private String port;

  public Ship() {

  }

  public Long getId() {
    return id;
  }

  public Ship(String vesselName, String vesselType, String charterer, String workRole, String port) {
    this.vesselName = vesselName;
    this.vesselType = vesselType;
    Charterer = charterer;
    this.workRole = workRole;
    this.port = port;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getVesselName() {
    return vesselName;
  }

  public void setVesselName(String vesselName) {
    this.vesselName = vesselName;
  }

  public String getVesselType() {
    return vesselType;
  }

  public void setVesselType(String vesselType) {
    this.vesselType = vesselType;
  }

  public String getCharterer() {
    return Charterer;
  }

  public void setCharterer(String charterer) {
    Charterer = charterer;
  }

  public String getWorkRole() {
    return workRole;
  }

  public void setWorkRole(String workRole) {
    this.workRole = workRole;
  }

  public String getPort() {
    return port;
  }

  public void setPort(String port) {
    this.port = port;
  }
}
