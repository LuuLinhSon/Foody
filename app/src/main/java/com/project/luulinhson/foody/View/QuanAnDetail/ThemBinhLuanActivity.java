package com.project.luulinhson.foody.View.QuanAnDetail;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.project.luulinhson.foody.Adapter.AdapterHinhDuocChon;
import com.project.luulinhson.foody.Model.Object.BinhLuan;
import com.project.luulinhson.foody.Model.Object.QuanAn;
import com.project.luulinhson.foody.Presenter.BinhLuan.XuLyDangBinhLuan;
import com.project.luulinhson.foody.R;

import java.util.ArrayList;
import java.util.List;

public class ThemBinhLuanActivity extends AppCompatActivity implements View.OnClickListener,ViewBinhLuan{

    TextView tvTenQuanAn,tvDiaChiQuanAn,tvDang;
    EditText edTieuDeBinhLuan,edNoiDungBinhLuan;
    ImageButton imbGallery;
    public static final int REQUES_CODE_GALLERY = 6666;
    RecyclerView recyclerHinhDuocChon;
    String maquanan;
    XuLyDangBinhLuan xuLyDangBinhLuan;
    List<String> listHinhDuocChon;
    QuanAn quanAn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_binh_luan);

//        quanAn = getIntent().getParcelableExtra("quanan");

        maquanan = getIntent().getStringExtra("maquanan");
        String tenquanan = getIntent().getStringExtra("tenquan");
        String diachi = getIntent().getStringExtra("diachi");

        tvTenQuanAn = (TextView) findViewById(R.id.tvTenQuanAn);
        tvDiaChiQuanAn = (TextView) findViewById(R.id.tvDiaChi);
        edTieuDeBinhLuan = (EditText) findViewById(R.id.edTieuDeBinhLuan);
        edNoiDungBinhLuan = (EditText) findViewById(R.id.edNoiDungBinhLuan);
        imbGallery = (ImageButton) findViewById(R.id.imbGallery);
        recyclerHinhDuocChon = (RecyclerView) findViewById(R.id.recyclerHinhDuocChon);
        tvDang = (TextView) findViewById(R.id.tvDang);

        xuLyDangBinhLuan = new XuLyDangBinhLuan(this);


        imbGallery.setOnClickListener(this);
        tvDang.setOnClickListener(this);

        tvTenQuanAn.setText(tenquanan);
        tvDiaChiQuanAn.setText(diachi);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.imbGallery:
                Intent iGallery = new Intent(ThemBinhLuanActivity.this,ChonHinhGalleryActivity.class);
                startActivityForResult(iGallery,REQUES_CODE_GALLERY);
                break;
            case R.id.tvDang:
                SharedPreferences sf = getSharedPreferences("userid",MODE_PRIVATE);
                String uid = sf.getString("uid","");
                BinhLuan binhLuan = new BinhLuan();
                binhLuan.setTieude(edTieuDeBinhLuan.getText().toString());
                binhLuan.setNoidung(edNoiDungBinhLuan.getText().toString());
                binhLuan.setChamdiem(0);
                binhLuan.setLuotthich(0);
                binhLuan.setMauser(uid);
                xuLyDangBinhLuan.DangBinhLuan(maquanan,binhLuan,listHinhDuocChon);


                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUES_CODE_GALLERY){
            if(resultCode == RESULT_OK){
                listHinhDuocChon = data.getStringArrayListExtra("hinhduocchon");
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ThemBinhLuanActivity.this,LinearLayoutManager.HORIZONTAL,false);
                AdapterHinhDuocChon adapterHinhDuocChon = new AdapterHinhDuocChon(ThemBinhLuanActivity.this,R.layout.custom_layout_hinhduocchon,listHinhDuocChon);
                recyclerHinhDuocChon.setLayoutManager(layoutManager);
                recyclerHinhDuocChon.setAdapter(adapterHinhDuocChon);
                adapterHinhDuocChon.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void DangBinhLuanThanhCong() {
        Toast.makeText(this,"Đăng bình luận thành công",Toast.LENGTH_LONG).show();
//        Intent iChiTietQuanAn = new Intent(ThemBinhLuanActivity.this,ChiTietQuanAnActivity.class);
//        iChiTietQuanAn.putExtra("quanan",quanAn);
//        startActivity(iChiTietQuanAn);
        finish();
    }

    @Override
    public void DangBinhLuanThatBai() {
        Toast.makeText(this,"Đăng bình luận thất bại",Toast.LENGTH_LONG).show();
    }
}
