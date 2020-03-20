package com.ehg.hackdays.chappie.chappiechat.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ehg.hackdays.chappie.chappiechat.R;
import com.ehg.hackdays.chappie.chappiechat.data.model.ResponseMessage;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.CustomViewHolder> {

  class CustomViewHolder extends RecyclerView.ViewHolder {
    TextView textView;
    public CustomViewHolder(@NonNull View itemView) {
      super(itemView);
      this.textView = itemView.findViewById(R.id.textMessage);
    }
  }

  List<ResponseMessage> responseMessages;
  Context context;

  public MessageAdapter(
      List<ResponseMessage> responseMessages, Context context) {
    this.responseMessages = responseMessages;
    this.context = context;
  }

  @Override
  public int getItemViewType(int position) {
    if(responseMessages.get(position).isUser()){
      return R.layout.user_message;
    }
    return R.layout.bot_message;
  }

  @NonNull
  @Override
  public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new CustomViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
    holder.textView.setText(responseMessages.get(position).getMessage());
  }

  @Override
  public int getItemCount() {
    return responseMessages.size();
  }
}
