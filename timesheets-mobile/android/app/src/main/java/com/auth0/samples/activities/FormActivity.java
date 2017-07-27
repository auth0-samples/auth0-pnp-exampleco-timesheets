package com.auth0.samples.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.storage.CredentialsManager;
import com.auth0.android.authentication.storage.CredentialsManagerException;
import com.auth0.android.authentication.storage.SharedPreferencesStorage;
import com.auth0.android.callback.BaseCallback;
import com.auth0.android.result.Credentials;
import com.auth0.samples.R;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ej on 7/9/17.
 */

public class FormActivity extends AppCompatActivity {

    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    private String accessToken;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_activity);
        Toolbar navToolbar = (Toolbar) findViewById(R.id.navToolbar);
        setSupportActionBar(navToolbar);

        Auth0 auth0 = new Auth0(getString(R.string.auth0_client_id), getString(R.string.auth0_domain));
        auth0.setOIDCConformant(true);

        AuthenticationAPIClient authAPIClient = new AuthenticationAPIClient(auth0);
        SharedPreferencesStorage sharedPrefStorage = new SharedPreferencesStorage(this);

        CredentialsManager credentialsManager = new CredentialsManager(authAPIClient, sharedPrefStorage);
        credentialsManager.getCredentials(new BaseCallback<Credentials, CredentialsManagerException>() {
            @Override
            public void onSuccess(Credentials payload) {
                accessToken = payload.getAccessToken();
            }

            @Override
            public void onFailure(CredentialsManagerException error) {
                Toast.makeText(FormActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Button submitTimeSheetButton = (Button) findViewById(R.id.submitTimeSheetButton);
        final EditText editProjectName = (EditText) findViewById(R.id.editProjectName);
        final EditText editHours = (EditText) findViewById(R.id.editHours);
        final DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);

        submitTimeSheetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year =  datePicker.getYear();

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);

                postAPI(
                        editProjectName.getText().toString(),
                        calendar.getTime(),
                        editHours.getText().toString()
                );
            }
        });
    }

    private void postAPI(String projectName, Date date, String hours) {

        JSONObject postBody = new JSONObject();
        try {
            postBody.put("project", projectName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            postBody.put("date", date);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            postBody.put("hours", hours);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String postStr = postBody.toString();

        final Request.Builder reqBuilder = new Request.Builder()
                .post(RequestBody.create(MEDIA_TYPE_JSON, postStr))
                .url(getString(R.string.api_url))
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
                        Toast.makeText(FormActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
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
                            clearForm((ViewGroup) findViewById(R.id.mainForm));
                            Intent intent = new Intent(FormActivity.this, TimeSheetActivity.class);
                            FormActivity.this.startActivity(intent);
                        } else {
                            Toast.makeText(FormActivity.this, "Timesheet creation failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void clearForm(ViewGroup group) {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText)view).setText("");
            }

            if(view instanceof ViewGroup && (((ViewGroup)view).getChildCount() > 0))
                clearForm((ViewGroup)view);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.form_action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_view:
                startActivity(new Intent(FormActivity.this, TimeSheetActivity.class));
                break;
            case R.id.action_profile:
                startActivity(new Intent(FormActivity.this, UserActivity.class));
                break;
            default:
                return super.onOptionsItemSelected(item);

        }
        return true;
    }
}
