package com.app.aquahey.purepani.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.app.aquahey.purepani.model.Product;
import com.app.aquahey.purepani.utils.ImageUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

public class AquasViewHolder extends BaseObservable {
    private Product product;

    public AquasViewHolder(Product product) {
        this.product = product;
    }

    @Bindable
    public Product getProduct() {
        return product;
    }

    public String getImageUrl() {
        return product.imagePath;
    }

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        ImageLoader.getInstance().displayImage(imageUrl, view, ImageUtil.getOption());

    }
}
