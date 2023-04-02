package com.LBA.tools.services;

import android.util.Log;

import com.LBA.tools.assets.Globals;
import com.LBA.tools.connection.HTTPClient;
import com.LBA.tools.misc.ProxyEntry;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;



public class Proxy {

    public static boolean AddProxy(String account, String name, String proxyID) throws Exception{

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId); jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("sourceAcc", account);
        jsonObject.put("name",name);
        jsonObject.put("proxyId",proxyID);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.AddProxyService, jsonObject);
        Log.e("AddProxy: ", ""+ jsonRespObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")) {
            throw new Exception(((jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "Beneficiary Rejected") ));
        }else if(jsonRespObject.getString("respCode").equals("000")){
            Globals.isSuccessful = true;
        }

       // JSONArray array = jsonRespObject.getJSONArray("proxylist");
       // Log.e("AddProxy: ", ""+ array);
       /* if (!array.equals(null)) {
            Globals.ProxyList.clear();
            Log.d("list benef", "array.length()=[" + array.length() + "]");
            for (int i = 0; i < array.length(); i++) {
                Globals.ProxyList.add(
                        new ProxyEntry(
                                array.getJSONObject(i).getString("userCode"),
                                array.getJSONObject(i).getString("proxy_ID"),
                                array.getJSONObject(i).getString("account_name"),
                                array.getJSONObject(i).getString("account_number"),
                                array.getJSONObject(i).getString("user_branch")
                        ));
                Log.d("GetForexList", "i=[" + i + "]" + " " + array.getJSONObject(i).toString());

            }
        }*/

        return true;
    }


    public static void DeleteProxy(String proxyId ) throws Exception{
        Globals.isSuccessful = false;
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("proxyId",proxyId);
        jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("otp", Globals.pinEntered); // MAW20190918

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceDeleteProxy, jsonObject);
        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")) {
            throw new Exception(jsonRespObject.getString("respCode"));
        }else if(jsonRespObject.getString("respCode").equals("000")){
            Globals.isSuccessful = true;
        }


    }

    public static Map<String,String> getProxyDetails(String proxyId ) throws Exception{
        Globals.isSuccessful = false;

        Map<String,String> proxydetails =new HashMap<>();

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("proxyId",proxyId);
        jsonObject.put("otp", Globals.pinEntered); // MAW20190918
        jsonObject.put("authenCode", Globals.authenCode);
        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceProxyFetch, jsonObject);
        Log.e( "getProxyDetails: ", ""+jsonRespObject);
        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")) {
            throw new Exception(jsonRespObject.getString("respCode"));
        }else if(jsonRespObject.getString("respCode").equals("000")){
            proxydetails.put("accountNumber","7845123698741");
            proxydetails.put("name","name");
            proxydetails.put("branchCode","branchCode");
            Globals.isSuccessful = true;
        }
     /*  proxydetails.put("accountNumber","7845123698741");
        proxydetails.put("name","name");
        proxydetails.put("branchCode","branchCode");*/
        Globals.isSuccessful = true;

        return proxydetails;
    }

    public static void UpdateProxy(String proxyId ,String sourceAcc) throws Exception{
        Globals.isSuccessful = false;
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("proxyId",proxyId);
        jsonObject.put("sourceAcc",sourceAcc);
        jsonObject.put("otp", Globals.pinEntered); // MAW20190918
        jsonObject.put("authenCode", Globals.authenCode);
        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceProxyUpdate, jsonObject);
        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")) {
            throw new Exception(jsonRespObject.getString("respCode"));
        }else if(jsonRespObject.getString("respCode").equals("000")){
            Log.e( "UpdateProxy: ", "isSuccessful");
            Globals.isSuccessful = true;
        }


    }

    public static void GetProxyList() throws Exception {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("otp", Globals.pinEntered); // MAW20190918

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceProxyList, jsonObject);

        if (jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("Fetch History FAILED :" + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : ""));

        Globals.ProxyList.clear();
        Log.e("Proxylist: ", "Before fetching data "+Globals.ProxyList);

        if(jsonRespObject.has("Proxylist")) {
            JSONArray Proxylist = jsonRespObject.getJSONArray("Proxylist");

            for (int i = 0; i < Proxylist.length(); i++) {
                Globals.ProxyList.add(new ProxyEntry(
                        Proxylist.getJSONObject(i).getString("account_number"),
                        Proxylist.getJSONObject(i).getString("proxy_ID")
                ));
            }
        }
        Log.e("Proxylist: ", "After fetching data "+Globals.ProxyList);



    }



    static public void SendProxyPay(String sourceAcc,String destinationAcc, String payeeName, String destinationBank, Double amount, String paymentDetails,String purpose , boolean ignoreUserLimit) throws Exception{
        Globals.transactionId=null;
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId); jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("sourceAcc",sourceAcc);
        jsonObject.put("destinationAcc",destinationAcc);
        jsonObject.put("transactionPurpose", purpose);
        jsonObject.put("destinationBank", destinationBank);
        jsonObject.put("ignoreUserLimit", ignoreUserLimit);
        jsonObject.put("amount", amount);
        jsonObject.put("paymentDetails", paymentDetails);

        jsonObject.put("otp", Globals.pinEntered); // MAW20190918


        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceProxyTransfer, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")) {
            // throw new Exception("INSTANT PAY TRANSFER REJECTED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");
            Globals.userLimit=null;

            if(jsonRespObject.getString("respCode").equals("005")) {
                Globals.userLimit = jsonRespObject.getString("userLimit");
                Log.e( "userLimit: ",""+  Globals.userLimit);
            }
            throw new Exception("PROXY TRANSFER REJECTED WITH CODE = " + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "\n\nTRANSACTION ID : " + (jsonRespObject.has("transactionId") ? jsonRespObject.getString("transactionId") : ""));
        }
        Globals.transactionId=jsonRespObject.getString(Globals.transactionIdTag);
    }




}
