package com.ehg.hackdays.chappie.chappiechat.data.model;

import com.ehg.hackdays.chappie.chappiechat.data.model.server.AssignmentInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoggedInUser implements Serializable {

  private String providerNumber;
  private String firstName;
  private String lastName;
  List<AssignmentInfo> assignments;

  public String getDisplayName() {
    return lastName + "," + firstName;
  }

  public String getProviderNumber() {
    return providerNumber;
  }

  public void setProviderNumber(String providerNumber) {
    this.providerNumber = providerNumber;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public List<AssignmentInfo> getAssignments() {
    return assignments;
  }

  public void setAssignments(
      List<AssignmentInfo> assignments) {
    this.assignments = assignments;
  }
}
