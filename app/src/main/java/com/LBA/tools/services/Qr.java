package com.LBA.tools.services;

import android.util.Log;

import com.LBA.tools.assets.Globals;
import com.LBA.tools.connection.HTTPClient;

import org.json.JSONObject;



public class Qr {

    static private final String TAG = Account.class.getSimpleName();

    static public String GetQrData(String objQr) throws Exception{

        JSONObject jsonObject = new JSONObject();
        Log.e(TAG, " objQr ********************** "+ objQr);

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        //   13062022
        jsonObject.put("authenCode", Globals.authenCode);
        //Log.d(Account.class.getName(), "GetBalance Globals.authToken[" + Globals.authenCode + "]");jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("objQr", objQr);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceQRParse, jsonObject);
        Log.e(TAG, "jsonRespObject ********************** "+ jsonRespObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("GET BALANCE FAILED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");

        String qrData = jsonRespObject.getString("qrData");

        Log.e(TAG, "GetQrData: -------------------- "+ qrData);
        Globals.qrTest = qrData;
        return qrData;
    }

   static public String GetQrData2(String objQr) throws Exception{

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
       jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("terminalId", objQr);
       //   13062022
       jsonObject.put("authenCode", Globals.authenCode);
        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceQrEnquiry, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")) {
            throw new Exception("GET QR DETAILS FAILED <RespCode=[" + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "]>");

        }
        String qrData = jsonRespObject.getString("qrData");

        return qrData;
    }

    static public void QRTransfer(String sourceAcc,  Double amount, String QRdata, String paymentDetails,boolean ignoreUserLimit , String purpose) throws Exception {
        Globals.transactionId=null;
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId); jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("sourceAcc",sourceAcc);
        jsonObject.put("ignoreUserLimit",ignoreUserLimit);
        jsonObject.put("qrdata",QRdata);
        jsonObject.put("amount", amount);
        jsonObject.put("paymentDetails", paymentDetails);
        jsonObject.put("purpose", purpose);
        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceQRTransfert, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")) {
            // throw new Exception("QR PAYMENT REJECTED REJECTED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");
            Globals.userLimit=null;

            if(jsonRespObject.getString("respCode").equals("005")) {
                Globals.userLimit = jsonRespObject.getString("userLimit");
                Log.e( "userLimit: ",""+  Globals.userLimit);
            }
            throw new Exception("QR PAYMENT REJECTED REJECTED  WITH CODE = " + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "\n\nTRANSACTION ID : " + (jsonRespObject.has("transactionId") ? jsonRespObject.getString("transactionId") : ""));
        }
        Globals.transactionId=jsonRespObject.getString(Globals.transactionIdTag);
    }
}
