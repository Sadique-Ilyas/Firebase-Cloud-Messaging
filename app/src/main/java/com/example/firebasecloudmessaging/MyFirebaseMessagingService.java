package com.example.firebasecloudmessaging;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public  static  final String TOKEN_BROADCAST = "myFcmTokenBroadcast";
    @Override
    public void onNewToken(@NonNull final String s) {
        super.onNewToken(s);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "getInstanceId failed", task.getException());
                            return;
                        }
                        else {
                            // Get new Instance ID token
                            String token = task.getResult().getToken();
                            System.out.println("------------------------------------------------");
                            System.out.println("------------------------------------------------");
                            System.out.println("Token: "+token);
                            System.out.println("------------------------------------------------");
                            System.out.println("------------------------------------------------");
                            Toast.makeText(MyFirebaseMessagingService.this, "Token: " +token, Toast.LENGTH_SHORT).show();
                            getApplicationContext().sendBroadcast(new Intent(TOKEN_BROADCAST));
                            storeToken(token);
                        }
                    }
                });
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        System.out.println("----------------------------------------------------");
        System.out.println("----------------------------------------------------");
        System.out.println("From: " + remoteMessage.getFrom());
        System.out.println("----------------------------------------------------");
        System.out.println("----------------------------------------------------");
        System.out.println("Notification Body:" +remoteMessage.getNotification().getTitle());
        System.out.println("Notification Body:" +remoteMessage.getNotification().getBody());
        System.out.println("----------------------------------------------------");
        System.out.println("----------------------------------------------------");

        notifyUser(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());

        // Check if message contains a data payload.
//        if (remoteMessage.getData().size() > 0) {
//            Log.d("TAG", "Message data payload: " + remoteMessage.getData());
//
//            if (/* Check if data needs to be processed by long running job */ true) {
//                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
//                scheduleJob();
//            } else {
//                // Handle message within 10 seconds
//                handleNow();
//            }
//
//        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d("TAG", "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    private void storeToken(String token) {
        SharedPrefManager.getInstance(getApplicationContext()).storeToken(token);
    }

    public  void notifyUser(String from, String notification){
        MyNotificationMangaer notificationMangaer = new MyNotificationMangaer(getApplicationContext());
        notificationMangaer.showNotification(from, notification, new Intent(getApplicationContext(), MainActivity.class));
    }
}


