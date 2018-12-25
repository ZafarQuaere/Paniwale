package com.app.aquahey.purepani.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class BrandData implements Parcelable {

    public int success;
    public List<Brand> resultArray;




    public static class Brand implements Parcelable {
        public String id;
        public String brandName;
        public String banner;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.brandName);
            dest.writeString(this.banner);
        }

        public Brand() {
        }

        protected Brand(Parcel in) {
            this.id = in.readString();
            this.brandName = in.readString();
            this.banner = in.readString();
        }

        public static final Parcelable.Creator<Brand> CREATOR = new Parcelable.Creator<Brand>() {
            @Override
            public Brand createFromParcel(Parcel source) {
                return new Brand(source);
            }

            @Override
            public Brand[] newArray(int size) {
                return new Brand[size];
            }
        };
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

    public BrandData() {
    }

    protected BrandData(Parcel in) {
        this.success = in.readInt();
        this.resultArray = in.createTypedArrayList(Brand.CREATOR);
    }

    public static final Parcelable.Creator<BrandData> CREATOR = new Parcelable.Creator<BrandData>() {
        @Override
        public BrandData createFromParcel(Parcel source) {
            return new BrandData(source);
        }

        @Override
        public BrandData[] newArray(int size) {
            return new BrandData[size];
        }
    };
}
