package com.example.im028.kclothinguser.utlity.Constant;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by im028 on 27/6/17.
 */

public class CommonMethod {

    public static void showSnackbar(View view, String message) {
        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public static String getText(TextInputEditText view) {
        return view.getText().toString();
    }

    public static boolean checkEmpty(TextInputEditText view) {
        if (getText(view).equalsIgnoreCase(""))
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


}
