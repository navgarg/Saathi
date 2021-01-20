package com.example.saathi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.saathi.data.Chart_Data;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import static android.content.ContentValues.TAG;
import static com.example.saathi.data.Constants.COLLECTION_DOCTOR;
import static com.example.saathi.data.Constants.COLLECTION_PATIENT;


public class LoginActivity extends AppCompatActivity {

    //a constant for detecting the login intent result
    private static final int RC_SIGN_IN = 234;
    private static final String TAG = "LoginActivity";
    boolean isRegistered;

    public static GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;
    static FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //if user is already signed in
        if (mAuth.getCurrentUser() != null) {
            sendIntent();
            //finish();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            Log.d(TAG, "in if in onActivityResult");
            try {
                //Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "in try");
                firebaseAuthWithGoogle(account);

            } catch (ApiException e) {
                Log.d(TAG, "in catch");
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            sendIntent();
                        } else {
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                    }
                    }
                });

    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    //todo: patient login checked, idk abt doc/unregistered.. no more email ids :( but it should work

    private void sendIntent(){
        Log.d(TAG, "signInWithCredential:success");
        //Toast.makeText(LoginActivity.this, "LogIn successful!", Toast.LENGTH_LONG).show();
        final FirebaseUser user = mAuth.getCurrentUser();
        //startActivity(new Intent(LoginActivity.this, PDashboard.class));
        db.collection(COLLECTION_PATIENT).whereEqualTo("uid", user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult() != null) {
                                for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                    startActivity(new Intent(LoginActivity.this, PDashboard.class));
                                    Log.d(TAG, "signin successful, registered patient");
                                    isRegistered = true;
                                    finish();
                                }

                            }
                        } else {
                            Log.w(TAG, "Error getting documents: patient db ", task.getException());

                        }
                    }
                });

        db.collection(COLLECTION_DOCTOR).whereEqualTo("uid", user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult() != null) {
                                for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                    startActivity(new Intent(LoginActivity.this, DDashboard.class));
                                    Log.d(TAG, "signin successful, registered doctor");
                                    isRegistered = true;
                                    finish();
                                }

                            }
                        } else {
                            Log.w(TAG, "Error getting documents: doctor db ", task.getException());
                            Log.d(TAG, "onComplete: new: ");
                        }
                    }
                });

//        if (!isRegistered){
//            startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
//            Toast.makeText(LoginActivity.this, "Kindly fill in your details", Toast.LENGTH_LONG).show();
//        }

    }


}
