package com.app.aquahey.purepani.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ikram-tiss on 1/5/18.
 */

public class SignIn implements Parcelable {
    public String id;
    public String name;
    public String password;
    public String mobile;
    public String email;
    public String token;
    // public String userType;


    public SignIn() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.mobile);
        dest.writeString(this.password);
        dest.writeString(this.email);
        dest.writeString(this.token);
        //dest.writeString(this.userType);
    }

    protected SignIn(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.mobile = in.readString();
        this.password = in.readString();
        this.email = in.readString();
        this.token = in.readString();
        // this.userType = in.readString();
    }

    public static final Creator<SignIn> CREATOR = new Creator<SignIn>() {
        @Override
        public SignIn createFromParcel(Parcel source) {
            return new SignIn(source);
        }

        @Override
        public SignIn[] newArray(int size) {
            return new SignIn[size];
        }
    };
}
