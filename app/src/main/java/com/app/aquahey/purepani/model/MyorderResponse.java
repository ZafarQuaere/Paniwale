package com.app.aquahey.purepani.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class MyorderResponse implements Parcelable {

    public int success;
    public ArrayList<MyOrder> resultArray;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.success);
        dest.writeTypedList(this.resultArray);
    }

    public MyorderResponse() {
    }

    protected MyorderResponse(Parcel in) {
        this.success = in.readInt();
        this.resultArray = in.createTypedArrayList(MyOrder.CREATOR);
    }

    public static final Parcelable.Creator<MyorderResponse> CREATOR = new Parcelable.Creator<MyorderResponse>() {
        @Override
        public MyorderResponse createFromParcel(Parcel source) {
            return new MyorderResponse(source);
        }

        @Override
        public MyorderResponse[] newArray(int size) {
            return new MyorderResponse[size];
        }
    };
}
