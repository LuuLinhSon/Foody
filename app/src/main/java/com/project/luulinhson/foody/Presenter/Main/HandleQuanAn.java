package com.project.luulinhson.foody.Presenter.Main;

import android.location.Location;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.View;

import com.project.luulinhson.foody.Model.Object.QuanAn;
import com.project.luulinhson.foody.ServerCallback.ServerCallback;
import com.project.luulinhson.foody.View.Main.ViewQuanAn;

import java.util.List;

/**
 * Created by Admin on 7/22/2017.
 */

public class HandleQuanAn implements iHandleQuanAn{

    ViewQuanAn viewQuanAn;
    QuanAn quanAn;


    public HandleQuanAn(ViewQuanAn viewQuanAn){
        this.viewQuanAn = viewQuanAn;
        quanAn = new QuanAn();
    }
    @Override
    public void HandleQuanAn(final Location vitrihientai) {

        quanAn.getDanhSachQuanAn(new ServerCallback() {
            @Override
            public void onSuccess(List<QuanAn> quanAnList) {
                Log.d("CHECK____________", "onSuccess: " + quanAnList.size() + "");
                if(quanAnList.size() > 0){
                    viewQuanAn.LayDanhSachQuanAn(quanAnList);
                }else {
                    viewQuanAn.LayDanhSachQuanAnThatBai();
                }

            }
        },vitrihientai);
    }
}
