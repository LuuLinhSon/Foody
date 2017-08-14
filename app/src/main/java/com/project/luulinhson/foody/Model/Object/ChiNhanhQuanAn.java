package com.project.luulinhson.foody.Model.Object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Admin on 7/26/2017.
 */

public class ChiNhanhQuanAn implements Parcelable{

    String diachi;
    double longitude;
    double latitude;
    double khoanngcach;


    public ChiNhanhQuanAn(){

    }

    protected ChiNhanhQuanAn(Parcel in) {
        diachi = in.readString();
        longitude = in.readDouble();
        latitude = in.readDouble();
        khoanngcach = in.readDouble();
    }

    public static final Creator<ChiNhanhQuanAn> CREATOR = new Creator<ChiNhanhQuanAn>() {
        @Override
        public ChiNhanhQuanAn createFromParcel(Parcel in) {
            return new ChiNhanhQuanAn(in);
        }

        @Override
        public ChiNhanhQuanAn[] newArray(int size) {
            return new ChiNhanhQuanAn[size];
        }
    };

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getKhoanngcach() {
        return khoanngcach;
    }

    public void setKhoanngcach(double khoanngcach) {
        this.khoanngcach = khoanngcach;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(diachi);
        dest.writeDouble(longitude);
        dest.writeDouble(latitude);
        dest.writeDouble(khoanngcach);
    }
}
