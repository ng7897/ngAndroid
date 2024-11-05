package com.example.tothedestination;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tothedestination.Fly;
import com.example.tothedestination.R;

import java.util.List;

public class FlyAdapter extends ArrayAdapter<Fly> {

    Context context;
    List<Fly> objects;

    public FlyAdapter(Context context, int resource, int textViewResourceld, List<Fly> objects) {
        super(context,resource,textViewResourceld,objects);
        this.context = context;
        this.objects = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_layout, parent, false);
        TextView tvHoursFly = (TextView) view.findViewById(R.id.tvHoursFlight);
        TextView tvCountry = (TextView) view.findViewById(R.id.tvCountry);
        TextView tvAttraction = (TextView) view.findViewById(R.id.tvAttraction);
        ImageView ivProduct = (ImageView) view.findViewById(R.id.ivProduct);

        Fly temp = objects.get(position);
        ivProduct.setImageBitmap(temp.getBitmap());
        tvHoursFly.setText(String.valueOf(temp.getHoursFlight()));
        tvCountry.setText(temp.getCountry());
        tvAttraction.setText(temp.getAttraction());
        return view;
    }
}
