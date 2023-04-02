package com.LBA.tools.services;

import android.util.Log;

import com.LBA.tools.assets.Globals;
import com.LBA.tools.connection.HTTPClient;

import org.json.JSONArray;
import org.json.JSONObject;



/**
 * Created by amine.wahbi on 3/11/2015.
 */
public class Bills {

    static public void SendAirtimeTopup(String sourceAcc,String network, String phone,Double amount,String purpose , boolean ignoreUserLimit) throws Exception{
        Globals.transactionId=null;
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("ignoreUserLimit", ignoreUserLimit);
        jsonObject.put("sourceAcc",sourceAcc);
        jsonObject.put("network",network);
        jsonObject.put("purpose", purpose);
        jsonObject.put("phone",phone);
        jsonObject.put("amount", amount);
       // jsonObject.put("narration", narration);


        jsonObject.put("otp", Globals.pinEntered); // MAW20190918


        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceAirtimeTopup, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")){
            Globals.userLimit=null;

            if(jsonRespObject.getString("respCode").equals("005")) {
                Globals.userLimit = jsonRespObject.getString("userLimit");
                Log.e( "userLimit: ",""+  Globals.userLimit);
            }
            //   27062022
            throw new Exception("PAYMENT REJECTED WITH CODE = " + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "\n\nTRANSACTION ID : " + (jsonRespObject.has("transactionId") ? jsonRespObject.getString("transactionId") : ""));
           // throw new Exception((jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):""));

        }

         Globals.transactionId=jsonRespObject.getString(Globals.transactionIdTag);
    }

    static public void SendMobileMoney(String sourceAcc,String provider, String receiverPhone, String receiverName,Double amount, String narration,boolean ignoreUserLimit, String purpose, boolean chargesAccepted) throws Exception{
        Globals.transactionId=null;
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId); jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("sourceAcc",sourceAcc);
        jsonObject.put("provider",provider);
        jsonObject.put("receiverPhone",receiverPhone);
        jsonObject.put("receiverName",receiverName);
        jsonObject.put("amount", amount);
        jsonObject.put("ignoreUserLimit", ignoreUserLimit);
        jsonObject.put("purpose", purpose);
        jsonObject.put("narration", narration);
        jsonObject.put("chargesAccepted", chargesAccepted);

        jsonObject.put("otp", Globals.pinEntered); // MAW20190918

        Log.e( "SendMobileMoney: ", ""+jsonObject);
        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceMobileMoney, jsonObject);

        // jsonRespObject.put("respCode", "000");
        Globals.OffAmountPay = null;

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")){
            Globals.userLimit=null;

            if(jsonRespObject.getString("respCode").equals("005")) {
                Globals.userLimit = jsonRespObject.getString("userLimit");
                Log.e( "userLimit: ",""+  Globals.userLimit);
            }else if(jsonRespObject.getString("respCode").equals("006")){
                Globals.OffAmountPay =  jsonRespObject.getString("GIPCharges");
            }
            //   throw new Exception("MOBILE MONEY FAILED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");
            //   27062022
            throw new Exception("PAYMENT REJECTED WITH CODE = " + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "\n\nTRANSACTION ID : " + (jsonRespObject.has("transactionId") ? jsonRespObject.getString("transactionId") : ""));

        }

        Globals.transactionId=jsonRespObject.getString(Globals.transactionIdTag);
        // Globals.transactionId = "000005";
    }


    static public void SendDSTV(String sourceAcc, String dstvAcc, Double amount , boolean ignoreUserLimit , String purpose) throws Exception{

        Globals.transactionId=null;
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId); jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("sourceAcc",sourceAcc);
        jsonObject.put("dstvAcc",dstvAcc);
        jsonObject.put("ignoreUserLimit",ignoreUserLimit);
        jsonObject.put("purpose",purpose);
        jsonObject.put("amount", amount);
        //jsonObject.put("narration", narration);

        jsonObject.put("otp", Globals.pinEntered); // MAW20190918


        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceDSTV, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")) {
            Globals.userLimit=null;

            if(jsonRespObject.getString("respCode").equals("005")) {
                Globals.userLimit = jsonRespObject.getString("userLimit");
                Log.e( "userLimit: ",""+  Globals.userLimit);
            }

          //  throw new Exception((jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : ""));
            throw new Exception("PAYMENT REJECTED WITH CODE = " + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "\n\nTRANSACTION ID : " + (jsonRespObject.has("transactionId") ? jsonRespObject.getString("transactionId") : ""));
        }

        Globals.transactionId=jsonRespObject.getString(Globals.transactionIdTag);

    }

    static public void SendECG(String sourceAcc, String ecgAccount, String ecgClientName, Double amount, boolean isBalanceOp) throws Exception{

        Globals.transactionId=null;
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId); jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("sourceAcc",sourceAcc);
        jsonObject.put("ecgAccount",ecgAccount);
        //jsonObject.put("ecgService", isBalanceOp?"B":"T");
        jsonObject.put("ecgClientName", ecgClientName);
        jsonObject.put("amount", amount);
      //  jsonObject.put("narration", narration);

        jsonObject.put("otp", Globals.pinEntered); // MAW20190918


        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceECG, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")) {
            Globals.userLimit=null;

            if(jsonRespObject.getString("respCode").equals("005")) {
                Globals.userLimit = jsonRespObject.getString("userLimit");
                Log.e( "userLimit: ",""+  Globals.userLimit);
            }

           // throw new Exception("ECG PAYMENT FAILED <RespCode=[" + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "]>");
            throw new Exception("PAYMENT REJECTED WITH CODE = " + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "\n\nTRANSACTION ID : " + (jsonRespObject.has("transactionId") ? jsonRespObject.getString("transactionId") : ""));
        }
        if(isBalanceOp) {
            Globals.ecgAmountDue = jsonRespObject.getDouble("ecgAmountDue");
            Globals.ecgClientName = jsonRespObject.getString("ecgClientName");
        }

        Globals.transactionId=jsonRespObject.getString(Globals.transactionIdTag);
    }

    static public void SendSurfline(String sourceAcc, String service, String bundle, String deviceId, Double amount ,String purpose , boolean ignoreUserLimit) throws Exception{
        Globals.transactionId=null;
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId); jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("sourceAcc",sourceAcc);
        jsonObject.put("service",service);
        jsonObject.put("ignoreUserLimit",ignoreUserLimit);
        jsonObject.put("purpose", purpose);
        jsonObject.put("bundle", bundle);
        jsonObject.put("deviceId", deviceId);
        jsonObject.put("amount", amount);
       // jsonObject.put("narration", narration);

        jsonObject.put("otp", Globals.pinEntered); // MAW20190918


        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceSurfline, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")) {
            Globals.userLimit=null;

            if(jsonRespObject.getString("respCode").equals("005")) {
                Globals.userLimit = jsonRespObject.getString("userLimit");
                Log.e( "userLimit: ",""+  Globals.userLimit);
            }
     //       throw new Exception((jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : ""));
            throw new Exception("PAYMENT REJECTED WITH CODE = " + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "\n\nTRANSACTION ID : " + (jsonRespObject.has("transactionId") ? jsonRespObject.getString("transactionId") : ""));
        }
        Globals.transactionId=jsonRespObject.getString(Globals.transactionIdTag);
    }


    static public void GetSurflineDeviceQuery(String deviceId) throws Exception{

        Globals.listSurflineBundles.clear();
        Globals.listSurflineBundlesAmount.clear();
        Globals.listSurflinePlusBundles.clear();
        Globals.listSurflinePlusBundlesAmount.clear();
        Globals.surf=false;
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId); jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("deviceId", deviceId);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceSurflineDeviceQuery, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")) {
            Globals.surf=true;
            throw new Exception("SURFLINE DEVICE QUERY FAILED <RespCode=[" + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "]>");
        }
        if (jsonRespObject.has("Details")){
            Globals.surflineAccountType = jsonRespObject.getJSONObject("Details").getString("AccountType");
           // Log.d(Bills.class.toString()+".GetSurflineDeviceQuery()", "Globals.surflineAccountType=["+Globals.surflineAccountType+"]");
            if(!Globals.surflineAccountType.toUpperCase().equals("UNKNOWN")){
                Globals.listSurflineBundles.add("Select");
                Globals.listSurflineBundlesAmount.add(0.0);
                Globals.listSurflinePlusBundles.add("Select");
                Globals.listSurflinePlusBundlesAmount.add(0.0);
                JSONArray jsonArray = jsonRespObject.getJSONArray("Bundles");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonobject = jsonArray.getJSONObject(i);
                    Globals.listSurflineBundles.add(jsonobject.getString("Name"));
                    Globals.listSurflineBundlesAmount.add(Double.parseDouble(jsonobject.getString("Value")));
                    Globals.listSurflinePlusBundles.add(jsonobject.getString("Name"));
                    Globals.listSurflinePlusBundlesAmount.add(Double.parseDouble(jsonobject.getString("Value")));
               //     Log.d(Bills.class.toString() + ".GetSurflineDeviceQuery()", "Bundle[" + i + "] Name["+jsonobject.getString("Name")+"] Value["+jsonobject.getString("Value")+"]");
                }
            }else{
                Globals.listSurflineBundles.add("Select");
                Globals.listSurflineBundlesAmount.add(0.0);
                JSONObject objBundles = jsonRespObject.getJSONObject("Bundles");
                JSONArray jsonArray = objBundles.getJSONArray("SurfBundles");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonobject = jsonArray.getJSONObject(i);
                    Globals.listSurflineBundles.add(jsonobject.getString("Name"));
                    Globals.listSurflineBundlesAmount.add(Double.parseDouble(jsonobject.getString("Value")));
                //    Log.d(Bills.class.toString() + ".GetSurflineDeviceQuery()", "SurfBundles[" + i + "] Name[" + jsonobject.getString("Name") + "] Value[" + jsonobject.getString("Value") + "]");
                }

                Globals.listSurflinePlusBundles.add("Select");
                Globals.listSurflinePlusBundlesAmount.add(0.0);
                jsonArray = objBundles.getJSONArray("SurfPlusBundles");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonobject = jsonArray.getJSONObject(i);
                    Globals.listSurflinePlusBundles.add(jsonobject.getString("Name"));
                    Globals.listSurflinePlusBundlesAmount.add(Double.parseDouble(jsonobject.getString("Value")));
              //      Log.d(Bills.class.toString() + ".GetSurflineDeviceQuery()", "SurfPlusBundles[" + i + "] Name[" + jsonobject.getString("Name") + "] Value[" + jsonobject.getString("Value") + "]");
                }
            }

        }
    }

    static public void SendVodafone(String sourceAcc, String vodafoneService, String vodafoneAcc, Double amount , boolean ignoreUserLimit) throws Exception{
        Globals.transactionId=null;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId); jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("sourceAcc",sourceAcc);
        jsonObject.put("vodafoneService",vodafoneService);
        jsonObject.put("vodafoneAcc", vodafoneAcc);
        jsonObject.put("ignoreUserLimit", ignoreUserLimit);
        jsonObject.put("amount", amount);

        jsonObject.put("otp", Globals.pinEntered); // MAW20190918


        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceVodafone, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")) {
            Globals.userLimit=null;

            if(jsonRespObject.getString("respCode").equals("005")) {
                Globals.userLimit = jsonRespObject.getString("userLimit");
                Log.e( "userLimit: ",""+  Globals.userLimit);
            }

            //throw new Exception("VODAFONE PAYMENT FAILED <RespCode=[" + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "]>");
            throw new Exception("PAYMENT REJECTED WITH CODE = " + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "\n\nTRANSACTION ID : " + (jsonRespObject.has("transactionId") ? jsonRespObject.getString("transactionId") : ""));

        }
        Globals.transactionId=jsonRespObject.getString(Globals.transactionIdTag);
    }



    static public void GetMobileWalletNameInq(String receiverPhone, String provider) throws Exception{  // MAW20170816

        Globals.ReceiverPhoneName = null;

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId); jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("receiverPhone", receiverPhone);
        jsonObject.put("provider", provider);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceMobileWalletNameInq, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("RECEIVER PHONE NAME INQUIRY FAILED WITH CODE = "+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"");

        if (jsonRespObject.has("name")){
            Globals.ReceiverPhoneName = jsonRespObject.getString("name");
        }
    }

    public static boolean GetDSTVName (String Account) throws Exception {

        Globals.DSTVName = null;
        boolean returnstmt = false;

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId); jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("DSTVAcc", Account);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceDSTVNameInq, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("DSTV NAME INQUIRY FAILED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");

        if (jsonRespObject.has("DSTVname")){
            Globals.DSTVName = jsonRespObject.getString("DSTVname");
            returnstmt = true;
        }
        return returnstmt;

    }

    public static boolean GetECGName (String Account) throws Exception {

        Globals.ECGName = null;
        boolean returnstmt = false;

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId); jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("ECGAcc", Account);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceECGNameInq, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("ECG NAME INQUIRY FAILED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");

        if (jsonRespObject.has("ECGname")){
            Globals.ECGName = jsonRespObject.getString("ECGname");
            returnstmt = true;
        }

        return returnstmt;
    }

}
