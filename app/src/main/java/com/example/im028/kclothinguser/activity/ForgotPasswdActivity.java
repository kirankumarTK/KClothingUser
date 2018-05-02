package com.example.im028.kclothinguser.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.im028.kclothinguser.Interface.VolleyResponseListerner;
import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.common.BackCommonActivity;
import com.example.im028.kclothinguser.common.CommonMethod;
import com.example.im028.kclothinguser.utlity.Constant.ConstantValues;
import com.example.im028.kclothinguser.utlity.webservice.WebServices;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotPasswdActivity extends BackCommonActivity {


    @BindView(R.id.currentPasswd)
    EditText currentPasswd;
    @BindView(R.id.newPasswd)
    EditText newPasswd;
    @BindView(R.id.confirmPasswd)
    EditText confirmPasswd;
    @BindView(R.id.submit)
    Button submit;

    String TAG = ForgotPasswdActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_forgot);
        ButterKnife.bind(this);


    }


    @OnClick(R.id.submit)
    public void onViewClicked() {
        WebServices.getInstance(ForgotPasswdActivity.this, TAG).changePasswd(ConstantValues.CHANGE_PASSWD, "280", currentPasswd.getText().toString(), newPasswd.getText().toString(), confirmPasswd.getText().toString(), new VolleyResponseListerner() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {

                if (response.getString("resultcode").equalsIgnoreCase("200")) {
                    CommonMethod.showSnackbar(submit, response.getString("resultmessage"), ForgotPasswdActivity.this);
                }
            }

            @Override
            public void onError(String message, String title) {

            }
        });
    }
}
