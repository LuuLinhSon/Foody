package com.project.luulinhson.foody.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.project.luulinhson.foody.R;

import java.util.List;

/**
 * Created by Admin on 8/7/2017.
 */

public class AdapterHinhDuocChon extends RecyclerView.Adapter<AdapterHinhDuocChon.ViewHolder> {

    Context context;
    int layout;
    List<String> hinhDuocChonList;
    public AdapterHinhDuocChon(Context context, int layout, List<String> hinhDuocChonList){
        this.context = context;
        this.layout = layout;
        this.hinhDuocChonList = hinhDuocChonList;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imHinhDuocChon,imDelete;
        public ViewHolder(View itemView) {
            super(itemView);
            imHinhDuocChon = (ImageView) itemView.findViewById(R.id.imHinhDuocChon);
            imDelete = (ImageView) itemView.findViewById(R.id.imDelete);
        }
    }

    @Override
    public AdapterHinhDuocChon.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterHinhDuocChon.ViewHolder viewHolder, int i) {
        Uri uri = Uri.parse(hinhDuocChonList.get(i));
        viewHolder.imHinhDuocChon.setImageURI(uri);
        viewHolder.imDelete.setTag(i);

        viewHolder.imDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int vitri = (int) v.getTag();
                hinhDuocChonList.remove(vitri);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return hinhDuocChonList.size();
    }


}
