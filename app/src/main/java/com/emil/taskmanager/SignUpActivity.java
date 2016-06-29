package com.emil.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    private EditText etEmail, etFullName, etPassw1, etPassw2;
    private Button btnSignUp;
    private FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        etEmail = (EditText) findViewById(R.id.etEmail);
        etFullName = (EditText) findViewById(R.id.etFulName);
        etPassw1 = (EditText) findViewById(R.id.etPassw1);
        etPassw2 = (EditText) findViewById(R.id.etPassw2);

        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(clickLis);

        auth = FirebaseAuth.getInstance();
    }

    private View.OnClickListener clickLis = new View.OnClickListener(){
            public void  onClick(View v){
                if (v.getId() == R.id.btnSignUp){
                    String email = etEmail.getText().toString();
                    String passw = etPassw1.getText().toString();
                    creatAccount(email,passw);
                }
            }
    };




    private FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                //user signin
                Toast.makeText(SignUpActivity.this, "user is signed in", Toast.LENGTH_SHORT).show();
                //go to next screen
            }
            else
            {
                //user sign uot
                //stay hier
                Toast.makeText(SignUpActivity.this, "user is signed out", Toast.LENGTH_SHORT).show();
            }


        }
    };

    protected  void  onStart()
    {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    protected  void onStop(){
     super.onStop();
     if(authStateListener != null)
         auth.removeAuthStateListener(authStateListener);

 }

    private void  creatAccount(String email,String passw) {

        auth.createUserWithEmailAndPassword(email,passw).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(SignUpActivity.this, "Authenitection Successful", Toast.LENGTH_SHORT).show();
                    //updateUserProfile(task.getResult().getUser())
                    //TODO go to the next Screen
                    Intent intent = new Intent(SignUpActivity.this,mngTaskActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(SignUpActivity.this, "Authenitection faild" +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    task.getException().printStackTrace();
                }

            }
        });
    }



}
