package com.project.luulinhson.foody.View.Main.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.project.luulinhson.foody.Adapter.AdapterRecyclerODau;
import com.project.luulinhson.foody.Model.Object.QuanAn;
import com.project.luulinhson.foody.Presenter.Main.HandleQuanAn;
import com.project.luulinhson.foody.R;
import com.project.luulinhson.foody.View.Main.ViewQuanAn;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Admin on 7/20/2017.
 */

public class FragmentODau extends Fragment implements ViewQuanAn{

    RecyclerView recyclerODau;
    HandleQuanAn handleQuanAn;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;
    Location vitrihientai;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_odau,container,false);


        sharedPreferences = getContext().getSharedPreferences("toadohientai", Context.MODE_PRIVATE);
        String latitude = sharedPreferences.getString("latitude","0");
        String longtitude = sharedPreferences.getString("longtitude","0");
        vitrihientai = new Location("");
        vitrihientai.setLatitude(Double.parseDouble(latitude));
        vitrihientai.setLongitude(Double.parseDouble(longtitude));

        Log.d("kiemtralongtitude", "onStart: " + "OK");

        handleQuanAn = new HandleQuanAn(this);
        handleQuanAn.HandleQuanAn(vitrihientai);

        recyclerODau = (RecyclerView) view.findViewById(R.id.recyclerODau);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBarTrangChu);


        return view;
    }


    @Override
    public void LayDanhSachQuanAn(List<QuanAn> quanAnList) {
        progressBar.setVisibility(View.INVISIBLE);
        Log.d("kiemtra", "LayDanhSachQuanAn: " + quanAnList.size() + "");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        AdapterRecyclerODau adapterRecyclerODau = new AdapterRecyclerODau(getContext(),R.layout.custom_recyclerview_odau,quanAnList);
        recyclerODau.setLayoutManager(layoutManager);
        recyclerODau.setAdapter(adapterRecyclerODau);
        adapterRecyclerODau.notifyDataSetChanged();
    }

    @Override
    public void LayDanhSachQuanAnThatBai() {

    }
}
