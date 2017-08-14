package com.project.luulinhson.foody.Model.Object;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.luulinhson.foody.ServerCallback.ServerCallback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 7/21/2017.
 */

public class QuanAn implements Parcelable {
    boolean giaohang;
    String maquanan;
    String giodongcua;
    String giomocua;
    String tenquanan;
    String videogioithieu;
    long luotthich;
    List<String> tienich;
    List<String> hinhanhquanan;
    List<BinhLuan> binhLuanList;
    List<ChiNhanhQuanAn> chiNhanhQuanAnList;

    DatabaseReference nodeRoot;

    public QuanAn(){
        nodeRoot = FirebaseDatabase.getInstance().getReference();
    }


    protected QuanAn(Parcel in) {
        giaohang = in.readByte() != 0;
        maquanan = in.readString();
        giodongcua = in.readString();
        giomocua = in.readString();
        tenquanan = in.readString();
        videogioithieu = in.readString();
        luotthich = in.readLong();
        tienich = in.createStringArrayList();
        hinhanhquanan = in.createStringArrayList();
        chiNhanhQuanAnList = new ArrayList<>();
        binhLuanList = new ArrayList<>();
        in.readTypedList(chiNhanhQuanAnList,ChiNhanhQuanAn.CREATOR);
        in.readTypedList(binhLuanList,BinhLuan.CREATOR);

    }

    public static final Creator<QuanAn> CREATOR = new Creator<QuanAn>() {
        @Override
        public QuanAn createFromParcel(Parcel in) {
            return new QuanAn(in);
        }

        @Override
        public QuanAn[] newArray(int size) {
            return new QuanAn[size];
        }
    };

    public String getVideogioithieu() {
        return videogioithieu;
    }

    public void setVideogioithieu(String videogioithieu) {
        this.videogioithieu = videogioithieu;
    }

    public List<ChiNhanhQuanAn> getChiNhanhQuanAnList() {
        return chiNhanhQuanAnList;
    }

    public void setChiNhanhQuanAnList(List<ChiNhanhQuanAn> chiNhanhQuanAnList) {
        this.chiNhanhQuanAnList = chiNhanhQuanAnList;
    }

    public List<BinhLuan> getBinhLuanList() {
        return binhLuanList;
    }

    public void setBinhLuanList(List<BinhLuan> binhLuanList) {
        this.binhLuanList = binhLuanList;
    }

    public String getMaquanan() {
        return maquanan;
    }

    public void setMaquanan(String maquanan) {
        this.maquanan = maquanan;
    }

    public List<String> getHinhanhquanan() {
        return hinhanhquanan;
    }

    public void setHinhanhquanan(List<String> hinhanhquanan) {
        this.hinhanhquanan = hinhanhquanan;
    }

    public long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(long luotthich) {
        this.luotthich = luotthich;
    }

    public boolean isGiaohang() {
        return giaohang;
    }

    public void setGiaohang(boolean giaohang) {
        this.giaohang = giaohang;
    }

    public String getGiodongcua() {
        return giodongcua;
    }

    public void setGiodongcua(String giodongcua) {
        this.giodongcua = giodongcua;
    }

    public String getGiomocua() {
        return giomocua;
    }

    public void setGiomocua(String giomocua) {
        this.giomocua = giomocua;
    }

    public String getTenquanan() {
        return tenquanan;
    }

    public void setTenquanan(String tenquanan) {
        this.tenquanan = tenquanan;
    }

    public List<String> getTienich() {
        return tienich;
    }

    public void setTienich(List<String> tienich) {
        this.tienich = tienich;
    }


    public void getDanhSachQuanAn(final ServerCallback serverCallback, final Location vitrihientai){


        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LayDanhSachQuanAn(dataSnapshot,serverCallback,vitrihientai);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

            nodeRoot.addValueEventListener(valueEventListener);

    }

    private void LayDanhSachQuanAn(DataSnapshot dataSnapshot,ServerCallback serverCallback,Location vitrihientai){

        final List<QuanAn> quanAnList = new ArrayList<>();

        // Lấy dữ liệu node Quán Ăn
        DataSnapshot dataQuanAn = dataSnapshot.child("quanans");
        for (DataSnapshot value : dataQuanAn.getChildren()){


            QuanAn quanAn = value.getValue(QuanAn.class);
            quanAn.setMaquanan(value.getKey());
            // Lấy dữ liệu node Hình Quán Ăn
            DataSnapshot dataHinhAnh = dataSnapshot.child("hinhanhquanans").child(value.getKey());
            List<String> hinhanhList = new ArrayList<>();
            for(DataSnapshot valueHinhAnh : dataHinhAnh.getChildren()){
                hinhanhList.add(valueHinhAnh.getValue(String.class));
            }
            quanAn.setHinhanhquanan(hinhanhList);
            // Lấy dữ liệu node Bình Luận
            DataSnapshot dataBinhLuan = dataSnapshot.child("binhluans").child(value.getKey());
            List<BinhLuan> binhLuanList = new ArrayList<>();
            for (DataSnapshot valueBinhLuan : dataBinhLuan.getChildren()){
                BinhLuan binhLuan = valueBinhLuan.getValue(BinhLuan.class);
                binhLuan.setMaBinhLuan(valueBinhLuan.getKey());
                ThanhVien thanhVien = dataSnapshot.child("thanhviens").child(binhLuan.getMauser()).getValue(ThanhVien.class);
                binhLuan.setThanhVien(thanhVien);

                List<String> hinhAnhBinhLuanList = new ArrayList<>();
                DataSnapshot dataHinhAnhBinhLuan = dataSnapshot.child("hinhanhbinhluans").child(binhLuan.getMaBinhLuan());
                for(DataSnapshot valueHinhAnhBinhLuan : dataHinhAnhBinhLuan.getChildren()){
                    String hinhAnhBinhLuan = valueHinhAnhBinhLuan.getValue(String.class);
                    hinhAnhBinhLuanList.add(hinhAnhBinhLuan);
                }
                binhLuan.setHinhAnhList(hinhAnhBinhLuanList);
                binhLuanList.add(binhLuan);
            }
            quanAn.setBinhLuanList(binhLuanList);

            // Lấy dữ liệu node Chi Nhánh Quán Ăn
            DataSnapshot dataChiNhanhQuanAn = dataSnapshot.child("chinhanhquanans").child(quanAn.getMaquanan());
            List<ChiNhanhQuanAn> chiNhanhQuanAnList = new ArrayList<>();
            for (DataSnapshot valueChiNhanhQuanAn : dataChiNhanhQuanAn.getChildren()){
                ChiNhanhQuanAn chiNhanhQuanAn = valueChiNhanhQuanAn.getValue(ChiNhanhQuanAn.class);
                Location vitriquanan = new Location("");
                vitriquanan.setLongitude(chiNhanhQuanAn.getLongitude());
                vitriquanan.setLatitude(chiNhanhQuanAn.getLatitude());

                double khoangcach = (vitrihientai.distanceTo(vitriquanan)/1000);
                Log.d("khoangcach", "onDataChange: " + khoangcach + "");
                chiNhanhQuanAn.setKhoanngcach(khoangcach);
                chiNhanhQuanAnList.add(chiNhanhQuanAn);
            }
            quanAn.setChiNhanhQuanAnList(chiNhanhQuanAnList);

            quanAnList.add(quanAn);
        }
        serverCallback.onSuccess(quanAnList);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (giaohang ? 1 : 0));
        dest.writeString(maquanan);
        dest.writeString(giodongcua);
        dest.writeString(giomocua);
        dest.writeString(tenquanan);
        dest.writeString(videogioithieu);
        dest.writeLong(luotthich);
        dest.writeStringList(tienich);
        dest.writeStringList(hinhanhquanan);
        dest.writeTypedList(chiNhanhQuanAnList);
        dest.writeTypedList(binhLuanList);

    }
}
