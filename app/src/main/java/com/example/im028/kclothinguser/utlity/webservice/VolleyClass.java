package com.example.im028.kclothinguser.utlity.webservice;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.im028.kclothinguser.Interface.VolleyResponseListerner;
import com.example.im028.kclothinguser.utlity.AppManager.MyApp;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by IM0033 on 8/2/2016.
 */
public class VolleyClass {
    // private PrefManager prefManager;
    String networkErrorMessage = "Network error – please try again.";
    String poorNetwork = "Your data connection is too slow – please try again when you have a better network connection";
    String timeout = "Response timed out – please try again.";
    String authorizationFailed = "Authorization failed – please try again.";
    String serverNotResponding = "Server not responding – please try again.";
    String parseError = "Data not received from server – please check your network connection.";
    String networkErrorTitle = "Network error";
    String poorNetworkTitle = "Poor Network Connection";
    String timeoutTitle = "Network Error";
    String authorizationFailedTitle = "Network Error";
    String serverNotRespondingTitle = "Server Error";
    String parseErrorTitle = "Network Error";
    private Context context;
    private String TAG = "";

    public VolleyClass(Context context, String tag) {
        this.context = context;
        this.TAG = tag + " VolleyClass";
    }

    public void volleyPostData(final String url, JSONObject jsonObject, final VolleyResponseListerner listerner) {

        Log.d(TAG, "volleyPostData  url - " + url);
        Log.d(TAG, "volleyPostData  data - " + jsonObject.toString());
        if (isOnLline()) {
            RequestQueue queue = Volley.newRequestQueue(context);
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST,
                    url, jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, "volleyPostData  response - " + response.toString());
                            try {

                                listerner.onResponse(response);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    if (error instanceof TimeoutError) {
                        listerner.onError(timeout, timeoutTitle);
                    } else if (error instanceof NoConnectionError) {
                        listerner.onError(poorNetwork, poorNetworkTitle);
                    } else if (error instanceof AuthFailureError) {
                        listerner.onError(authorizationFailed, authorizationFailedTitle);
                    } else if (error instanceof ServerError) {
                        listerner.onError(serverNotResponding, serverNotRespondingTitle);
                    } else if (error instanceof NetworkError) {
                        listerner.onError(networkErrorMessage, networkErrorTitle);
                    } else if (error instanceof ParseError) {
                        listerner.onError(parseError, parseErrorTitle);
                    }

                }
            });
            int MY_SOCKET_TIMEOUT_MS = 30000;
            jsonRequest.setRetryPolicy(new DefaultRetryPolicy(MY_SOCKET_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MyApp.getInstanse().addToRequestQueue(jsonRequest);
        } else {
            Log.d(TAG, "volleyPostData response - No Internet");
            listerner.onError(parseError, parseErrorTitle);
        }
    }


    public boolean isOnLline() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}
