package com.LBA.tools.services;

import android.util.Log;

import com.LBA.tools.assets.Globals;
import com.LBA.tools.connection.HTTPClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;




public class Payment {

    static public void GetTransferTransactionPurposes(String purposeType) throws Exception{  // MAW20170816

        Globals.purpose_type = "";

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId); jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("purposetype", purposeType);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceTrPurpose, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("TRANSACTION PURPOSE INQUIRY FAILED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");

        /*Globals.PurposeLst = new ArrayList<>();
        Globals.PurposeLst.add(Globals.Select); // MAW20180106
        JSONArray arrayPurpose = jsonRespObject.getJSONArray("PurposeList");
        for(int i = 0 ; i < arrayPurpose.length() ; i++){
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

            Globals.PurposeLst.add(arrayPurpose.getString(i));
        }*/

        Globals.agentsList = new ArrayList<>();
        Globals.agentsList.add(Globals.Select); // MAW20180106
        JSONArray arrayAgents = jsonRespObject.getJSONArray("nameList");
        for(int i = 0 ; i < arrayAgents.length() ; i++){
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

            Globals.agentsList.add(arrayAgents.getString(i));
        }
    }

    static public void paymentTravel(String name, String booking, String debitAccount, Double amount, String paymentDetail, String email, String purpose, String destAccount , boolean ignoreUserLimit ) throws Exception{
        Globals.transactionId=null;
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId); jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("sourceAcc",debitAccount);
        jsonObject.put("booking",booking);
        jsonObject.put("name", name);
        jsonObject.put("amount", amount);
        jsonObject.put("transactionPurpose", purpose);
        jsonObject.put("ignoreUserLimit", ignoreUserLimit);
        jsonObject.put("paymentDetails", paymentDetail);
        jsonObject.put("email", email);
        jsonObject.put("destAcc", destAccount);

        jsonObject.put("otp", Globals.pinEntered); // MAW20190918

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.servicePaymentTravel, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")) {
            //throw new Exception(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"");
            Globals.userLimit=null;

            if(jsonRespObject.getString("respCode").equals("005")) {
                Globals.userLimit = jsonRespObject.getString("userLimit");
                Log.e( "userLimit: ",""+  Globals.userLimit);
            }    throw new Exception("TRAVEL PAYMENT WITH CODE = " + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "\n\nTRANSACTION ID : " + (jsonRespObject.has("transactionId") ? jsonRespObject.getString("transactionId") : ""));
        }
        Globals.transactionId=jsonRespObject.getString(Globals.transactionIdTag);

    }
    static public void paymentHotel(String name, String booking, String debitAccount, Double amount, String paymentDetail, String email, String purpose, String destAccount , boolean ignoreUserLimit) throws Exception {
        Globals.transactionId = null;
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("sourceAcc", debitAccount);
        jsonObject.put("booking", booking);
        jsonObject.put("name", name);
        jsonObject.put("amount", amount);
        jsonObject.put("transactionPurpose", purpose);
        jsonObject.put("ignoreUserLimit", ignoreUserLimit);
        jsonObject.put("paymentDetails", paymentDetail);
        jsonObject.put("email", email);
        jsonObject.put("destAcc", destAccount);

        jsonObject.put("otp", Globals.pinEntered); // MAW20190918

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.servicePaymentHotel, jsonObject);

        if (jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")){
            //throw new Exception((jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):""));
            Globals.userLimit=null;

            if(jsonRespObject.getString("respCode").equals("005")) {
                Globals.userLimit = jsonRespObject.getString("userLimit");
                Log.e( "userLimit: ",""+  Globals.userLimit);
            }
            throw new Exception("HOTEL PAYMENT REJECTED WITH CODE = " + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "\n\nTRANSACTION ID : " + (jsonRespObject.has("transactionId") ? jsonRespObject.getString("transactionId") : ""));
    }
        Globals.transactionId=jsonRespObject.getString(Globals.transactionIdTag);

    }

    static public void paymentUniversity(String name, String studentName, String studentID, String indexNum, String debitAccount, Double amount, String paymentDetail, String email, String purpose, String destAccount) throws Exception{
        Globals.transactionId=null;
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId); jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("sourceAcc",debitAccount);
        jsonObject.put("studentName",studentName);
        jsonObject.put("name", name);
        jsonObject.put("amount", amount);
        jsonObject.put("transactionPurpose", purpose);
        jsonObject.put("paymentDetails", paymentDetail);
        jsonObject.put("email", email);
        jsonObject.put("destAcc", destAccount);
        jsonObject.put("studentID", studentID);
        jsonObject.put("indexNum", indexNum);

        jsonObject.put("otp", Globals.pinEntered); // MAW20190918

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.servicePaymentUniversity, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")) {
            // throw new Exception("UNIVERSITY PAYMENT REJECTED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");
            Globals.userLimit=null;

            if(jsonRespObject.getString("respCode").equals("005")) {
                Globals.userLimit = jsonRespObject.getString("userLimit");
                Log.e( "userLimit: ",""+  Globals.userLimit);
            }
            throw new Exception("UNIVERSITY PAYMENT REJECTED WITH CODE = " + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "\n\nTRANSACTION ID : " + (jsonRespObject.has("transactionId") ? jsonRespObject.getString("transactionId") : ""));
        }
        Globals.transactionId=jsonRespObject.getString(Globals.transactionIdTag);

    }

    static public void paymentSchool(String name, String studentName, String studentID, String Class, String debitAccount, Double amount, String paymentDetail, String email, String purpose, String destAccount , boolean ignoreUserLimit) throws Exception{
        Globals.transactionId=null;
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId); jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("sourceAcc",debitAccount);
        jsonObject.put("studentName",studentName);
        jsonObject.put("name", name);
        jsonObject.put("amount", amount);
        jsonObject.put("ignoreUserLimit", ignoreUserLimit);
        jsonObject.put("transactionPurpose", purpose);
        jsonObject.put("paymentDetails", paymentDetail);
        jsonObject.put("email", email);
        jsonObject.put("destAcc", destAccount);
        jsonObject.put("studentID", studentID);
        jsonObject.put("Class", Class);

        jsonObject.put("otp", Globals.pinEntered); // MAW20190918

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.servicePaymentSchool, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            //throw new Exception((jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):""));
        {  Globals.userLimit=null;

            if(jsonRespObject.getString("respCode").equals("005")) {
                Globals.userLimit = jsonRespObject.getString("userLimit");
                Log.e( "userLimit: ",""+  Globals.userLimit);
            }
            throw new Exception("SCHOOL PAYMENT REJECTED WITH CODE = "+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"\n\nTRANSACTION ID : "+(jsonRespObject.has("transactionId")?jsonRespObject.getString("transactionId"):""));
        }

        Globals.transactionId=jsonRespObject.getString(Globals.transactionIdTag);

    }

    static public void paymentChurch(String name, String debitAccount, Double amount, String paymentDetail, String email, String purpose, String destAccount , boolean ignoreUserLimit) throws Exception{
        Globals.transactionId=null;
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId); jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("sourceAcc",debitAccount);
        jsonObject.put("name", name);
        jsonObject.put("amount", amount);
        jsonObject.put("transactionPurpose", purpose);
        jsonObject.put("ignoreUserLimit", ignoreUserLimit);
        jsonObject.put("paymentDetails", paymentDetail);
        jsonObject.put("email", email);
        jsonObject.put("destAcc", destAccount);

        jsonObject.put("otp", Globals.pinEntered); // MAW20190918

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.servicePaymentChurch, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            //throw new Exception((jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):""));
        {
            Globals.userLimit=null;

            if(jsonRespObject.getString("respCode").equals("005")) {
                Globals.userLimit = jsonRespObject.getString("userLimit");
                Log.e( "userLimit: ",""+  Globals.userLimit);
            }
            throw new Exception("CHURCH PAYMENT REJECTED WITH CODE = "+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"\n\nTRANSACTION ID : "+(jsonRespObject.has("transactionId")?jsonRespObject.getString("transactionId"):""));
        }

        Globals.transactionId=jsonRespObject.getString(Globals.transactionIdTag);

    }



    public static void GetPayeeList(String payeeName, String type) throws Exception{
        Globals.sessionTKO ="";

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId); jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("payeeName", payeeName);
        jsonObject.put("type", type);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.servicePayeeNamesList, jsonObject);
        Globals.sessionTKO = jsonRespObject.getString("respCode");

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("PAYEES NAMES INQUIRY FAILED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");

        //Globals.agentAccount = new ArrayList<>();
        //Globals.agentAccount.add(Globals.Select); // MAW20180106

        //Globals.payeeAccount = new ArrayList<String>();
        if(type.equals("Travel")) {
            JSONArray arrayAgents = jsonRespObject.getJSONArray("payeeAccount");
            Globals.payeeAccount.clear();
            Globals.payeeAccount.add(Globals.Select);
            for (int i = 0; i < arrayAgents.length(); i++) {
                // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

                Globals.payeeAccount.add(arrayAgents.getString(i));
            }
        }
        if(type.equals("Hotel")){
            Globals.payeeAccount.clear();
            Globals.payeeAccount.add(Globals.Select);
            JSONArray arrayHotels = jsonRespObject.getJSONArray("payeeAccount");
            for (int i = 0; i < arrayHotels.length(); i++) {
                // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

                Globals.payeeAccount.add(arrayHotels.getString(i));
            }
        }
        if(type.equals("University")){
            Globals.payeeAccount.clear();
            Globals.payeeAccount.add(Globals.Select);
            JSONArray arrayUni = jsonRespObject.getJSONArray("payeeAccount");
            for (int i = 0; i < arrayUni.length(); i++) {
                // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

                Globals.payeeAccount.add(arrayUni.getString(i));
            }
        }
        if(type.equals("School")){
            Globals.payeeAccount.clear();
            Globals.payeeAccount.add(Globals.Select);
            JSONArray arraySchool = jsonRespObject.getJSONArray("payeeAccount");
            for (int i = 0; i < arraySchool.length(); i++) {
                // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

                Globals.payeeAccount.add(arraySchool.getString(i));
            }
        }
        if(type.equals("Church")){
            Globals.payeeAccount.clear();
            Globals.payeeAccount.add(Globals.Select);
            JSONArray arrayChurch = jsonRespObject.getJSONArray("payeeAccount");
            for (int i = 0; i < arrayChurch.length(); i++) {
                // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

                Globals.payeeAccount.add(arrayChurch.getString(i));
            }
        }
    }

}
