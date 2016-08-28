package trendy.coloc.tools;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import trendy.coloc.R;
import trendy.coloc.entities.Notification;

/**
 * Created by malik on 28-Aug-16.
 */
public class MyNotificationBuilder {

    /**
     * @param notification
     * @param context               getapplocationContext
     * @param activityClass         get the activityFile.class
     * @param myNotificationManager pass it in activity as
     *                              (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
     * @return
     */
    public static String notificationMaker(Notification notification, Context context, Class activityClass, NotificationManager myNotificationManager) {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setContentTitle("New Message");
        mBuilder.setContentText("You've received new message.");
        mBuilder.setTicker("New Message Alert!");
        mBuilder.setSmallIcon(R.drawable.removeicon);

        //set multiline Style
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        String[] events = new String[6];
        events[0] = new String("This is first line....");
        events[1] = new String("This is second line...");
        events[2] = new String("This is third line...");
        events[3] = new String("This is 4th line...");
        events[4] = new String("This is 5th line...");
        events[5] = new String("This is 6th line...");
        // Sets a title for the Inbox style big view
        inboxStyle.setBigContentTitle("Big Title Details:");
        // Moves events into the big view
        for (int i = 0; i < events.length; i++) {
            inboxStyle.addLine(events[i]);
        }
        mBuilder.setStyle(inboxStyle);



  /* Creates an explicit intent for an Activity in your app */
        Intent resultIntent = new Intent(context, activityClass);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(activityClass);

// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        // Issue the notification
        // notificationID allows you to update the notification later on.
        myNotificationManager.notify(notification.getId(), mBuilder.build());

        return null;
    }


}
