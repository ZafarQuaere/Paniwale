package com.app.aquahey.purepani.model;

import android.os.Parcel;
import android.os.Parcelable;

public class OtpResponse implements Parcelable {

    public String sms_text;
    public String status;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sms_text);
        dest.writeString(this.status);
    }

    public OtpResponse() {
    }

    protected OtpResponse(Parcel in) {
        this.sms_text = in.readString();
        this.status = in.readString();
    }

    public static final Parcelable.Creator<OtpResponse> CREATOR = new Parcelable.Creator<OtpResponse>() {
        @Override
        public OtpResponse createFromParcel(Parcel source) {
            return new OtpResponse(source);
        }

        @Override
        public OtpResponse[] newArray(int size) {
            return new OtpResponse[size];
        }
    };
}
