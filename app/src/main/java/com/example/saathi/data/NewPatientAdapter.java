package com.example.saathi.data;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.saathi.R;

import java.util.ArrayList;

import static com.example.saathi.data.Constants.DB_COLOR_AMBER;
import static com.example.saathi.data.Constants.DB_COLOR_GREEN;

public class NewPatientAdapter extends ArrayAdapter<Person> {
    public NewPatientAdapter(Activity context, ArrayList<Person> person) {
        super(context, 0, person);
    }
        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View listItemView = convertView;
            if(listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.list_item_new_pat, parent, false);
            }

            final Person currentPerson = getItem(position);
            ImageView details = listItemView.findViewById(R.id.list_new_pat_details);

            TextView name = listItemView.findViewById(R.id.list_new_pat_name);
            name.setText(currentPerson.getName());

            TextView info = listItemView.findViewById(R.id.list_new_pat_info);
            info.setText(currentPerson.getInfo());

            if (currentPerson.getColor().equals(DB_COLOR_GREEN)){
                name.setTextColor(Color.parseColor("#388E3C"));
            }
            else if (currentPerson.getColor().equals(DB_COLOR_AMBER)){
                name.setTextColor(Color.parseColor("#ffbf00"));
            }
            else{
                name.setTextColor(Color.parseColor("#FF3D00"));
            }
            details.setVisibility(android.view.View.VISIBLE);
            details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //todo: complete
                }
            });

            ImageView accept = listItemView.findViewById(R.id.list_new_pat_accept);
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //todo
                }
            });

            ImageView decline = listItemView.findViewById(R.id.list_new_pat_decline);
            decline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //todo
                }
            });


            return listItemView;
        }
    }