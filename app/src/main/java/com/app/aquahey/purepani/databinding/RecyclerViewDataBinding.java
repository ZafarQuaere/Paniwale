package com.app.aquahey.purepani.databinding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import com.app.aquahey.purepani.adapter.DataAdapter;
import com.app.aquahey.purepani.adapter.ProductAdapter;
import com.app.aquahey.purepani.model.BrandData;
import com.app.aquahey.purepani.model.Product;

import java.util.List;

public class RecyclerViewDataBinding {


    @BindingAdapter({"app:adapter", "app:data"})
    public void bind(RecyclerView recyclerView, DataAdapter adapter, List<BrandData.Brand> data) {
        recyclerView.setAdapter(adapter);
        adapter.updateData(data);
    }

    @BindingAdapter({"app:adapter", "app:data"})
    public void bind(RecyclerView recyclerView, ProductAdapter adapter, List<Product> data) {
        recyclerView.setAdapter(adapter);
        adapter.updateData(data);
    }


}
