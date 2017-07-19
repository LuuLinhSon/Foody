package com.project.luulinhson.foody.View.Main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.luulinhson.foody.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    LinearLayout lnMoiNhat,lnDanhMuc,lnKhuVuc;
    TextView tvMoiNhat,tvDanhMuc,tvKhuVuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lnMoiNhat = (LinearLayout) findViewById(R.id.lnMoiNhat);
        lnDanhMuc = (LinearLayout) findViewById(R.id.lnDanhMuc);
        lnKhuVuc = (LinearLayout) findViewById(R.id.lnKhuVuc);
        tvMoiNhat = (TextView) findViewById(R.id.tvMoiNhat);
        tvDanhMuc = (TextView) findViewById(R.id.tvDanhMuc);
        tvKhuVuc = (TextView) findViewById(R.id.tvKhuVuc);

        lnMoiNhat.setOnClickListener(this);
        lnDanhMuc.setOnClickListener(this);
        lnKhuVuc.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.lnMoiNhat:
                tvMoiNhat.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvDanhMuc.setTextColor(getResources().getColor(R.color.colorBlack));
                tvKhuVuc.setTextColor(getResources().getColor(R.color.colorBlack));
                break;
            case R.id.lnDanhMuc:
                tvDanhMuc.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvMoiNhat.setTextColor(getResources().getColor(R.color.colorBlack));
                tvKhuVuc.setTextColor(getResources().getColor(R.color.colorBlack));
                break;
            case R.id.lnKhuVuc:
                tvKhuVuc.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvDanhMuc.setTextColor(getResources().getColor(R.color.colorBlack));
                tvMoiNhat.setTextColor(getResources().getColor(R.color.colorBlack));
                break;
        }
    }
}
