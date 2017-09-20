package com.auth0.samples.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.authentication.storage.CredentialsManager;
import com.auth0.android.authentication.storage.CredentialsManagerException;
import com.auth0.android.authentication.storage.SharedPreferencesStorage;
import com.auth0.android.callback.BaseCallback;
import com.auth0.android.jwt.JWT;
import com.auth0.android.provider.AuthCallback;
import com.auth0.android.provider.ResponseType;
import com.auth0.android.provider.WebAuthProvider;
import com.auth0.android.result.Credentials;
import com.auth0.samples.R;
import com.auth0.samples.models.User;
import com.auth0.samples.utils.UserProfileManager;

/**
 * Created by lbalmaceda on 5/10/17.
 */

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginWithTokenButton = (Button) findViewById(R.id.loginButton);
        loginWithTokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
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

        AuthenticationAPIClient authAPIClient = new AuthenticationAPIClient(auth0);
        SharedPreferencesStorage sharedPrefStorage = new SharedPreferencesStorage(this);
        final CredentialsManager credentialsManager = new CredentialsManager(authAPIClient, sharedPrefStorage);

        WebAuthProvider.init(auth0)
                .withScheme("demo")
                .withAudience("https://api.exampleco.com/timesheets")
                .withResponseType(ResponseType.CODE)
                .withScope("create:timesheets read:timesheets openid profile email offline_access")
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
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "Log In - Success", Toast.LENGTH_SHORT).show();
                            }
                        });

                        credentialsManager.saveCredentials(credentials);
                        JWT jwt = new JWT(credentials.getIdToken());
                        String scopes = credentials.getScope();
                        User user = new User(
                                jwt.getClaim("email").asString(),
                                jwt.getClaim("name").asString(),
                                jwt.getClaim("picture").asString(),
                                credentials.getScope()
                        );
                        UserProfileManager.saveUserInfo(LoginActivity.this, user);

                        startActivity(new Intent(LoginActivity.this, TimeSheetActivity.class));
                    }
                });
    }
}
