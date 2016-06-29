package com.emil.taskmanager.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.emil.taskmanager.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by HSchool on 27/06/2016.
 */
//3
public class MyAdapterTask extends ArrayAdapter<myTask>
{
    //9.
    private DatabaseReference reference;

    public MyAdapterTask(Context context, int resource) {
        super(context, resource);

        //10 get Fixed email
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail().replace('.','-');
        //11
      //  DatabaseReference reference = FirebaseDatabase.getInstance().getReference(email).child("MyTasks");
         reference = FirebaseDatabase.getInstance().getReference(email+"/MyTasks");


    }
    //4
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //5 datasource

        //  if (convertView == null)
        //  {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_my_task,parent,false);
        //  }

        //row ui
        //6. to get refernce to each ui object at the : R.layout.item_my_task

        CheckBox chbIscompleted = (CheckBox) convertView.findViewById(R.id.chbxIsComplated);
        TextView tvText = (TextView) convertView.findViewById(R.id.tvText);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);
        ImageButton btnCall = (ImageButton) convertView.findViewById(R.id.btnCall);
        ImageButton btnLocation = (ImageButton) convertView.findViewById(R.id.btnLocation);



        //7. data source
        final myTask mytask = getItem(position); //datasource

        //8. Connection between the data source and at the Ui
        chbIscompleted.setChecked(mytask.isCompleted());

        tvText.setText(mytask.getText());
        tvDate.setText("*********");

        chbIscompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mytask.setCompleted(isChecked);
                //12
                reference.child(mytask.getTaskKey()).setValue(mytask);
            }
        });

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //make call
            }
        });

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),mytask.getText()+":"+mytask.getLocation(), Toast.LENGTH_LONG).show();
            }
        });
        return convertView;
    }
}
