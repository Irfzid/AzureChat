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
    public static final String CHANNEL_ID = "TestChannel";
    public static final int NOTIFICATION_ID = 888888;
    private String temp_key;
    private RemoteMessage remoteMessage;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {

            //addNotification(remoteMessage);

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

        databaseReference.child("Chatchat").child("room1").push().setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
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

//    private void addNotification(RemoteMessage remoteMessage) {
//        //createNotificationChannel();
//        NotificationCompat.Builder builder =
//                new NotificationCompat.Builder(this, CHANNEL_ID)
//                        .setSmallIcon(R.mipmap.ic_launcher_round)
//                        .setContentTitle("NOTIFICATION EXAMPLE")
//                        .setContentText(remoteMessage.getData().get("msg"));
//
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(contentIntent);
//
//        // Add as notification
//        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.notify(NOTIFICATION_ID, builder.build());
//    }



}
