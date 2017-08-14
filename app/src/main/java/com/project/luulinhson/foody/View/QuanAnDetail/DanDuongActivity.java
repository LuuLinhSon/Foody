package com.project.luulinhson.foody.View.QuanAnDetail;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.project.luulinhson.foody.Presenter.DanDuong.XuLyDanDuong;
import com.project.luulinhson.foody.R;

import java.util.ArrayList;
import java.util.List;

public class DanDuongActivity extends AppCompatActivity implements OnMapReadyCallback,ViewDanDuong{

    GoogleMap googleMap;
    MapFragment mapFragment;
    double latitude = 0;
    double longitude = 0;
    SharedPreferences sharedPreferences;
    Location vitrihientai;
    String duongdan = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dan_duong);

        latitude = getIntent().getDoubleExtra("latitude",0);
        longitude = getIntent().getDoubleExtra("longitude",0);

        sharedPreferences = getSharedPreferences("toadohientai", Context.MODE_PRIVATE);
        String latitudehientai = sharedPreferences.getString("latitude","0");
        String longtitudehientai = sharedPreferences.getString("longtitude","0");
        vitrihientai = new Location("");
//        vitrihientai.setLatitude(Double.parseDouble(latitudehientai));
//        vitrihientai.setLongitude(Double.parseDouble(longtitudehientai));
        vitrihientai.setLatitude(20.970996);
        vitrihientai.setLongitude(105.955838);

        duongdan = "https://maps.googleapis.com/maps/api/directions/json?origin=" + vitrihientai.getLatitude() + "," + vitrihientai.getLongitude() +
                "&destination=" + latitude + "," + longitude + "&key=AIzaSyA_kR3ZsVi1-1kF8jwh5xIm1nG-GuNKeFk";
//        duongdan = "https://maps.googleapis.com/maps/api/directions/json?origin=20.9971023,105.8646043&destination=21.0043885,105.8510689&key=AIzaSyA_kR3ZsVi1-1kF8jwh5xIm1nG-GuNKeFk";

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        XuLyDanDuong xuLyDanDuong = new XuLyDanDuong(this);
        xuLyDanDuong.XuLyDanDuong(this,duongdan);

    }

    @Override
    public void LayDanDuongThanhCong(List<LatLng> latLngList) {
        Log.d("listlatlng", "LayDanDuongThanhCong: " + latLngList.size());

        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(R.color.colorGoogle);
        polylineOptions.width(20);

        for (LatLng toado : latLngList){
            polylineOptions.add(toado);
        }

        Polyline polyline = googleMap.addPolyline(polylineOptions);


        LatLng latLngViTriHienTai = new LatLng(vitrihientai.getLatitude(),vitrihientai.getLongitude());
        googleMap.addMarker(new MarkerOptions()
                .position(latLngViTriHienTai));

        LatLng latLngViTriQuanAn = new LatLng(latitude,longitude);
        googleMap.addMarker(new MarkerOptions()
                .position(latLngViTriQuanAn));

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLngViTriHienTai,16);
        googleMap.moveCamera(cameraUpdate);
    }

}
