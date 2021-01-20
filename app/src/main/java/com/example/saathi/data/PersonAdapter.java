package com.example.saathi.data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.saathi.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import static com.example.saathi.data.Constants.COLLECTION_DOCTOR;
import static com.example.saathi.data.Constants.COLLECTION_PATIENT;
import static com.example.saathi.data.Constants.DB_COLOR_AMBER;
import static com.example.saathi.data.Constants.DB_COLOR_GREEN;

public class PersonAdapter extends ArrayAdapter<Person> {

    public PersonAdapter(Activity context, ArrayList<Person> person) {
        super(context, 0, person);
    }
    static String TAG = "PersonAdapter";


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        final Person currentPerson = getItem(position);
        ImageView details = listItemView.findViewById(R.id.list_details);

        TextView name = listItemView.findViewById(R.id.list_name);
        name.setText(currentPerson.getName());

        if (currentPerson.getProfession().equals(COLLECTION_DOCTOR)){
            TextView info = listItemView.findViewById(R.id.list_info);
            info.setText(currentPerson.getInfo());
            details.setVisibility(View.GONE);
        }
        else{
            TextView info = listItemView.findViewById(R.id.list_info);
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
            details.setVisibility(View.VISIBLE);
            details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //todo: complete
                }
            });
        }


        ImageView message = listItemView.findViewById(R.id.list_message);
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: complete
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_APP_MESSAGING);
                intent.setData(Uri.parse("smsto:"+currentPerson.getPhone()));
                getContext().startActivity(intent);
            }
        });

        ImageView call = listItemView.findViewById(R.id.list_call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: complete
            }
        });


        return listItemView;
    }
}