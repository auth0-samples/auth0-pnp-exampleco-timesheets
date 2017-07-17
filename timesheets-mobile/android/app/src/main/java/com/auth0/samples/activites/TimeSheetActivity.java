package com.auth0.samples.activites;

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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.callback.BaseCallback;
import com.auth0.android.management.UsersAPIClient;
import com.auth0.android.result.UserProfile;
import com.auth0.samples.R;
import com.auth0.samples.utils.CredentialsManager;
import com.auth0.samples.utils.TimeSheetAdapter;
import com.auth0.samples.models.TimeSheet;
import com.auth0.samples.utils.UserProfileManager;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ej on 7/9/17.
 */

public class TimeSheetActivity extends AppCompatActivity {

    private static final String API_URL = "http://10.0.2.2:8080/timesheets";

    private ArrayList<TimeSheet> timesheets = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timesheet_activity);
        Toolbar navToolbar = (Toolbar) findViewById(R.id.navToolbar);
        setSupportActionBar(navToolbar);

        Auth0 auth0 = new Auth0(getString(R.string.auth0_client_id), getString(R.string.auth0_domain));
        auth0.setOIDCConformant(true);

        UsersAPIClient usersClient = new UsersAPIClient(auth0, CredentialsManager.getCredentials(this).getIdToken());
        AuthenticationAPIClient authClient = new AuthenticationAPIClient(auth0);

        authClient.userInfo(CredentialsManager.getCredentials(this).getAccessToken())
                .start(new BaseCallback<UserProfile, AuthenticationException>() {

                    @Override
                    public void onSuccess(final UserProfile userInfo) {
                        UserProfileManager.saveUserInfo(TimeSheetActivity.this, userInfo);
                    }

                    @Override
                    public void onFailure(AuthenticationException error) {
                        Log.d("AuthClient: ", String.valueOf(error));
                    }
                });

        callAPI();
    }

    private void callAPI() {

        final Request.Builder reqBuilder = new Request.Builder()
                .get()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + CredentialsManager.getCredentials(this).getAccessToken());

        OkHttpClient client = new OkHttpClient();
        Request request = reqBuilder.build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("API", "Error: ", e);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(TimeSheetActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                timesheets = processResults(response);
                final TimeSheetAdapter adapter = new TimeSheetAdapter(TimeSheetActivity.this, timesheets);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()) {
                            ListView listView = (ListView) findViewById(R.id.timesheetList);
                            listView.setAdapter(adapter);
                            adapter.addAll(timesheets);
                        } else {
                            Toast.makeText(TimeSheetActivity.this, "API call failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private ArrayList<TimeSheet> processResults (Response response) {
        ArrayList<TimeSheet> timesheets = new ArrayList<>();
        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONArray timesheetJSONArray = new JSONArray(jsonData);
                for (int i = 0; i < timesheetJSONArray.length(); i++) {
                    JSONObject timesheetJSON = timesheetJSONArray.getJSONObject(i);
                    String userID = timesheetJSON.getString("user_id");
                    String projectName = timesheetJSON.getString("project");
                    String dateStr = timesheetJSON.getString("date");
                    Double hours = timesheetJSON.getDouble("hours");
                    int id = timesheetJSON.getInt("id");

                    TimeSheet timesheet = new TimeSheet(userID, projectName, dateStr, hours, id);
                    timesheets.add(timesheet);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return timesheets;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.timesheet_action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new:
                startActivity(new Intent(TimeSheetActivity.this, FormActivity.class));
                break;
            case R.id.action_profile:
                startActivity(new Intent(TimeSheetActivity.this, UserActivity.class));
                break;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
        return true;
    }
}
