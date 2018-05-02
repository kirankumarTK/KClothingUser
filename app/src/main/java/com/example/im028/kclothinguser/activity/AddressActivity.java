package com.example.im028.kclothinguser.activity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

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

public class AddressActivity extends BackCommonActivity {

    @BindView(R.id.firstName)
    EditText firstName;
    @BindView(R.id.lastName)
    EditText lastName;
    @BindView(R.id.companyName)
    EditText companyName;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.phoneNo)
    EditText phoneNo;
    @BindView(R.id.address1)
    EditText address1;
    @BindView(R.id.address2)
    EditText address2;
    @BindView(R.id.townCity)
    EditText townCity;
    @BindView(R.id.stateCountry)
    EditText stateCountry;
    @BindView(R.id.postcode)
    EditText postcode;
    @BindView(R.id.homeRadio)
    RadioButton homeRadio;
    @BindView(R.id.officeRadio)
    RadioButton officeRadio;
    @BindView(R.id.shipToDiffAdd)
    CheckBox shipToDiffAdd;
    @BindView(R.id.placeOrder)
    TextView placeOrder;

    public String TAG = AddressActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_address);
        ButterKnife.bind(this);

        if (getIntent().getStringExtra("editAddress") != null) {
            fetchAddress();
        }


    }

    private void fetchAddress() {
        WebServices.getInstance(AddressActivity.this, TAG).getBillingAddress(ConstantValues.GET_BILL_ADDRESS, "280", new VolleyResponseListerner() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {

                if (response.getString("resultcode").equalsIgnoreCase("200")) {
                    JSONObject jsonObject = response.getJSONObject("data");
                    firstName.setText(jsonObject.getString("billing_first_name"));
                    lastName.setText(jsonObject.getString("billing_last_name"));
                    email.setText(jsonObject.getString("billing_email"));
                    phoneNo.setText(jsonObject.getString("billing_phone"));
                    address1.setText(jsonObject.getString("billing_address_1"));
                    address2.setText(jsonObject.getString("billing_address_2"));
                    townCity.setText(jsonObject.getString("billing_city"));
                    stateCountry.setText(jsonObject.getString("billing_state"));
                    postcode.setText(jsonObject.getString("billing_postcode"));
                }
            }

            @Override
            public void onError(String message, String title) {

            }
        });
    }

    @OnClick(R.id.placeOrder)
    public void onViewClicked() {
        if (CommonMethod.checkEmptyEditText(firstName)) {
            if (CommonMethod.checkEmptyEditText(lastName)) {

                WebServices.getInstance(AddressActivity.this, TAG).updateAddress(ConstantValues.UPDATE_ADDRESS, "280", firstName.getText().toString(), lastName.getText().toString(), email.getText().toString()
                        , phoneNo.getText().toString(), "India", address1.getText().toString(), address2.getText().toString(), townCity.getText().toString(), stateCountry.getText().toString(), postcode.getText().toString(), new VolleyResponseListerner() {
                            @Override
                            public void onResponse(JSONObject response) throws JSONException {
                                if (response.getString("resultcode").equalsIgnoreCase("200")) {
                                    CommonMethod.showSnackbar(firstName, response.getString("message"), AddressActivity.this);
                                }
                            }

                            @Override
                            public void onError(String message, String title) {

                            }
                        });

            } else
                lastName.setError("Enter last name");
        } else
            firstName.setError("Enter First name");

    }

}
