package com.app.aquahey.purepani.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by ikram-tiss on 1/5/18.
 */

public class ProductResponse implements Parcelable {
    public int success;
    public List<Product> resultArray;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.success);
        dest.writeTypedList(this.resultArray);
    }

    public ProductResponse() {
    }

    protected ProductResponse(Parcel in) {
        this.success = in.readInt();
        this.resultArray = in.createTypedArrayList(Product.CREATOR);
    }

    public static final Parcelable.Creator<ProductResponse> CREATOR = new Parcelable.Creator<ProductResponse>() {
        @Override
        public ProductResponse createFromParcel(Parcel source) {
            return new ProductResponse(source);
        }

        @Override
        public ProductResponse[] newArray(int size) {
            return new ProductResponse[size];
        }
    };
}
