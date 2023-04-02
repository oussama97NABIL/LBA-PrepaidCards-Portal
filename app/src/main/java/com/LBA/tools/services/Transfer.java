package com.LBA.tools.services;

import android.util.Log;

import com.LBA.tools.assets.Globals;
import com.LBA.tools.connection.HTTPClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;



/**
 * Created by amine.wahbi on 22/10/2015.
 */
public class Transfer {
    //3/28/2023 static private final String TAG = ChangeCardPinActivity.class.getSimpleName();

   /* static public void SendA2ATransfer(String sourceAcc,String destinationAcc,Double amount, String paymentDetails) throws Exception{
        Globals.transactionId=null;
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId); jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("sourceAcc",sourceAcc);
        jsonObject.put("destinationAcc",destinationAcc);
        jsonObject.put("amount", amount);
        jsonObject.put("paymentDetails", paymentDetails);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceAccountToAccount, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("ACCOUNT TO ACCOUNT TRANSFER REJECTED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");

        Globals.transactionId=jsonRespObject.getString(Globals.transactionIdTag);
    }*///(String sourceAcc,String destinationAcc,Double amount, String paymentDetails, String purpose)
   static public void SendA2ATransfer(String sourceAcc,String destinationAcc,Double amount, String paymentDetails, String purpose , boolean ignoreUserLimit) throws Exception{
       Globals.transactionId=null;
       Globals.userLimit=null;

       JSONObject jsonObject = new JSONObject();

       jsonObject.put("user", Globals.user);
       jsonObject.put("password", Globals.password);
       jsonObject.put("sessionId", Globals.sessionId); jsonObject.put("authenCode", Globals.authenCode);
       jsonObject.put("sourceAcc",sourceAcc);
       jsonObject.put("destinationAcc",destinationAcc);
       jsonObject.put("amount", amount);
       jsonObject.put("paymentDetails", paymentDetails);
       jsonObject.put("purpose", purpose);
       jsonObject.put("ignoreUserLimit", ignoreUserLimit);



       jsonObject.put("otp", Globals.pinEntered); // MAW20190918

       JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceAccountToAccount, jsonObject);

       if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")) {

           if(jsonRespObject.getString("respCode").equals("005")) {
               Globals.userLimit = jsonRespObject.getString("userLimit");
               Log.e("TAG", "userLimit: "+  Globals.userLimit);
           }
           throw new Exception("ACCOUNT TO ACCOUNT TRANSFER REJECTED WITH CODE = " + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "\n\nTRANSACTION ID : " + (jsonRespObject.has("transactionId") ? jsonRespObject.getString("transactionId") : ""));
       }
       Globals.transactionId=jsonRespObject.getString(Globals.transactionIdTag);
   }

    static public void SendMoneyVoucher(String sourceAcc,String destinationMob,String voucherPIN, Double amount,String purpose , boolean ignoreUserLimit) throws Exception{
        Globals.transactionId=null;
        Globals.userLimit=null;

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId); jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("sourceAcc",sourceAcc);
        jsonObject.put("purpose", purpose);
        jsonObject.put("destinationMob",destinationMob);
        jsonObject.put("voucherPIN", voucherPIN);
        jsonObject.put("amount", amount);
        jsonObject.put("ignoreUserLimit", ignoreUserLimit);


        jsonObject.put("otp", Globals.pinEntered); // MAW20190918
        //Log.d(TAG, "JSONOBJECT++++++++++++++++"+jsonObject.toString());

        //Log.d(TAG, "JSONOBJECT++++++++++++++++"+jsonObject.toString());

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceMoneyVoucher, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")) {
            //throw new Exception((jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):""));
            if(jsonRespObject.getString("respCode").equals("005")) {
                Globals.userLimit = jsonRespObject.getString("userLimit");
                Log.e("TAG", "userLimit: "+  Globals.userLimit);
            }
            throw new Exception("Money Voucher  TRANSFER REJECTED WITH CODE = " + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "\n\nTRANSACTION ID : " + (jsonRespObject.has("transactionId") ? jsonRespObject.getString("transactionId") : ""));

        }
        Globals.transactionId=jsonRespObject.getString(Globals.transactionIdTag);
    }


    static public void SendGInstantPay(String sourceAcc,String destinationAcc, String payeeName, String destinationBank, Double amount, String paymentDetails, String purpose, boolean ignoreUserLimit,boolean chargesAccepted) throws Exception{
        Globals.transactionId=null;
        Globals.userLimit=null;

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId); jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("sourceAcc",sourceAcc);
        jsonObject.put("purpose", purpose);
        jsonObject.put("destinationAcc",destinationAcc);
        jsonObject.put("destinationBank", destinationBank);
        jsonObject.put("amount", amount);
        jsonObject.put("ignoreUserLimit", ignoreUserLimit);
        jsonObject.put("paymentDetails", paymentDetails);
        jsonObject.put("chargesAccepted", chargesAccepted);

        jsonObject.put("otp", Globals.pinEntered); // MAW20190918
        Globals.OffAmountPay = null;

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceInstantPay, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")) {
            // throw new Exception("INSTANT PAY TRANSFER REJECTED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");
            if(jsonRespObject.getString("respCode").equals("005")) {
                Globals.userLimit = jsonRespObject.getString("userLimit");
                Log.e("TAG", "userLimit: "+  Globals.userLimit);
            }else if(jsonRespObject.getString("respCode").equals("006")){
                Globals.OffAmountPay =  jsonRespObject.getString("GIPCharges");
            }
            throw new Exception("INSTANT PAY TRANSFER REJECTED WITH CODE = " + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "\n\nTRANSACTION ID : " + (jsonRespObject.has("transactionId") ? jsonRespObject.getString("transactionId") : ""));
        }
        Globals.transactionId=jsonRespObject.getString(Globals.transactionIdTag);
    }

    static public String getInstantPayNameCr(String sourceAcc,String destinationAcc, String payeeName, String destinationBank, Double amount, String paymentDetails) throws Exception{
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("sourceAcc",sourceAcc);
        jsonObject.put("destinationAcc",destinationAcc);
        jsonObject.put("destinationBank", destinationBank);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceInstantPayNameEnq, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("INSTANT PAY NAME ENQUIRYREJECTED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");


        return !jsonRespObject.has("name")?"":jsonRespObject.getString("name");
    }

    static public void SendACH(String sourceAcc,String destinationAcc, String payeeName, String destinationBank, String destinationBranch, Double amount, String paymentDetails,String purpose , boolean ignoreUserLimit ) throws Exception {
        Globals.transactionId = null;
        Globals.userLimit=null;

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("sourceAcc", sourceAcc);
        jsonObject.put("purpose", purpose);
        jsonObject.put("destinationAcc", destinationAcc);
        jsonObject.put("destinationBank", destinationBank);
        jsonObject.put("destinationBranch", destinationBranch);
        jsonObject.put("payeeName", payeeName);
        jsonObject.put("amount", amount);
        jsonObject.put("paymentDetails", paymentDetails);
        jsonObject.put("ignoreUserLimit", ignoreUserLimit);


        jsonObject.put("otp", Globals.pinEntered); // MAW20190918


        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceACH, jsonObject);

        if (jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")){
            if(jsonRespObject.getString("respCode").equals("005")) {
                Globals.userLimit = jsonRespObject.getString("userLimit");
                Log.e("TAG", "userLimit: "+  Globals.userLimit);
            }
            throw new Exception("ACH TRANSFER REJECTED WITH CODE = " + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "\n\nTRANSACTION ID : " + (jsonRespObject.has("transactionId") ? jsonRespObject.getString("transactionId") : ""));
    }
//        throw new Exception((jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):""));
        Globals.transactionId=jsonRespObject.getString(Globals.transactionIdTag);
    }

    // younes

    static public void SendBtwnAcc(String sourceAcc,String destinationAcc,Double amount, String paymentDetails, String purpose, boolean ignoreUserLimit) throws Exception {
        Globals.transactionId = null;
        Globals.userLimit=null;

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("sourceAcc", sourceAcc);
        jsonObject.put("destinationAcc", destinationAcc);
        jsonObject.put("amount", amount);
        jsonObject.put("paymentDetails", paymentDetails);
        jsonObject.put("ignoreUserLimit", ignoreUserLimit);
        jsonObject.put("transactionPurpose", purpose);

        jsonObject.put("otp", Globals.pinEntered); // MAW20190918

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceBtwnAcc, jsonObject);

        if (jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")){

            if(jsonRespObject.getString("respCode").equals("005")) {
                Globals.userLimit = jsonRespObject.getString("userLimit");
                Log.e("TAG", "userLimit: "+  Globals.userLimit);
            }
            throw new Exception("TRANSFER REJECTED WITH CODE = " + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "\n\nTRANSACTION ID : " + (jsonRespObject.has("transactionId") ? jsonRespObject.getString("transactionId") : ""));

    }
        //throw new Exception(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"");

        Globals.transactionId=jsonRespObject.getString(Globals.transactionIdTag);
    }

    static public void GetTransferTransactionAccount(String Number) throws Exception{  // MAW20170816

        Globals.purpose_type = "";

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId); jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("mobile", Number);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceTrPurpose, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")) {
            if(jsonRespObject.getString("respCode").equals("005")) {
                Globals.userLimit = jsonRespObject.getString("userLimit");
                Log.e("TAG", "userLimit: "+  Globals.userLimit);
            }
            throw new Exception("ACCOUNT MOBILE INQUIRY FAILED <RespCode=[" + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "]>");

        }
       // Globals.DestAccMobile = jsonRespObject.get("AccList")
        Globals.DestAccMobile = new ArrayList<String>();
        Globals.DestAccMobile.add(Globals.Select); // MAW20180106
        JSONArray arrayPurpose = jsonRespObject.getJSONArray("AccList");
        for(int i = 0 ; i < arrayPurpose.length() ; i++){
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

            Globals.DestAccMobile.add(arrayPurpose.getString(i));
        }
    }

    static public void SendA2MTransfer(String sourceAcc,Double amount, String paymentDetails, String purpose, String mobile , boolean ignoreUserLimit) throws Exception{
        Globals.transactionId=null;
        Globals.userLimit=null;
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId); jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("sourceAcc",sourceAcc);
       // jsonObject.put("destinastionAcc",destinationAcc);
        jsonObject.put("amount", amount);
        jsonObject.put("paymentDetails", paymentDetails);
        jsonObject.put("purpose", purpose);
        jsonObject.put("ignoreUserLimit", ignoreUserLimit);
        jsonObject.put("mobile", mobile);


        jsonObject.put("otp", Globals.pinEntered); // MAW20190918

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceAccountToMobile, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")) {
            //throw new Exception((jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):""));
            if(jsonRespObject.getString("respCode").equals("005")) {
                Globals.userLimit = jsonRespObject.getString("userLimit");
                Log.e("TAG", "userLimit: "+  Globals.userLimit);
            }

            throw new Exception("TRANSFER REJECTED WITH CODE = " + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "\n\nTRANSACTION ID : " + (jsonRespObject.has("transactionId") ? jsonRespObject.getString("transactionId") : ""));

        }
        Globals.transactionId=jsonRespObject.getString(Globals.transactionIdTag);
        Log.e("TAG", "SendA2MTransfer: "+jsonRespObject.getString("respCode") +"");
    }
}
