package com.ehg.hackdays.chappie.chappiechat.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.ehg.hackdays.chappie.chappiechat.R;
import com.ehg.hackdays.chappie.chappiechat.ui.chat.ChatActivity;

public class LoginActivity extends AppCompatActivity {

  private LoginViewModel loginViewModel;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
        .get(LoginViewModel.class);
    final EditText providerNumberEditText = findViewById(R.id.providerNumber);
    final Button loginButton = findViewById(R.id.login);
    final ProgressBar loadingProgressBar = findViewById(R.id.loading);
    setTitle("Chat with Chappie from CHG");
    loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
      @Override
      public void onChanged(@Nullable LoginFormState loginFormState) {
        if (loginFormState == null) {
          return;
        }
        loginButton.setEnabled(loginFormState.isDataValid());
        if (loginFormState.getProviderNumberError() != null) {
          providerNumberEditText.setError(getString(loginFormState.getProviderNumberError()));
        }
      }
    });

    loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
      @Override
      public void onChanged(@Nullable LoginResult loginResult) {
        if (loginResult == null) {
          return;
        }
        loadingProgressBar.setVisibility(View.GONE);
        if (loginResult.getError() != null) {
          showLoginFailed(loginResult.getError());
        }
        if (loginResult.getSuccess() != null) {
          updateUiWithUser(loginResult.getSuccess());
        }
        setResult(Activity.RESULT_OK);

        //Complete and destroy login activity once successful
        finish();
      }
    });

    TextWatcher afterTextChangedListener = new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // ignore
      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        // ignore
      }

      @Override
      public void afterTextChanged(Editable s) {
        loginViewModel.loginDataChanged(providerNumberEditText.getText().toString());
      }
    };
    providerNumberEditText.addTextChangedListener(afterTextChangedListener);
    providerNumberEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
          loginViewModel.login(providerNumberEditText.getText().toString());
        }
        return false;
      }
    });

    loginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        loadingProgressBar.setVisibility(View.VISIBLE);
        loginViewModel.login(providerNumberEditText.getText().toString());
      }
    });
  }

  private void updateUiWithUser(LoggedInUserView model) {
    Intent intent = new Intent(this, ChatActivity.class);
    Bundle bundle = new Bundle();
    bundle.putString("providerNumber", model.getLoggedInUser().getProviderNumber());
    bundle.putString("displayName", model.getDisplayName());
    intent.putExtras(bundle);
    startActivity(intent);
  }

  private void showLoginFailed(@StringRes Integer errorString) {
    Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_LONG).show();

  }
}
