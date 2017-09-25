package com.example.im028.kclothinguser.utlity.webservice;

import android.content.Context;

import com.example.im028.kclothinguser.Interface.VolleyResponseListerner;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by im028 on 27/6/17.
 */

public class WebServices {

    private VolleyClass volleyClass;
    private static WebServices webServices;


    public static WebServices getInstance(Context context, String TAG) {
        if (webServices == null) {
            webServices = new WebServices(context, TAG);
        }
        return webServices;
    }

    private WebServices(Context context, String TAG) {

        volleyClass = new VolleyClass(context, TAG);
    }

    public void login(String url, String username, String password, final VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("password", password);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        volleyClass.volleyPostData(url, jsonObject, listerner);
    }

    public void register(String url, String firstname, String lastname, String email, String password, final VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("firstname", firstname);
            jsonObject.put("lastname", lastname);
            jsonObject.put("user_email", email);
            jsonObject.put("user_pass", password);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        volleyClass.volleyPostData(url, jsonObject, listerner);
    }

    public void getEvents(String url, String paged, String limit, final VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("paged", paged);
            jsonObject.put("limit", limit);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        volleyClass.volleyPostData(url, jsonObject, listerner);
    }

    public void getProducts(String url, String paged, String limit, String category_id, String search, String orderby, final VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("paged", paged);
            jsonObject.put("limit", limit);
            jsonObject.put("category_id", category_id);
            jsonObject.put("search", search);
            jsonObject.put("orderby", orderby);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        volleyClass.volleyPostData(url, jsonObject, listerner);
    }

    public void getProductsDetails(String url, String productID, final VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("product_id", productID);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        volleyClass.volleyPostData(url, jsonObject, listerner);
    }

    public void getMainPageDetails(String url, final VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        volleyClass.volleyPostData(url, jsonObject, listerner);
    }

}
