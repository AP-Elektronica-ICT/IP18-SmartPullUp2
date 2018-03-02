package com.smartpullup.smartpullup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mAuth = FirebaseAuth.getInstance();

        Button loginButton = (Button)findViewById(R.id.btn_Login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final EditText txtUsername = (EditText)findViewById(R.id.edit_Email_L);
                final EditText txtPassword = (EditText)findViewById(R.id.edit_Password_L);
                String email = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();

                if(email != null && !email.equals("") && password != null && !password.equals(""))
                {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "signInWithEmail:complete");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        goToMainActivity();
                                    } else {
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(LoginActivity.this, "Authentication failed. \n Wrong username or password.",
                                                Toast.LENGTH_LONG).show();
                                        txtUsername.setText("");
                                        txtPassword.setText("");
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(LoginActivity.this, "Failed to authenticate. try again later", Toast.LENGTH_LONG).show();
                                }
                            });
                }
                else Toast.makeText(LoginActivity.this, "Empty field",
                        Toast.LENGTH_SHORT).show();
            }
        });

        Button registerButton=(Button)findViewById(R.id.btn_Register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(register);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            goToMainActivity();
        }
    }

    public void goToMainActivity(){
        Intent login = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(login);
    }
}
