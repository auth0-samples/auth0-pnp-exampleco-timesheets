package com.auth0.samples;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ej on 7/9/17.
 */

public class TimeSheetActivity extends Activity {

    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    private static final String API_URL = "http://10.0.2.2:8080/timesheets";

    private String accessToken;

    private static final String TAG = TimeSheetActivity.class.getSimpleName();

    private ArrayList<TimeSheet> timesheets = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timesheet_activity);
        accessToken = getIntent().getStringExtra("token");

        Button newTimeSheetButton = (Button) findViewById(R.id.newTimeSheetButton);

        newTimeSheetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoNewTimeSheetForm(accessToken);
            }
        });

        callAPI();
    }

    private void gotoNewTimeSheetForm(String token) {
        Intent intent = new Intent(TimeSheetActivity.this, Form.class);
        intent.putExtra("token", token);
        TimeSheetActivity.this.startActivity(intent);
    }

    private void callAPI() {

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
}
