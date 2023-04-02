package com.LBA.tools.services;

import android.util.Log;

import com.LBA.tools.assets.Globals;
import com.LBA.tools.connection.HTTPClient;
import com.LBA.tools.misc.BranchGeoEntry;
import com.LBA.tools.misc.ForexEntry;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by amine.wahbi on 30/10/2015.
 */
public class General {

    static public void GetForexList() throws Exception{

        List<ForexEntry> listTrx=new ArrayList<ForexEntry>();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", Globals.authenCode);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceForexList, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("SERVICE NOT AVAILABLE");

        JSONArray array = jsonRespObject.getJSONArray("forexList");

        Globals.forexList.clear();

        Log.d("GetForexList","array.length()=["+array.length()+"]");
        for (int i = 0; i < array.length(); i++) {
            Globals.forexList.add(
                    new ForexEntry(
                            array.getJSONObject(i).getString("currencyFrom"),
                            array.getJSONObject(i).getString("currencyTo"),
                            array.getJSONObject(i).getDouble("buyingRate"),
                            array.getJSONObject(i).getDouble("sellingRate"))
            );
            Log.d("GetForexList", "i=[" +i+ "]"+" "+array.getJSONObject(i).toString());

        }



    }

   /* public static void checkOTP(String otp) throws Exception {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId); jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("otp", otp);


        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceCheckOTP, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("CHECK OTP FAILED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");
    }
*/
   public static void CheckPIN(String pin) throws Exception{
       JSONObject jsonObject = new JSONObject();
       jsonObject.put("user", Globals.username);
       jsonObject.put("password", "");
       jsonObject.put("otp", pin);
       jsonObject.put("sessionId", "");
       //   13062022
       jsonObject.put("authenCode", Globals.authenCode);
       JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceCheckPIN, jsonObject);

       Globals.ERpin = jsonRespObject.getString("respCode");
    //   Log.d("",Globals.ERpin);
       if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")) {

           throw new Exception("" + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "");
       }

   }






   public static void checkOTP(String pin) throws Exception {
       JSONObject jsonObject = new JSONObject();

       jsonObject.put("user", Globals.user);
       jsonObject.put("password", Globals.password);
       jsonObject.put("sessionId", Globals.sessionId);
       jsonObject.put("authenCode", Globals.authenCode);
       jsonObject.put("otp", pin);

       if(Globals.useOTPSelfSignUp==true){
           jsonObject.put("phone",Globals.phone);
       }
       JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceCheckOTP, jsonObject);

     /*  if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
         throw new Exception("CHECK OTP FAILED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");*/

     Globals.ERpin = jsonRespObject.getString("respCode");
      //  Log.d("",Globals.ERpin);
       if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")) {

           throw new Exception("" + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "");
       }
       //Globals.otp = otp; // MAW20190918::
   }
    public static void sendOTP() throws Exception {
        JSONObject jsonObject = new JSONObject();
        Log.d("GetForexList", "i=+++");
        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", Globals.authenCode);
        //Log.d("GetForexList", jsonObject.toString());

        if(Globals.useOTPSelfSignUp==true){
            //jsonObject.put("fname",Globals.fname);
            //jsonObject.put("lname",Globals.lname);
            jsonObject.put("phone",Globals.phone);
            Log.d("GetForexList", "K=+++");
        }
        Log.d("GetForexList", "h=+++");
        Log.d("GetForexList", jsonObject.toString());
        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceGenerateOTP, jsonObject);

        Log.d("GetForexList", "f=+++");
        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("GEN OTP FAILED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");
    }

    static public void GetBranchesLocations() throws Exception{

        JSONObject jsonObject = new JSONObject();

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceGetBranchesLocations,jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("Branch Location service <RespCode=[" + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "]>");




        JSONArray array = jsonRespObject.getJSONArray("BranchLocList");
        if (!array.equals(null)) {
            Globals.ListBranchGeoEntry.clear();
            Log.d("list Branches locations", "array.length()=[" + array.length() + "]");
            for (int i = 0; i < array.length(); i++) {
                Globals.ListBranchGeoEntry.add(
                        new BranchGeoEntry(
                                array.getJSONObject(i).getString("branchCode"),
                                array.getJSONObject(i).getString("branchName"),
                                array.getJSONObject(i).getString("mnemonic"),
                                array.getJSONObject(i).getString("address"),
                                array.getJSONObject(i).getString("city"),
                                array.getJSONObject(i).getString("region"),
                                array.getJSONObject(i).getString("GPS"),
                                array.getJSONObject(i).getString("latitude").trim(),
                                array.getJSONObject(i).getString("longitude").trim(),
                                array.getJSONObject(i).getString("saturdayOpen")
                        ));
              //  Log.d("GetForexList", "i=[" + i + "]" + " " + array.getJSONObject(i).getString("latitude")+ " +++++ " + array.getJSONObject(i).getString("longitude"));

            }
        }


    }

    static public void GetListBranches()throws Exception {
        JSONObject jsonObject = new JSONObject();
        Log.d("start of get", "+++++++++++++++++++++++++++++++++++++");

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceGetBranches, jsonObject);

    //    JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceSignUp,jsonObject);

        Log.d("before if", "+++++++++++++++++++++++++++++++++++++");

        //if (jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
          //  throw new Exception("Branch list service <RespCode=[" + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "]>");



            //Globals.branches =  new ArrayList<BranchEntry>();
            //JSONArray array = jsonRespObject.getJSONArray("BranchList");
       /* JSONArray arrayBranches = jsonRespObject.getJSONArray("BranchList");
        Globals.branches.add(new BranchEntry("Select","Select","Select","Select","Select","Select","Select"));
        Log.d("GetForexList", ""+arrayBranches.toString());
        for (int i = 0; i < arrayBranches.length(); i++) {
            Globals.branches.add(new BranchEntry(
                    arrayBranches.getJSONObject(i).getString("branchCode"),
                    arrayBranches.getJSONObject(i).getString("bankCode"),
                    arrayBranches.getJSONObject(i).getString("branchName"),
                    arrayBranches.getJSONObject(i).getString("achBankCode"),
                    arrayBranches.getJSONObject(i).getString("gipBankCode"),
                    arrayBranches.getJSONObject(i).getString("achCode"),
                    arrayBranches.getJSONObject(i).getString("gipCode")
            ));
        }*/
            //Globals.branches = new ArrayList<BranchEntry>();
         /*   Globals.branches = new ArrayList<BranchEntry>();
            JSONArray arrayBranches = jsonRespObject.getJSONArray("BranchList");
            for (int i = 0; i < arrayBranches.length(); i++) {
                //if(i==0) Globals.branchesList.add(new BranchEntry(Globals.Select,Globals.Select,Globals.Select,Globals.Select,Globals.Select,Globals.Select,Globals.Select));

                Globals.branches.add(new BranchEntry(
                        arrayBranches.getJSONObject(i).getString("branchCode"),
                        arrayBranches.getJSONObject(i).getString("bankCode"),
                        arrayBranches.getJSONObject(i).getString("branchName"),
                        arrayBranches.getJSONObject(i).getString("achBankCode"),
                        arrayBranches.getJSONObject(i).getString("gipBankCode"),
                        arrayBranches.getJSONObject(i).getString("achCode"),
                        arrayBranches.getJSONObject(i).getString("gipCode")
                ));
            }
            if(Globals.branchesList!=null) {
                Collections.sort(Globals.branchesList, new Comparator<BranchEntry>() {
                    public int compare(BranchEntry c1, BranchEntry c2) {
                        return c1.getBranchName().compareTo(c2.getBranchName());
                    }
                });
                Globals.branchesList.add(0, new BranchEntry(Globals.Select,Globals.Select,Globals.Select,Globals.Select,Globals.Select,Globals.Select,Globals.Select));
            }

        }*/
            Globals.branchOn = new ArrayList<String>();
            Globals.branchOn.add(Globals.Select);
            JSONArray arrayBranches = jsonRespObject.getJSONArray("BranchList");
        //Log.d("LENGHT", "+++++++++++++++++++++++++++++++++++++"+arrayBranches.length());
            for (int i = 0; i < arrayBranches.length(); i++) {
                Globals.branchOn.add(arrayBranches.get(i).toString());
                Log.d("IN LOOP", "+++++++++++++++++++++++++++++++++++++");
            }
            Log.d("after else", "+++++++++++++++++++++++++++++++++++++");

        }
    }
//}
