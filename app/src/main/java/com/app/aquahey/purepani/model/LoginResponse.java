package com.app.aquahey.purepani.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ikram-tiss on 1/5/18.
 */

public class LoginResponse implements Parcelable {
    public int status;
    //  public int totalItem;
    public SignIn data;
    public String message;


    public LoginResponse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.status);
        dest.writeParcelable(this.data, flags);
        dest.writeString(this.message);
    }

    protected LoginResponse(Parcel in) {
        this.status = in.readInt();
        this.data = in.readParcelable(SignIn.class.getClassLoader());
        this.message = in.readString();
    }

    public static final Creator<LoginResponse> CREATOR = new Creator<LoginResponse>() {
        @Override
        public LoginResponse createFromParcel(Parcel source) {
            return new LoginResponse(source);
        }

        @Override
        public LoginResponse[] newArray(int size) {
            return new LoginResponse[size];
        }
    };
}
