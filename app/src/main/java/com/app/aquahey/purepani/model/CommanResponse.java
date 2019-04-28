package com.app.aquahey.purepani.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CommanResponse implements Parcelable {
    public int status;
    public String message;
    public String data;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.status);
        dest.writeString(this.message);
        dest.writeString(this.data);
    }

    public CommanResponse() {
    }

    protected CommanResponse(Parcel in) {
        this.status = in.readInt();
        this.message = in.readString();
        this.data = in.readString();
    }

    public static final Parcelable.Creator<CommanResponse> CREATOR = new Parcelable.Creator<CommanResponse>() {
        @Override
        public CommanResponse createFromParcel(Parcel source) {
            return new CommanResponse(source);
        }

        @Override
        public CommanResponse[] newArray(int size) {
            return new CommanResponse[size];
        }
    };
}
