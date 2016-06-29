package com.emil.taskmanager.telephony;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.emil.taskmanager.data.myTask;
import com.emil.taskmanager.mngTaskActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//1.
public class MySMSReceiver extends BroadcastReceiver {
    public MySMSReceiver() {
    }

    @Override
    ///
    public void onReceive(final Context context, Intent intent) {
        Toast.makeText(context, "HIII SMS", Toast.LENGTH_SHORT).show();
        //2.
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED"))
        {
            Bundle bundle= intent.getExtras();
            Object[] pdus = (Object[])bundle.get("pdus");
            String smsInfo ="";
            String inPhoneNum ="";

            for (int i = 0; i < pdus.length;i++)
            {
                SmsMessage smsMsg = SmsMessage.createFromPdu((byte[])pdus[i]);
                inPhoneNum = smsMsg.getDisplayOriginatingAddress();
                smsInfo = smsMsg.getDisplayMessageBody() +",";

            }

            if (FirebaseDatabase.getInstance()!= null) {
                myTask myTask = new myTask(smsInfo,false,1,"",inPhoneNum);

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
                            Toast.makeText(context,"Save succefuls",Toast.LENGTH_LONG).show();
                            makeNotification(context);
                        }
                        else
                        {
                            Toast.makeText(context,"Save Failed"+databaseError.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
            //auto sms Response
            if(smsInfo.contains("hello"))
            {
                //you need to add send_sms uses premession
                SmsManager manager = SmsManager.getDefault();
                manager.sendTextMessage(inPhoneNum,null,"welcome",null,null);
            }


        }

    }

    public void makeNotification(Context context)
    {
        //1. boniem notification
        NotificationCompat.Builder builder= (NotificationCompat.Builder) new NotificationCompat.Builder(context)
              // .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("TasksManager")
                .setContentText("New SMS Added to your Tasks");
        //2.response to the notification selection
        Intent resIntent = new Intent(context,mngTaskActivity.class);
        PendingIntent resPendingIntent = PendingIntent.getActivity(context,0,resIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        //3.
        builder.setContentIntent(resPendingIntent);
        //4.service
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify(22,builder.build());

    }
}

