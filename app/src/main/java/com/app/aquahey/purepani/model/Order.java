package com.app.aquahey.purepani.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ikram-tiss on 3/5/18.
 */

public class Order implements Parcelable {
    public String id;
    public String userId;
    public String paymentId;
    public String productId;
    public String dealerId;
    public String addressId;
    public String orderDate;
    public String deliverDate;
    public int quantity;
    public int status;
    public int amount;
    public int OrderType;
    public int AlternateDay;


    public Order() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.userId);
        dest.writeString(this.paymentId);
        dest.writeString(this.productId);
        dest.writeString(this.dealerId);
        dest.writeString(this.addressId);
        dest.writeString(this.orderDate);
        dest.writeString(this.deliverDate);
        dest.writeInt(this.quantity);
        dest.writeInt(this.status);
        dest.writeInt(this.amount);
        dest.writeInt(this.OrderType);
        dest.writeInt(this.AlternateDay);
    }

    protected Order(Parcel in) {
        this.id = in.readString();
        this.userId = in.readString();
        this.paymentId = in.readString();
        this.productId = in.readString();
        this.dealerId = in.readString();
        this.addressId = in.readString();
        this.orderDate = in.readString();
        this.deliverDate = in.readString();
        this.quantity = in.readInt();
        this.status = in.readInt();
        this.amount = in.readInt();
        this.OrderType = in.readInt();
        this.AlternateDay = in.readInt();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel source) {
            return new Order(source);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
}
