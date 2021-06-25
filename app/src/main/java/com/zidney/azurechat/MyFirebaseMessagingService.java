package com.zidney.azurechat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private DatabaseReference databaseReference;


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {


            Map<String,String> temp = remoteMessage.getData();

            SendNotiftoDatabase(temp);
//            Log.d(TAG, "onMessageReceived: "+temp);
//
//            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: "+ token);

        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
    }

    private void SendNotiftoDatabase(Map<String, String> data) {

        Log.d(TAG, "SendNotiftoDatabase: " + data);
        Log.d(TAG, "SendNotiftoDatabase: " + data.get("map"));

        databaseReference = FirebaseDatabase.getInstance("https://azurechat-68083-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();

        databaseReference.child("Chatting").child("room1").push().setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "onSuccess: Data added to server ");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "onFailure: Failed");
            }
        });


    }



}
