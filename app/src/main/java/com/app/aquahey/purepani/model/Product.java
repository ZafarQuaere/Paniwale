package com.app.aquahey.purepani.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ikram-tiss on 25/4/18.
 */

public class Product implements Parcelable {
    public String id;
    public String pName;
    public String dealerId;
    public String dealerName;
    public String imagePath;
    public String price;
    public String latitude;
    public String longitude;
    public String rating;
    public String addressOne;
    public String dealerContact;



    public Product() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.pName);
        dest.writeString(this.dealerId);
        dest.writeString(this.dealerName);
        dest.writeString(this.imagePath);
        dest.writeString(this.price);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
        dest.writeString(this.rating);
        dest.writeString(this.addressOne);
        dest.writeString(this.dealerContact);
    }

    protected Product(Parcel in) {
        this.id = in.readString();
        this.pName = in.readString();
        this.dealerId = in.readString();
        this.dealerName = in.readString();
        this.imagePath = in.readString();
        this.price = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
        this.rating = in.readString();
        this.addressOne = in.readString();
        this.dealerContact = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
