package com.app.aquahey.purepani.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.app.aquahey.purepani.model.BrandData;
import com.app.aquahey.purepani.utils.ImageUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

public class DataItemViewModel extends BaseObservable {
    private BrandData.Brand brand;

    public DataItemViewModel(BrandData.Brand brand) {
        this.brand = brand;
    }


    @Bindable
    public BrandData.Brand getBrand() {
        if (null != brand) {
            return brand;
        } else {
            return null;
        }
    }

    public String getImageUrl() {
        return brand.banner;
    }

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        ImageLoader.getInstance().displayImage(imageUrl, view, ImageUtil.getOption());

    }
}
