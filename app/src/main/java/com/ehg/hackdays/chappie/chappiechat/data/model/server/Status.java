package com.ehg.hackdays.chappie.chappiechat.data.model.server;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Status {

  boolean success;
  Object error;

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public Object getError() {
    return error;
  }

  public void setError(Object error) {
    this.error = error;
  }
}
