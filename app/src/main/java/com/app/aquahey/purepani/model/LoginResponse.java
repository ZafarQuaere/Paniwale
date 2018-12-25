package com.app.aquahey.purepani.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ikram-tiss on 1/5/18.
 */

public class LoginResponse implements Parcelable {
    public int success;
  //  public int totalItem;
    public SignIn resultArray;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.success);
     //   dest.writeInt(this.totalItem);
        dest.writeParcelable(this.resultArray, flags);
    }

    public LoginResponse() {
    }

    protected LoginResponse(Parcel in) {
        this.success = in.readInt();
      //  this.totalItem = in.readInt();
        this.resultArray = in.readParcelable(SignIn.class.getClassLoader());
    }

    public static final Parcelable.Creator<LoginResponse> CREATOR = new Parcelable.Creator<LoginResponse>() {
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
