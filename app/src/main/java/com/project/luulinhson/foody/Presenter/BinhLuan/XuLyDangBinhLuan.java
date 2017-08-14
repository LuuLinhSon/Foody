package com.project.luulinhson.foody.Presenter.BinhLuan;

import com.project.luulinhson.foody.Model.Object.BinhLuan;
import com.project.luulinhson.foody.Presenter.DanDuong.XuLyDanDuong;
import com.project.luulinhson.foody.ServerCallback.ServerCallbackThongTinThanhVien;
import com.project.luulinhson.foody.View.QuanAnDetail.ViewBinhLuan;

import java.util.List;

/**
 * Created by Admin on 8/8/2017.
 */

public class XuLyDangBinhLuan implements iDangBinhLuan {


    BinhLuan binhLuan;
    ViewBinhLuan viewBinhLuan;
    public XuLyDangBinhLuan(ViewBinhLuan viewBinhLuan){
        this.viewBinhLuan = viewBinhLuan;
        binhLuan = new BinhLuan();
    }

    @Override
    public void DangBinhLuan(String maquanan, BinhLuan binhLuan, List<String> hinhList) {
        binhLuan.DangBinhLuan(maquanan, binhLuan, hinhList, new ServerCallbackThongTinThanhVien() {
            @Override
            public void onSuccess(boolean bl) {
                if(bl){
                    viewBinhLuan.DangBinhLuanThanhCong();
                }else {
                    viewBinhLuan.DangBinhLuanThatBai();
                }
            }
        });
    }
}
