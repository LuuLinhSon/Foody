package com.project.luulinhson.foody.Model.DanDuong;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.luulinhson.foody.ServerCallback.ServerCallbackJsonGoogleMapAPI;

/**
 * Created by Admin on 8/3/2017.
 */

public class DanDuongModel {

    public void LayJsonGoogleDirection(Context context,String url,final ServerCallbackJsonGoogleMapAPI serverCallbackJsonGoogleMapAPI){

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                serverCallbackJsonGoogleMapAPI.onSuccess(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
