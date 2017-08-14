package com.project.luulinhson.foody.Presenter.Register;

import com.project.luulinhson.foody.Model.Object.ThanhVien;
import com.project.luulinhson.foody.ServerCallback.ServerCallbackThongTinThanhVien;
import com.project.luulinhson.foody.View.Register.ViewDangKyThongTinThanhVien;

/**
 * Created by Admin on 7/23/2017.
 */

public class XuLyDangKyThongTinThanhVien implements iXuLyDangKyThongTinThanhVien {

    ViewDangKyThongTinThanhVien viewDangKyThongTinThanhVien;
    ThanhVien thanhVien;
    public XuLyDangKyThongTinThanhVien(ViewDangKyThongTinThanhVien viewDangKyThongTinThanhVien){
        this.viewDangKyThongTinThanhVien = viewDangKyThongTinThanhVien;
        thanhVien = new ThanhVien();
    }

    @Override
    public void XuLyDangKyThongTinThanhVien(String userId, ThanhVien thanhVien) {
        thanhVien.DangKyThongTinThanhVien(userId, thanhVien, new ServerCallbackThongTinThanhVien() {
            @Override
            public void onSuccess(boolean bl) {
                if(bl){
                    viewDangKyThongTinThanhVien.dangKyThanhCong();
                }else {
                    viewDangKyThongTinThanhVien.dangKyThatBai();
                }
            }
        });
    }
}
