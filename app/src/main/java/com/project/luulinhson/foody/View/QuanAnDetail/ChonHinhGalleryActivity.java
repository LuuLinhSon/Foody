package com.project.luulinhson.foody.View.QuanAnDetail;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.project.luulinhson.foody.Adapter.AdapterGallery;
import com.project.luulinhson.foody.Model.Object.HinhGallery;
import com.project.luulinhson.foody.R;

import java.util.ArrayList;
import java.util.List;

public class ChonHinhGalleryActivity extends AppCompatActivity implements View.OnClickListener {

    List<HinhGallery> hinhGalleryList;
    List<String> hinhGalleryDuocChonList;
    TextView tvXong;
    RecyclerView recyclerGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_hinh_gallery);

        recyclerGallery = (RecyclerView) findViewById(R.id.recyclerGallery);
        tvXong = (TextView) findViewById(R.id.tvXong);

        tvXong.setOnClickListener(this);

        hinhGalleryList = new ArrayList<>();
        hinhGalleryDuocChonList = new ArrayList<>();

        int checkPermissonCourceLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (checkPermissonCourceLocation != PackageManager.PERMISSION_GRANTED) {
            android.support.v4.app.ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},222);

        } else {
            LayTatCaHinhAnhTrongTheNho();
        }

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2, LinearLayoutManager.VERTICAL,true);
        recyclerGallery.setLayoutManager(layoutManager);
        AdapterGallery adapterGallery = new AdapterGallery(this,R.layout.custom_layout_gallery,hinhGalleryList);
        recyclerGallery.setAdapter(adapterGallery);
        recyclerGallery.smoothScrollToPosition(recyclerGallery.getAdapter().getItemCount() - 1);
        adapterGallery.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 222:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LayTatCaHinhAnhTrongTheNho();
                }
                break;
        }
    }

    public void LayTatCaHinhAnhTrongTheNho(){
        String [] projection = {MediaStore.Images.Media.DATA};
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri,projection,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String duongdan = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            HinhGallery hinhGallery = new HinhGallery(duongdan,false);
            hinhGalleryList.add(hinhGallery);
            cursor.moveToNext();

        }


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.tvXong:
                for (HinhGallery hinhGallery : hinhGalleryList){
                    if(hinhGallery.isCheck()){
                        hinhGalleryDuocChonList.add(hinhGallery.getDuongdan());
                    }
                }
                Intent intent = getIntent();
                intent.putStringArrayListExtra("hinhduocchon", (ArrayList<String>) hinhGalleryDuocChonList);
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }
}
