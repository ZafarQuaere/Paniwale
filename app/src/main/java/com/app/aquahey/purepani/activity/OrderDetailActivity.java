package com.app.aquahey.purepani.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.app.aquahey.purepani.R;
import com.app.aquahey.purepani.databinding.ActivityOrderDetailBinding;
import com.app.aquahey.purepani.model.MyOrder;
import com.app.aquahey.purepani.utils.LocalConfiq;
import com.app.aquahey.purepani.viewmodel.MyOrderViewModel;

public class OrderDetailActivity extends AppCompatActivity {
    private MyOrderViewModel myOrderViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityOrderDetailBinding detailBinding = DataBindingUtil.setContentView(this, R.layout.activity_order_detail);
        final MyOrder myOrder = getIntent().getParcelableExtra("MyOrder");
        myOrderViewModel = new MyOrderViewModel(myOrder);
        detailBinding.setMyOrder(myOrderViewModel);
        String name = LocalConfiq.getUserName(getApplicationContext());
        detailBinding.userName.setText(name);

    }
}
