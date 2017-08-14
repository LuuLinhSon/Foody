package com.project.luulinhson.foody.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.project.luulinhson.foody.Model.Object.HinhGallery;
import com.project.luulinhson.foody.R;

import java.util.List;

/**
 * Created by Admin on 8/6/2017.
 */

public class AdapterGallery extends RecyclerView.Adapter<AdapterGallery.ViewHolder> {

    Context context;
    int layout;
    List<HinhGallery> duongDanList;
    public AdapterGallery(Context context, int layout, List<HinhGallery> duongDanList){
        this.context = context;
        this.layout = layout;
        this.duongDanList = duongDanList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imGallery;
        CheckBox checkBoxGallery;
        public ViewHolder(View itemView) {
            super(itemView);
            imGallery = (ImageView) itemView.findViewById(R.id.imGallery);
            checkBoxGallery = (CheckBox) itemView.findViewById(R.id.checkboxGallery);
        }
    }


    @Override
    public AdapterGallery.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterGallery.ViewHolder viewHolder, final int i) {
        final HinhGallery hinhGallery = duongDanList.get(i);
        Uri uri = Uri.parse(hinhGallery.getDuongdan());
        viewHolder.imGallery.setImageURI(uri);
        viewHolder.checkBoxGallery.setChecked(hinhGallery.isCheck());

        viewHolder.checkBoxGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                duongDanList.get(i).setCheck(checkBox.isChecked());
            }
        });

    }

    @Override
    public int getItemCount() {
        return duongDanList.size();
    }


}
