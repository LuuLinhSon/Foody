package com.project.luulinhson.foody.Model.Object;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.luulinhson.foody.ServerCallback.ServerCallbackThongTinThanhVien;

import java.io.File;
import java.util.List;

/**
 * Created by Admin on 7/23/2017.
 */

public class BinhLuan implements Parcelable{
    long chamdiem,luotthich;
    ThanhVien thanhVien;
    String tieude;
    String noidung;
    String mauser;
    List<String> hinhAnhList;
    String maBinhLuan;

    public BinhLuan(){

    }


    protected BinhLuan(Parcel in) {
        chamdiem = in.readLong();
        luotthich = in.readLong();
        tieude = in.readString();
        noidung = in.readString();
        mauser = in.readString();
        hinhAnhList = in.createStringArrayList();
        maBinhLuan = in.readString();
        thanhVien = in.readParcelable(ThanhVien.class.getClassLoader());
    }

    public static final Creator<BinhLuan> CREATOR = new Creator<BinhLuan>() {
        @Override
        public BinhLuan createFromParcel(Parcel in) {
            return new BinhLuan(in);
        }

        @Override
        public BinhLuan[] newArray(int size) {
            return new BinhLuan[size];
        }
    };

    public List<String> getHinhAnhList() {
        return hinhAnhList;
    }

    public void setHinhAnhList(List<String> hinhAnhList) {
        this.hinhAnhList = hinhAnhList;
    }

    public String getMaBinhLuan() {
        return maBinhLuan;
    }

    public void setMaBinhLuan(String maBinhLuan) {
        this.maBinhLuan = maBinhLuan;
    }

    public String getMauser() {
        return mauser;
    }

    public void setMauser(String mauser) {
        this.mauser = mauser;
    }

    public long getChamdiem() {
        return chamdiem;
    }

    public void setChamdiem(long chamdiem) {
        this.chamdiem = chamdiem;
    }

    public long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(long luotthich) {
        this.luotthich = luotthich;
    }

    public ThanhVien getThanhVien() {
        return thanhVien;
    }

    public void setThanhVien(ThanhVien thanhVien) {
        this.thanhVien = thanhVien;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(chamdiem);
        dest.writeLong(luotthich);
        dest.writeString(tieude);
        dest.writeString(noidung);
        dest.writeString(mauser);
        dest.writeStringList(hinhAnhList);
        dest.writeString(maBinhLuan);
        dest.writeParcelable(thanhVien,flags);
    }

    public void DangBinhLuan(String maquanan, BinhLuan binhLuan, final List<String> listHinh, final ServerCallbackThongTinThanhVien serverCallbackThongTinThanhVien){
        DatabaseReference nodeBinhLuan = FirebaseDatabase.getInstance().getReference().child("binhluans");
        String mabinhluan = nodeBinhLuan.child(maquanan).push().getKey();
        nodeBinhLuan.child(maquanan).child(mabinhluan).setValue(binhLuan).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                if(task.isSuccessful()){
                    if(listHinh.size() > 0){
                        for (String hinhAnh : listHinh){
                            Uri uri = Uri.fromFile(new File(hinhAnh));
                            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("hinhanh/"+uri.getLastPathSegment());
                            storageReference.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(Task<UploadTask.TaskSnapshot> task) {
                                    if(task.isSuccessful()){
                                        serverCallbackThongTinThanhVien.onSuccess(true);
                                    }
                                }
                            });

                        }
                    }
                }
            }
        });
        if(listHinh.size() > 0){
            for (String hinhAnh : listHinh){
                Uri uri = Uri.fromFile(new File(hinhAnh));
                FirebaseDatabase.getInstance().getReference().child("hinhanhbinhluans").child(mabinhluan).push().setValue(uri.getLastPathSegment());
            }
        }
    }
}
