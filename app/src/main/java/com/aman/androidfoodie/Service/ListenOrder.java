package com.aman.androidfoodie.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;


import com.aman.androidfoodie.Common.Common;
import com.aman.androidfoodie.Model.Request;
import com.aman.androidfoodie.OrderStatus;
import com.aman.androidfoodie.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Map;

/**
 * Created by Aman on 4/25/2019.
 */

public class ListenOrder extends Service implements ChildEventListener{
FirebaseDatabase database;
DatabaseReference requests;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        database=FirebaseDatabase.getInstance();
        requests=database.getReference("Requests");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        requests.addChildEventListener(this);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        Request request=dataSnapshot.getValue(Request.class);
        showNotification(dataSnapshot.getKey(),request);
    }

    private void showNotification(String key, Request request) {
        Intent intent=new Intent(getBaseContext(), OrderStatus.class);
        intent.putExtra("userPhone",request.getPhone());
        PendingIntent contentIntent=PendingIntent.getActivity(getBaseContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(getBaseContext());
        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setTicker("Foodie")
                .setContentInfo("Your Order Was Updated")
                .setContentText("Order #"+key+"was update status to "+ Common.convertCodeToStatus(request.getStatus()))
                .setContentIntent(contentIntent)
                .setContentInfo("info")
                .setSmallIcon(R.drawable.ic_action_cart);
        NotificationManager notificationManager=(NotificationManager)getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,builder.build());
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
