package com.ehg.hackdays.chappie.chappiechat.data.model;

public class ResponseMessage {

  String message;
  boolean user;

  public ResponseMessage(String message, boolean user) {
    this.message = message;
    this.user = user;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public boolean isUser() {
    return user;
  }

  public void setUser(boolean user) {
    this.user = user;
  }
}
