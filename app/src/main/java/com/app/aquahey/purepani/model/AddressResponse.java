package com.app.aquahey.purepani.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by ikram-tiss on 4/5/18.
 */

public class AddressResponse implements Parcelable {

    public int success;
    public ArrayList<DeliveryAddress> resultArray;


    public AddressResponse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.success);
        dest.writeTypedList(this.resultArray);
    }

    protected AddressResponse(Parcel in) {
        this.success = in.readInt();
        this.resultArray = in.createTypedArrayList(DeliveryAddress.CREATOR);
    }

    public static final Creator<AddressResponse> CREATOR = new Creator<AddressResponse>() {
        @Override
        public AddressResponse createFromParcel(Parcel source) {
            return new AddressResponse(source);
        }

        @Override
        public AddressResponse[] newArray(int size) {
            return new AddressResponse[size];
        }
    };
}
