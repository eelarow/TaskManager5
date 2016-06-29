package com.emil.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.emil.taskmanager.data.MyAdapterTask;
import com.emil.taskmanager.data.myTask;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class mngTaskActivity extends AppCompatActivity {

   // private EditText etFastAdd;
 //   private ImageButton btnFastAdd;

    //6

    private ListView listView;
    private MyAdapterTask adapterTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
     //   etFastAdd = (EditText) findViewById(R.id.etFastAdd);
      //  btnFastAdd = (ImageButton) findViewById(R.id.btnFastAdd);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mng_task_activity);
        //7
        listView = (ListView) findViewById(R.id.listView);
        adapterTask = new MyAdapterTask(this,R.layout.item_my_task);

        //8
        listView.setAdapter(adapterTask);



        //1 get Fixed email
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail().replace('.','-');
        //2
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(email);
        //3
        reference.child("MyTasks").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                //9. um alles am angang sauber machen "menake kul htchonot "
                adapterTask.clear();
                //4 alle daten von databse
                for (DataSnapshot ds:dataSnapshot.getChildren())
                {
                    //5 so lesen die Daten
                    myTask mytask = ds.getValue(myTask.class);
                    mytask.setTaskKey((ds.getKey()));
                    //To use myTask
                    //10 add myTask to Adapter
                    adapterTask.add(mytask);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Snackbar.make(view,"Replace with your action",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                Intent intent = new Intent(mngTaskActivity.this,AddTaskActivity.class);
                startActivity(intent);
            }
        });


    }
    @Override
    protected void onResume() {
        super.onResume();


    }


}
