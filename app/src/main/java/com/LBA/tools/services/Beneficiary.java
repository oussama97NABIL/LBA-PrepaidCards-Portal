package com.LBA.tools.services;

import com.LBA.tools.assets.Globals;
import com.LBA.tools.connection.HTTPClient;
import com.LBA.tools.misc.BeneficiaryEntry;

import org.json.JSONArray;
import org.json.JSONObject;



public class Beneficiary {


    public static boolean AddBeneficiary (String benefAccount, String benefName, String bankBenef, String operationBenef, String achBank, String mobile
    , String amount, String narration, String purpose, String user_data) throws Exception{
        Globals.transactionId=null;
        JSONObject jsonObject = new JSONObject();
        boolean result = false;


        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("benefName", benefName);
        jsonObject.put("benefAccount",benefAccount);
        jsonObject.put("bankBenef",bankBenef);
        jsonObject.put("operationBenef",operationBenef);
        jsonObject.put("achBank",achBank);
        jsonObject.put("mobile",mobile);

        jsonObject.put("amount",amount);
        jsonObject.put("narration", narration);
        jsonObject.put("purpose", purpose);
        jsonObject.put("user_data", user_data);

       // jsonObject.put("clientID", Globals.ClientId);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.AddBeneficiary, jsonObject);
        Globals.errBenef = jsonRespObject.getString("respCode");

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("Beneficiary Added Successfully")) {
            result = false;
            throw new Exception("Beneficiary service <RespCode=[" + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "Beneficiary Rejected") + "]>");
        }



        JSONArray array = jsonRespObject.getJSONArray("beneflist");
        if (!array.equals(null)) {
            Globals.BeneficyList.clear();
            //Log.d("list benef", "array.length()=[" + array.length() + "]");
            for (int i = 0; i < array.length(); i++) {
                Globals.BeneficyList.add(
                        new BeneficiaryEntry(
                                array.getJSONObject(i).getString("userCode"),
                                array.getJSONObject(i).getString("beneficiary_name"),
                                array.getJSONObject(i).getString("beneficiary_account"),
                                array.getJSONObject(i).getString("beneficiary_inst_code"),
                                array.getJSONObject(i).getString("beneficiary_operation"),
                                array.getJSONObject(i).getString("beneficiary_ach_code"),
                                array.getJSONObject(i).getString("beneficiary_mobile"),
                                array.getJSONObject(i).getString("narration"),
                                array.getJSONObject(i).getString("amount"),
                                array.getJSONObject(i).getString("purpose"),
                                array.getJSONObject(i).getString("user_data")
                        ));
               // Log.d("GetForexList", "i=[" + i + "]" + " " + array.getJSONObject(i).toString());

            }
            result = true;
        }
        return result;
    }

    static public void GetBenificiaryList() throws Exception{

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", Globals.authenCode);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.ListBeneficiary, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("SERVICE NOT AVAILABLE");

        JSONArray array = jsonRespObject.getJSONArray("list");
        if (!array.equals(null)) {
            //Log.d("list benef", "array.length()=[" + array.length() + "]");
            Globals.BeneficyList.clear();

            for (int i = 0; i < array.length(); i++) {
                Globals.BeneficyList.add(
                        new BeneficiaryEntry(
                                array.getJSONObject(i).getString("userCode"),
                                array.getJSONObject(i).getString("beneficiary_name"),
                                array.getJSONObject(i).getString("beneficiary_account"),
                                array.getJSONObject(i).getString("beneficiary_inst_code"),
                                array.getJSONObject(i).getString("beneficiary_operation"),
                                array.getJSONObject(i).getString("beneficiary_ach_code"),
                                array.getJSONObject(i).getString("beneficiary_mobile"),
                                array.getJSONObject(i).getString("narration"),
                                array.getJSONObject(i).getString("amount"),
                                array.getJSONObject(i).getString("purpose"),
                                array.getJSONObject(i).getString("user_data")
                        ));
            }
        }else
            Globals.BeneficyList.add(new BeneficiaryEntry());
    }




    static public void UpdtBeneficiary(String BenefAcc , String BenefName, String BenefMob, String BenefAmount, String BenefPr ) throws Exception {

        Globals.transactionId = null;
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", Globals.authenCode);

        jsonObject.put("BenefAcc", BenefAcc);
        jsonObject.put("BeneName", BenefName);
        jsonObject.put("BenefMob", BenefMob);
        jsonObject.put("BenefAmount", BenefAmount);
        jsonObject.put("BenefPr", BenefPr);

        jsonObject.put("clientID", Globals.ClientId);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.UpdateBeneficiary, jsonObject);
        Globals.errBenef = jsonRespObject.getString("respCode");

        if (jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("Beneficiary service <RespCode=[" + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "]>");

            //     Globals.transactionId = jsonRespObject.getString(Globals.transactionIdTag);

        else {
            JSONArray array = jsonRespObject.getJSONArray("Beneflist");
          //  Log.d("list benef", "array.length()=[" + array.length() + "]");

            if (!array.equals(null)) {
             //   Log.d("list benef", "array.length()=[" + array.length() + "]");
                Globals.BeneficyList.clear();

                for (int i = 0; i < array.length(); i++) {
                    Globals.BeneficyList.add(
                            new BeneficiaryEntry(
                                    array.getJSONObject(i).getString("userCode"),
                                    array.getJSONObject(i).getString("beneficiary_name"),
                                    array.getJSONObject(i).getString("beneficiary_account"),
                                    array.getJSONObject(i).getString("beneficiary_inst_code"),
                                    array.getJSONObject(i).getString("beneficiary_operation"),
                                    array.getJSONObject(i).getString("beneficiary_ach_code"),
                                    array.getJSONObject(i).getString("beneficiary_mobile"),
                                    array.getJSONObject(i).getString("narration"),
                                    array.getJSONObject(i).getString("amount"),
                                    array.getJSONObject(i).getString("purpose"),
                                    array.getJSONObject(i).getString("user_data")
                            ));
                }
            } else
                Globals.BeneficyList.add(new BeneficiaryEntry());
        }
    }


    public static void DeleteBeneficiary(String accountBenef, String nameBenef, String mobile) throws Exception {

        Globals.transactionId = null;
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", Globals.authenCode);

        jsonObject.put("accBenef", accountBenef);
        jsonObject.put("nameBenef", nameBenef);
        jsonObject.put("mobileBenef", mobile);

        jsonObject.put("clientID", Globals.ClientId);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.DeleteBeneficiary, jsonObject);
        Globals.errBenef = jsonRespObject.getString("respCode");

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")) {

            throw new Exception("OPERATION REJECTED <RespCode=[" + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "]>");

        }

        JSONArray array = jsonRespObject.getJSONArray("Beneflist");

            // Globals.BeneficyList.clear();

            if (array != null && array.length() > 0) {
             //   Log.d("list benef", "array.length()=[" + array.length() + "]");
                Globals.BeneficyList.clear();

                for (int i = 0; i < array.length(); i++) {
                    Globals.BeneficyList.add(
                            new BeneficiaryEntry(
                                    array.getJSONObject(i).getString("userCode"),
                                    array.getJSONObject(i).getString("beneficiary_name"),
                                    array.getJSONObject(i).getString("beneficiary_account"),
                                    array.getJSONObject(i).getString("beneficiary_inst_code"),
                                    array.getJSONObject(i).getString("beneficiary_operation"),
                                    array.getJSONObject(i).getString("beneficiary_ach_code"),
                                    array.getJSONObject(i).getString("beneficiary_mobile"),
                                    array.getJSONObject(i).getString("narration"),
                                    array.getJSONObject(i).getString("amount"),
                                    array.getJSONObject(i).getString("purpose"),
                                    array.getJSONObject(i).getString("user_data")
                            ));
                }
            } else {
                Globals.BeneficyList.clear();
                //Globals.BeneficyList.add(new BeneficiaryEntry());
            }
        }




}
