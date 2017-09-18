package com.auth0.samples.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.storage.CredentialsManager;
import com.auth0.android.authentication.storage.CredentialsManagerException;
import com.auth0.android.authentication.storage.SharedPreferencesStorage;
import com.auth0.android.callback.BaseCallback;
import com.auth0.android.result.Credentials;
import com.auth0.samples.R;
import com.auth0.samples.models.TimeSheet;
import com.auth0.samples.utils.UserProfileManager;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ej on 9/9/17.
 */

public class BaseActivity extends AppCompatActivity {
    protected static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    protected String mAccessToken;
    protected ArrayList<TimeSheet> timesheets = new ArrayList<>();
    protected RecyclerView mRecyclerView;
    protected RecyclerView.Adapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Auth0 auth0 = new Auth0(getString(R.string.auth0_client_id), getString(R.string.auth0_domain));
        auth0.setOIDCConformant(true);
        AuthenticationAPIClient authAPIClient = new AuthenticationAPIClient(auth0);
        SharedPreferencesStorage sharedPrefStorage = new SharedPreferencesStorage(this);

        CredentialsManager credentialsManager = new CredentialsManager(authAPIClient, sharedPrefStorage);
        credentialsManager.getCredentials(new BaseCallback<Credentials, CredentialsManagerException>() {
            @Override
            public void onSuccess(Credentials payload) {
                mAccessToken = payload.getAccessToken();
            }

            @Override
            public void onFailure(CredentialsManagerException error) {
                Toast.makeText(BaseActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void callAPI(String url) {
        final Request.Builder reqBuilder = new Request.Builder()
                .get()
                .url(url)
                .addHeader("Authorization", "Bearer " + mAccessToken);

        OkHttpClient client = new OkHttpClient();
        Request request = reqBuilder.build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("API", "Error: ", e);
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()) {
                            processResults(response);
                        } else {
                            try {
                                throw new IOException("Unexpected code " + response);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
                thread.run();
            }
        });
    }

    protected void processResults (Response response) {
        try {
            String jsonData = response.body().string();
            JSONArray timesheetJSONArray = new JSONArray(jsonData);
            for (int i = 0; i < timesheetJSONArray.length(); i++) {
                JSONObject timesheetJSON = timesheetJSONArray.getJSONObject(i);
                String userID = timesheetJSON.getString("user_id");
                String projectName = timesheetJSON.getString("project");
                String dateStr = timesheetJSON.getString("date");
                Double hours = timesheetJSON.getDouble("hours");
                Boolean approved = timesheetJSON.getBoolean("approved");
                String id = timesheetJSON.getString("id");

                TimeSheet timesheet = new TimeSheet(userID, projectName, dateStr, hours, approved, id);
                timesheets.add(timesheet);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    protected void initializeMenu() {
        Toolbar menuToolbar = (Toolbar) findViewById(R.id.menuToolbar);
        setSupportActionBar(menuToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Boolean canApprove = UserProfileManager.getUserInfo(this).hasScope("approve:timesheets");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actions, menu);
        MenuItem item = menu.findItem(R.id.action_approve);
        item.setVisible(canApprove);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new:
                startActivity(new Intent(BaseActivity.this, FormActivity.class));
                break;
            case R.id.action_view:
                startActivity(new Intent(BaseActivity.this, TimeSheetActivity.class));
                break;
            case R.id.action_approve:
                startActivity(new Intent(BaseActivity.this, ApproveActivity.class));
                break;
            case R.id.action_profile:
                startActivity(new Intent(BaseActivity.this, UserActivity.class));
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
