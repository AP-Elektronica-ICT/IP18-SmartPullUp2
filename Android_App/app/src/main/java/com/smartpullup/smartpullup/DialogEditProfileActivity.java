package com.smartpullup.smartpullup;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Context.MODE_PRIVATE;

public class DialogEditProfileActivity extends Dialog implements
        android.view.View.OnClickListener{

    public DialogEditProfileActivity(@NonNull Context context) {
        super(context);
    }
    
    private EditText Email;
    private EditText Height;
    private EditText Weight;
    private EditText DateBirth;
    private Button Save;
    private Button Cancel;

    private String userId;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    FirebaseUser currentUser;
    private DatabaseReference userDatabase;
    String weight;
    String height;
    String email;
    String dateOfBirth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_edit_profile);
        Email = (EditText) findViewById(R.id.edit_Email);
        DateBirth = (EditText)findViewById(R.id.edit_Date_of_Birth);
        Height = (EditText)findViewById(R.id.height_edt);
        Weight = (EditText) findViewById(R.id.weight_edt);
        Save=(Button)findViewById(R.id.btn_Save);
        Cancel=(Button)findViewById(R.id.btn_cancel);

        database = FirebaseDatabase.getInstance();
        userDatabase = database.getReference("Users");
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        userId = currentUser.getUid();




        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    userDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Log.d("User", userId);
                            Log.d("User", dataSnapshot.getValue().toString());
                            // Weight.setText(dataSnapshot.child(userId).child("weght").getValue().toString());
                            // Height.setText(dataSnapshot.child(userId).child("height").getValue().toString());
                            //DateBirth.setText(dataSnapshot.child(userId).child("dateBirth").getValue().toString());
                            //Email.setText(dataSnapshot.child(userId).child("email").getValue().toString());

                            weight = Weight.getText().toString();
                            height = Height.getText().toString();
                            email = Email.getText().toString();
                            dateOfBirth = DateBirth.getText().toString();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                            Toast.makeText(getContext(), "Hello",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

                if(weight !=null && !weight.equals("") && height!=null && !height.equals("") && dateOfBirth !=null &&!dateOfBirth.equals("") && email !=null &&!email.equals("")) {
                    userDatabase.child(userId).child("weight").setValue(weight);
                    userDatabase.child(userId).child("height").setValue(height);
                    userDatabase.child(userId).child("dateBirth").setValue(dateOfBirth);
                    userDatabase.child(userId).child("email").setValue(email);
                }



            }
        });

        Cancel.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

    }
}
