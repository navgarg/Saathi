package com.example.saathi.data;

import android.app.Activity;
import android.content.Context;
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
import androidx.core.content.ContextCompat;

import com.example.saathi.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import static com.example.saathi.data.Constants.COLLECTION_PATIENT;

public class PersonAdapter extends ArrayAdapter<Person> {

    public PersonAdapter(Activity context, ArrayList<Person> person) {
        super(context, 0, person);
    }


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

        TextView info = listItemView.findViewById(R.id.list_info);
        info.setText(currentPerson.getInfo());

        if (currentPerson.getProfession().equals(COLLECTION_PATIENT)){
            details.setVisibility(View.GONE);
        }
        else{
            if (currentPerson.getIsCritical()) name.setTextColor(Color.RED);
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