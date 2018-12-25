package com.app.aquahey.purepani.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MyOrder implements Parcelable {

    public String productId;
    public String addressId;
    public String dealerId;
    public String orderDate;
    public String deliverDate;
    public String pName;
    public String imagePath;
    public String price;
    public String addressOne;
    public String dName;
    public String dMobile;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.productId);
        dest.writeString(this.addressId);
        dest.writeString(this.dealerId);
        dest.writeString(this.orderDate);
        dest.writeString(this.deliverDate);
        dest.writeString(this.pName);
        dest.writeString(this.imagePath);
        dest.writeString(this.price);
        dest.writeString(this.addressOne);
        dest.writeString(this.dName);
        dest.writeString(this.dMobile);
    }

    public MyOrder() {
    }

    protected MyOrder(Parcel in) {
        this.productId = in.readString();
        this.addressId = in.readString();
        this.dealerId = in.readString();
        this.orderDate = in.readString();
        this.deliverDate = in.readString();
        this.pName = in.readString();
        this.imagePath = in.readString();
        this.price = in.readString();
        this.addressOne = in.readString();
        this.dName = in.readString();
        this.dMobile = in.readString();
    }

    public static final Parcelable.Creator<MyOrder> CREATOR = new Parcelable.Creator<MyOrder>() {
        @Override
        public MyOrder createFromParcel(Parcel source) {
            return new MyOrder(source);
        }

        @Override
        public MyOrder[] newArray(int size) {
            return new MyOrder[size];
        }
    };
}
