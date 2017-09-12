package com.auth0.samples.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.auth0.samples.R;
import com.auth0.samples.utils.TimeSheetAdapter;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by ej on 9/10/17.
 */

public class ApproveActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve);
        initializeMenu();

        mRecyclerView = (RecyclerView) findViewById(R.id.approveList);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new TimeSheetAdapter(this, timesheets, R.layout.item_timesheet_approve);
        mRecyclerView.setAdapter(mAdapter);

        if (mAccessToken != null) {
            callAPI(getString(R.string.api_approvals));
        }
    }

    public void approveTimeSheet (String id, final int position) {
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, "");
        final Request.Builder reqBuilder = new Request.Builder()
                .put(body)
                .url(getString(R.string.api_approvals) + "/" + id)
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
                        Toast.makeText(ApproveActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()) {
                            removeEntry(position);
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

    public void removeEntry (final int position) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                timesheets.remove(position);
                mAdapter.notifyItemRemoved(position);
                mAdapter.notifyItemRangeChanged(position, timesheets.size());
            }
        });
    }
}
