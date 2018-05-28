package com.example.whiteuser.shop;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends ArrayAdapter<Data> {

    private Context context;
    private List<Data> list;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Data data = list.get(position);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View view = inflater.inflate(R.layout.custom_layout_goods,null);

        TextView tvName = view.findViewById(R.id.item_tv_name);
        TextView tvPrice = view.findViewById(R.id.item_tv_price);

        tvName.setText(convertToLongString(data.getName(),10));
        tvPrice.setText(String.valueOf(data.getPrice()));

        return view;
    }

    Adapter(@NonNull Context context, int resource, ArrayList<Data> list)
    {
        super(context, resource,list);

        this.context = context;
        this.list = list;
    }

    private String convertToLongString(String text,int limit)
    {
        /*
        * This function get two parameters
        * First is string, text which need to cat
        * Limit it number, so when lenght more then limit
        * Will cut down
        * and add ...
        * */
        if(text.length() > limit)
        {
            char[] buffer = text.toCharArray();

            StringBuilder stringBuilder = new StringBuilder();

            for(int i = 0; i < limit; i++)
                stringBuilder.append(buffer[i]);

            stringBuilder.append("...");

            text = stringBuilder.toString();
        }

        return text;
    }
}
