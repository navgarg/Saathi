package com.example.saathi.data;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saathi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;


public class ChooseDocAdapter extends RecyclerView.Adapter<ChooseDocAdapter.ViewHolder>{
    private List<Person> personList;
    private Context context;
    private int lastSelectedPosition = -1;
    Person person;
    String docid = "";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public ChooseDocAdapter(List<Person> list, Context context) {
        this.personList = list;
        this.context = context;
        db.collection(Constants.COLLECTION_DOCTOR).whereEqualTo("uid", "uid")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (DocumentSnapshot document : task.getResult().getDocuments()){
                                docid = document.getId();
                                Log.d("ChooseDoctorAdapter", "docid: " + docid);
                            }
                        }
                    }
                });
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
        person = personList.get(position);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChooseDocAdapter.this.context);
                    builder.setMessage("Send request to Doctor " + name.getText() + "?");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            //todo: update
                            DocumentReference docRef = db.collection(Constants.COLLECTION_DOCTOR).document(docid);
                            //todo: get id from firebase: id of current logged in user
                            docRef.update("newPatients", FieldValue.arrayUnion("")); //FieldValue.arrayUnion(user.getUID or smth));
                            Toast.makeText(ChooseDocAdapter.this.context, "Request sent", Toast.LENGTH_SHORT).show();
                            Toast.makeText(ChooseDocAdapter.this.context, "You will be able " +
                                    "to contact the doctor once he approves your request.", Toast.LENGTH_LONG).show();

                        }
                    });
                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //do nothing atm
                                    selectionState.setChecked(false);
                                }
                            });

                    AlertDialog alert = builder.create();
                    alert.setTitle("Choose Doctor");
                    alert.show();

                }
            });
        }
    }
}