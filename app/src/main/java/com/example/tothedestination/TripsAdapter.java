package com.example.tothedestination;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

public class TripsAdapter  extends ArrayAdapter<vacation> {

    Context context;
    List<vacation> objects;

    public TripsAdapter(Context context, int resource, int textViewResourceld, List<vacation> objects) {
        super(context, resource, textViewResourceld, objects);
        this.context = context;
        this.objects = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_layout2, parent, false);
        TextView tvHoursFly = (TextView) view.findViewById(R.id.tvHoursFlight);
        TextView tvCountry = (TextView) view.findViewById(R.id.tvCountry);
        TextView tvAgeOfChild = (TextView) view.findViewById(R.id.tvAgeOfChild);
        TextView tvSeason = (TextView) view.findViewById(R.id.tvSeason);
        TextView tvAttraction = (TextView) view.findViewById(R.id.tvAttraction);
        TextView tvDateFrom = (TextView) view.findViewById(R.id.tvDateFrom);
        TextView tvDateTo = (TextView) view.findViewById(R.id.tvDateTo);
        ImageView ivProduct = (ImageView) view.findViewById(R.id.ivProduct);

        vacation temp = objects.get(position);
        ivProduct.setImageBitmap(temp.getBitmap());
        tvHoursFly.setText(String.valueOf(temp.getHoursFlight()));
        tvCountry.setText(temp.getCountry());
        tvAgeOfChild.setText(temp.getAgeOfChild());
        tvAttraction.setText(temp.getAttraction());
        tvSeason.setText(temp.getSeason());
        // Create a Date object from the timestamp
        Date date = new Date(temp.getFromDate());
        Date date1 = new Date(temp.getToDate());

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy", Locale.getDefault());

        tvDateFrom.setText(dateFormat.format(date));
        tvDateTo.setText(dateFormat.format(date1));


        ivProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, tripData.class);
                i.putExtra("vacUserKey", temp.getKeyUser());
                i.putExtra("vacKey", temp.getKey());
                context.startActivity(i);
            }
        });


        return view;
    }
}
