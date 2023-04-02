package com.LBA.tools.services;

import android.util.Log;

import com.LBA.tools.assets.Globals;
import com.LBA.tools.connection.HTTPClient;

import org.json.JSONObject;



/**
 * Created by amine.wahbi on 2/11/2015.
 */
public class Requests {

    //   05072022

    public static void ChequeStatus(String sourceAcc, String chequeNumber) throws Exception {
        Globals.chequeStatus="";
        Globals.isSuccessful = false;
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId); jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("account", sourceAcc);
        jsonObject.put("chequeNo", chequeNumber);
        jsonObject.put("otp", Globals.pinEntered);


        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceChequeStatus, jsonObject);
        Log.e( "ChequeStatus: ","jsonRespObject : "+jsonRespObject );
        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception(" CHEQUE STATUS FAILED WITH CODE : "+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):""));
        if(jsonRespObject.has("status")){
            Log.e( "ChequeStatus: ","jsonObject.has('status') : "+jsonRespObject.has("status") );
            Globals.chequeStatus=jsonRespObject.getString("status");
            Globals.isSuccessful = true;
        }
    }
    public static void SendCheque(String sourceAcc, int chequeNumPages) throws Exception {
        Globals.transactionId=null;
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId); jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("sourceAcc", sourceAcc);
        jsonObject.put("chequeNumPages", chequeNumPages);


        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceCheque, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("CHEQUE REQUEST FAILED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");

        Globals.transactionId=jsonRespObject.getString(Globals.transactionIdTag);
    }

    public static void SendStopCheque(String sourceAcc, String chequeNumber, String reason) throws Exception {
        Globals.transactionId=null;
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId); jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("sourceAcc", sourceAcc);
        jsonObject.put("chequeNumber", chequeNumber);
        jsonObject.put("reason", reason);


        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceStopCheque, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("STOP CHEQUE FAILED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");

        Globals.transactionId=jsonRespObject.getString(Globals.transactionIdTag);
    }

    public static void SendConfirmCheque(String debAcc, String chequeNum, String Issuee, Double Amount, String branch) throws Exception{
        Globals.transactionId=null;
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("debAcc", debAcc);
        jsonObject.put("chequeNumber", chequeNum);
        jsonObject.put("Issuee", Issuee);
        jsonObject.put("Amount", Amount);
        jsonObject.put("otp", Globals.pinEntered);
        jsonObject.put("payingBranch", branch);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceConfirmCheque, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("CHEQUE CONFIRMATION FAILED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");

        //Globals.transactionId=jsonRespObject.getString(Globals.transactionIdTag);
    }

    public static void SendTravelNotice(String selectedCountry, String depDate, String arrDate, String card6, String email, String contactNum) throws Exception{
        //Globals.transactionId=null;
        JSONObject jsonObject = new JSONObject();

        //Log.d("email++++",""+email);
        //Log.d("contact++++",""+contactNum);

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("selectedCountry", selectedCountry);
        jsonObject.put("depDate", depDate);
        jsonObject.put("arrDate", arrDate);
        jsonObject.put("card6", card6);
        //jsonObject.put("card4", card4);
        jsonObject.put("email", email);
        jsonObject.put("contactNum", contactNum);
        jsonObject.put("otp", Globals.pinEntered);
        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceTravelNotice, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("TRAVEL NOTICE REQUEST FAILED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");

       // Globals.transactionId=jsonRespObject.getString(Globals.transactionIdTag);

    }

/**    public static void SendTravelNoticeO(String selectedCountry, String depDate, String arrDate, String card6, String card4) throws Exception{
        Globals.transactionId=null;
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("selectedCountry", selectedCountry);
        jsonObject.put("depDate", depDate);
        jsonObject.put("arrDate", arrDate);
        jsonObject.put("card6", card6);
        jsonObject.put("card4", card4);
        jsonObject.put("otp", Globals.otp);
        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceTravelNotice, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("TRAVEL NOTICE REQUEST FAILED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");

        Globals.transactionId=jsonRespObject.getString(Globals.transactionIdTag);

    }
 **/

        public static void StandingOrderRequest (String srcAcc, String destAcc,String periodType, String dayOFWeek, int dayOfMonth, String dayOfYear,String expDate, Double amount, String narration ) throws Exception{
            Globals.transactionId=null;
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("user", Globals.user);
            jsonObject.put("password", Globals.password);
            jsonObject.put("sessionId", Globals.sessionId);
            jsonObject.put("authenCode", Globals.authenCode);
            jsonObject.put("sourceAcc", srcAcc);
            jsonObject.put("destinationAcc", destAcc);
            jsonObject.put("periodType", periodType);
            jsonObject.put("dayOfWeek", dayOFWeek);
            jsonObject.put("dayOfMonth", dayOfMonth);
            jsonObject.put("dayOfYear", dayOfYear);
            jsonObject.put("expDate", expDate);
            jsonObject.put("amount", amount);
            jsonObject.put("narration", narration);


            jsonObject.put("otp", Globals.pinEntered);
            JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceStandingOrderService, jsonObject);

            if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
               // throw new Exception("STANDING ORDER REQUEST FAILED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");
                throw new Exception("STANDING ORDER REQUEST FAILED WITH CODE = "+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"\n\nTRANSACTION ID : "+(jsonRespObject.has("transactionId")?jsonRespObject.getString("transactionId"):""));

            Globals.transactionId=jsonRespObject.getString(Globals.transactionIdTag);

        }
}
