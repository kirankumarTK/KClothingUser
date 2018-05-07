package com.example.im028.kclothinguser.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.common.BackCommonActivity;
import com.example.im028.kclothinguser.common.CommonMethod;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailsActivity extends BackCommonActivity {

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.address1)
    TextView address1;
    @BindView(R.id.address2)
    TextView address2;
    @BindView(R.id.phoneNo)
    TextView phoneNo;
    @BindView(R.id.office)
    TextView office;
    @BindView(R.id.editChange)
    TextView editChange;
    @BindView(R.id.addNewAddress)
    TextView addNewAddress;
    @BindView(R.id.viewDetails)
    TextView viewDetails;
    @BindView(R.id.orderTotal)
    TextView orderTotal;
    @BindView(R.id.deliveryPrice)
    TextView deliveryPrice;
    @BindView(R.id.totalPayable)
    TextView totalPayable;
    @BindView(R.id.continue1)
    TextView continue1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_order_details,"Order Details");
        ButterKnife.bind(this);
    }

    @OnClick({R.id.editChange, R.id.addNewAddress, R.id.viewDetails, R.id.continue1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.editChange:
                editAddress();
                break;
            case R.id.addNewAddress:
                CommonMethod.changeActivity(OrderDetailsActivity.this, AddressActivity.class);
                break;
            case R.id.viewDetails:
                break;
            case R.id.continue1:
                break;
        }
    }

    private void editAddress() {
    CommonMethod.changeActivityWithParamsText(OrderDetailsActivity.this,AddressActivity.class,"editAddress","");
    }
}
