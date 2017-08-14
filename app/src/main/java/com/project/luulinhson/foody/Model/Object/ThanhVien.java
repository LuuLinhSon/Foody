package com.project.luulinhson.foody.Model.Object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.luulinhson.foody.ServerCallback.ServerCallbackThongTinThanhVien;

/**
 * Created by Admin on 7/23/2017.
 */

public class ThanhVien implements Parcelable{
    String hoten;
    String hinhanh;
    private DatabaseReference databaseReference;

    public ThanhVien(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("thanhviens");
    }

    protected ThanhVien(Parcel in) {
        hoten = in.readString();
        hinhanh = in.readString();
    }

    public static final Creator<ThanhVien> CREATOR = new Creator<ThanhVien>() {
        @Override
        public ThanhVien createFromParcel(Parcel in) {
            return new ThanhVien(in);
        }

        @Override
        public ThanhVien[] newArray(int size) {
            return new ThanhVien[size];
        }
    };

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public void DangKyThongTinThanhVien(String userId, ThanhVien thanhVien, final ServerCallbackThongTinThanhVien serverCallbackThongTinThanhVien){
        databaseReference.child(userId).setValue(thanhVien).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                serverCallbackThongTinThanhVien.onSuccess(true);
            }
        });
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hoten);
        dest.writeString(hinhanh);
    }
}
