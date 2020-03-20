package com.ehg.hackdays.chappie.chappiechat.data.model.server;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AssignmentInfo {

  private String clientNumber;
  private String clientName;
  private String contractingNumber;
  private String contractingName;
  private String providerNumber;
  private String providerName;
  private String worksiteNumber;
  private String worksiteName;
  private String worksiteCity;
  private String worksiteState;
  @JsonFormat(
      shape = Shape.STRING,
      pattern = "yyyy-MM-dd"
  )
  private LocalDate startDate;
  @JsonFormat(
      shape = Shape.STRING,
      pattern = "yyyy-MM-dd"
  )
  private LocalDate endDate;
  private String division;
  private String specialty;
  private String costCenter;
  private String assignmentDates;

  public String getClientNumber() {
    return clientNumber;
  }

  public void setClientNumber(String clientNumber) {
    this.clientNumber = clientNumber;
  }

  public String getClientName() {
    return clientName;
  }

  public void setClientName(String clientName) {
    this.clientName = clientName;
  }

  public String getContractingNumber() {
    return contractingNumber;
  }

  public void setContractingNumber(String contractingNumber) {
    this.contractingNumber = contractingNumber;
  }

  public String getContractingName() {
    return contractingName;
  }

  public void setContractingName(String contractingName) {
    this.contractingName = contractingName;
  }

  public String getProviderNumber() {
    return providerNumber;
  }

  public void setProviderNumber(String providerNumber) {
    this.providerNumber = providerNumber;
  }

  public String getProviderName() {
    return providerName;
  }

  public void setProviderName(String providerName) {
    this.providerName = providerName;
  }

  public String getWorksiteNumber() {
    return worksiteNumber;
  }

  public void setWorksiteNumber(String worksiteNumber) {
    this.worksiteNumber = worksiteNumber;
  }

  public String getWorksiteName() {
    return worksiteName;
  }

  public void setWorksiteName(String worksiteName) {
    this.worksiteName = worksiteName;
  }

  public String getWorksiteCity() {
    return worksiteCity;
  }

  public void setWorksiteCity(String worksiteCity) {
    this.worksiteCity = worksiteCity;
  }

  public String getWorksiteState() {
    return worksiteState;
  }

  public void setWorksiteState(String worksiteState) {
    this.worksiteState = worksiteState;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public String getDivision() {
    return division;
  }

  public void setDivision(String division) {
    this.division = division;
  }

  public String getSpecialty() {
    return specialty;
  }

  public void setSpecialty(String specialty) {
    this.specialty = specialty;
  }

  public String getCostCenter() {
    return costCenter;
  }

  public void setCostCenter(String costCenter) {
    this.costCenter = costCenter;
  }

  public String getAssignmentDates() {
    return assignmentDates;
  }

  public void setAssignmentDates(String assignmentDates) {
    this.assignmentDates = assignmentDates;
  }
}
