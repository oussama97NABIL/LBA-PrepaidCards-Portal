package com.LBA.tools.services;

import android.util.Log;

import com.LBA.tools.assets.Globals;
import com.LBA.tools.connection.HTTPClient;
import com.LBA.tools.misc.ExpenseLimitEntry;
import com.LBA.tools.misc.ExpenseTrackingBean;
import com.LBA.tools.misc.ExpenseTrackingDetailsBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;



public class Expense {

    /*      LimitId = jsonObject.getString("LimitId");
			logger.info("LimitId=["+LimitId+"]");

			String ipAddress = GlobalVars.getIpAddress(request);
			logger.info("ipAddress=["+ipAddress+"]");

			*/

    public static void DeleteTransactionLimit(String LimitId) throws Exception{
        Globals.isSuccessful = false;
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("LimitId",LimitId);
        jsonObject.put("otp", Globals.pinEntered); // MAW20190918
        //   13062022
        jsonObject.put("authenCode", Globals.authenCode);
        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceDeleteExpenseLimit, jsonObject);
        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")) {
            throw new Exception(jsonRespObject.getString("respCode"));
        }else if(jsonRespObject.getString("respCode").equals("000")){
            Globals.isSuccessful = true;
        }


    }


    public static void UpdateTransactionLimit(String accountNumber,String LimitId,Double dailyLimit) throws Exception{
        Globals.isSuccessful = false;
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("accountNumber",accountNumber);
        jsonObject.put("LimitId",LimitId);
        jsonObject.put("dailyLimit", dailyLimit);
        jsonObject.put("otp", Globals.pinEntered); // MAW20190918

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceUpdateExpenseLimit, jsonObject);
        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")) {
            throw new Exception(jsonRespObject.getString("respCode"));
        }else if(jsonRespObject.getString("respCode").equals("000")){
            Globals.isSuccessful = true;
        }


    }
    static public void getAllAccountsLimits(String operation ,String service)throws Exception{
        ArrayList<ExpenseLimitEntry> AllLimits = new ArrayList<>();

        Globals.AllAccountsLimits.clear();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", Globals.user);
        jsonObject.put("service", service);
        jsonObject.put("operation", operation);
        jsonObject.put("password", Globals.password);
        //   13062022
        jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("sessionId", Globals.sessionId);
        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceExpenseLimitDetails, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("FETCHING ACCOUNTS LIMIT FAILED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");
/*
*  while( keys.hasNext() ){
                String key = (String)keys.next();
                List<ExpenseTrackingBean> expenseTrackingListValue = new ArrayList<ExpenseTrackingBean>();
                JSONArray expenseTrackingList = jsonObj.getJSONArray(key);
                Log.e("expenseTrackingList", ": "+ expenseTrackingList.toString());
                // Log.d("list ::::: ", ""+expenseTrackingList.toString());
                for(int i = 0 ; i < expenseTrackingList.length() ; i++) {
*/
        if(jsonRespObject.has("accountsLimits")){
            JSONArray expenseLimitsList = jsonRespObject.getJSONArray("accountsLimits");
            Log.e("getAllAccountsLimits: ", ""+expenseLimitsList);
            for(int i = 0 ; i < expenseLimitsList.length() ; i++) {
                ExpenseLimitEntry value = new ExpenseLimitEntry(expenseLimitsList.getJSONObject(i).getString("OPERATION_TYPE"),expenseLimitsList.getJSONObject(i).getString("LIMITID"),expenseLimitsList.getJSONObject(i).getString("ACCOUNT_NUMBER"),expenseLimitsList.getJSONObject(i).getString("LIMIT"),expenseLimitsList.getJSONObject(i).getString("limit_type"));
                AllLimits.add(value);
            }
            Globals.AllAccountsLimits.addAll(AllLimits);
        }

        Log.e("details: "," AllAccountsLimits --> "+  Globals.AllAccountsLimits);
    }


    static public List<ExpenseTrackingBean> getAllAccountsDetails(HashMap<String, List<ExpenseTrackingBean>> map){
        ArrayList<ExpenseTrackingBean> alldata = new ArrayList<ExpenseTrackingBean>();
        List<List<ExpenseTrackingBean>> list = new ArrayList<List<ExpenseTrackingBean>>(map.values());
        for (List<ExpenseTrackingBean> listElemt:list) {
            alldata.addAll(listElemt);
        }
        return alldata;

    }

    static public void GetExpenseTrackingList() throws Exception{
        HashMap<String, List<ExpenseTrackingBean>> expenseMap = new HashMap<String, List<ExpenseTrackingBean>>();
        DateFormat dateFormat = new SimpleDateFormat("MM");
        DateFormat dateFormat2 = new SimpleDateFormat("YYYY");
        Date date = new Date();
        int current_month = Integer.parseInt(dateFormat.format(date));
        int current_year = Integer.parseInt(dateFormat2.format(date));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        //   13062022
        jsonObject.put("authenCode", Globals.authenCode);
        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceExpenseTrackingList, jsonObject);
        Log.e("*** jsonRespObject ***",""+ jsonRespObject);

        Globals.expenseTrackingmap.clear();
        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("Expense Tracking Statement FAILED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");

        if(jsonRespObject.has("list")){
            JSONObject jsonObj = jsonRespObject.getJSONObject("list");

            Iterator<?> keys = jsonObj.keys();
            while( keys.hasNext() ){
                String key = (String)keys.next();
                List<ExpenseTrackingBean> expenseTrackingListValue = new ArrayList<ExpenseTrackingBean>();
                JSONArray expenseTrackingList = jsonObj.getJSONArray(key);
                // Log.e("expenseTrackingList", ": "+ expenseTrackingList.toString());
                // Log.d("list ::::: ", ""+expenseTrackingList.toString());
                for(int i = 0 ; i < expenseTrackingList.length() ; i++) {
                    // Log.e("object", ": "+ expenseTrackingList.getJSONObject(i).toString());

                    // Log.e("yaer ***********", "GetExpenseTrackingList: "+expenseTrackingList.getJSONObject(i).getString("year") );
                    int returned_monh = Integer.parseInt(expenseTrackingList.getJSONObject(i).getString("month"));
                    int returned_yaer= Integer.parseInt(expenseTrackingList.getJSONObject(i).getString("year"));
                    /*
                    Log.e("returned_monh", ": "+ returned_monh);
                    Log.e("returned_yaer", ": "+ returned_yaer);
                    Log.e("current_month", ": "+ current_month);
                    Log.e("current_year", ": "+ current_year);

                     */

                    if((current_month >= returned_monh && (current_year == returned_yaer)) ||(current_month < returned_monh && (returned_yaer == current_year-1))){
                        ExpenseTrackingBean value = new ExpenseTrackingBean(
                                expenseTrackingList.getJSONObject(i).getString("account_debit"),
                                Double.parseDouble(expenseTrackingList.getJSONObject(i).getString("amount")),
                                Integer.parseInt(expenseTrackingList.getJSONObject(i).getString("month")),
                                expenseTrackingList.getJSONObject(i).getString("transaction_purpose"),
                                expenseTrackingList.getJSONObject(i).getString("transaction_type"));
                        expenseTrackingListValue.add(value);
                    }
                }
                expenseMap.put(key,expenseTrackingListValue );


            }

            Globals.expenseTrackingmap.put("All Accounts",getAllAccountsDetails(expenseMap));
            Globals.expenseTrackingmap.putAll(expenseMap);
            Log.e("expenseMap", Globals.expenseTrackingmap.toString());

        }
    }


    public static void GetExpenseTrackingDetailed(String sourceAcc, String purpose ,int month,String transaction_type) throws Exception {
        HashMap<String, ArrayList<ExpenseTrackingDetailsBean>> expenseMap = new HashMap<String, ArrayList<ExpenseTrackingDetailsBean>>();

        if(sourceAcc.equals("All Accounts"))
            sourceAcc ="0";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("sourceAcc", sourceAcc);
        jsonObject.put("purpose", purpose);
        jsonObject.put("transaction_type", transaction_type);
        jsonObject.put("month", month);
        //   13062022
        jsonObject.put("authenCode", Globals.authenCode);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceExpenseTrackingDetailed, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("Expense Details <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");

        Log.e("WebService", "jsonRespObject: "+ jsonRespObject);

        JSONObject jsonObj = jsonRespObject.getJSONObject("list");
        Log.e("WebService", "jsonObj: "+ jsonObj);
     /* JSONArray array = jsonObj.getJSONArray(purpose);
        Log.e("WebService", "GetExpenseTrackingDetailed: "+ array);
        */
        Iterator<?> keys = jsonObj.keys();
        while( keys.hasNext() ){
            String key = (String)keys.next();
            ArrayList<ExpenseTrackingDetailsBean> data = new ArrayList<>();
            JSONArray expenseTrackingList = jsonObj.getJSONArray(key);
            Log.e("expenseTrackingList", ": "+ expenseTrackingList.toString());
            // Log.d("list ::::: ", ""+expenseTrackingList.toString());
            for(int i = 0 ; i < expenseTrackingList.length() ; i++) {
                ExpenseTrackingDetailsBean value = new ExpenseTrackingDetailsBean(
                        expenseTrackingList.getJSONObject(i).getString("account_credit"),
                        expenseTrackingList.getJSONObject(i).getString("account_debit"),
                        Double.parseDouble(expenseTrackingList.getJSONObject(i).getString("amount")),
                        expenseTrackingList.getJSONObject(i).getString("operation_date"),
                        expenseTrackingList.getJSONObject(i).getString("transaction_type"),
                        expenseTrackingList.getJSONObject(i).getString("operation_details"));
                // Log.e("getTRList", Globals.expenseTrackingList.toString());
                data.add(value);
            }
            expenseMap.put(key,data );

        }
        Log.e("WebService", "GetExpenseTrackingDetailed: "+ expenseMap);


        Globals.expenseTrackingDetailsMap.putAll(expenseMap);
    }

    public static void SetExpenseLimit(String accountNumber,String service,Double dailyLimit,String transactionType,String limitType) throws Exception{
        Globals.isSuccessful = false;
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("accountNumber",accountNumber);
        jsonObject.put("service",service);
        jsonObject.put("dailyLimit", dailyLimit);
        jsonObject.put("transactionType", transactionType);
        jsonObject.put("limitType", limitType);
        jsonObject.put("otp", Globals.pinEntered); // MAW20190918

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceExpenseLimit, jsonObject);
        Log.e("SetExpenseLimit ","99999999999999999999"+jsonRespObject);
        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")) {
            throw new Exception(jsonRespObject.getString("respCode"));
        }else if(jsonRespObject.getString("respCode").equals("000")){
            Globals.isSuccessful = true;
        }


    }

    public static void GetLimitList() throws Exception {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("otp", Globals.pinEntered); // MAW20190918

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceExpenseLimitList, jsonObject);

        if (jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("Fetch limits FAILED :" + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : ""));

        //**************** limitedOperations List ********************
        Globals.limitedOperationsAccounts.clear();
        Globals.limitedOperations.clear();

        HashMap<String, ArrayList<String>> limitsMAp = new HashMap<>();

        if(jsonRespObject.has("limitedOperations")){
            JSONObject jsonObj = jsonRespObject.getJSONObject("limitedOperations");
            Iterator<?> keys = jsonObj.keys();
            while( keys.hasNext() ){
                String key = (String)keys.next();
                ArrayList<String> LimitedAccounts = new ArrayList<String>();
                JSONArray limitedOperationsList = jsonObj.getJSONArray(key);
                for(int i = 0 ; i < limitedOperationsList.length() ; i++) {
                    LimitedAccounts.add(limitedOperationsList.getString(i));
                }
                limitsMAp.put(key,LimitedAccounts );
            }
            Globals.limitedOperationsAccounts.putAll(limitsMAp);
            Log.e(""," limitedOperationsAccounts --> "+ Globals.limitedOperationsAccounts);
        }
        Globals.limitedOperations.addAll(Globals.limitedOperationsAccounts.keySet());


        Log.e("limits: ", "After fetching data "+ Globals.limitedOperations);



    }


}
