package com.example.tothedestination;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
        TextView tvAgeOfChild = (TextView) view.findViewById(R.id.tvAgeOfChild);
        TextView tvSeason = (TextView) view.findViewById(R.id.tvSeason);
        ImageView ivProduct = (ImageView) view.findViewById(R.id.ivProduct);
        CheckBox isCheck = (CheckBox) view.findViewById(R.id.isChecked);

        Fly temp = objects.get(position);
        ivProduct.setImageBitmap(temp.getBitmap());
        tvHoursFly.setText(String.valueOf(temp.getHoursFlight()));
        tvCountry.setText(temp.getCountry());
        tvAttraction.setText(temp.getAttraction());
        tvAgeOfChild.setText(temp.getAgeOfChild());
        tvSeason.setText(temp.getSeason());

        isCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                temp.setChecked(isChecked);
            }
        });


        ivProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = context.getSharedPreferences("myPref", 0);
                boolean isAdmin = sp.getBoolean("CanEditAttraction",false);
                if(isAdmin)
                {
                    Intent i = new Intent(context, DetailsFlyMain.class);
                    i.putExtra("flyKey", temp.getKey());
                    context.startActivity(i);
                }
                else
                {
                    Intent i = new Intent(context, after_deleteMain.class);
                    i.putExtra("flyKey", temp.getKey());
                    context.startActivity(i);
                }
            }
        });


        return view;
    }
}
