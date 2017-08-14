package com.project.luulinhson.foody.View.QuanAnDetail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.project.luulinhson.foody.Adapter.AdapterRecyclerBinhLuan;
import com.project.luulinhson.foody.Model.Object.QuanAn;
import com.project.luulinhson.foody.Model.Object.TienIch;
import com.project.luulinhson.foody.R;
import com.project.luulinhson.foody.View.Main.MainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ChiTietQuanAnActivity extends AppCompatActivity implements OnMapReadyCallback {

    TextView tvTenQuanAn,tvDiaChiQuanAn,tvTongBinhLuan,tvTongHinhAnh,tvTongCheckIn,tvTongLuuLai,tvGioMoCuaDongCua,tvTrangThaiChuaHang;
    ImageView imHinhQuanAn,imBack;
    RecyclerView recyclerBinhLuan;
    Toolbar toolbar;
    NestedScrollView nestedScrollChiTietQuanAn;
    GoogleMap googleMap;
    MapFragment mapFragment;
    QuanAn quanAn;
    LinearLayout lnTienIch;
    Button btnDanDuong,btnBinhLuan;
    VideoView videoView;
    ImageView imPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_chitietquanan);

        tvTenQuanAn = (TextView) findViewById(R.id.tvTenQuanAn);
        tvDiaChiQuanAn = (TextView) findViewById(R.id.tvDiaChiQuanAn);
        tvTongBinhLuan = (TextView) findViewById(R.id.tvTongBinhLuan);
        tvTongHinhAnh = (TextView) findViewById(R.id.tvTongHinhAnh);
        tvTongCheckIn = (TextView) findViewById(R.id.tvTongCheckIn);
        tvTongLuuLai = (TextView) findViewById(R.id.tvTongLuuLai);
        tvGioMoCuaDongCua = (TextView) findViewById(R.id.tvGioMoCuaDongCua);
        tvTrangThaiChuaHang = (TextView) findViewById(R.id.tvTrangThaiCuaHang);
        imBack = (ImageView) findViewById(R.id.imBack);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        nestedScrollChiTietQuanAn = (NestedScrollView) findViewById(R.id.nestedScrollChiTietQuanAn);
        imHinhQuanAn = (ImageView) findViewById(R.id.imHinhQuanAn);
        recyclerBinhLuan = (RecyclerView) findViewById(R.id.recyclerBinhLuan);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        lnTienIch = (LinearLayout) findViewById(R.id.lnTienIch);
        btnDanDuong = (Button) findViewById(R.id.btnDanDuong);
        btnBinhLuan = (Button) findViewById(R.id.btnBinhLuan);
        videoView = (VideoView) findViewById(R.id.videoView);
        imPlay = (ImageView) findViewById(R.id.imPlay);

        mapFragment.getMapAsync(this);

//        toolbar.setTitle("");
//        setSupportActionBar(toolbar);

        quanAn = getIntent().getParcelableExtra("quanan");


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

        String giohientai = dateFormat.format(calendar.getTime());
        String giomocua = quanAn.getGiomocua();
        String giodongcua = quanAn.getGiodongcua();
        try {
            Date dateHienTai = dateFormat.parse(giohientai);
            Date dateMoCua = dateFormat.parse(giomocua);
            Date dateDongCua = dateFormat.parse(giodongcua);

            if(dateHienTai.after(dateMoCua) && dateHienTai.before(dateDongCua)){
                tvTrangThaiChuaHang.setText("ĐANG MỞ CỬA");
                tvTrangThaiChuaHang.setTextColor(getResources().getColor(R.color.backgroundButton));
            }else {
                tvTrangThaiChuaHang.setText("ĐÃ ĐÓNG CỬA");
                tvTrangThaiChuaHang.setTextColor(getResources().getColor(R.color.colorPrimary));
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


        tvTenQuanAn.setText(quanAn.getTenquanan());
        tvDiaChiQuanAn.setText(quanAn.getChiNhanhQuanAnList().get(0).getDiachi());
        tvTongHinhAnh.setText(quanAn.getHinhanhquanan().size() + "");
        tvTongBinhLuan.setText(quanAn.getBinhLuanList().size() + "");
        tvGioMoCuaDongCua.setText(quanAn.getGiomocua() + " - "+ quanAn.getGiodongcua());
        StorageReference storageHinhAnh = FirebaseStorage.getInstance().getReference().child("hinhanh").child(quanAn.getHinhanhquanan().get(0));
        long ONE_MEGABYTE = 1024 * 1024;
        storageHinhAnh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                imHinhQuanAn.setImageBitmap(bitmap);
            }
        });

        layHinhTienIch();

        if(quanAn.getVideogioithieu() != null){
            videoView.setVisibility(View.VISIBLE);
            imPlay.setVisibility(View.VISIBLE);
            imHinhQuanAn.setVisibility(View.GONE);

            StorageReference store = FirebaseStorage.getInstance().getReference().child("videos").child(quanAn.getVideogioithieu());
            store.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    videoView.setVideoURI(uri);
                    videoView.seekTo(6);
                }
            });

            imPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    videoView.start();
                    MediaController mediaController = new MediaController(ChiTietQuanAnActivity.this);
                    videoView.setMediaController(mediaController);
                    imPlay.setVisibility(View.GONE);
                }
            });

        }else {
            videoView.setVisibility(View.GONE);
            imPlay.setVisibility(View.GONE);
            imHinhQuanAn.setVisibility(View.VISIBLE);
        }



        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iTrangChu = new Intent(ChiTietQuanAnActivity.this, MainActivity.class);
                startActivity(iTrangChu);
                finish();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true);
        recyclerBinhLuan.setLayoutManager(layoutManager);
        AdapterRecyclerBinhLuan adapterRecyclerBinhLuan = new AdapterRecyclerBinhLuan
                (this,R.layout.custom_layout_binh_luan,quanAn.getBinhLuanList());
        recyclerBinhLuan.setAdapter(adapterRecyclerBinhLuan);
        adapterRecyclerBinhLuan.notifyDataSetChanged();

        nestedScrollChiTietQuanAn.smoothScrollTo(0,0);

        btnDanDuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iDanDuong = new Intent(ChiTietQuanAnActivity.this,DanDuongActivity.class);
                iDanDuong.putExtra("latitude",quanAn.getChiNhanhQuanAnList().get(0).getLatitude());
                iDanDuong.putExtra("longitude",quanAn.getChiNhanhQuanAnList().get(0).getLongitude());
                startActivity(iDanDuong);
            }
        });

        btnBinhLuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iThemBinhLuan = new Intent(ChiTietQuanAnActivity.this,ThemBinhLuanActivity.class);
                iThemBinhLuan.putExtra("maquanan",quanAn.getMaquanan());
                iThemBinhLuan.putExtra("tenquan",quanAn.getTenquanan());
                iThemBinhLuan.putExtra("diachi",quanAn.getChiNhanhQuanAnList().get(0).getDiachi());
//                iThemBinhLuan.putExtra("quanan",quanAn);
                startActivity(iThemBinhLuan);
            }
        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        double latitude = quanAn.getChiNhanhQuanAnList().get(0).getLatitude();
        double longtitude = quanAn.getChiNhanhQuanAnList().get(0).getLongitude();
        LatLng latLng = new LatLng(latitude,longtitude);

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude,longtitude))
                .title(quanAn.getTenquanan()));

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,16);
        googleMap.moveCamera(cameraUpdate);

    }

    private void layHinhTienIch(){

        for (String matienich : quanAn.getTienich()){
            DatabaseReference nodeTienIch = FirebaseDatabase.getInstance().getReference().child("quanlytienichs").child(matienich);
            nodeTienIch.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    TienIch tienIch = dataSnapshot.getValue(TienIch.class);

                    StorageReference storageHinhAnh = FirebaseStorage.getInstance().getReference().child("hinhtienich").child(tienIch.getHinhtienich());
                    long ONE_MEGABYTE = 1024 * 1024;
                    storageHinhAnh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                            ImageView imHinhTienIch = new ImageView(ChiTietQuanAnActivity.this);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(100,100);
                            layoutParams.setMargins(10,10,10,10);
                            imHinhTienIch.setLayoutParams(layoutParams);
                            imHinhTienIch.setPadding(5,5,5,5);
                            imHinhTienIch.setScaleType(ImageView.ScaleType.FIT_XY);

                            imHinhTienIch.setImageBitmap(bitmap);
                            lnTienIch.addView(imHinhTienIch);



                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }
}
