package com.zidney.azurechat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.messaging.FirebaseMessaging;

import com.zidney.azurechat.model.DataModel;
import com.zidney.azurechat.model.NotificationModel;
import com.zidney.azurechat.model.Read;
import com.zidney.azurechat.model.ResponeModel;
import com.zidney.azurechat.model.RootModel;
import com.zidney.azurechat.retrofit.ApiClient;
import com.zidney.azurechat.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private String token, msg;
    private RecyclerView recyclerView;
    private EditText add_msg;
    private Button send_msg;
    private AzureAdapter azureAdapter;
    ArrayList<RootModel> root = new ArrayList<>();
    ArrayList<Read> read = new ArrayList<>();
    String topic = "Chatting";
    String tpc = "/topics/"+topic;
    DatabaseReference dbref;
    //token Mi A1 = cy0iEV_fRgKCNgkNlAZsAx:APA91bFv_5DXe4Q7f3ikGEAIMFdfQpwqitkvMC16VwMMMGl9T_Qt8zm4C5qCzWnWZaxuaOvjnKAc5iHBHDlHBy6USF4U4mRafl9GZqGacrgBdmJqos-UDvQkBBsE8QgOIoBuLzd5yFfH
    //token emu = ca5QWvFzSfuxdbLXs-yCdW:APA91bGnh6iZvESjidKecY_wNryOf4slxJ_JhkD7qacYCVx0otYs-nP_Qiwr-CCg-l3PZX53DEfEpqFC0q6eNaHM0eehtpgaOhbCH6jcOCvAVfThUfQKgPaNQsl7ZSirhjKq_H0BAZM4

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rc_message);
        add_msg = findViewById(R.id.insert_msg);
        send_msg = findViewById(R.id.send_button);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        dbref = FirebaseDatabase.getInstance("https://azurechat-68083-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG,"Fetching FCM registration token Failed", task.getException());
                            return;
                        }

                        token = task.getResult();

                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

                    }
                });

        FirebaseMessaging.getInstance().subscribeToTopic(topic)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg ="Subsribed to Chatchat topic";
                        if (!task.isSuccessful()) {
                            msg = "Failed to Subscribed to ChatChat topic";
                        }
                        Log.d(TAG, msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp_msg = add_msg.getText().toString();
                SendNotifMessages(tpc,temp_msg);
                add_msg.setText("");
                Log.d(TAG, "onClick: "+ tpc);
                Log.d(TAG, "onClick: "+temp_msg);
            }
        });

        getMessagefromDB();

    }

    private void getMessagefromDB() {

        Query query = dbref.child(topic).child("room1");

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull  DataSnapshot snapshot, @Nullable  String previousChildName) {

                Read addmsg = snapshot.getValue(Read.class);
                read.add(addmsg);

                azureAdapter = new AzureAdapter(read, token);
                recyclerView.setAdapter(azureAdapter);



//                for (int i = 0; i < read.size(); i++){
//                    Log.d(TAG, "onChildAdded: " + read.get(i).getMsg());
//                }

                //Log.d(TAG, "onChildAdded: Arraylist read" + read);

            }

            @Override
            public void onChildChanged(@NonNull  DataSnapshot snapshot, @Nullable  String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull  DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull  DataSnapshot snapshot, @Nullable  String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
    }


    private void SendNotifMessages(String topic, String temp_msg) {

        Map<String, String> mdata = new HashMap<>();
        mdata.put("From", token);
        mdata.put("To", topic);
        mdata.put("msg", temp_msg);
        Log.d(TAG, "SendNotifMessages: " + token);
        Log.d(TAG, "SendNotifMessages: " + topic);
        Log.d(TAG, "SendNotifMessages: " + temp_msg);
        Log.d(TAG, "SendNotifMessages: " + mdata);


        RootModel rootModel = new RootModel(topic, new NotificationModel("New Message", temp_msg), mdata);


//        root.add(new RootModel(token, new NotificationModel("Title", temp_msg), new DataModel("Name", "30")));


        ApiInterface apiService =  ApiClient.getClient().create(ApiInterface.class);
        Call<ResponeModel> responseBodyCall = apiService.sendNotification(rootModel);

        responseBodyCall.enqueue(new Callback<ResponeModel>() {
            @Override
            public void onResponse(Call<ResponeModel> call, Response<ResponeModel> response) {

                if(!response.isSuccessful())
                {
                    Log.d(TAG, "onResponse: failed to respon" + response.code());
                    Log.d(TAG, "onResponse: failed to respon" + response.message());
                    return;
                }

                Log.d(TAG,"Successfully notification send by using retrofit."+response.code());
            }

            @Override
            public void onFailure(retrofit2.Call<ResponeModel> call, Throwable t) {
                Log.d(TAG, "Failed to send notification");

            }
        });


    }


}