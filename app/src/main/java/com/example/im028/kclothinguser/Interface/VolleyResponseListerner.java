package com.example.im028.kclothinguser.Interface;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Im033 on 5/9/2017.
 */

public interface VolleyResponseListerner {
    void onResponse(JSONObject response) throws JSONException;

    void onError(String message, String title);
}
