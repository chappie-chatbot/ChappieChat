package com.ehg.hackdays.chappie.chappiechat.ui.chat;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ehg.hackdays.chappie.chappiechat.chat.MessageAdapter;
import com.ehg.hackdays.chappie.chappiechat.data.model.ResponseMessage;
import androidx.appcompat.app.AppCompatActivity;
import com.ehg.hackdays.chappie.chappiechat.R;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import tech.gusavila92.websocketclient.WebSocketClient;

public class ChatActivity extends AppCompatActivity {

  EditText userInput;
  RecyclerView recyclerView;
  List<ResponseMessage> responseMessages;
  MessageAdapter messageAdapter;
  WebSocketClient webSocketClient;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chat);
    userInput = findViewById(R.id.userInput);
    recyclerView = findViewById(R.id.conversation);
    responseMessages = new ArrayList<>();
    messageAdapter = new MessageAdapter(responseMessages, this);
    recyclerView
        .setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    recyclerView.setAdapter(messageAdapter);
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
  }

  private void addToResponseMessages(String message, boolean user){
    responseMessages.add(new ResponseMessage(message, user));
    messageAdapter.notifyDataSetChanged();
    if (!isLastVisible())
      recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
  }

}
