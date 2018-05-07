package com.example.im028.kclothinguser.common;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by im028 on 27/6/17.
 */

public class CommonMethod {

    public static void showSnackbar(View view, String message, Activity activity) {
        try {
            hideKeyboard(activity);
            Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
            snackbar.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void showSnackbar(View view, JSONObject message, Activity activity) {
        try {
            hideKeyboard(activity);
            Snackbar snackbar = Snackbar.make(view, message.optString("resultmessage"), Snackbar.LENGTH_LONG);
            snackbar.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String getText(TextInputEditText view) {
        return view.getText().toString();
    }

    public static String getEditTextValue(EditText view) {
        return view.getText().toString();
    }

    public static boolean checkEmpty(TextInputEditText view) {
        if (getText(view).equalsIgnoreCase(""))
            return false;
        else
            return true;
    }

    public static boolean checkEmptyEditText(EditText view) {
        if (view.getText().toString().equalsIgnoreCase(""))
            return false;
        else
            return true;
    }

    public static void setTextTexView(TextView textTexView, String text) {
        textTexView.setText(text);
    }

    public static void showLog(String TAG, String message) {
        Log.v(TAG, message);
    }

    public static void showLogError(String TAG, String message) {
        Log.e(TAG, message);
    }

    public static void changeActivity(Context context, Class<?> c) {
        Intent i = new Intent(context, c);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        context.startActivity(i);
    }

    public static void changeActivityWithParamsObject(Context context, Class<?> c, ArrayList arrayList) {
        Intent i = new Intent(context, c);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("arrayList", arrayList);
        context.startActivity(i);
    }


    public static void changeActivityWithParamsText(Context context, Class<?> c, String text, String text1) {
        Intent i = new Intent(context, c);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("text", text);
        i.putExtra("text1", text1);
        context.startActivity(i);
    }

    public static void googleMap(Context context, String latLang) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(latLang));
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void call(Context context, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        try {
            context.startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "Their is no call Feature", Toast.LENGTH_SHORT).show();
        }
    }

    public static void sendMail(Context context, String contentString) {
        try {

            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:" + "hello@bykaveri.com"));
            emailIntent.setType("message/rfc822");
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "KAVERI (K Clothing)");
            emailIntent.putExtra(Intent.EXTRA_TEXT, contentString);
            emailIntent.setData(Uri.parse("mailto:" + "hello@bykaveri.com"));
            emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }


}
