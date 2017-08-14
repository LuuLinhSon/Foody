package com.project.luulinhson.foody.Model.Object;

/**
 * Created by Admin on 8/6/2017.
 */

public class HinhGallery {

    String duongdan;
    boolean isCheck;

    public HinhGallery(String duongdan,boolean isCheck){
        this.duongdan = duongdan;
        this.isCheck = isCheck;
    }


    public String getDuongdan() {
        return duongdan;
    }

    public void setDuongdan(String duongdan) {
        this.duongdan = duongdan;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }


}
