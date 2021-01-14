package com.example.saathi.data;

import android.app.Activity;
import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class PersonAdapter extends ArrayAdapter<Person> {

    public PersonAdapter(Activity context, ArrayList<Person> person) {
        super(context, 0, person);
    }

    
}
