package com.example.im028.kclothinguser.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.im028.kclothinguser.Interface.VolleyResponseListerner;
import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.common.CommonMethod;
import com.example.im028.kclothinguser.utlity.Constant.ConstantValues;
import com.example.im028.kclothinguser.utlity.sharedPreferance.Session;
import com.example.im028.kclothinguser.utlity.webservice.WebServices;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by im028 on 27/6/17.
 */

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {


    @BindView(R.id.email)
    TextInputEditText email;
    @BindView(R.id.password)
    TextInputEditText password;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.forgotPassword)
    TextView forgotPassword;
    @BindView(R.id.signUp)
    TextView signUp;
    @BindView(R.id.facebook)
    LinearLayout facebook;
    @BindView(R.id.gmail)
    LinearLayout gmail;
    private String TAG = LoginActivity.class.getSimpleName();
    CallbackManager mCallbackManager;
    public static GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    String facebookEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login_layout);
        ButterKnife.bind(this);

        //Google login
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @OnClick({R.id.login, R.id.forgotPassword, R.id.signUp, R.id.facebook, R.id.gmail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login:
                if (CommonMethod.checkEmptyEditText(email)) {
                    if (CommonMethod.checkEmptyEditText(password))
                        loginFun();
                    else
                        CommonMethod.showSnackbar(password, "Enter Password", LoginActivity.this);
                } else {
                    CommonMethod.showSnackbar(email, "Enter E-mail", LoginActivity.this);
                }
                break;
            case R.id.forgotPassword:
                CommonMethod.changeActivity(LoginActivity.this, ForgotPasswdActivity.class);
                break;
            case R.id.signUp:
                CommonMethod.changeActivity(LoginActivity.this, RegisterActivity.class);
                break;
            case R.id.facebook:
                facebookLogin();
                break;
            case R.id.gmail:
                gmailLogin();
                break;
        }
    }

    private void gmailLogin() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient);
        final Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN && resultCode == RESULT_OK && data != null) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);

        } else if (resultCode == RESULT_OK && data != null) {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            if (mGoogleApiClient.hasConnectedApi(Auth.GOOGLE_SIGN_IN_API)) {
                final GoogleSignInAccount acct = result.getSignInAccount();

                callSocialWebService(acct.getEmail(), acct.getDisplayName(), "Gmail");

            } else {
                Log.e(TAG, "Google+ not connected");
            }

        } else {
            // Signed out, show unauthenticated UI.
            Toast.makeText(LoginActivity.this, "Signed out" + result, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void facebookLogin() {

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));

        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.e(TAG, String.valueOf(loginResult.getAccessToken()));
                        Log.e(TAG, String.valueOf(loginResult.getRecentlyDeniedPermissions()));


                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {

                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        Log.d("TEST", response.toString());

                                        try {
                                            callSocialWebService(object.getString("id") + "@facebook.com", object.getString("name"), "Facebook");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.d("Facebook", exception.toString());
                        Toast.makeText(LoginActivity.this, "Err " + exception.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );

    }

    private void callSocialWebService(String email, String name, String type) {
        WebServices.getInstance(LoginActivity.this, TAG).socialLogin(ConstantValues.SOCIAL_LOGIN, email, name, type, new VolleyResponseListerner() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                if (response.optString("resultcode").equalsIgnoreCase("200")) {
                    Session.getInstance(LoginActivity.this, TAG).createSession(response.optJSONObject("data").optString("ID"),
                            response.optJSONObject("data").optString("user_email"), response.optJSONObject("data").optString("first_name"),
                            response.optJSONObject("data").optString("last_name"),response.optJSONObject("data").optString("token"));
                    CommonMethod.showSnackbar(facebook, response.optString("resultmessage"), LoginActivity.this);

                    finish();
                }
            }

            @Override
            public void onError(String message, String title) {

            }
        });
    }

    private void loginFun() {
        WebServices.getInstance(LoginActivity.this, TAG).login(ConstantValues.Login, CommonMethod.getEditTextValue(email), CommonMethod.getEditTextValue(password), new VolleyResponseListerner() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                if (response.optString("resultcode").equalsIgnoreCase("200")) {
                    Session.getInstance(LoginActivity.this, TAG).createSession(response.optJSONObject("data").optString("ID"),
                            response.optJSONObject("data").optString("user_email"), response.optJSONObject("data").optString("first_name"),
                            response.optJSONObject("data").optString("last_name"),response.optJSONObject("data").optString("token"));
                    CommonMethod.showSnackbar(email, response.optString("resultmessage"), LoginActivity.this);

                    finish();

                }else
                    CommonMethod.showSnackbar(email, response.optString("resultmessage"), LoginActivity.this);
            }

            @Override
            public void onError(String message, String title) {
                CommonMethod.showSnackbar(email, message, LoginActivity.this);
            }
        });
    }
}
