package trendy.coloc;



import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import trendy.coloc.tools.NotificationView;

public class MainActivity extends Activity {
    private NotificationManager myNotificationManager;
    private int notificationIdOne = 111;
    private int notificationIdTwo = 112;
    private int numMessagesOne = 0;
    private int numMessagesTwo = 0;

    String ch ="{message:'+'success|failure'}";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ScheduledExecutorService scheduler =
                Executors.newSingleThreadScheduledExecutor();



        scheduler.scheduleAtFixedRate(new Runnable() {

            public void run() {
                Log.i("hello", "world");
                runOnUiThread(new Runnable() {
                    public void run() {
                      //  displayNotificationOne();





                    }
                });

            }
        }, 10, 10, TimeUnit.SECONDS);


    }

    protected void displayNotificationOne() {

        // Invoking the default notification service
        NotificationCompat.Builder  mBuilder = new NotificationCompat.Builder(this);

        mBuilder.setContentTitle("New Message with explicit intent");
        mBuilder.setContentText("New message from javacodegeeks received");
        mBuilder.setTicker("Explicit: New Message Received!");
        mBuilder.setSmallIcon(R.drawable.redp);

        // Increase notification number every time a new notification arrives
        mBuilder.setNumber(++numMessagesOne);

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, NotificationView.class);
        resultIntent.putExtra("notificationId", notificationIdOne);

        //This ensures that navigating backward from the Activity leads out of the app to Home page
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        // Adds the back stack for the Intent
        stackBuilder.addParentStack(NotificationView.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_ONE_SHOT //can only be used once
                );
        // start the activity when the user clicks the notification text
        mBuilder.setContentIntent(resultPendingIntent);

        myNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // pass the Notification object to the system
        myNotificationManager.notify(notificationIdOne, mBuilder.build());
    }


    public void  ConnNative(View v){


        Intent i = new Intent(MainActivity.this,ConnectionNative.class);
        startActivity(i);


    }


    public void  ConnFb(View v){


        Intent i = new Intent(MainActivity.this,FbLogin.class);
        startActivity(i);


    }

    public void  Register(View v){


        Intent i = new Intent(MainActivity.this,RegisterActivity.class);
        startActivity(i);


    }


    public void  DmdColoc(View v){


        Intent i = new Intent(MainActivity.this,DemandeColocationActivity.class);
        startActivity(i);


    }


    public void  ChercherDmdColoc(View v){


        Intent i = new Intent(MainActivity.this,DemandeColocationSearchActivity.class);
        startActivity(i);


    }

    public void  ListeDemandes(View v){


        Intent i = new Intent(MainActivity.this,ListeDesDemandesActivity.class);
        startActivity(i);


    }


}



