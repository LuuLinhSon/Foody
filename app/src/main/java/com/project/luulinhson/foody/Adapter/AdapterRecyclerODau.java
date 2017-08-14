package com.project.luulinhson.foody.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.project.luulinhson.foody.Model.Object.BinhLuan;
import com.project.luulinhson.foody.Model.Object.ChiNhanhQuanAn;
import com.project.luulinhson.foody.Model.Object.QuanAn;
import com.project.luulinhson.foody.R;
import com.project.luulinhson.foody.View.QuanAnDetail.ChiTietQuanAnActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admin on 7/22/2017.
 */

public class AdapterRecyclerODau extends RecyclerView.Adapter<AdapterRecyclerODau.ViewHolder> {

    Context context;
    int layout;
    List<QuanAn> quanAnList;
    public AdapterRecyclerODau(Context context, int layout, List<QuanAn> quanAnList){
        this.context = context;
        this.layout = layout;
        this.quanAnList = quanAnList;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenQuanAnODau,tvTieuDeBinhLuan1,tvTieuDeBinhLuan2,tvNoiDungBinhLuan1,tvNoiDungBinhLuan2,
                tvChamDiemBinhLuan1,tvChamDiemBinhLuan2,tvTongBinhLuan,tvTongHinhBinhLuan,tvDiemTrungBinh,tvDiaChiQuanAnODau,tvKhoanngCach;
        Button btnDatHangODau;
        ImageView imHinhQuanAnODau;
        ProgressBar progressBarHinhQuanAn;
        CircleImageView circleImageUser1,circleImageUser2;
        LinearLayout lnBinhLuan1,lnBinhLuan2;
        FrameLayout itemTong;
        public ViewHolder(View itemView) {
            super(itemView);
            tvTenQuanAnODau = (TextView) itemView.findViewById(R.id.tvTenQuanAnODau);
            btnDatHangODau = (Button) itemView.findViewById(R.id.btnDatHangODau);
            imHinhQuanAnODau = (ImageView) itemView.findViewById(R.id.imHinhQuanAnODau);
            progressBarHinhQuanAn = (ProgressBar) itemView.findViewById(R.id.progress_bar_image_quanan);
            tvTieuDeBinhLuan1 = (TextView) itemView.findViewById(R.id.tvTieuDeBinhLuan1);
            tvTieuDeBinhLuan2 = (TextView) itemView.findViewById(R.id.tvTieuDeBinhLuan2);
            tvNoiDungBinhLuan1 = (TextView) itemView.findViewById(R.id.tvNoiDungBinhLuan1);
            tvNoiDungBinhLuan2 = (TextView) itemView.findViewById(R.id.tvNoiDungBinhLuan2);
            circleImageUser1 = (CircleImageView) itemView.findViewById(R.id.circleImageUser1);
            circleImageUser2 = (CircleImageView) itemView.findViewById(R.id.circleImageUser2);
            lnBinhLuan1 = (LinearLayout) itemView.findViewById(R.id.lnBinhLuan1);
            lnBinhLuan2 = (LinearLayout) itemView.findViewById(R.id.lnBinhLuan2);
            tvChamDiemBinhLuan1 = (TextView) itemView.findViewById(R.id.tvChamDiemBinhLuan1);
            tvChamDiemBinhLuan2 = (TextView) itemView.findViewById(R.id.tvChamDiemBinhLuan2);
            tvTongBinhLuan = (TextView) itemView.findViewById(R.id.tvTongBinhLuan);
            tvTongHinhBinhLuan = (TextView) itemView.findViewById(R.id.tvTongHinhBinhLuan);
            tvDiemTrungBinh = (TextView) itemView.findViewById(R.id.tvDiemTrungBinh);
            tvDiaChiQuanAnODau = (TextView) itemView.findViewById(R.id.tvDiaChiQuanAnODau);
            tvKhoanngCach = (TextView) itemView.findViewById(R.id.tvKhoangCach);
            itemTong = (FrameLayout) itemView.findViewById(R.id.itemTong);

        }
    }

    @Override
    public AdapterRecyclerODau.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterRecyclerODau.ViewHolder holder, int position) {
        final QuanAn quanAn = quanAnList.get(position);
        holder.tvTenQuanAnODau.setText(quanAn.getTenquanan());
        if(quanAn.isGiaohang()){
            holder.btnDatHangODau.setVisibility(View.VISIBLE);
        }

        if(quanAn.getHinhanhquanan().size() > 0){
            Log.d("kiemtrahinhanh", "onBindViewHolder: " + quanAn.getHinhanhquanan().size() + "");
            StorageReference storageHinhAnh = FirebaseStorage.getInstance().getReference().child("hinhanh").child(quanAn.getHinhanhquanan().get(0));
            long ONE_MEGABYTE = 1024 * 1024;
            storageHinhAnh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    holder.imHinhQuanAnODau.setImageBitmap(bitmap);
                    holder.progressBarHinhQuanAn.setVisibility(View.INVISIBLE);
                }
            });
        }

        if(quanAn.getBinhLuanList().size() > 0){

            BinhLuan binhLuan = quanAn.getBinhLuanList().get(0);
            holder.tvTieuDeBinhLuan1.setText(binhLuan.getTieude());
            holder.tvNoiDungBinhLuan1.setText(binhLuan.getNoidung());
            holder.tvChamDiemBinhLuan1.setText(binhLuan.getChamdiem() + "");

            StorageReference storageHinhAnh = FirebaseStorage.getInstance().getReference().child("thanhvien").child(binhLuan.getThanhVien().getHinhanh());
            long ONE_MEGABYTE = 1024 * 1024;
            storageHinhAnh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    holder.circleImageUser1.setImageBitmap(bitmap);
                }
            });

            if(quanAn.getBinhLuanList().size() > 1){
                BinhLuan binhLuan2 = quanAn.getBinhLuanList().get(1);
                holder.tvTieuDeBinhLuan2.setText(binhLuan2.getTieude());
                holder.tvNoiDungBinhLuan2.setText(binhLuan2.getNoidung());
                holder.tvChamDiemBinhLuan2.setText(binhLuan2.getChamdiem() + "");

                StorageReference storageHinhAnh2 = FirebaseStorage.getInstance().getReference().child("thanhvien").child(binhLuan2.getThanhVien().getHinhanh());
                long ONE_MEGABYTE_2 = 1024 * 1024;
                storageHinhAnh2.getBytes(ONE_MEGABYTE_2).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                        holder.circleImageUser2.setImageBitmap(bitmap);
                    }
                });
                holder.tvTongBinhLuan.setText(quanAn.getBinhLuanList().size() + "");

                int tonghinhbinhluan = 0;
                double tongdiem = 0;
                for (BinhLuan binhLuan1 : quanAn.getBinhLuanList()){
                    tonghinhbinhluan += binhLuan1.getHinhAnhList().size();
                    tongdiem += binhLuan1.getChamdiem();
                }

                double diemtrungbinh = tongdiem/quanAn.getBinhLuanList().size();
                holder.tvTongHinhBinhLuan.setText(tonghinhbinhluan + "");
                holder.tvDiemTrungBinh.setText(String.format("%.1f",diemtrungbinh));
            }
        }else {
            holder.lnBinhLuan1.setVisibility(View.GONE);
            holder.lnBinhLuan2.setVisibility(View.GONE);
            holder.tvTongBinhLuan.setText("0");
            holder.tvTongHinhBinhLuan.setText("0");
        }

        // Lấy địa chỉ quán ăn và khoảng cách km
        if(quanAn.getChiNhanhQuanAnList().size() > 0){
            ChiNhanhQuanAn chiNhanhQuanAnTam = quanAn.getChiNhanhQuanAnList().get(0);
            for(ChiNhanhQuanAn chiNhanhQuanAn : quanAn.getChiNhanhQuanAnList()){
                if(chiNhanhQuanAnTam.getKhoanngcach() > chiNhanhQuanAn.getKhoanngcach()){
                    chiNhanhQuanAnTam = chiNhanhQuanAn;
                }
            }

            holder.tvDiaChiQuanAnODau.setText(chiNhanhQuanAnTam.getDiachi());
            holder.tvKhoanngCach.setText(String.format("%.1f",chiNhanhQuanAnTam.getKhoanngcach()) + " km");
            Log.d("sokm", "onBindViewHolder: " + String.format("%.1f",chiNhanhQuanAnTam.getKhoanngcach()) + " km");

        }

        holder.itemTong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iChiTietQuanAn = new Intent(context,ChiTietQuanAnActivity.class);
                Log.d("video2", "onClick: " + quanAn.getVideogioithieu());
                iChiTietQuanAn.putExtra("quanan",quanAn);
                context.startActivity(iChiTietQuanAn);

            }
        });

    }

    @Override
    public int getItemCount() {
        return quanAnList.size();
    }

}
