package com.app.aquahey.purepani.network;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ikram on 11/10/2017.
 */

public class NetworkResponse implements Parcelable {


    public int success;
    public String id;
    public String token;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.success);
        dest.writeString(this.id);
        dest.writeString(this.token);
    }

    public NetworkResponse() {
    }

    protected NetworkResponse(Parcel in) {
        this.success = in.readInt();
        this.id = in.readString();
        this.token = in.readString();
    }

    public static final Parcelable.Creator<NetworkResponse> CREATOR = new Parcelable.Creator<NetworkResponse>() {
        @Override
        public NetworkResponse createFromParcel(Parcel source) {
            return new NetworkResponse(source);
        }

        @Override
        public NetworkResponse[] newArray(int size) {
            return new NetworkResponse[size];
        }
    };
}
