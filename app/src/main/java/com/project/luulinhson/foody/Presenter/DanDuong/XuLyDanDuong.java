package com.project.luulinhson.foody.Presenter.DanDuong;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;
import com.project.luulinhson.foody.Model.DanDuong.DanDuongModel;
import com.project.luulinhson.foody.ServerCallback.ServerCallbackJsonGoogleMapAPI;
import com.project.luulinhson.foody.View.QuanAnDetail.ViewDanDuong;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Admin on 8/3/2017.
 */

public class XuLyDanDuong implements iXulyDanDuong {

    DanDuongModel danDuongModel;
    ViewDanDuong viewDanDuong;
    public XuLyDanDuong(ViewDanDuong viewDanDuong){
        this.viewDanDuong = viewDanDuong;
        danDuongModel = new DanDuongModel();
    }

    @Override
    public void XuLyDanDuong(Context context,String url) {
        danDuongModel.LayJsonGoogleDirection(context,url,new ServerCallbackJsonGoogleMapAPI() {
            @Override
            public void onSuccess(String jsonString) {
                Log.d("jsonString", "onSuccess: " + jsonString);
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONArray jsonArrayRoutes = jsonObject.getJSONArray("routes");
                    for (int i = 0;i < jsonArrayRoutes.length();i++){
                        JSONObject jsonObject1 = jsonArrayRoutes.getJSONObject(i);
                        JSONObject jsonOverview = jsonObject1.getJSONObject("overview_polyline");
                        String point = jsonOverview.getString("points");

                        List<LatLng> latLngList = PolyUtil.decode(point);
                        viewDanDuong.LayDanDuongThanhCong(latLngList);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
