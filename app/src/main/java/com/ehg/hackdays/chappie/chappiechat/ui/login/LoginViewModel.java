package com.ehg.hackdays.chappie.chappiechat.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.util.Patterns;

import com.ehg.hackdays.chappie.chappiechat.data.LoginRepository;
import com.ehg.hackdays.chappie.chappiechat.data.Result;
import com.ehg.hackdays.chappie.chappiechat.data.model.LoggedInUser;
import com.ehg.hackdays.chappie.chappiechat.R;
import org.apache.commons.lang3.StringUtils;

public class LoginViewModel extends ViewModel {

  private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
  private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
  private LoginRepository loginRepository;

  LoginViewModel(LoginRepository loginRepository) {
    this.loginRepository = loginRepository;
  }

  LiveData<LoginFormState> getLoginFormState() {
    return loginFormState;
  }

  LiveData<LoginResult> getLoginResult() {
    return loginResult;
  }

  public void login(Result result){
    if (result instanceof Result.Success) {
      LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
      loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName(), data)));
    } else {
      loginResult.setValue(new LoginResult(R.string.login_failed));
    }
  }

  public void login(String providerNumber) {
    // can be launched in a separate asynchronous job
    Result<LoggedInUser> result = loginRepository.login(providerNumber);
    login(result);
  }

  public void loginDataChanged(String providerNumber) {
    if (!isProviderNumberValid(providerNumber)) {
      loginFormState.setValue(new LoginFormState(R.string.invalid_providerNumber));
    } else {
      loginFormState.setValue(new LoginFormState(true));
    }
  }

  // A placeholder password validation check
  private boolean isProviderNumberValid(String providerNumber) {
    return providerNumber != null && providerNumber.trim().length() > 5 && StringUtils
        .isNumeric(providerNumber);
  }
}
