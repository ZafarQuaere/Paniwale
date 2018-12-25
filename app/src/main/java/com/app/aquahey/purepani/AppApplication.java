/*
 * This is the source code of DMAudioStreaming for Android v. 1.0.0.
 * You should have received a copy of the license in this archive (see LICENSE).
 * Copyright @Dibakar_Mistry(dibakar.ece@gmail.com), 2017.
 */
package com.app.aquahey.purepani;

import android.app.Application;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;

import com.app.aquahey.purepani.databinding.AppDataBindingComponent;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;


public class AppApplication extends Application {

  /*  public static Typeface Roboto_Light;
    public static Typeface Roboto_Regular;
    public static Typeface Roboto_Thin;
    public static Typeface FONT_AWESOME;
    public static Typeface GEO_CALENDAR;
*/

    @Override
    public void onCreate() {
        super.onCreate();
        DataBindingUtil.setDefaultComponent(new AppDataBindingComponent());


      /*  Roboto_Light = Typeface.createFromAsset(getAssets(), "roboto_light.ttf");
        Roboto_Regular = Typeface.createFromAsset(getAssets(), "roboto_regular.ttf");
        Roboto_Thin = Typeface.createFromAsset(getAssets(), "roboto_thin.ttf");
        FONT_AWESOME = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        GEO_CALENDAR = Typeface.createFromAsset(getAssets(), "geo_calendar.ttf");
*/
        //Image Loader
        initImageLoader(getApplicationContext());
    }

    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app
        ImageLoader.getInstance().init(config.build());
    }


}
