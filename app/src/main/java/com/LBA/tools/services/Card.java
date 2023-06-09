package com.LBA.tools.services;

import android.util.Log;

import com.LBA.tools.assets.Globals;
import com.LBA.tools.connection.HTTPClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by amine.wahbi on 30/10/2015.
 */
public class Card {
    static public void GetBalance() throws Exception{


        JSONObject jsonObject = new JSONObject();
        Log.e("TAG", "Token: "+Globals.token);
        jsonObject.put("token", Globals.token);

        Log.e("TAG", "Token: "+Globals.user);


        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceGetBalance, jsonObject);
        Log.e("TAG", "CardDetails: "+jsonRespObject);

        if(jsonRespObject.has("responseCode") && !jsonRespObject.getString("responseCode").equals("00"))

            throw new Exception("PIN REQUEST FAILED <RespCode=["+(jsonRespObject.has("responseCode")?jsonRespObject.getString("responseCode"):"")+"]>");


        Globals.availableBalance = jsonRespObject.getString("availableBalance");
        Globals.balance = jsonRespObject.getString("balance");
        Globals.currency = jsonRespObject.getString("currency");


        Log.e("TAG", "cardNumber "+Globals.availableBalance);

    }

    static public void PINRequest(String cardNbr) throws Exception{


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("cardNbr", cardNbr);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.servicePINRequest, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            // throw new Exception("Pin request already done");
            throw new Exception("PIN REQUEST FAILED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");
        // Globals.transactionId=jsonRespObject.getString(Globals.transactionIdTag);

    }
    //oussama 3/31/2023
    static public void CardDetails() throws Exception{


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", "4281993108775830" );
        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceCardDetails, jsonObject);
        Log.e("TAG", "CardDetails: "+jsonRespObject);

        if(jsonRespObject.has("responseCode") && !jsonRespObject.getString("responseCode").equals("00"))

            throw new Exception("PIN REQUEST FAILED <RespCode=["+(jsonRespObject.has("responseCode")?jsonRespObject.getString("responseCode"):"")+"]>");


        Globals.cardNumber = jsonRespObject.getString("cardNumber");
        Globals.bankCode = jsonRespObject.getString("bankCode");
        Globals.bankName = jsonRespObject.getString("bankName");
        Globals.clientCode= jsonRespObject.getString("clientCode");
        Globals.Branch= jsonRespObject.getString("branch");
        Globals.clientType= jsonRespObject.getString("clientType");
        Globals.statusCard= jsonRespObject.getString("statusCard");
        Globals.productCode= jsonRespObject.getString("productCode");
        Globals.titleCardHolder= jsonRespObject.getString("titleCardholder");
        Globals.familyName= jsonRespObject.getString("familyName");
        Globals.documentCode= jsonRespObject.getString("documentCode");
        Globals.documentId= jsonRespObject.getString("documentId");
        Globals.address= jsonRespObject.getString("address");
        Globals.expiryDate= jsonRespObject.getString("expiryDate");
        Globals.birthDate= jsonRespObject.getString("birthDate");
        Globals.mobileNumber= jsonRespObject.getString("mobileNumber");
        Globals.city= jsonRespObject.getString("city");
        Globals.country= jsonRespObject.getString("country");


        Log.e("TAG", "cardNumber "+Globals.cardNumber);
    }
    static public void AccountToCard() throws Exception{


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", "4281993108775830" );



        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceAccountToCard, jsonObject);
        Log.e("TAG", "Account To card: "+jsonRespObject);

        if(jsonRespObject.has("responseCode") && !jsonRespObject.getString("responseCode").equals("92"))
            // throw new Exception("Pin request already done");
            throw new Exception("DONNEE ERRONE <RespCode=["+(jsonRespObject.has("responseCode")?jsonRespObject.getString("responseCode"):"")+"]>");
        // Globals.transactionId=jsonRespObject.getString(Globals.transactionIdTag);

        Globals.message = jsonRespObject.getString("message");
    }
    static public void CardToCard() throws Exception{


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cardToken", "6361899512621901" );
        jsonObject.put("accountNumber", "54823158647" );
        jsonObject.put("amount", "20000" );
        jsonObject.put("currency", "USD" );
        jsonObject.put("memo", "transfert to my card" );


        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceCardToCard, jsonObject);
        Log.e("TAG", "Card To card: "+jsonRespObject);

        if(jsonRespObject.has("responseCode") && !jsonRespObject.getString("responseCode").equals("01"))
            // throw new Exception("Pin request already done");
            throw new Exception("PIN REQUEST FAILED <RespCode=["+(jsonRespObject.has("responseCode")?jsonRespObject.getString("responseCode"):"")+"]>");
        // Globals.transactionId=jsonRespObject.getString(Globals.transactionIdTag);
        Globals.message = jsonRespObject.getString("message");

    }
    static public void BlockCard(String operation) throws Exception{


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cardToken", "4281993108775830");
        jsonObject.put("operation", operation );


        Log.e("TAG", "doInBackground blockCard: " );
        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceBlockCard, jsonObject);
        Log.e("TAG", "Block Card 2 : "+jsonRespObject);

        if(jsonRespObject.has("message") )

            throw new Exception(""+ jsonRespObject.getString("message"));

        Globals.message = jsonRespObject.getString("message");
    }
    static public void UpdateLimit() throws Exception{

        Globals.message ="";
                JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", "4281993108775830" );
        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceCardLimit, jsonObject);
        Log.e("TAG", "Update Limit: "+jsonRespObject);

        if(jsonRespObject.has("responseCode") && !jsonRespObject.getString("responseCode").equals("00"))
            // throw new Exception("Pin request already done");
            throw new Exception("PIN REQUEST FAILED <RespCode=["+(jsonRespObject.has("responseCode")?jsonRespObject.getString("responseCode"):"")+"]>");
        // Globals.transactionId=jsonRespObject.getString(Globals.transactionIdTag);
          Globals.cardNumber = jsonRespObject.getString("cardNumber");
          Globals.limitCash = jsonRespObject.getString("limitCash");
          Globals.limitPurchase = jsonRespObject.getString("limitPurchase");
          Globals.limitEcom = jsonRespObject.getString("limitEcom");
          Globals.message = jsonRespObject.getString("message");
    }
    static public void GetCardsToUnblockList() throws Exception{

        List<String> list=new ArrayList<String>();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", Globals.authenCode);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceCardsToUnblockList, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("GET CARDS TO ACTIVATE LIST FAILED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");

        Globals.cardsToActivateList.clear();
        Globals.maskedCardsToActivateList.clear();
        JSONArray arrayCards = jsonRespObject.getJSONArray("list");
        Globals.cardsToActivateList.add("Select");
        Globals.maskedCardsToActivateList.add("Select");
        for(int i = 0 ; i < arrayCards.length() ; i++){
            Globals.cardsToActivateList.add(arrayCards.getString(i));
            Globals.maskedCardsToActivateList.add(arrayCards.getString(i).substring(0,6)+"******"+arrayCards.getString(i).substring(12, 16));
            //  Globals.maskedCardsToActivateList.add("***********"+arrayCards.getString(i).substring(11));
        }
    }
    static public void StopCard(String cardNbr) throws Exception{

        Globals.transactionId=null;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("cardNbr", cardNbr);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceStopCard, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            //throw new Exception("Card Already Stopped");
            throw new Exception("STOP CARD FAILED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");


    }

    static public void GetCardsToActivateList() throws Exception{

        List<String> list=new ArrayList<String>();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", Globals.authenCode);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceCardsNotActiveList, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("GET CARDS TO ACTIVATE LIST FAILED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");

        Globals.cardsToActivateList.clear();
        Globals.maskedCardsToActivateList.clear();
        JSONArray arrayCards = jsonRespObject.getJSONArray("list");
        Globals.cardsToActivateList.add("Select");
        Globals.maskedCardsToActivateList.add("Select");
        for(int i = 0 ; i < arrayCards.length() ; i++){
            Globals.cardsToActivateList.add(arrayCards.getString(i));
            Globals.maskedCardsToActivateList.add(arrayCards.getString(i).substring(0,6)+"******"+arrayCards.getString(i).substring(12, 16));
          //  Globals.maskedCardsToActivateList.add("***********"+arrayCards.getString(i).substring(11));
        }
    }

    static public void GetActiveCardsList() throws Exception{

        List<String> list=new ArrayList<String>();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", Globals.authenCode);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceCardsActiveList, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("GET CARDS TO ACTIVATE LIST FAILED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");

        Globals.cardsList.clear();
        Globals.maskedCardsList.clear();
        JSONArray arrayCards = jsonRespObject.getJSONArray("list");
        Globals.cardsList.add("Select");
        Globals.maskedCardsList.add("Select");
        for(int i = 0 ; i < arrayCards.length() ; i++){
            Globals.cardsList.add(arrayCards.getString(i));
            Globals.maskedCardsList.add(arrayCards.getString(i).substring(0,6)+"******"+arrayCards.getString(i).substring(12, 16));
          //  Globals.maskedCardsList.add("***********"+arrayCards.getString(i).substring(11));
        }
    }


    static public void UnblockCard(String cardNumber) throws Exception{

        List<String> list=new ArrayList<String>();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("cardNbr", cardNumber);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceUnblockCard, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            //throw new Exception("Card Already Stopped");
            throw new Exception("UNBLOCK CARD FAILED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");
    }
    static public void ActivateCard(String cardNumber) throws Exception{

        List<String> list=new ArrayList<String>();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("cardNbr", cardNumber);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceActivateCard, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            //throw new Exception("Card Already Stopped");
            throw new Exception("ACTIVATE CARD FAILED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");
    }

    static public void PINChange(String cardNumber, String pin) throws Exception{

        List<String> list=new ArrayList<String>();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("cardNbr", cardNumber);
        jsonObject.put("pin", pin);

        jsonObject.put("otp", Globals.pinEntered); // MAW20190918


        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.servicePINChange, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            //throw new Exception("Card Already Stopped");
            throw new Exception("Created CARD PIN FAILED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");
    }

    public static void RequestCardLimit(String card, String startDate, String endDate, Double AmountAtm,Double AmountPos) throws Exception{

        JSONObject jsonObject = new JSONObject();

        //Log.d("SD++++",""+startDate);Log.d("pos++++",""+AmountPos);
        //Log.d("ED++++",""+endDate);Log.d("CC++++",""+card);Log.d("Atm++++",""+AmountAtm);

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("card", card);
        jsonObject.put("startDate", startDate);
        jsonObject.put("endDate", endDate);
        jsonObject.put("AmountAtm", AmountAtm);
        jsonObject.put("AmountPos", AmountPos);
        jsonObject.put("otp", Globals.pinEntered);
        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceCardLimit, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception(""+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):""));


    }

    static public void PrepaidTopUpService(String sourceAcc,String destinationAcc,Double amount, String paymentDetails, String purpose , boolean ignoreUserLimit) throws Exception{
        Globals.transactionId=null;
        Globals.userLimit=null;

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("sourceAcc",sourceAcc);
        jsonObject.put("destinationAcc",destinationAcc);
        jsonObject.put("ignoreUserLimit",ignoreUserLimit);
        jsonObject.put("amount", amount);
        jsonObject.put("paymentDetails", paymentDetails);
        jsonObject.put("purpose", purpose);


        jsonObject.put("otp", Globals.pinEntered); // MAW20190918

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.servicePrepaidTopUp, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")){
            if(jsonRespObject.getString("respCode").equals("005")) {
                Globals.userLimit = jsonRespObject.getString("userLimit");
                Log.e("", "userLimit: "+  Globals.userLimit);
            }
            throw new Exception("Prepaid Top Up REJECTED WITH CODE : "+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+ "\n\nTRANSACTION ID : " + (jsonRespObject.has("transactionId") ? jsonRespObject.getString("transactionId") : ""));
        }

        Globals.transactionId=jsonRespObject.getString(Globals.transactionIdTag);
    }

}
