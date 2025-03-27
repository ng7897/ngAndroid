package com.example.tothedestination;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import java.util.List;

public class AttractionAdapter extends ArrayAdapter<Attraction>{
    Context context;
    List<Attraction> objects;

    public AttractionAdapter(Context context, int resource, int textViewResourceld, List<Attraction> objects) {
        super(context, resource, textViewResourceld, objects);
        this.context = context;
        this.objects = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_layout3, parent, false);
        TextView tvAtName = (TextView) view.findViewById(R.id.tvAtName);
        TextView tvAtCoordinatesX = (TextView) view.findViewById(R.id.tvAtCoordinatesX);
        TextView tvAtCoordinatesY = (TextView) view.findViewById(R.id.tvAtCoordinatesY);
        TextView tvExplain = (TextView) view.findViewById(R.id.tvExplain);
        ImageView ivProduct = (ImageView) view.findViewById(R.id.ivProduct);

        Attraction temp = objects.get(position);
        ivProduct.setImageBitmap(temp.getBitmap());
        tvAtName.setText(temp.getNameAtt());
        tvAtCoordinatesX.setText(String.valueOf(temp.getCoordinatesX()));
        tvAtCoordinatesY.setText(String.valueOf(temp.getCoordinatesY()));
        tvExplain.setText(temp.getExplain());

        return view;
    }

}
