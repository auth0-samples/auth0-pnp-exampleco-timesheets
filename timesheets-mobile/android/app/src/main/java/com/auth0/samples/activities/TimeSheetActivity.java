package com.auth0.samples.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.auth0.samples.R;
import com.auth0.samples.utils.TimeSheetAdapter;

/**
 * Created by ej on 7/9/17.
 */

public class TimeSheetActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_timesheet);
        initializeMenu();

        mRecyclerView = (RecyclerView) findViewById(R.id.timesheetList);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new TimeSheetAdapter(this, timesheets, R.layout.item_timesheet);
        mRecyclerView.setAdapter(mAdapter);

        if (mAccessToken != null) {
            callAPI(getString(R.string.api_timesheets));
        }
    }
}
