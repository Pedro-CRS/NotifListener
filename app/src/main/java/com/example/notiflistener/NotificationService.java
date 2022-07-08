package com.example.notiflistener;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class NotificationService extends NotificationListenerService {

    /* These are the package names of the apps. for which we want to listen the notifications */
    private static final class ApplicationPackageNames {
        public static final String TWITTER_PACK_NAME = "com.twitter.android";
    }

    /* These are the return codes we use in the method which intercepts the notifications,
        to decide whether we should do something or not */
    public static final class InterceptedNotificationCode {
        public static final int TWITTER_CODE = 1; // We ignore all notification with code != 1
        public static final int OTHER_NOTIFICATIONS_CODE = 5;
    }

    static MyListener myListener;

    private int matchNotificationCode(StatusBarNotification sbn) {
        String packageName = sbn.getPackageName();
        if(packageName.equals(ApplicationPackageNames.TWITTER_PACK_NAME))
            return(InterceptedNotificationCode.TWITTER_CODE);
        else
            return(InterceptedNotificationCode.OTHER_NOTIFICATIONS_CODE);
    }

    @Override
    public void onNotificationPosted (StatusBarNotification sbn) {
        int notificationCode = matchNotificationCode(sbn);
        String alarm = "New twitter app notification received";

        if(notificationCode != InterceptedNotificationCode.OTHER_NOTIFICATIONS_CODE) {
            myListener.setValue(new StringBuilder().append("\n" + alarm).toString());

            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "https://httpbin.org/post";

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Display the first 500 characters of the response string.
                    myListener.setValue(new StringBuilder().append("Response is: " + response).toString());
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    myListener.setValue(new StringBuilder().append("That didn't work!" + error).toString());
                }
            });

            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }
    }

    @Override
    public void onNotificationRemoved (StatusBarNotification sbn) {
        myListener.setValue("\nNotification removed from status bar: " + sbn.getPackageName());
    }

    public void setListener (MyListener myListener) {
        NotificationService. myListener = myListener;
    }
}