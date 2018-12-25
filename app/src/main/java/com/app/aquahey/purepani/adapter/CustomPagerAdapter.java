package com.app.aquahey.purepani.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.aquahey.purepani.R;
import com.app.aquahey.purepani.model.BrandData;
import com.app.aquahey.purepani.utils.ImageUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class CustomPagerAdapter extends PagerAdapter {

    private List<BrandData.Brand> data;
    private Context context;

    private ImageLoader imageLoader = ImageLoader.getInstance();


    public CustomPagerAdapter(final Context context, final List<BrandData.Brand> brands) {
        this.data = brands;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return false;
    }


    @Override
    public Object instantiateItem(@NonNull ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.brand_image, collection, false);
       // final ImageView imageView = layout.findViewById(R.id.imageUrl);
        final BrandData.Brand imagePath = data.get(position);

        /*if (null != imagePath.banner && !imagePath.banner.isEmpty()) {
            imageLoader.displayImage(imagePath.banner, imageView, ImageUtil.getOption());
        }*/

        collection.addView(layout);

        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);

    }


}
