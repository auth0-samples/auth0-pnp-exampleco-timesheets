package com.auth0.samples.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.auth0.samples.R;
import com.squareup.okhttp.Callback;
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

public class FormActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_form);
        initializeMenu();
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
            if (mAccessToken != null) {
                postAPI(
                        editProjectName.getText().toString(),
                        calendar.getTime(),
                        editHours.getText().toString()
                );
            }
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
                .url(getString(R.string.api_timesheets))
                .addHeader("Authorization", "Bearer " + mAccessToken);

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
}
