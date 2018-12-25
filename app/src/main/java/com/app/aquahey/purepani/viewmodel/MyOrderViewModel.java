package com.app.aquahey.purepani.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.app.aquahey.purepani.model.MyOrder;
import com.app.aquahey.purepani.utils.ImageUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MyOrderViewModel extends BaseObservable {
    private MyOrder order;

    public MyOrderViewModel(MyOrder order) {
        this.order = order;
    }


    @Bindable
    public MyOrder getOrder() {
        if (null != order) {
            return order;
        }
        return null;
    }

    public String getImageUrl() {
        return order.imagePath;
    }

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        ImageLoader.getInstance().displayImage(imageUrl, view, ImageUtil.getOption());

    }
}
