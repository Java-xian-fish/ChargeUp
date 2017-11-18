package com.example.jiangtao.chargeup.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jiangtao.chargeup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiangtao on 2017/11/13.
 */
public class Main_ListAdapter extends ArrayAdapter<String>{

    private int resourceId;


    public Main_ListAdapter(Context context, int textViewResourceId,List<String> list){
        super(context,textViewResourceId,list);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        String str = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        TextView main_list_text = (TextView)view.findViewById(R.id.main_list_text);
        main_list_text.setText(str);
        main_list_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }
}
