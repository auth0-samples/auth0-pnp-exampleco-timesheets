package com.auth0.samples.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.auth0.samples.R;
import com.auth0.samples.utils.ImageTask;
import com.auth0.samples.utils.UserProfileManager;

/**
 * Created by ej on 7/13/17.
 */

public class UserActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initializeMenu();

        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvName.setText(UserProfileManager.getUserInfo(this).getName());
        tvEmail.setText(UserProfileManager.getUserInfo(this).getEmail());

        new ImageTask((ImageView) findViewById(R.id.ivPicture))
                .execute(UserProfileManager.getUserInfo(this).getPictureURL());

                UserProfileManager.getUserInfo(this).getName();
    }
}
