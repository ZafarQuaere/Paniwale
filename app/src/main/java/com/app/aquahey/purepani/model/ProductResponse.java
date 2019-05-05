package com.app.aquahey.purepani.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by ikram-tiss on 1/5/18.
 */

public class ProductResponse implements Parcelable {
    public int status;
    public List<Product> data;
    public String message;


    public ProductResponse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.status);
        dest.writeTypedList(this.data);
        dest.writeString(this.message);
    }

    protected ProductResponse(Parcel in) {
        this.status = in.readInt();
        this.data = in.createTypedArrayList(Product.CREATOR);
        this.message = in.readString();
    }

    public static final Creator<ProductResponse> CREATOR = new Creator<ProductResponse>() {
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
