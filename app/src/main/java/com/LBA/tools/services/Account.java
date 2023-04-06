package com.LBA.tools.services;

import android.util.Log;

import com.LBA.tools.assets.Globals;
import com.LBA.tools.connection.HTTPClient;
import com.LBA.tools.misc.HistoryEntry;
import com.LBA.tools.misc.StandingOrderEntry;
import com.LBA.tools.misc.T24TrxHistDetails;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by amine.wahbi on 22/10/2015.
 */
public class Account {

    static public void UnlinkAccount() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userCode", Globals.user );
        jsonObject.put("otp", Globals.otp);
        jsonObject.put("userType", Globals.userType);
        //   13062022
        jsonObject.put("authenCode", Globals.authenCode);

        Log.e("UnlinkAccount"," : "+jsonObject.toString() );
        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.UnlinkAccount, jsonObject);
        Log.e("UnlinkAccount"," : "+jsonObject.toString());
        Globals.errorOnBoard = jsonRespObject.getString("respCode");

        if (jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")) {
            throw new Exception("" + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "");
        }
        if (jsonRespObject.has("respCode") && jsonRespObject.getString("respCode").equals("000")) {
            Globals.hasLinkedAccount=false;
            Globals.AccountUnLink = "Account Unlinked successfully";
        }

       // Globals.username = jsonRespObject.getString("fname");
        //   Log.d("new Self Onboarding", "" + Globals.username);
        //Globals.firstName = jsonRespObject.getString("firstName");
    }
    static public void LinkPropritorToindvBiz(String account, String branch, String dob) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userCode",Globals.user );
        jsonObject.put("account", account);
        jsonObject.put("branch", branch);
        jsonObject.put("dob", dob);
        jsonObject.put("otp", Globals.otp);
        //   13062022
        jsonObject.put("authenCode", Globals.authenCode);
        Log.e("LinkPropritorToindvBiz"," : "+jsonObject.toString() );
        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.linkAccountsBiz, jsonObject);
        Log.e("LinkPropritorToindvBiz"," : "+jsonObject.toString());
        Globals.errorOnBoard = jsonRespObject.getString("respCode");

        if (jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")) {
            throw new Exception("" + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "");
        }
        if (jsonRespObject.has("respCode") && jsonRespObject.getString("respCode").equals("000")) {
            Globals.AccountLink = "Account Linked successfully";
            Globals.hasLinkedAccount=true;

        }

       // Globals.username = jsonRespObject.getString("fname");
        //   Log.d("new Self Onboarding", "" + Globals.username);
        //Globals.firstName = jsonRespObject.getString("firstName");
    }
    static public void LinkPropritorToindv(String account, String branch, String bizcert, String dob) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userCode",Globals.user );
        jsonObject.put("account", account);
        jsonObject.put("branch", branch);
        jsonObject.put("bizcert", bizcert);
        jsonObject.put("dob", dob);
        jsonObject.put("otp", Globals.otp);
        //   13062022;
        jsonObject.put("authenCode", Globals.authenCode);
        Log.e("LinkPropritorToindv: ",""+jsonObject.toString() );
        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.linkAccounts, jsonObject);
        Log.e("LinkPropritorToindv:  ",jsonObject.toString());
        Globals.errorOnBoard = jsonRespObject.getString("respCode");

        if (jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")) {
            throw new Exception("" + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "");
        }
        if (jsonRespObject.has("respCode") && jsonRespObject.getString("respCode").equals("000")) {
            Globals.AccountLink = "Account Linked successfully";
            Globals.hasLinkedAccount = true;
        }

       // Globals.username = jsonRespObject.getString("fname");
        //   Log.d("new Self Onboarding", "" + Globals.username);
        //Globals.firstName = jsonRespObject.getString("firstName");
    }

    static public void updateAccount() throws Exception {
        JSONObject jsonObject = new JSONObject();
        Globals.UpdateAccountMessage ="";
        jsonObject.put("user",Globals.user );
        jsonObject.put("sessionId" , Globals.sessionId);
        jsonObject.put("password" , Globals.password);
        //   13062022
        jsonObject.put("authenCode", Globals.authenCode);
        Log.e("updateAccounts"," request : "+jsonObject.toString() );
        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.UpdateAccount, jsonObject);
        Log.e("UpdateAccounts"," response  : "+jsonObject.toString());

        if (jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")) {
            throw new Exception("" + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "");
        }
        Globals.UpdateAccountMessage ="successful" ;

        JSONArray arrayAccounts = jsonRespObject.getJSONArray("accountsList");
        for (int i = 0; i < arrayAccounts.length(); i++) {
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106
            Globals.accountsList.add(arrayAccounts.getString(i));
        }



        // Globals.username = jsonRespObject.getString("fname");
        //   Log.d("new Self Onboarding", "" + Globals.username);
        //Globals.firstName = jsonRespObject.getString("firstName");
    }

    static public void GetActivityHistory() throws Exception {

            Globals.historyEntryList.clear();
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("user", Globals.user);
            jsonObject.put("password", Globals.password);
            jsonObject.put("sessionId", Globals.sessionId);
            jsonObject.put("authenCode", Globals.authenCode);
            jsonObject.put("otp", Globals.pinEntered); // MAW20190918

            JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.UserActivityHistory, jsonObject);

            if (jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
                throw new Exception("Fetch History FAILED :" + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : ""));


            JSONArray arrayHistory = jsonRespObject.getJSONArray("ActionsHistoryList");
            for (int i = 0; i < arrayHistory.length(); i++) {

                String transfer_id= null ;
                if( arrayHistory.getJSONObject(i).has("transaction_id")){
                    transfer_id =arrayHistory.getJSONObject(i).getString("transaction_id");
                }
                HistoryEntry historyEntry = new HistoryEntry(
                        arrayHistory.getJSONObject(i).getString("operation_date"),
                        arrayHistory.getJSONObject(i).getString("service"),
                        arrayHistory.getJSONObject(i).getString("op_status"),
                        Double.parseDouble(arrayHistory.getJSONObject(i).getString("amount")),
                        arrayHistory.getJSONObject(i).getString("operation_type"),
                        transfer_id

                );
                Globals.historyEntryList.add(
                        historyEntry
                );

            }
        /*
        JSONObject jsonObj = jsonRespObject.getJSONObject("list");
        Log.e("Notifications ","99999999999999999999"+jsonRespObject);
        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception(jsonRespObject.getString("respCode"));
*/


        }


    static public List<String> GetBalance(String accountNumber) throws Exception{

        List<String> balData = new ArrayList<String>();

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        //Log.d(Account.class.getName(), "GetBalance Globals.authToken[" + Globals.authenCode + "]")
        //   13062022
        jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("accountNumber", accountNumber);
        Log.e("GetBalance json: ", jsonObject.toString());

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceAccountBalance, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")) {

            throw new Exception("GET BALANCE FAILED <RespCode=[" + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "]>");

        }
        //String balanceData = jsonRespObject.getString("balanceData");

        JSONArray array = jsonRespObject.getJSONArray("balanceData");
        if (!array.equals(null)) {
           // Log.d("balance", "array.length()=[" + array.length() + "]");
            for (int i = 0; i < array.length(); i++) {
                balData.add(array.get(i).toString());
            }
           // Log.d("balance", "" + balData);
        }

        return balData;
    }


    static public void GetTransactionList(String accountNumber,String dateStart,String dateEnd) throws Exception{

        /*List<T24TrxHistDetails> listTrx =new ArrayList<T24TrxHistDetails>();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("accountNumber", accountNumber);
        jsonObject.put("dateStart", dateStart);
        jsonObject.put("dateEnd", dateEnd);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceTransactionList, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("GET Mini Statement FAILED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");

        Globals.transactionList.clear();
        JSONArray arrayTransactionList = jsonRespObject.getJSONArray("transactionList");
        for(int i = 0 ; i < arrayTransactionList.length() ; i++){
            Globals.transactionList.add(new T24TrxHistDetails(
                    arrayTransactionList.getJSONObject(i).getString("bookingDate"),
                    arrayTransactionList.getJSONObject(i).getString("reference"),
                    arrayTransactionList.getJSONObject(i).getString("description"),
                    arrayTransactionList.getJSONObject(i).getString("description2"),
                    arrayTransactionList.getJSONObject(i).getString("description3"),
                    arrayTransactionList.getJSONObject(i).getString("valueDate"),
                    arrayTransactionList.getJSONObject(i).getString("debit"),
                    arrayTransactionList.getJSONObject(i).getString("credit"),
                    arrayTransactionList.getJSONObject(i).getString("closingBalance")
            ));
           // Log.d("getTRList",Globals.transactionList.toString());
        }*/

   /*     Globals.TrxList.clear();
        JSONArray arrayTransactionList = jsonRespObject.getJSONArray("transactionList");
        for(int i = 0 ; i < arrayTransactionList.length() ; i++) {
            Globals.TrxList.add(new EStatementEntry(
                    arrayTransactionList.getJSONObject(i).getString("account_title"),
                    arrayTransactionList.getJSONObject(i).getString("currency"),
                    arrayTransactionList.getJSONObject(i).getString("book_date"),
                    arrayTransactionList.getJSONObject(i).getString("reference"),
                    arrayTransactionList.getJSONObject(i).getString("trx_type"),
                    arrayTransactionList.getJSONObject(i).getString("narration"),
                    arrayTransactionList.getJSONObject(i).getString("value_date"),
                    arrayTransactionList.getJSONObject(i).getString("debit"),
                    arrayTransactionList.getJSONObject(i).getString("credit"),
                    arrayTransactionList.getJSONObject(i).getString("closing_balance")

            ));

        }
        Log.d("getTRList",Globals.TrxList.get(0).getAccount_title());          // to use refernce, value date, debit, credit, narration
*/
  //      Log.d("getTRList",Globals.transactionList.toString());

        /**
        T24TrxHistDetails trx=new T24TrxHistDetails();
        trx.setDescription("RETRAIT ATM");
        trx.setDebit("100.00");
        trx.setReference("123456798544");
        trx.setValueDate("10-11-2015");
        listTrx.add(trx);


        trx.setDescription("TRANSFER MONEY VOUCHER");
        trx.setCredit("200.00");
        trx.setReference("546216798510");
        trx.setValueDate("12-11-2015");
        listTrx.add(trx);
         Globals.transactionList.clear();
         Globals.transactionList=listTrx;
        **/

    }


    static public String AccountInfo(String accountNumber) throws Exception{
        Globals.sessionTKO ="";
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("accountNumber", accountNumber);
        //jsonObject.put("clientID", Globals.ClientId);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceAccountInfo, jsonObject);
        Globals.sessionTKO = jsonRespObject.getString("respCode");

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("GET ACCOUNT INFO FAILED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");

        String clientName = jsonRespObject.getString("clientName");


        return clientName;
    }
    static public String AccountInfoByPhone(String PhoneNumber) throws Exception{
        Globals.sessionTKO ="";
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("mobile", PhoneNumber);
        //jsonObject.put("clientID", Globals.ClientId);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceAccountInfoByPhone, jsonObject);
        Globals.sessionTKO = jsonRespObject.getString("respCode");

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("GET ACCOUNT INFO FAILED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");

        String clientName = jsonRespObject.getString("recipientName");


        return clientName;
    }

    public static void GetEStatement(String account, String dateFrom, String dateTo, String datefromDS, String datetoDS , String otp) throws Exception{
        Globals.transactionId=null;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("accountNumber", account);
        jsonObject.put("dateStart", dateFrom);
        jsonObject.put("dateEnd", dateTo);
        jsonObject.put("datefromDS", datefromDS);
        jsonObject.put("datetoDS", datetoDS);
        jsonObject.put("otp", otp);


        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceEStatement, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("GET E STATEMENT FAILED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");

        Globals.transactionId = jsonRespObject.getString("transactionId");

    }
  /** younes 06/05/2021  public static void GetOffStatement(String account, String dateFrom, String dateTo, String datefromDS, String datetoDS, String branch, String narration) throws Exception{
        Globals.transactionId=null;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId); jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("accountNumber", account);
        jsonObject.put("dateStart", dateFrom);
        jsonObject.put("dateEnd", dateTo);
        jsonObject.put("datefromDS", datefromDS);
        jsonObject.put("datetoDS", datetoDS);
        jsonObject.put("branch", branch);
        jsonObject.put("narration", narration);



        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceOffStatement, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("GET OFF STATEMENT FAILED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");

       // Globals.transactionId = jsonRespObject.getString("transactionId");

    }**/

    public static void GetSOList(String srcAcc, String destAcc) throws Exception {
        Globals.SOList = new ArrayList<StandingOrderEntry>();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("sourceAcc", srcAcc);
        jsonObject.put("destinationAcc", destAcc);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceStandingOrderList, jsonObject);

        if (jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("GET Mini Statement FAILED <RespCode=[" + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "]>");

        JSONArray array = jsonRespObject.getJSONArray("standingOrderList");
        if (!array.equals(null)) {
            //Log.d("list standing order", "array.length()=[" + array.length() + "]");
            Globals.SOList.clear();

            for (int i = 0; i < array.length(); i++) {
                Globals.SOList.add(
                        new StandingOrderEntry(
                                array.getJSONObject(i).getString("standingOrderId"),
                                array.getJSONObject(i).getString("debitAccount"),
                                array.getJSONObject(i).getString("creditAccount"),
                                array.getJSONObject(i).getDouble("amount"),
                                array.getJSONObject(i).getString("expiryDate"),
                                array.getJSONObject(i).getString("periodType").charAt(0),
                                array.getJSONObject(i).getString("dayOfWeek"),
                                array.getJSONObject(i).getInt("dayOfMonth"),
                                array.getJSONObject(i).getString("dateOfYear"),
                                array.getJSONObject(i).getString("operationDate")

                        ));
            }


            for (int i = 0; i < Globals.SOList.size(); i++) {
                Log.d("list standing order", "" + Globals.SOList.get(i).getDebitAccount()
                        + " " + Globals.SOList.get(i).getCreditAccount()
                        + " " + Globals.SOList.get(i).getDateOfYear()
                        + " " + Globals.SOList.get(i).getDayOfWeek()
                        + " " + Globals.SOList.get(i).getExpiryDate()
                        + " " + Globals.SOList.get(i).getOperationDate()
                        + " " + Globals.SOList.get(i).getAmount()
                        + " " + Globals.SOList.get(i).getPeriodType()
                        + " " + Globals.SOList.get(i).getDayOfMonth()
                        + " " + Globals.SOList.get(i).getStandingOrderId());
            }

        }
    }


    public static void GetOffStatement(String account, String dateFrom, String dateTo, String datefromDS, String datetoDS, String branch, String narration) throws Exception{
        Globals.transactionId=null;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("accountNumber", account);
        jsonObject.put("dateStart", dateFrom);
        jsonObject.put("dateEnd", dateTo);
        jsonObject.put("datefromDS", datefromDS);
        jsonObject.put("datetoDS", datetoDS);
        jsonObject.put("branch", branch);
        jsonObject.put("narration", narration);



        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceOffStatement, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("GET OFF STATEMENT FAILED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");

        Globals.OffAmountPay = jsonRespObject.getString("amountPay");
        Globals.OffPages =  jsonRespObject.getString("pages");

//        Globals.transactionId = jsonRespObject.getString("transactionId");

    }

    public static void GetOffStatementCharges(String account, String branch, String narration , String pickUpby, String thirdPartyName) throws Exception{
        Globals.transactionId=null;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("accountNumber", account);
        jsonObject.put("amountPay", Globals.OffAmountPay);
        jsonObject.put("branch", branch);
        jsonObject.put("narration", narration);
        jsonObject.put("thirdPartyName", thirdPartyName);
        jsonObject.put("pickUpby", pickUpby);


        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceOffStatementCharges, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("GET OFF STATEMENT FAILED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");

        Globals.OffAmountPay = null;//jsonRespObject.getString("amountPay");
        // Globals.transactionId = jsonRespObject.getString("transactionId");

    }




        //-------------------------- start   30/09/2022 ---------------------------------------
        public static void GetVisaStatement(String account, String dateFrom, String dateTo, String datefromDS, String datetoDS, String branch, String narration) throws Exception{
            Globals.transactionId=null;

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("user", Globals.user);
            jsonObject.put("password", Globals.password);
            jsonObject.put("sessionId", Globals.sessionId);
            jsonObject.put("authenCode", Globals.authenCode);
            jsonObject.put("accountNumber", account);
            jsonObject.put("dateStart", dateFrom);
            jsonObject.put("dateEnd", dateTo);
            jsonObject.put("datefromDS", datefromDS);
            jsonObject.put("datetoDS", datetoDS);
            jsonObject.put("branch", branch);
            jsonObject.put("narration", narration);

            JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceVisaStatement, jsonObject);


            if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
                throw new Exception("GET VISA STATEMENT FAILED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");

            Globals.OffAmountPay = jsonRespObject.getString("amountPay");
            Globals.OffPages =  jsonRespObject.getString("pages");

        //        Globals.transactionId = jsonRespObject.getString("transactionId");

        }


        public static void GetVisaStatementCharges(String account, String branch, String narration, String pickUpby, String thirdPartyName ) throws Exception{
                Globals.transactionId=null;

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("user", Globals.user);
                jsonObject.put("password", Globals.password);
                jsonObject.put("sessionId", Globals.sessionId);
                jsonObject.put("authenCode", Globals.authenCode);
                jsonObject.put("accountNumber", account);
                jsonObject.put("amountPay", Globals.OffAmountPay);
                jsonObject.put("branch", branch);
                jsonObject.put("narration", narration);
                jsonObject.put("thirdPartyName", thirdPartyName);
                jsonObject.put("pickUpby", pickUpby);

                JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceVisaStatementCharges, jsonObject);

                if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
                    throw new Exception("GET VISA STATEMENT FAILED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");

                Globals.OffAmountPay = null;//jsonRespObject.getString("amountPay");
                // Globals.transactionId = jsonRespObject.getString("transactionId");

            }

            //-------------------------- end   30/09/2022 ---------------------------------------

}
