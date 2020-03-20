package com.ehg.hackdays.chappie.chappiechat.data.model.server;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageList {

  Status status;
  @JsonProperty("items")
  List<Message> messages;

  public MessageList() {
  }

  public MessageList(
      List<Message> messages) {
    this.messages = messages;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public List<Message> getMessages() {
    return messages;
  }

  public void setMessages(
      List<Message> messages) {
    this.messages = messages;
  }
}
