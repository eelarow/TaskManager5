package com.emil.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import com.emil.taskmanager.data.myTask;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTaskActivity extends AppCompatActivity {
//1
    private EditText etText, etPhone,etLocation;
    private Button btnContacts, btnSave;
    private ImageButton btnlocation;
    private RatingBar rtPerio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

//2
        etText = (EditText) findViewById(R.id.etText);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etLocation = (EditText) findViewById(R.id.etLocation);

        btnContacts = (Button)findViewById(R.id.btnContacts);
        btnSave = (Button)findViewById(R.id.btnSave);

        btnlocation = (ImageButton)findViewById(R.id.btnLocation);
        rtPerio = (RatingBar) findViewById(R.id.rtBarPriority);

        btnSave.setOnClickListener(clickListener);
        btnContacts.setOnClickListener(clickListener);
        btnlocation.setOnClickListener(clickListener);




    }

    //3
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//4
            if (v==btnSave)
            {
               //5
                String txt = etText.getText().toString();
                String phone = etPhone.getText().toString();
                String loc = etLocation.getText().toString();
                int prio = rtPerio.getProgress();
                //6
                myTask myTask = new myTask(txt,false,prio,loc,phone);

                //7 save Data
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                //8
                String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
               // reference.child("user").setValue("");
                // 9 all my task under my mail. under the root myTasks
                //reference.child("user").child(email.replace(".","-")).child("MyTasks").push().setValue(myTask, new DatabaseReference.CompletionListener() {
                reference.child(email.replace(".","-")).child("MyTasks").push().setValue(myTask, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(databaseError == null){
                            Toast.makeText(AddTaskActivity.this,"Save succefuls",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(AddTaskActivity.this,"Save Failed"+databaseError.getMessage(),
                                    Toast.LENGTH_LONG).show();
                         }
                    }
                });

            }

            if (v == btnlocation )
            {
              startActivityForResult(new Intent(AddTaskActivity.this,MapsActivity2.class),70);

            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK)
        {
            if (requestCode == 70)
            {
               double lat = data.getExtras().getDouble("lat");
                double lng = data.getExtras().getDouble("lng");
                String s = lat + "'"+ lng;
                etLocation.setText(s);
            }
        }
    }
}
