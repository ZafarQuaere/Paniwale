package com.app.aquahey.purepani.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ikram-tiss on 26/4/18.
 */

public class DeliveryAddress implements Parcelable {
    public String id;
    public String userId;
    public String addressOne;
    public String city;
    public String state;
    public String pincode;
    public double latitude;
    public double longitude;
    public String name;
    public String mobile;
    public boolean isSelected;




    public DeliveryAddress() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.userId);
        dest.writeString(this.addressOne);
        dest.writeString(this.city);
        dest.writeString(this.state);
        dest.writeString(this.pincode);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeString(this.name);
        dest.writeString(this.mobile);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
    }

    protected DeliveryAddress(Parcel in) {
        this.id = in.readString();
        this.userId = in.readString();
        this.addressOne = in.readString();
        this.city = in.readString();
        this.state = in.readString();
        this.pincode = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.name = in.readString();
        this.mobile = in.readString();
        this.isSelected = in.readByte() != 0;
    }

    public static final Creator<DeliveryAddress> CREATOR = new Creator<DeliveryAddress>() {
        @Override
        public DeliveryAddress createFromParcel(Parcel source) {
            return new DeliveryAddress(source);
        }

        @Override
        public DeliveryAddress[] newArray(int size) {
            return new DeliveryAddress[size];
        }
    };
}