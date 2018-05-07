package com.example.im028.kclothinguser.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.common.BackCommonActivity;
import com.example.im028.kclothinguser.common.CommonMethod;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileUpdate extends BackCommonActivity {
    @BindView(R.id.NameEdittext)
    TextInputEditText NameEdittext;
    @BindView(R.id.emailEdittext)
    TextInputEditText emailEdittext;
    @BindView(R.id.alternativeEmailEdittext)
    TextInputEditText alternativeEmailEdittext;
    @BindView(R.id.phoneNoEditText)
    TextInputEditText phoneNoEditText;
    @BindView(R.id.addressEdittext)
    TextInputEditText addressEdittext;
    @BindView(R.id.birthdayEdittext)
    TextInputEditText birthdayEdittext;
    @BindView(R.id.hobbyEdittext)
    TextInputEditText hobbyEdittext;
    @BindView(R.id.favoritecolorEdittext)
    TextInputEditText favoritecolorEdittext;
    @BindView(R.id.XSRadio)
    RadioButton XSRadio;
    @BindView(R.id.SRadio)
    RadioButton SRadio;
    @BindView(R.id.MRadio)
    RadioButton MRadio;
    @BindView(R.id.LRadio)
    RadioButton LRadio;
    @BindView(R.id.XLRadio)
    RadioButton XLRadio;
    @BindView(R.id.XLLRadio)
    RadioButton XLLRadio;
    @BindView(R.id.placeOrder)
    TextView placeOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.profile_update,"Would like to know your better");
        ButterKnife.bind(this);
    }

    @OnClick(R.id.placeOrder)
    public void onViewClicked() {
        if (!emailEdittext.getText().toString().equalsIgnoreCase("")) {
            emailEdittext.setError(null);
            if (!phoneNoEditText.getText().toString().equalsIgnoreCase("")) {
                phoneNoEditText.setError(null);
                submit();

            } else {
                phoneNoEditText.setError("Enter your phone");
                phoneNoEditText.requestFocus();
            }
        } else {
            emailEdittext.setError("Enter your email");
            emailEdittext.requestFocus();
        }

    }

    private void submit() {
        CommonMethod.showSnackbar(phoneNoEditText, "Update successfully", this);
//        finish();
    }
}
