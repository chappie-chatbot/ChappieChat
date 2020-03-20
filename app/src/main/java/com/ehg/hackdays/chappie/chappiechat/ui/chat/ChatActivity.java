package com.ehg.hackdays.chappie.chappiechat.ui.chat;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ehg.hackdays.chappie.chappiechat.R;
import com.ehg.hackdays.chappie.chappiechat.chat.MessageAdapter;
import com.ehg.hackdays.chappie.chappiechat.data.model.ResponseMessage;
import com.ehg.hackdays.chappie.chappiechat.data.model.server.Message;
import com.ehg.hackdays.chappie.chappiechat.data.model.server.MessageList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.apache.commons.lang3.StringUtils;

public class ChatActivity extends AppCompatActivity {

  EditText userInput;
  RecyclerView recyclerView;
  List<ResponseMessage> responseMessages;
  MessageAdapter messageAdapter;
  private String displayName;
  private String providerNumber;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle extras = getIntent().getExtras();
    this.displayName = extras.getString("displayName");
    this.providerNumber = extras.getString("providerNumber");
    if (StringUtils.isNotBlank(displayName)) {
      setTitle("Welcome " + displayName);
    }
    setContentView(R.layout.activity_chat);
    userInput = findViewById(R.id.userInput);
    recyclerView = findViewById(R.id.conversation);
    responseMessages = new ArrayList<>();
    messageAdapter = new MessageAdapter(responseMessages, this);
    recyclerView
        .setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    recyclerView.setAdapter(messageAdapter);
    sendMessage(new Message(providerNumber, "Who are you?"));
    userInput.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (!isLastVisible()) {
          recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
        }
      }
    });
    userInput.setOnEditorActionListener(new OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEND) {
          sendMessage(userInput.getText().toString());
          userInput.getText().clear();
        }
        return false;
      }
    });
  }

  boolean isLastVisible() {
    LinearLayoutManager layoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
    int pos = layoutManager.findLastCompletelyVisibleItemPosition();
    int numItems = recyclerView.getAdapter().getItemCount();
    return (pos >= numItems);
  }

  public void sendMessage(String message) {
    addToResponseMessages(message, true);
    sendMessage(new Message(providerNumber, message));
    if (!isLastVisible()) {
      recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
    }
  }

  private void addToResponseMessages(String message, boolean user) {
    responseMessages.add(new ResponseMessage(message, user));
    messageAdapter.notifyDataSetChanged();
    if (!isLastVisible()) {
      recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
    }
  }

  private void sendMessage(Message message) {
    try {
      OkHttpClient client = new OkHttpClient();
      ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
      RequestBody body = RequestBody.create(
          MediaType.parse("application/json"), objectMapper.writeValueAsString(new MessageList(
              Arrays.asList(message))));
      Request request = new Request.Builder()
          .url("https://chappie.etherous.net/chat")
          .post(body)
          .build();
      client.newCall(request).enqueue(new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
          Log.e("SendingMessage", "Unable to send message", e);
          addToResponseMessages("Unable to send message", true);
        }

        @Override
        public void onResponse(Call call, okhttp3.Response response) throws IOException {
          if (response.isSuccessful()) {
            final String myResponse = response.body().string();
            System.out.println("Got data " + myResponse);
            ChatActivity.this.runOnUiThread(new Runnable() {
              @Override
              public void run() {
                ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
                try {
                  MessageList messageList = objectMapper
                      .readValue(myResponse, MessageList.class);
                  responseMessages.add(new ResponseMessage(messageList.getMessages().get(0).getText(), false));
                  messageAdapter.notifyDataSetChanged();
                  if (!isLastVisible()) {
                    recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
                  }
                } catch (Exception e) {
                  Log.e("SendingMessage", "Unable to send message", e);
                  addToResponseMessages("Unable to send message", true);
                }
              }
            });
          } else {
            Log.e("SendingMessage", "Unable to send message. Response code "+response.code());
            addToResponseMessages("Unable to send message", true);
          }
        }
      });
    } catch (Exception e) {
      Log.e("SendingMessage", "Unable to send message", e);
      addToResponseMessages("Unable to send message", true);
    }
  }

  private void getAllMessages() {
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder().url("https://chappie.etherous.net/message?topic=chat")
        .build();

    client.newCall(request).enqueue(new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {
        e.printStackTrace();
      }

      @Override
      public void onResponse(Call call, okhttp3.Response response) throws IOException {
        if (response.isSuccessful()) {
          final String myResponse = response.body().string();
          System.out.println("Got data " + myResponse);
          ChatActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
              ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
              try {
                MessageList res = objectMapper.readValue(myResponse, MessageList.class);
                List<ResponseMessage> messages = res.getMessages().stream().map(Message::getText)
                    .map(s -> new ResponseMessage(s, false)).collect(
                        Collectors.toList());
                responseMessages.addAll(messages);
                messageAdapter.notifyDataSetChanged();
              } catch (JsonProcessingException e) {
                e.printStackTrace();
              }
            }
          });
        } else {
          System.out.println("Error getting data");
        }
      }
    });
  }
}
