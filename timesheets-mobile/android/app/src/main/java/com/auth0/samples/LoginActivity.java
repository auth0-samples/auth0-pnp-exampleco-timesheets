package com.auth0.samples;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.provider.AuthCallback;
import com.auth0.android.provider.AuthProvider;
import com.auth0.android.provider.ResponseType;
import com.auth0.android.provider.WebAuthProvider;
import com.auth0.android.result.Credentials;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import okio.BufferedSink;

/**
 * Created by lbalmaceda on 5/10/17.
 */

public class LoginActivity extends Activity {

    private static final String API_URL = "http://10.0.2.2:8080/timesheets";

    private String accessToken;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        Button callAPIWithTokenButton = (Button) findViewById(R.id.callAPIWithTokenButton);
        Button callAPIWithoutTokenButton = (Button) findViewById(R.id.callAPIWithoutTokenButton);
        Button loginWithTokenButton = (Button) findViewById(R.id.loginButton);
        callAPIWithTokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callAPI(true);
            }
        });
        callAPIWithoutTokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callAPI(false);
            }
        });
        loginWithTokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void callAPI(boolean sendToken) {

        final Request.Builder reqBuilder = new Request.Builder()
                .get()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + accessToken);

        OkHttpClient client = new OkHttpClient();
        Request request = reqBuilder.build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("API", "Error: ", e);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                final String resBody = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "API call success!", Toast.LENGTH_SHORT).show();
                            Log.d("Respone Body", resBody);
                        } else {
                            Toast.makeText(LoginActivity.this, "API call failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (WebAuthProvider.resume(intent)) {
            return;
        }
        super.onNewIntent(intent);
    }

    private void login() {
        Auth0 auth0 = new Auth0(getString(R.string.auth0_client_id), getString(R.string.auth0_domain));
        auth0.setOIDCConformant(true);

        WebAuthProvider.init(auth0)
                .withScheme("demo")
                .withAudience("https://api.abcinc.com/timesheets")
                .withResponseType(ResponseType.CODE)
                .withScope("create:timesheets read:timesheets")
                .start(LoginActivity.this, new AuthCallback() {
                    @Override
                    public void onFailure(@NonNull final Dialog dialog) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(final AuthenticationException exception) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "Error: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onSuccess(@NonNull final Credentials credentials) {
                        accessToken = credentials.getAccessToken();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "Log in: Success", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }
}
