package com.project.luulinhson.foody.View.QuanAnDetail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.project.luulinhson.foody.Adapter.AdapterHinhBinhLuan;
import com.project.luulinhson.foody.Model.Object.BinhLuan;
import com.project.luulinhson.foody.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admin on 7/31/2017.
 */

public class ChiTietBinhLuanActivity extends AppCompatActivity {

    CircleImageView imImageUser;
    TextView tvTieuDeBinhLuan,tvNoiDungBinhLuan,tvChamDiemBinhLuan;
    RecyclerView recyclerHinhBinhLuan;
    List<Bitmap> bitmapList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_layout_binh_luan);

        final BinhLuan binhLuan = getIntent().getParcelableExtra("binhluan");

        bitmapList = new ArrayList<>();

        imImageUser = (CircleImageView) findViewById(R.id.circleImageUser);
        tvTieuDeBinhLuan = (TextView) findViewById(R.id.tvTieuDeBinhLuan);
        tvNoiDungBinhLuan = (TextView) findViewById(R.id.tvNoiDungBinhLuan);
        tvChamDiemBinhLuan = (TextView) findViewById(R.id.tvChamDiemBinhLuan);
        recyclerHinhBinhLuan = (RecyclerView) findViewById(R.id.recyclerHinhBinhLuan);

        tvTieuDeBinhLuan.setText(binhLuan.getTieude());
        tvNoiDungBinhLuan.setText(binhLuan.getNoidung());
        tvChamDiemBinhLuan.setText(binhLuan.getChamdiem() + "");

        StorageReference storageHinhAnh = FirebaseStorage.getInstance().getReference().child("hinhanh").child(binhLuan.getThanhVien().getHinhanh());
        long ONE_MEGABYTE = 1024 * 1024;
        storageHinhAnh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                imImageUser.setImageBitmap(bitmap);
            }
        });

        for (String linkHinh : binhLuan.getHinhAnhList()){
            StorageReference storageHinhAnhBinhLuan = FirebaseStorage.getInstance().getReference().child("hinhanh").child(linkHinh);
            long ONE_MEGABYTE_2 = 1024 * 1024;
            storageHinhAnhBinhLuan.getBytes(ONE_MEGABYTE_2).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    bitmapList.add(bitmap);
                    if(bitmapList.size() == binhLuan.getHinhAnhList().size()){
                        AdapterHinhBinhLuan adapterHinhBinhLuan = new AdapterHinhBinhLuan(ChiTietBinhLuanActivity.this,R.layout.custom_layout_hinh_binh_luan,bitmapList,binhLuan,true);
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(ChiTietBinhLuanActivity.this,2);
                        recyclerHinhBinhLuan.setLayoutManager(layoutManager);
                        recyclerHinhBinhLuan.setAdapter(adapterHinhBinhLuan);
                        adapterHinhBinhLuan.notifyDataSetChanged();
                    }

                }
            });
        }

    }
}
