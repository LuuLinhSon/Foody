package com.project.luulinhson.foody.View.Main.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.luulinhson.foody.R;

/**
 * Created by Admin on 7/20/2017.
 */

public class FragmentAnGi extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_angi,container,false);
        return view;
    }
}
