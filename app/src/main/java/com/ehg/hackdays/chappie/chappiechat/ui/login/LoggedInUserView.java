package com.ehg.hackdays.chappie.chappiechat.ui.login;

import com.ehg.hackdays.chappie.chappiechat.data.model.LoggedInUser;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {

  private String displayName;
  private LoggedInUser loggedInUser;

  public LoggedInUserView(String displayName,
      LoggedInUser loggedInUser) {
    this.displayName = displayName;
    this.loggedInUser = loggedInUser;
  }

  public String getDisplayName() {
    return displayName;
  }

  public LoggedInUser getLoggedInUser() {
    return loggedInUser;
  }
}
