package com.app.aquahey.purepani.model;

import android.os.Parcel;
import android.os.Parcelable;

public class NumberVerify implements Parcelable {
    public String mobile;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mobile);
    }

    public NumberVerify() {
    }

    protected NumberVerify(Parcel in) {
        this.mobile = in.readString();
    }

    public static final Parcelable.Creator<NumberVerify> CREATOR = new Parcelable.Creator<NumberVerify>() {
        @Override
        public NumberVerify createFromParcel(Parcel source) {
            return new NumberVerify(source);
        }

        @Override
        public NumberVerify[] newArray(int size) {
            return new NumberVerify[size];
        }
    };
}
