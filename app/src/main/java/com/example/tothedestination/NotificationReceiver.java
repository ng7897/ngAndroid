package com.example.tothedestination;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

//BroadcastReceiver - הוא מאזין לאירועים כמו התראה מתוזמנת ושידור מותאם אישית
public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //מייצגות את שם הערוץ והID שלו
        //בלי זה, התראות לא יוצגו באנדרואיד 8 ומעלה
        String channelId = "flight_channel";
        String channelName = "Flight Notifications";

        //קבלת גישה למנהל ההתראות דרכו אפשר להציג לבטל וליצור התראות
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Create channel if needed (Android 8+)
        //בדיקה האם הגרסת אנדרואיד הנוכחית היא אדראודי 8 וגבוהה יותר או לא, זאת מכיוון שהתראות עובדות רק מגרסת 8 ומעלה
        //אם הגרסה קטנה יותר לא תתקבל התראה
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //יוצרת אובייקט של NotificationChannel שהמזהה הייחודי לערוץ  הוא channelId, השם המוצג למשתמש בהגדרות המערכת הוא channelName וNotificationManager.IMPORTANCE_HIGH מגדיר את חשיבות ההתראה, שהיא תיהיה בולטת וכו
            NotificationChannel channel = new NotificationChannel(
                    channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            //יוצרת ושומרת את הערוץ החדש שיצרנו
            notificationManager.createNotificationChannel(channel);
        }

        //הBuilder משמש לבניית ההתראה צעד אחר צעד, הוא מקבל את הchannelId channelName, הוא מגדיר אייקון ומגדיר כותרת קטנהFlight Reminder. מוגד גם טקסט ואת חשיבות ההתראה וכאשר המשתמש ילחץ עליה היא תיעלם
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("Flight Reminder")
                .setContentText("Your flight is tomorrow! Don't forget to check in.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        //1 זה מזהה ייחודי עבור התראה זו באפליקציה
        //מורה לnotificationManager להציג את ההתראה בפני המשתמש
        notificationManager.notify(1, builder.build());
    }

}
