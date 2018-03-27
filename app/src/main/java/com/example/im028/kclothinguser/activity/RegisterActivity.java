package com.example.im028.kclothinguser.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.im028.kclothinguser.Interface.VolleyResponseListerner;
import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.common.CommonMethod;
import com.example.im028.kclothinguser.utlity.Constant.ConstantValues;
import com.example.im028.kclothinguser.utlity.sharedPreferance.Session;
import com.example.im028.kclothinguser.utlity.webservice.WebServices;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by im028 on 27/6/17.
 */

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.firstNameEditextLogin)
    TextInputEditText firstName;
    @BindView(R.id.lastNameEditextLogin)
    TextInputEditText lastName;
    @BindView(R.id.emailEditextLogin)
    TextInputEditText email;
    @BindView(R.id.passwordEditextLogin)
    TextInputEditText password;
    @BindView(R.id.registerButtonLogin)
    Button register;
    @BindView(R.id.progressBarRegister)
    ProgressBar progressBarRegister;

    private String TAG = RegisterActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_register_layout);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.registerButtonLogin)
    public void onViewClicked() {
//        if (CommonMethod.checkEmpty(firstName)) {
//            if (CommonMethod.checkEmpty(lastName)) {
        if (CommonMethod.checkEmpty(email)) {
            if (CommonMethod.checkEmpty(password))
                callRegiterApi();
            else
                CommonMethod.showSnackbar(firstName, "Enter Password");
        } else
            CommonMethod.showSnackbar(firstName, "Enter Email ID");
//            } else
//                CommonMethod.showSnackbar(firstName, "Enter Last Name");
//        } else
//            CommonMethod.showSnackbar(firstName, "Enter First Name");

    }

    public void callRegiterApi() {
        progressBarRegister.setVisibility(View.VISIBLE);
        register.setEnabled(false);
        WebServices.getInstance(this, TAG).register(ConstantValues.REGISTER, CommonMethod.getText(firstName),
                CommonMethod.getText(lastName), CommonMethod.getText(email), CommonMethod.getText(password),
                new VolleyResponseListerner() {
                    @Override
                    public void onResponse(JSONObject response) throws JSONException {
                        progressBarRegister.setVisibility(View.VISIBLE);
                        register.setEnabled(false);
                        CommonMethod.showLog(TAG, response.toString());
                        if (response.getString("resultcode").equalsIgnoreCase("200")) {
                            Session.getInstance(RegisterActivity.this, TAG).createSession(response.getJSONObject("data").getString("ID"),
                                    response.getJSONObject("data").getString("user_email"), response.getJSONObject("data").getString("first_name"),
                                    response.getJSONObject("data").getString("last_name"));
                            finish();
                        } else {
                            CommonMethod.showSnackbar(firstName, response.getString("resultmessage"));
                        }
                    }

                    @Override
                    public void onError(String message, String title) {
                        progressBarRegister.setVisibility(View.VISIBLE);
                        register.setEnabled(false);
                        CommonMethod.showLogError(TAG, message.toString());
                        CommonMethod.showSnackbar(firstName, message.toString());
                    }
                });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
