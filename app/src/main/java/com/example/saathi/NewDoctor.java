package com.example.saathi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.saathi.data.ChooseDocAdapter;
import com.example.saathi.data.Person;

import java.util.ArrayList;
import java.util.List;

public class NewDoctor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_doctor);

        RecyclerView chooseDoctorRecyclerView = findViewById(R.id.recycler_view);

        LinearLayoutManager recyclerLayoutManager = new LinearLayoutManager(this);
        chooseDoctorRecyclerView.setLayoutManager(recyclerLayoutManager);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(chooseDoctorRecyclerView.getContext(),
                        recyclerLayoutManager.getOrientation());
        chooseDoctorRecyclerView.addItemDecoration(dividerItemDecoration);


        ChooseDocAdapter recyclerViewAdapter = new ChooseDocAdapter(getArray(),this);
        chooseDoctorRecyclerView.setAdapter(recyclerViewAdapter);
    }

    private List<Person> getArray(){
        List<Person> arrayList = new ArrayList<>();
        //todo: get this from db
        arrayList.add(new Person("Manish Garg", "Pulmonologist", "uid"));
        return arrayList;
    }
}