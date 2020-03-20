package com.ehg.hackdays.chappie.chappiechat.data;

import android.util.Log;
import com.ehg.hackdays.chappie.chappiechat.data.Result.Error;
import com.ehg.hackdays.chappie.chappiechat.data.Result.Success;
import com.ehg.hackdays.chappie.chappiechat.data.model.LoggedInUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import okhttp3.Authenticator;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.HttpUrl.Builder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource implements Authenticator {

  public Result<LoggedInUser> login(String providerNumber) {
    return getProviderInfo(providerNumber);
  }

  public void logout() {
    // TODO: revoke authentication
  }

  private Result<LoggedInUser> getProviderInfo(String providerNumber) {
    CompletableFuture<Result> resultCompletableFuture = CompletableFuture.supplyAsync(() -> {
      OkHttpClient client = new OkHttpClient();
      Builder urlBuilder
          = HttpUrl.parse("https://chappie.etherous.net/getProviderInfo").newBuilder();
      urlBuilder.addQueryParameter("providerNumber", providerNumber);
      Request request = new Request.Builder().url(urlBuilder.build()).build();
      Call call = client.newCall(request);
      try {
        Response response = call.execute();
        if (response.isSuccessful()) {
          ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
          LoggedInUser loggedInUser = objectMapper
              .readValue(response.body().bytes(), LoggedInUser.class);
          return new Success<>(loggedInUser);
        }
      } catch (Exception e) {
        Log.d("LoginError", "Error logging in for provider number ", e);
      }
      return new Error(
          new IOException("Error logging in for provider number " + providerNumber));
    }).thenApply(response -> response);

    CompletableFuture.allOf(resultCompletableFuture);

    try {
      return resultCompletableFuture.get();
    } catch (Exception e) {
      return new Error(
          new IOException("Error logging in for provider number " + providerNumber));
    }
  }

  @Override
  public Request authenticate(Route route, Response response) throws IOException {
    return null;
  }
}
