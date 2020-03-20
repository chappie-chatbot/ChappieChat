package com.ehg.hackdays.chappie.chappiechat.ui.login;

import androidx.annotation.Nullable;

/**
 * Data validation state of the login form.
 */
class LoginFormState {

  @Nullable
  private Integer providerNumberError;
  private boolean isDataValid;

  LoginFormState(@Nullable Integer providerNumberError) {
    this.providerNumberError = providerNumberError;
    this.isDataValid = false;
  }

  LoginFormState(boolean isDataValid) {
    this.providerNumberError = null;
    this.isDataValid = isDataValid;
  }

  @Nullable
  public Integer getProviderNumberError() {
    return providerNumberError;
  }

  boolean isDataValid() {
    return isDataValid;
  }
}
