package com.auth0.samples.activites;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.auth0.samples.R;
import com.auth0.samples.utils.ImageTask;
import com.auth0.samples.utils.UserProfileManager;

/**
 * Created by ej on 7/13/17.
 */

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);
        Toolbar navToolbar = (Toolbar) findViewById(R.id.navToolbar);
        setSupportActionBar(navToolbar);

        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvEmail = (TextView) findViewById(R.id.tvEmail);

        tvName.setText(UserProfileManager.getUserInfo(this).getName());
        tvEmail.setText(UserProfileManager.getUserInfo(this).getEmail());

        new ImageTask((ImageView) findViewById(R.id.ivPicture))
                .execute(UserProfileManager.getUserInfo(this).getPictureURL());

                UserProfileManager.getUserInfo(this).getName();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new:
                startActivity(new Intent(UserActivity.this, FormActivity.class));
                break;
            case R.id.action_view:
                startActivity(new Intent(UserActivity.this, TimeSheetActivity.class));
                break;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
        return true;
    }

}
