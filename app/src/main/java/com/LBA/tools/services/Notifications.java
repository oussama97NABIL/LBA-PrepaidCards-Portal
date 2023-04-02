package com.LBA.tools.services;

import android.util.Log;

import com.LBA.tools.assets.Globals;
import com.LBA.tools.connection.HTTPClient;
import com.LBA.tools.misc.NotificationEntry;

import org.json.JSONArray;
import org.json.JSONObject;



public class Notifications {

    public static void GetNotifications() throws Exception{
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("otp", Globals.pinEntered); // MAW20190918

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceNotification, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("Fetch Notifications FAILED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");

        Log.e("Notifications WS", "jsonRespObject: "+ jsonRespObject);

        JSONArray arrayNotifications = jsonRespObject.getJSONArray("notificationsList");
        Globals.notificationsList.clear();
        for (int i = 0; i < arrayNotifications.length(); i++) {
            NotificationEntry notificationEntry =  new NotificationEntry(
                    arrayNotifications.getJSONObject(i).getString("id"),
                    arrayNotifications.getJSONObject(i).getString("title"),
                    arrayNotifications.getJSONObject(i).getString("body"),
                    arrayNotifications.getJSONObject(i).getString("operation_date")
            );
            Globals.notificationsList.add(
                    notificationEntry
            );
            Log.d("notifications ::::: ", ""+notificationEntry.toString());

        }


    }

    public static void saveNotificationHistory(String notificationId) throws Exception{
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        //   13062022
        jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("notificationId", notificationId);
        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.notificationHistory, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("Fetch Notifications FAILED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");

        Log.e("Notifications WS", "jsonRespObject: "+ jsonRespObject);

    }


    public static void GetUnviewedNotifications() throws Exception{
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("otp", Globals.pinEntered); // MAW20190918

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceUnviewedNotification, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("Fetch Notifications FAILED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");

        Log.e("Notifications WS", "jsonRespObject: "+ jsonRespObject);

        JSONArray arrayNotifications = jsonRespObject.getJSONArray("unviewedNotificationsList");
        Globals.unviewdNotificationsList.clear();
        for (int i = 0; i < arrayNotifications.length(); i++) {
            NotificationEntry notificationEntry =  new NotificationEntry(
                    arrayNotifications.getJSONObject(i).getString("id"),
                    arrayNotifications.getJSONObject(i).getString("title"),
                    arrayNotifications.getJSONObject(i).getString("body"),
                    arrayNotifications.getJSONObject(i).getString("operation_date"),
                    arrayNotifications.getJSONObject(i).getString("user")
            );
            Globals.unviewdNotificationsList.add(
                    notificationEntry
            );
            Log.d("notifications ::::: ", ""+notificationEntry.toString());

        }


    }


    public static void GetAllNotificationOfUser() throws Exception{
        JSONObject jsonObject = new JSONObject();
        Log.e("USER", "GetAllNotificationOfUser: " + Globals.user );
        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("otp", Globals.pinEntered); // MAW20190918

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.getAllNotifications, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("Fetch Notifications FAILED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");

        Log.e(" Notifications WS", " GetAllNotificationOfUser jsonRespObject: "+ jsonRespObject);

        JSONArray arrayNotifications = jsonRespObject.getJSONArray("getAllNotifications");
        JSONArray arrayNotifications2 = jsonRespObject.getJSONArray("unviewedNotificationsList");
        Log.e(" Notifications WS", " GetAllNotificationOfUser arrayNotifications2: "+ arrayNotifications2);

        Globals.getAllNotificationList.clear();
        for (int i = 0; i < arrayNotifications.length(); i++) {
            NotificationEntry notificationEntry =  new NotificationEntry(
                    arrayNotifications.getJSONObject(i).getString("id"),
                    arrayNotifications.getJSONObject(i).getString("title"),
                    arrayNotifications.getJSONObject(i).getString("body"),
                    arrayNotifications.getJSONObject(i).getString("operation_date"),
                    arrayNotifications.getJSONObject(i).getString("user")
            );
            Globals.getAllNotificationList.add(
                    notificationEntry
            );
            Log.d("notifications ::::: ", ""+notificationEntry.toString());

        }
        JSONArray unviewedNotificationsList = jsonRespObject.getJSONArray("unviewedNotificationsList");
        Globals.unviewdNotificationsListIn.clear();
        for (int i = 0; i < unviewedNotificationsList.length(); i++) {
            NotificationEntry notificationEntry =  new NotificationEntry(
                    unviewedNotificationsList.getJSONObject(i).getString("id"),
                    unviewedNotificationsList.getJSONObject(i).getString("title"),
                    unviewedNotificationsList.getJSONObject(i).getString("body"),
                    unviewedNotificationsList.getJSONObject(i).getString("operation_date"),
                    unviewedNotificationsList.getJSONObject(i).getString("user")
            );
            Globals.unviewdNotificationsListIn.add(
                    notificationEntry
            );
            Log.d("notifications ::::: ", ""+notificationEntry.toString());
            Log.e("notifications size", ""+Globals.unviewdNotificationsListIn.size());

        }


    }


}
