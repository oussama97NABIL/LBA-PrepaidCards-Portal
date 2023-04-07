package com.LBA.tools.services;

import android.util.Log;

import com.LBA.tools.assets.Globals;
import com.LBA.tools.connection.HTTPClient;
import com.LBA.tools.misc.LastTransactionDetail;

import org.json.JSONArray;
import org.json.JSONObject;

public class Transactions {
    static public void GetLast10Transactions() throws Exception{


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", "4281993108775830" );
        JSONObject jsonRespObject = HTTPClient.sendPostJSONLastTransactions(Globals.service10LastTransactions, jsonObject);
        Log.e("TAG", "getLastTransactoins: "+jsonRespObject);

        if(jsonRespObject.has("responseCode") && !jsonRespObject.getString("responseCode").equals("00"))
            // throw new Exception("Pin request already done");
            throw new Exception("PIN REQUEST FAILED <RespCode=["+(jsonRespObject.has("responseCode")?jsonRespObject.getString("responseCode"):"")+"]>");
        // Globals.transactionId=jsonRespObject.getString(Globals.transactionIdTag);
        Globals.transactionList.clear();
        JSONArray arrayTransactionList = jsonRespObject.getJSONArray("transactions");
        for(int i = 0 ; i < arrayTransactionList.length() ; i++){
            Globals.transactionList.add(new LastTransactionDetail(
                    arrayTransactionList.getJSONObject(i).getString("transactionType"),
                    arrayTransactionList.getJSONObject(i).getString("date"),
                    arrayTransactionList.getJSONObject(i).getString("amount"),
                    arrayTransactionList.getJSONObject(i).getString("currency"),
                    arrayTransactionList.getJSONObject(i).getString("referenceNumber"),
                    arrayTransactionList.getJSONObject(i).getString("location")
            ));
        }
    }
}
