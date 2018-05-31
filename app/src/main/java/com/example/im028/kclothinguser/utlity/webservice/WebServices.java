package com.example.im028.kclothinguser.utlity.webservice;

import android.content.Context;

import com.example.im028.kclothinguser.Interface.VolleyResponseListerner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by im028 on 27/6/17.
 */

public class WebServices {

    private static WebServices webServices;
    private VolleyClass volleyClass;


    private WebServices(Context context, String TAG) {

        volleyClass = new VolleyClass(context, TAG);
    }

    public static WebServices getInstance(Context context, String TAG) {
        if (webServices == null) {
            webServices = new WebServices(context, TAG);
        }
        return webServices;
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

    public void register(String url, String firstname, String lastname, String email, String password, String appId, final VolleyResponseListerner listerner) {
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

    public void sendAppId(String url, String appId, VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("appid", appId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        volleyClass.volleyPostData(url, jsonObject, listerner);
    }

    public void getProductCategoryList(String url, String category_name, int paged, int limit, String appId,
                                       String orderby, final VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("paged", paged);
            jsonObject.put("limit", limit);
            jsonObject.put("cat_name", category_name);
            jsonObject.put("orderby", orderby);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        volleyClass.volleyPostData(url, jsonObject, listerner);
    }

    public void getProductsDetails(String url, String productID, String appId, final VolleyResponseListerner listerner) {
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

    public void getHomePage(String url, String appId, final VolleyResponseListerner listerner) {
        volleyClass.volleyPostData(url, new JSONObject(), listerner);
    }

    public void socialLogin(String url, String email, String name, String type, final VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("name", name);
            jsonObject.put("type", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        volleyClass.volleyPostData(url, jsonObject, listerner);
    }


    public void updateAddress(String url, String userId, String billing_first_name, String billing_last_name, String billing_email,
                              String billing_phone, String billing_country, String billing_address_1, String billing_address_2,
                              String billing_city, String billing_state, String billing_postcode, final VolleyResponseListerner listerner) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", userId);
            jsonObject.put("billing_first_name", billing_first_name);
            jsonObject.put("billing_last_name", billing_last_name);
            jsonObject.put("billing_email", billing_email);
            jsonObject.put("billing_phone", billing_phone);
            jsonObject.put("billing_country", billing_country);
            jsonObject.put("billing_address_1", billing_address_1);
            jsonObject.put("billing_address_2", billing_address_2);
            jsonObject.put("billing_city", billing_city);
            jsonObject.put("billing_state", billing_state);
            jsonObject.put("billing_postcode", billing_postcode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        volleyClass.volleyPostData(url, jsonObject, listerner);
    }

    public void getBillingAddress(String url, String userId, final VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        volleyClass.volleyPostData(url, jsonObject, listerner);
    }

    public void changePasswd(String url, String userId, String current_pass, String new_pass, String confirm_pass, final VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", userId);
            jsonObject.put("current_password", current_pass);
            jsonObject.put("new_password", new_pass);
            jsonObject.put("confirm_password", confirm_pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        volleyClass.volleyPostData(url, jsonObject, listerner);
    }

    public void addToBag(String url, String product_id, String user_id, String quantity, String sizeType, Map<String, String> size, String special, String cat_name, final VolleyResponseListerner listerner) {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("product_id", product_id);
            jsonObject.put("user_id", user_id);
            jsonObject.put("quantity", quantity);
            jsonObject.put("checksize", sizeType);
            for (String key : size.keySet()) {
                jsonObject.put(key, size.get(key));
            }
            jsonObject.put("special", special);

            if (sizeType.equalsIgnoreCase("customchecked")) {
                if (cat_name.equalsIgnoreCase("pants")) {
                    jsonObject.put("sizechart", 2);
                } else {
                    jsonObject.put("sizechart", 1);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        volleyClass.volleyPostData(url, jsonObject, listerner);

    }

    public void displayCart(String url, String user_id, final VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        volleyClass.volleyPostData(url, jsonObject, listerner);
    }

    public void removeCart(String url, String user_id, String cart_item_key, final VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
            jsonObject.put("cart_item_key", cart_item_key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        volleyClass.volleyPostData(url, jsonObject, listerner);
    }


    public void updateCart(String url, String user_id, String cart_item_key, String quantity, final VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
            jsonObject.put("cart_item_key", cart_item_key);
            jsonObject.put("quantity", quantity);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        volleyClass.volleyPostData(url, jsonObject, listerner);
    }


    public void addWishlist(String url, String user_id, String product_id, final VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
            jsonObject.put("product_id", product_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        volleyClass.volleyPostData(url, jsonObject, listerner);
    }

    public void getWishlist(String url, String user_id, final VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        volleyClass.volleyPostData(url, jsonObject, listerner);
    }

    public void removeWishlist(String url, String user_id, String product_id, final VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
            jsonObject.put("product_id", product_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        volleyClass.volleyPostData(url, jsonObject, listerner);
    }
}
