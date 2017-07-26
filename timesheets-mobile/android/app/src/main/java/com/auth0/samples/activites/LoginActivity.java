package com.auth0.samples.activites;

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
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.jwt.JWT;
import com.auth0.android.provider.AuthCallback;
import com.auth0.android.provider.ResponseType;
import com.auth0.android.provider.WebAuthProvider;
import com.auth0.android.result.Credentials;
import com.auth0.samples.R;
import com.auth0.samples.models.User;
import com.auth0.samples.utils.CredentialsManager;
import com.auth0.samples.utils.UserProfileManager;

/**
 * Created by lbalmaceda on 5/10/17.
 */

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
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

        WebAuthProvider.init(auth0)
                .withScheme("demo")
                .withAudience("https://api.abcinc.com/timesheets")
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
                        CredentialsManager.saveCredentials(LoginActivity.this, credentials);
                        JWT jwt = new JWT(CredentialsManager.getCredentials(LoginActivity.this).getIdToken());

                        User user = new User(
                                jwt.getClaim("email").asString(),
                                jwt.getClaim("name").asString(),
                                jwt.getClaim("picture").asString()
                        );

                        UserProfileManager.saveUserInfo(LoginActivity.this, user);
                        startActivity(new Intent(LoginActivity.this, TimeSheetActivity.class));
                    }
                });
    }
}
