package com.example.im028.kclothinguser.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

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

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.userNameEditextLogin)
    TextInputEditText userName;
    @BindView(R.id.passwordEditextLogin)
    TextInputEditText password;
    @BindView(R.id.loginButtonLogin)
    Button loginButton;
    @BindView(R.id.forgotPasswordTextview)
    TextView forgotPassword;
    @BindView(R.id.newUserTextview)
    TextView newUser;
    @BindView(R.id.progressBarLogin)
    ProgressBar progressBarLogin;

    private String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.loginButtonLogin, R.id.forgotPasswordTextview, R.id.newUserTextview})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.loginButtonLogin:
                login();
                break;
            case R.id.forgotPasswordTextview:
                break;
            case R.id.newUserTextview:
                CommonMethod.changeActivity(LoginActivity.this, RegisterActivity.class);
                finish();
                break;
        }
    }

    private void login() {
        progressBarLogin.setVisibility(View.VISIBLE);
        loginButton.setEnabled(false);
        if (CommonMethod.checkEmpty(userName)) {
            if (CommonMethod.checkEmpty(password)) {
                callLoginApi();
            } else {
                CommonMethod.showSnackbar(password, "Enter Password");
                password.requestFocus();
            }
        } else {
            CommonMethod.showSnackbar(userName, "Enter User Name");
            userName.requestFocus();
        }
    }

    private void callLoginApi() {
        WebServices.getInstance(this, TAG).login(ConstantValues.Login, CommonMethod.getText(userName), CommonMethod.getText(password), new VolleyResponseListerner() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                CommonMethod.showLog(TAG, response.toString());
                progressBarLogin.setVisibility(View.GONE);
                loginButton.setEnabled(true);
                if (response.getString("resultcode").equalsIgnoreCase("200")) {
                    Session.getInstance(LoginActivity.this, TAG).createSession(response.getJSONObject("data").getString("ID"),
                            response.getJSONObject("data").getString("user_email"), response.getJSONObject("data").getString("first_name"),
                            response.getJSONObject("data").getString("last_name"));
                    finish();
                } else {
                    CommonMethod.showSnackbar(userName, response.getString("resultmessage"));
                }
            }

            @Override
            public void onError(String message, String title) {
                CommonMethod.showLogError(TAG, message.toString());
                CommonMethod.showSnackbar(userName, message.toString());
                progressBarLogin.setVisibility(View.GONE);
                loginButton.setEnabled(true);
            }
        });
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}
