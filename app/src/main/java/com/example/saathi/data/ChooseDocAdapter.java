package com.example.saathi.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.saathi.R;

import java.util.List;


public class ChooseDocAdapter extends RecyclerView.Adapter<ChooseDocAdapter.ViewHolder>{
    private List<Person> personList;
    private Context context;
    private int lastSelectedPosition = -1;

    public ChooseDocAdapter(List<Person> list, Context context) {
        this.personList = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_choose_doc, parent, false);

        ChooseDocAdapter.ViewHolder viewHolder = new ChooseDocAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Person person = personList.get(position);
        holder.name.setText(person.getName());
        holder.speciality.setText("" + person.getInfo());

        //since only one radio button is allowed to be selected,
        // this condition un-checks previous selections
        holder.selectionState.setChecked(lastSelectedPosition == position);

    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView speciality;
        public RadioButton selectionState;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.choose_list_name);
            speciality = (TextView) view.findViewById(R.id.choose_list_speciality);
            selectionState = (RadioButton) view.findViewById(R.id.choose_list_button);

            selectionState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();

                    //todo: update this
                    Toast.makeText(ChooseDocAdapter.this.context,
                            "selected doctor is " + name.getText(),
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}