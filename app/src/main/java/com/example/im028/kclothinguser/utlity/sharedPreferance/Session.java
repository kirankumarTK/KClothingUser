package com.example.im028.kclothinguser.utlity.sharedPreferance;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by IM028 on 8/2/16.
 */
public class Session {
    private String TAG;
    private Context context;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private static String PREF_NAME = "kClothingUser";
    private static String ID = "ID";
    private static String user_email = "user_email";
    private static String first_name = "first_name";
    private static String last_name = "last_name";

    private static final String IS_LOGIN = "IsLoggedIn";


    private Session(Context context, String TAG) {
        this.context = context;
        this.TAG = "Session " + TAG;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    private static Session session;

    public static Session getInstance(Context context, String TAG) {
        if (session == null) {
            session = new Session(context, TAG);
        }
        return session;
    }

    public void createSession(String ID, String user_email, String first_name, String last_name) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(this.ID, ID);
        editor.putString(this.user_email, user_email);
        editor.putString(this.first_name, first_name);
        editor.putString(this.last_name, last_name);
        editor.commit();
    }

    public void logout() {
        editor.clear();
        editor.commit();
    }

    public boolean getIsLogin() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}
