package com.project.luulinhson.foody.View.Main;

import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.project.luulinhson.foody.Adapter.AdapterViewPagerTrangChu;
import com.project.luulinhson.foody.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,ViewPager.OnPageChangeListener,RadioGroup.OnCheckedChangeListener{

    LinearLayout lnMoiNhat,lnDanhMuc,lnKhuVuc;
    TextView tvMoiNhat,tvDanhMuc,tvKhuVuc;
    ViewPager viewPager;
    RadioButton rbODau,rbAnGi;
    RadioGroup rgTrangChu;
    FirebaseAuth firebaseAuth;
    SharedPreferences sfUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        sfUserId = getSharedPreferences("userid",MODE_PRIVATE);

        lnMoiNhat = (LinearLayout) findViewById(R.id.lnMoiNhat);
        lnDanhMuc = (LinearLayout) findViewById(R.id.lnDanhMuc);
        lnKhuVuc = (LinearLayout) findViewById(R.id.lnKhuVuc);
        tvMoiNhat = (TextView) findViewById(R.id.tvMoiNhat);
        tvDanhMuc = (TextView) findViewById(R.id.tvDanhMuc);
        tvKhuVuc = (TextView) findViewById(R.id.tvKhuVuc);
        viewPager = (ViewPager) findViewById(R.id.viewPagerTrangChu);
        rbODau = (RadioButton) findViewById(R.id.rbODau);
        rbAnGi = (RadioButton) findViewById(R.id.rbAnGi);
        rgTrangChu = (RadioGroup) findViewById(R.id.rgTrangChu);

        AdapterViewPagerTrangChu adapterViewPagerTrangChu = new AdapterViewPagerTrangChu(getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPagerTrangChu);
        adapterViewPagerTrangChu.notifyDataSetChanged();
        viewPager.addOnPageChangeListener(this);

        lnMoiNhat.setOnClickListener(this);
        lnDanhMuc.setOnClickListener(this);
        lnKhuVuc.setOnClickListener(this);
        rgTrangChu.setOnCheckedChangeListener(this);

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String uid = firebaseUser.getUid();
        Log.d("uid", "onCreate: " + uid);
        SharedPreferences.Editor editor = sfUserId.edit();
        editor.putString("uid",uid);
        editor.commit();

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

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        if(i == 0){
            rbODau.setChecked(true);
        }else if(i == 1){
            rbAnGi.setChecked(true);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rbODau:
                viewPager.setCurrentItem(0);
                break;
            case R.id.rbAnGi:
                viewPager.setCurrentItem(1);
                break;
        }
    }
}
