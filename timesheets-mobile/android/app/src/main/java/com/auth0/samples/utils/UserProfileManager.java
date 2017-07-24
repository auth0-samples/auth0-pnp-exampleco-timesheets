package com.auth0.samples.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.auth0.android.result.UserProfile;
import com.auth0.samples.models.User;

/**
 * Created by ej on 7/13/17.
 */

public class UserProfileManager {

    private static final String PREFERENCES_NAME = "auth0_user_profile";
    private static final String ID = "id";
    private static final String EMAIL = "email";
    private static final String NAME = "name";
    private static final String PICTURE_URL = "picture_url";

    public static void saveUserInfo(Context context, UserProfile userInfo) {
        SharedPreferences sp = context.getSharedPreferences(
                PREFERENCES_NAME, Context.MODE_PRIVATE);

        sp.edit()
                .putString(ID, userInfo.getId())
                .putString(EMAIL, userInfo.getEmail())
                .putString(NAME, userInfo.getName())
                .putString(PICTURE_URL, userInfo.getPictureURL())
                .apply();
    }

    public static User getUserInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences(
                PREFERENCES_NAME, Context.MODE_PRIVATE);

        return new User(
                sp.getString(ID, null),
                sp.getString(EMAIL, null),
                sp.getString(NAME, null),
                sp.getString(PICTURE_URL, null)
        );
    }

    public static void deleteUserInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences(
                PREFERENCES_NAME, Context.MODE_PRIVATE);

        sp.edit()
                .putString(ID, null)
                .putString(EMAIL, null)
                .putString(NAME, null)
                .putString(PICTURE_URL, null)
                .apply();
    }


}
