package com.project.luulinhson.foody.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.project.luulinhson.foody.Model.Object.BinhLuan;
import com.project.luulinhson.foody.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admin on 7/30/2017.
 */

public class AdapterRecyclerBinhLuan extends RecyclerView.Adapter<AdapterRecyclerBinhLuan.ViewHolder> {

    Context context;
    int layout;
    List<BinhLuan> binhLuanList;

    public AdapterRecyclerBinhLuan(Context context, int layout, List<BinhLuan> binhLuanList){
        this.context = context;
        this.layout = layout;
        this.binhLuanList = binhLuanList;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imImageUser;
        TextView tvTieuDeBinhLuan,tvNoiDungBinhLuan,tvChamDiemBinhLuan;
        RecyclerView recyclerHinhBinhLuan;
        public ViewHolder(View itemView) {
            super(itemView);

            imImageUser = (CircleImageView) itemView.findViewById(R.id.circleImageUser);
            tvTieuDeBinhLuan = (TextView) itemView.findViewById(R.id.tvTieuDeBinhLuan);
            tvNoiDungBinhLuan = (TextView) itemView.findViewById(R.id.tvNoiDungBinhLuan);
            tvChamDiemBinhLuan = (TextView) itemView.findViewById(R.id.tvChamDiemBinhLuan);
            recyclerHinhBinhLuan = (RecyclerView) itemView.findViewById(R.id.recyclerHinhBinhLuan);
        }
    }


    @Override
    public AdapterRecyclerBinhLuan.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterRecyclerBinhLuan.ViewHolder holder, int position) {

        final BinhLuan binhLuan = binhLuanList.get(position);
        holder.tvTieuDeBinhLuan.setText(binhLuan.getTieude());
        holder.tvNoiDungBinhLuan.setText(binhLuan.getNoidung());
        holder.tvChamDiemBinhLuan.setText(binhLuan.getChamdiem() + "");

        StorageReference storageHinhAnh = FirebaseStorage.getInstance().getReference().child("hinhanh").child(binhLuan.getThanhVien().getHinhanh());
        long ONE_MEGABYTE = 1024 * 1024;
        storageHinhAnh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                holder.imImageUser.setImageBitmap(bitmap);
            }
        });

        final List<Bitmap> bitmapList = new ArrayList<>();

        for (String linkHinh : binhLuan.getHinhAnhList()){
            StorageReference storageHinhAnhBinhLuan = FirebaseStorage.getInstance().getReference().child("hinhanh").child(linkHinh);
            long ONE_MEGABYTE_2 = 1024 * 1024;
            storageHinhAnhBinhLuan.getBytes(ONE_MEGABYTE_2).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    bitmapList.add(bitmap);
                    if(bitmapList.size() == binhLuan.getHinhAnhList().size()){
                        AdapterHinhBinhLuan adapterHinhBinhLuan = new AdapterHinhBinhLuan(context,R.layout.custom_layout_hinh_binh_luan,bitmapList,binhLuan,false);
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context,2);
                        holder.recyclerHinhBinhLuan.setLayoutManager(layoutManager);
                        holder.recyclerHinhBinhLuan.setAdapter(adapterHinhBinhLuan);
                        adapterHinhBinhLuan.notifyDataSetChanged();
                    }

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if(binhLuanList.size() > 5){
            return 5;
        }else {
            return binhLuanList.size();
        }
    }


}
