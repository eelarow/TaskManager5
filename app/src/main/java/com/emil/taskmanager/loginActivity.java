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

public class loginActivity extends AppCompatActivity {
    //1.
    private EditText etEmail;
    private EditText etPassw;
    private Button btnsignIn,btnNewAccount,btnsignout;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.etemail);
        etPassw = (EditText) findViewById(R.id.etpassword);

        btnsignIn = (Button)findViewById(R.id.btnSignIn);
        btnNewAccount = (Button)findViewById(R.id.btnNewAcount);
        btnNewAccount = (Button)findViewById(R.id.btnsignout);

        btnNewAccount.setOnClickListener(clickLis1);

        btnsignIn.setOnClickListener(clickLis1);
//2
        auth = FirebaseAuth.getInstance();

        auth.removeAuthStateListener(authStateListener);

    }


//7
    private void  signIn(String email,String passwd){

        auth.signInWithEmailAndPassword(email,passwd).addOnCompleteListener(loginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //8
             if(task.isSuccessful())
             {
                 Intent intent = new Intent(loginActivity.this,MainFcmActivity.class);
                 startActivity(intent);
                 finish();
             }
             else
             {
                Toast.makeText(loginActivity.this,"signIn faild."+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                 task.getException().printStackTrace();
             }

            }
        });
    }
    private View.OnClickListener clickLis1 = new View.OnClickListener(){
        public void  onClick(View v){
            if (v.getId() == R.id.btnNewAcount) {
                Intent intent = new Intent(loginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
            if (v.getId() == R.id.btnSignIn) {
                Intent intent = new Intent(loginActivity.this,mngTaskActivity.class);
                startActivity(intent);
            }
            if (v.getId() == R.id.btnsignout) {
                FirebaseAuth.getInstance().signOut();
            }

        }
    };


//3
    private FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            //4
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                //user signin
                Toast.makeText(loginActivity.this, "user is signed in", Toast.LENGTH_SHORT).show();
                //go to next screen
                Intent intent = new Intent(loginActivity.this,mngTaskActivity.class);
                startActivity(intent);
                finish();
            }
            else
            {
                //user sign uot
                //stay hier
                Toast.makeText(loginActivity.this, "user is signed out", Toast.LENGTH_SHORT).show();
            }


        }
    };
//5
    protected  void  onStart()
    {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }
//6
    protected  void onStop(){
        super.onStop();
        if(authStateListener != null)
            auth.removeAuthStateListener(authStateListener);

    }
}
