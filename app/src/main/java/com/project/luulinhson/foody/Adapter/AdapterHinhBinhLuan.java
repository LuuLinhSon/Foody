package com.project.luulinhson.foody.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.project.luulinhson.foody.Model.Object.BinhLuan;
import com.project.luulinhson.foody.R;
import com.project.luulinhson.foody.View.QuanAnDetail.ChiTietBinhLuanActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 7/31/2017.
 */

public class AdapterHinhBinhLuan extends RecyclerView.Adapter<AdapterHinhBinhLuan.ViewHolder> {

    Context context;
    int layout;
    List<Bitmap> linksHinh;
    BinhLuan binhLuan;
    boolean isCheckChiTietBinhLuan;
    public AdapterHinhBinhLuan(Context context, int layout, List<Bitmap> linksHinh, BinhLuan binhLuan,boolean isCheckChiTietBinhLuan){
        this.context = context;
        this.layout = layout;
        this.linksHinh = linksHinh;
        this.binhLuan = binhLuan;
        this.isCheckChiTietBinhLuan = isCheckChiTietBinhLuan;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imHinhBinhLuan;
        TextView tvSoHinhAnhBinhLuan;
        FrameLayout khungSoHinhBinhLuan;
        public ViewHolder(View itemView) {
            super(itemView);
            imHinhBinhLuan = (ImageView) itemView.findViewById(R.id.imHinhBinhLuan);
            tvSoHinhAnhBinhLuan = (TextView) itemView.findViewById(R.id.tvSoHinhAnhBinhLuan);
            khungSoHinhBinhLuan = (FrameLayout) itemView.findViewById(R.id.khungSoHinhBinhLuan);
        }
    }


    @Override
    public AdapterHinhBinhLuan.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterHinhBinhLuan.ViewHolder viewHolder, int i) {

        viewHolder.imHinhBinhLuan.setImageBitmap(linksHinh.get(i));

        if(!isCheckChiTietBinhLuan){
            if(i == 3){
                int sohinhconlai = linksHinh.size() - 4;
                if(sohinhconlai > 0){
                    viewHolder.khungSoHinhBinhLuan.setVisibility(View.VISIBLE);
                    viewHolder.tvSoHinhAnhBinhLuan.setText("+" + sohinhconlai);

                    viewHolder.khungSoHinhBinhLuan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent iChiTietBinhLuan = new Intent(context, ChiTietBinhLuanActivity.class);
                            iChiTietBinhLuan.putExtra("binhluan",binhLuan);
                            context.startActivity(iChiTietBinhLuan);
                        }
                    });

                }
            }
        }
    }

    @Override
    public int getItemCount() {
        if(!isCheckChiTietBinhLuan){
            if(linksHinh.size() < 4){
                return linksHinh.size();
            }else {
                return 4;
            }
        }else {
            return linksHinh.size();
        }

    }


}
