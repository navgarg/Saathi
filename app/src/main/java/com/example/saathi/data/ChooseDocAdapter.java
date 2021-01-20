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

import com.example.saathi.NewDoctor;
import com.example.saathi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.saathi.data.Constants.COLLECTION_DOCTOR;
import static com.example.saathi.data.Constants.DB_NEW_PATIENTS;

//not working :(

public class ChooseDocAdapter extends RecyclerView.Adapter<ChooseDocAdapter.ViewHolder>{
    private List<Person> personList;
    private Context context;
    ArrayList<String> arrayList = new ArrayList<>();
    private int lastSelectedPosition = -1;
    static final String TAG = "ChooseDocAdapter";
    Person person;
    String docid = "";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public ChooseDocAdapter(List<Person> list, Context context) {
        this.personList = list;
        this.context = context;
        db.collection(COLLECTION_DOCTOR).whereEqualTo("uid", "uid")
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
                    //Alert dialog to confirm
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChooseDocAdapter.this.context);
                    builder.setMessage("Send request to Doctor " + name.getText() + "?");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            db.collection(COLLECTION_DOCTOR).document(docid)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()){
                                        DocumentSnapshot document = task.getResult();
                                        Map<String, Object> reading = new HashMap<>();
                                        arrayList = (ArrayList<String>) document.get(DB_NEW_PATIENTS);
                                        arrayList.add(person.getUid());
                                        reading.put(DB_NEW_PATIENTS, arrayList);
                                        db.collection(COLLECTION_DOCTOR)
                                                .document(docid).set(reading)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d(TAG, "onClick: patients array" + db.collection(COLLECTION_DOCTOR).document(docid).get().getResult().get(DB_NEW_PATIENTS));
                                                        Toast.makeText(ChooseDocAdapter.this.context, "Request sent", Toast.LENGTH_SHORT).show();
                                                        Toast.makeText(ChooseDocAdapter.this.context, "You will be able " +
                                                                "to contact the doctor once he approves your request.", Toast.LENGTH_LONG).show();
                                                        selectionState.setChecked(false);
                                                    }
                                                });
                                    }
                                }
                            });


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