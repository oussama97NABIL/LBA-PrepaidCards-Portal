package com.LBA.tools.services;


// younes

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.LBA.tools.assets.Globals;
import com.LBA.tools.connection.HTTPClient;
import com.LBA.tools.misc.AccountHabEntry;
import com.LBA.tools.misc.AdsEntry;
import com.LBA.tools.misc.BankEntry;
import com.LBA.tools.misc.BeneficiaryEntry;
import com.LBA.tools.misc.BranchEntry;
import com.LBA.tools.misc.MomoProviderEntry;
import com.LBA.tools.misc.NotificationEntry;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



/**
 * Created by amine.wahbi on 17/8/2015.
 */
public class User {

    static public void login(String userCode, String password, String authenCode, int flag) throws Exception {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("userName", userCode);
        jsonObject.put("password", password);
        jsonObject.put("authenCode", authenCode == null ? "" : authenCode);
        jsonObject.put("flag", flag);
        Log.e("login: ", ""+jsonObject);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceSignIn, jsonObject);
        //hajer 24/06/2022


        Log.e("TAG", "Login: "+jsonRespObject);

        if(jsonRespObject.has("responseCode") && !jsonRespObject.getString("responseCode").equals("00"))
            // throw new Exception("Pin request already done");
            throw new Exception("PIN REQUEST FAILED <RespCode=["+(jsonRespObject.has("responseCode")?jsonRespObject.getString("responseCode"):"")+"]>");


        Globals.notificationViewed = false;

        Globals.ERmsg = jsonRespObject.getString("responseCode");
        //Log.d("", "" + Globals.ERmsg);

        Globals.userWelcome = jsonRespObject.getString("clientName");

        Globals.authenToken = jsonRespObject.getString("token");
        Log.e("TAG", "login: Globals.authenToken" + Globals.authenToken);
       /* Globals.sessionId = jsonRespObject.getString("sessionId");
        Log.e("login: sessionId =>  ",Globals.sessionId );
        Globals.fname = jsonRespObject.getString("fname"); // younes
        Globals.ClientId = jsonRespObject.getString("clientID");
        Globals.statusBiom = jsonRespObject.getString("statusBiom"); // hajer bio
        Globals.userType = jsonRespObject.getString("userType");

        Globals.accountsList = new ArrayList<String>();
        Globals.accountsList.add(Globals.Select); // MAW20180106
        JSONArray arrayAccounts = jsonRespObject.getJSONArray("accountsList");
        for (int i = 0; i < arrayAccounts.length(); i++) {
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106
            Globals.accountsList.add(arrayAccounts.getString(i));
        }
*/

        //*********************** Must used operations **************************

      /*  JSONArray operationsList = jsonRespObject.getJSONArray("MostUsedServices");
        Globals.MustUsedOperations.clear();
        List<String> mustUserdOperations = new ArrayList<>();

        for (int i = 0; i < operationsList.length(); i++) {
            if (operationsList.getJSONObject(i).getString("service").equals("Proxy")) {
                mustUserdOperations.add(operationsList.getJSONObject(i).getString("service"));
            } else {
                mustUserdOperations.add(operationsList.getJSONObject(i).getString("operation_type"));

            }
        }

        int index2 = 0 ;
        while (mustUserdOperations.size() < 3) {
            mustUserdOperations.add(Globals.defeaultoperationLinks.get(index2));
            index2++;

        }
        Globals.MustUsedOperations.addAll(mustUserdOperations);
        Log.e("login: ", " MustUsedOperations --> " + Globals.MustUsedOperations);



        Globals.accountsHabList = new ArrayList<AccountHabEntry>();
        JSONArray arrayAccountsHab = jsonRespObject.getJSONArray("accountsHabList");
        for (int i = 0; i < arrayAccountsHab.length(); i++) {
            Globals.accountsHabList.add(new AccountHabEntry(arrayAccountsHab.getJSONObject(i).getString("accountNumber"), arrayAccountsHab.getJSONObject(i).getString("operationId")));
        }

        Globals.cardsList = new ArrayList<String>();
        Globals.maskedCardsList = new ArrayList<String>();
        JSONArray arrayCards = jsonRespObject.getJSONArray("cardsList");
        for (int i = 0; i < arrayCards.length(); i++) {
            Globals.cardsList.add(arrayCards.getString(i));
            Globals.maskedCardsList.add("***********" + arrayCards.getString(i).substring(11));
        }

        Globals.banksList = new ArrayList<BankEntry>();
        Globals.achBanksList = new ArrayList<BankEntry>();
        Globals.gipBanksList = new ArrayList<BankEntry>();
        JSONArray arrayBanks = jsonRespObject.getJSONArray("banksList");
        for (int i = 0; i < arrayBanks.length(); i++) {
            // if(i==0) Globals.banksList.add(new BankEntry(Globals.Select,Globals.Select,Globals.Select,Globals.Select));

            Globals.banksList.add(new BankEntry(
                    arrayBanks.getJSONObject(i).getString("bankCode"),
                    arrayBanks.getJSONObject(i).getString("wording"),
                    arrayBanks.getJSONObject(i).getString("achCode"),
                    arrayBanks.getJSONObject(i).getString("gipCode")));
        }

        if (Globals.banksList != null) {
            Collections.sort(Globals.banksList, new Comparator<BankEntry>() {
                public int compare(BankEntry c1, BankEntry c2) {
                    return c1.getWording().compareTo(c2.getWording());
                }
            });

            Globals.banksList.add(0, new BankEntry(Globals.Select, Globals.Select, Globals.Select, Globals.Select));

            for (BankEntry bank : Globals.banksList)
                if (!bank.getBankCode().equals("00100") && bank.getAchCode() != null && bank.getAchCode().length() > 0)
                    Globals.achBanksList.add(bank);

            for (BankEntry bank : Globals.banksList)
                if (!bank.getBankCode().equals("00100") && bank.getGipCode() != null && bank.getGipCode().length() > 0)
                    Globals.gipBanksList.add(bank);
        }

        //hajer 13/06/2022 start
        Globals.momoProvidersList = new ArrayList<MomoProviderEntry>();
        JSONArray arrayMomoProviders = jsonRespObject.getJSONArray("MomoProviders");
        for(int i = 0 ; i < arrayMomoProviders.length() ; i++){
            Log.d("MOMO ",arrayMomoProviders.getJSONObject(i).getString("wording"));

            Globals.momoProvidersList.add(new MomoProviderEntry(
                    arrayMomoProviders.getJSONObject(i).getString("providerCode"),
                    arrayMomoProviders.getJSONObject(i).getString("wording"),
                    arrayMomoProviders.getJSONObject(i).getString("routingCode")));

            Log.d("MOMO GLOBAL LIST",Globals.momoProvidersList.get(i).getWording());

        }

        if(Globals.momoProvidersList!=null) {
            Collections.sort(Globals.momoProvidersList, new Comparator<MomoProviderEntry>() {
                public int compare(MomoProviderEntry c1, MomoProviderEntry c2) {
                    return c1.getWording().compareTo(c2.getWording());
                }
            });
            Globals.momoProvidersList.add(0, new MomoProviderEntry(Globals.Select,Globals.Select,Globals.Select));
        }
        //hajer 13/06/2022 end

        Globals.branchesList = new ArrayList<BranchEntry>();
        JSONArray arrayBranches = jsonRespObject.getJSONArray("branchesList");
        for (int i = 0; i < arrayBranches.length(); i++) {
            // if(i==0) Globals.branchesList.add(new BranchEntry(Globals.Select,Globals.Select,Globals.Select,Globals.Select,Globals.Select,Globals.Select,Globals.Select));

            Globals.branchesList.add(new BranchEntry(
                    arrayBranches.getJSONObject(i).getString("branchCode"),
                    arrayBranches.getJSONObject(i).getString("bankCode"),
                    arrayBranches.getJSONObject(i).getString("branchName"),
                    arrayBranches.getJSONObject(i).getString("achBankCode"),
                    arrayBranches.getJSONObject(i).getString("gipBankCode"),
                    arrayBranches.getJSONObject(i).getString("achCode"),
                    arrayBranches.getJSONObject(i).getString("gipCode")
            ));
        }


        if (Globals.branchesList != null) {
            Collections.sort(Globals.branchesList, new Comparator<BranchEntry>() {
                public int compare(BranchEntry c1, BranchEntry c2) {
                    return c1.getBranchName().compareTo(c2.getBranchName());
                }
            });
            Globals.branchesList.add(0, new BranchEntry(Globals.Select, Globals.Select, Globals.Select, Globals.Select, Globals.Select, Globals.Select, Globals.Select));
        }
*/
        // purpose transfer

    /*    Globals.PurposeLstTR = new ArrayList<>();
        Globals.PurposeLstTR.add(Globals.Select); // MAW20180106
        JSONArray arrayPurposeTR = jsonRespObject.getJSONArray("PurposeListTR");
        for (int i = 0; i < arrayPurposeTR.length(); i++) {
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

            Globals.PurposeLstTR.add(arrayPurposeTR.getString(i));
        }


        // purpose and payment place

        // ---------------- purpose Prepaid Card Topup ------------------
        Globals.PurposeLstCT = new ArrayList<>();
        Globals.PurposeLstCT.add(Globals.Select); // MAW20180106
        JSONArray arrayPurposeCT = jsonRespObject.getJSONArray("PURPOSE_CT");
        for (int i = 0; i < arrayPurposeCT.length(); i++) {
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

            Globals.PurposeLstCT.add(arrayPurposeCT.getString(i));
        }

        // ---------------- purpose   Mobile Transfer ------------------
        Globals.PurposeLstA2M = new ArrayList<>();
        Globals.PurposeLstA2M.add(Globals.Select); // MAW20180106
        JSONArray arrayPurposeA2M = jsonRespObject.getJSONArray("PURPOSE_A2M");
        for (int i = 0; i < arrayPurposeA2M.length(); i++) {
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

            Globals.PurposeLstA2M.add(arrayPurposeA2M.getString(i));
        }

        // ---------------- Othman Loading Notifications ------------------
        JSONArray unviewedNotificationsList = jsonRespObject.getJSONArray("unviewedNotificationsList");
        Globals.unviewdNotificationsList.clear();
        for (int i = 0; i < unviewedNotificationsList.length(); i++) {
            NotificationEntry notificationEntry =  new NotificationEntry(
                    unviewedNotificationsList.getJSONObject(i).getString("id"),
                    unviewedNotificationsList.getJSONObject(i).getString("title"),
                    unviewedNotificationsList.getJSONObject(i).getString("body"),
                    unviewedNotificationsList.getJSONObject(i).getString("operation_date"),
                    unviewedNotificationsList.getJSONObject(i).getString("user")
            );
            Globals.unviewdNotificationsList.add(
                    notificationEntry
            );
            Log.d("notifications ::::: ", ""+notificationEntry.toString());
            Log.e("notifications size", ""+Globals.unviewdNotificationsList.size());


        }
        Globals.NOTIFICATION_LIST = Globals.unviewdNotificationsList.size();

        //**************** Notification List ********************
   /*     JSONArray arrayAllNotifications = jsonRespObject.getJSONArray("getAllNotifications");
        Globals.getAllNotificationList.clear();
        for (int i = 0; i < arrayAllNotifications.length(); i++) {
            NotificationEntry notificationEntry =  new NotificationEntry(
                    arrayAllNotifications.getJSONObject(i).getString("id"),
                    arrayAllNotifications.getJSONObject(i).getString("title"),
                    arrayAllNotifications.getJSONObject(i).getString("body"),
                    arrayAllNotifications.getJSONObject(i).getString("operation_date")
            );
            Globals.getAllNotificationList.add(
                    notificationEntry
            );
            Log.d("notifications INSIDE IF", ""+arrayAllNotifications.get(i));
            Log.d("notifications ::::: ", ""+notificationEntry.toString());

        }*/

        // ---------------- purpose Money Voucher ------------------
    /*    Globals.PurposeLstVT = new ArrayList<>();
        Globals.PurposeLstVT.add(Globals.Select); // MAW20180106
        JSONArray arrayPurposeLstVT = jsonRespObject.getJSONArray("PURPOSE_VT");
        for (int i = 0; i < arrayPurposeLstVT.length(); i++) {
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

            Globals.PurposeLstVT.add(arrayPurposeLstVT.getString(i));
        }*/



        // ---------------- purpose Instant Pay Transfer ------------------
    /*    Globals.PurposeLstGIP = new ArrayList<>();
        Globals.PurposeLstGIP.add(Globals.Select); // MAW20180106
        JSONArray arrayPurposeLstGIP = jsonRespObject.getJSONArray("PURPOSE_GIP");
        for (int i = 0; i < arrayPurposeLstGIP.length(); i++) {
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

            Globals.PurposeLstGIP.add(arrayPurposeLstGIP.getString(i));
        }

        // ---------------- purpose Between Accounts Transfer ------------------
        Globals.PurposeLstBA = new ArrayList<>();
        Globals.PurposeLstBA.add(Globals.Select); // MAW20180106
        JSONArray arrayPurposeBA = jsonRespObject.getJSONArray("PURPOSE_BA");
        for (int i = 0; i < arrayPurposeBA.length(); i++) {
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

            Globals.PurposeLstBA.add(arrayPurposeBA.getString(i));
        }

        // ---------------- purpose Two Days Transfer - ACH ------------------
        Globals.PurposeLstACH = new ArrayList<>();
        Globals.PurposeLstACH.add(Globals.Select); // MAW20180106
        JSONArray arrayPurposeACH = jsonRespObject.getJSONArray("PURPOSE_ACH");
        for (int i = 0; i < arrayPurposeACH.length(); i++) {
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

            Globals.PurposeLstACH.add(arrayPurposeACH.getString(i));
        }

        // ---------------- purpose  Account to Account Transfer ------------------
        Globals.PurposeLstA2A = new ArrayList<>();
        Globals.PurposeLstA2A.add(Globals.Select); // MAW20180106
        JSONArray arrayPurposeA2A = jsonRespObject.getJSONArray("PURPOSE_A2A");
        for (int i = 0; i < arrayPurposeA2A.length(); i++) {
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

            Globals.PurposeLstA2A.add(arrayPurposeA2A.getString(i));
        }


        // purpose and payment place
        Globals.PurposeLstAG = new ArrayList<>();
        Globals.PurposeLstAG.add(Globals.Select); // MAW20180106
        JSONArray arrayPurposeAG = jsonRespObject.getJSONArray("PurposeListAG");
        for (int i = 0; i < arrayPurposeAG.length(); i++) {
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

            Globals.PurposeLstAG.add(arrayPurposeAG.getString(i));
        }

        Globals.agentsList = new ArrayList<>();
        Globals.agentsList.add(Globals.Select); // MAW20180106
        JSONArray arrayAgents = jsonRespObject.getJSONArray("agentList");
        for (int i = 0; i < arrayAgents.length(); i++) {
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

            Globals.agentsList.add(arrayAgents.getString(i));
        }
        // HOTEL
        Globals.PurposeLstH = new ArrayList<>();
        Globals.PurposeLstH.add(Globals.Select); // MAW20180106
        JSONArray arrayPurposeH = jsonRespObject.getJSONArray("PurposeListH");
        for (int i = 0; i < arrayPurposeH.length(); i++) {
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

            Globals.PurposeLstH.add(arrayPurposeH.getString(i));
        }

        Globals.hotelList = new ArrayList<>();
        Globals.hotelList.add(Globals.Select); // MAW20180106
        JSONArray arrayHotels = jsonRespObject.getJSONArray("hotelList");
        for (int i = 0; i < arrayHotels.length(); i++) {
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

            Globals.hotelList.add(arrayHotels.getString(i));
        }

        // UNIVERSITY
        Globals.PurposeLstU = new ArrayList<>();
        Globals.PurposeLstU.add(Globals.Select); // MAW20180106
        JSONArray arrayPurposeU = jsonRespObject.getJSONArray("PurposeListU");
        for (int i = 0; i < arrayPurposeU.length(); i++) {
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

            Globals.PurposeLstU.add(arrayPurposeU.getString(i));
        }

        Globals.universityList = new ArrayList<>();
        Globals.universityList.add(Globals.Select); // MAW20180106
        JSONArray arrayUniversity = jsonRespObject.getJSONArray("universityList");
        for (int i = 0; i < arrayUniversity.length(); i++) {
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

            Globals.universityList.add(arrayUniversity.getString(i));
        }

        // SCHOOLS

        Globals.schoolsList = new ArrayList<>();
        Globals.schoolsList.add(Globals.Select); // MAW20180106
        JSONArray arraySchool = jsonRespObject.getJSONArray("schoolList");
        for (int i = 0; i < arraySchool.length(); i++) {
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

            Globals.schoolsList.add(arraySchool.getString(i));
        }

        // CHURCH

        Globals.PurposeLstC = new ArrayList<>();
        Globals.PurposeLstC.add(Globals.Select); // MAW20180106
        JSONArray arrayPurposeC = jsonRespObject.getJSONArray("PurposeListC");
        for (int i = 0; i < arrayPurposeC.length(); i++) {
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

            Globals.PurposeLstC.add(arrayPurposeC.getString(i));
        }

        Globals.churchList = new ArrayList<>();
        Globals.churchList.add(Globals.Select); // MAW20180106
        JSONArray arrayChurch = jsonRespObject.getJSONArray("churchList");
        for (int i = 0; i < arrayChurch.length(); i++) {
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

            Globals.churchList.add(arrayChurch.getString(i));
        }


        // MM purposes : mobile money
        Globals.PurposeMM = new ArrayList<>();
        Globals.PurposeMM.add(Globals.Select); // MAW20180106
        JSONArray arrayPurposeMM = jsonRespObject.getJSONArray("PurposeListMM");
        for (int i = 0; i < arrayPurposeMM.length(); i++) {
            Globals.PurposeMM.add(arrayPurposeMM.getString(i));
        }

        Log.e( "login: PurposeListMM : ", ""+ Globals.PurposeMM );

        // QR purposes // Qr payements
        Globals.PurposeQR = new ArrayList<>();
        Globals.PurposeQR.add(Globals.Select); // MAW20180106
        JSONArray arrayPurposeQr = jsonRespObject.getJSONArray("PURPOSE_QR_PAYMENT");
        for (int i = 0; i < arrayPurposeQr.length(); i++) {
            Globals.PurposeQR.add(arrayPurposeQr.getString(i));
        }

        Log.e( "login: PurposeListQR : ", ""+ Globals.PurposeQR );


        //PurposeListAD : Airtime & data

        Globals.PurposeAD = new ArrayList<>();
        Globals.PurposeAD.add(Globals.Select); // MAW20180106
        JSONArray arrayPurposeAD = jsonRespObject.getJSONArray("PurposeListAD");
        for (int i = 0; i < arrayPurposeAD.length(); i++) {
            Globals.PurposeAD.add(arrayPurposeAD.getString(i));
        }

        Log.e( "login: PurposeListAD : ", ""+ Globals.PurposeAD );

        //PurposeListVD : vodafone

        Globals.PurposeVODAFONE = new ArrayList<>();
        Globals.PurposeVODAFONE.add(Globals.Select); // MAW20180106
        JSONArray PurposeVODAFONE = jsonRespObject.getJSONArray("PurposeListVD");
        for (int i = 0; i < PurposeVODAFONE.length(); i++) {
            Globals.PurposeVODAFONE.add(PurposeVODAFONE.getString(i));
        }

        Log.e( "login: PurposeListAD : ", ""+ Globals.PurposeVODAFONE );



        //PurposeListVD : vodafone

        Globals.PurposeDstv = new ArrayList<>();
        Globals.PurposeDstv.add(Globals.Select); // MAW20180106
        JSONArray Purposeds = jsonRespObject.getJSONArray("PurposeListDS");
        for (int i = 0; i < Purposeds.length(); i++) {
            Globals.PurposeDstv.add(Purposeds.getString(i));
        }

        Log.e( "login: PurposeListAD : ", ""+ Globals.PurposeDstv );



        // beneficiaries list

        JSONArray array = jsonRespObject.getJSONArray("Beneflist");
        if (!array.equals(null)) {
            Globals.BeneficyList.clear();
            // Globals.BeneficyList.add(new BeneficiaryEntry("Select","Select","Select","Select","Select","Select","Select"));


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

        // branches names
        Globals.branchOn = new ArrayList<String>();
        Globals.branchOn.add(Globals.Select);
        JSONArray array1Branches = jsonRespObject.getJSONArray("BranchList");
        for (int i = 0; i < array1Branches.length(); i++) {
            Globals.branchOn.add(array1Branches.get(i).toString());
        }
        if(jsonRespObject.has("CC")){
            Globals.CC = jsonRespObject.getString("CC");
        }
        Log.d("USER", "id["+Globals.statusBiom+"]");

        //**************** Notification List ********************
        JSONArray arrayNotifications = jsonRespObject.getJSONArray("notificationsList");
        Globals.notificationsList.clear();
        for (int i = 0; i < arrayNotifications.length(); i++) {
            NotificationEntry notificationEntry =  new NotificationEntry(
                    arrayNotifications.getJSONObject(i).getString("id"),
                    arrayNotifications.getJSONObject(i).getString("title"),
                    arrayNotifications.getJSONObject(i).getString("body"),
                    arrayNotifications.getJSONObject(i).getString("operation_date")
            );
            Globals.notificationsList.add(
                    notificationEntry
            );
            Log.d("notifications ::::: ", ""+notificationEntry.toString());

        }

        //**************** limitedOperations List ********************
  /*      Globals.limitedOperationsAccounts.clear();
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
            Log.e("login: "," limitedOperationsAccounts --> "+ Globals.limitedOperationsAccounts);
        }
        Globals.limitedOperations.addAll(Globals.limitedOperationsAccounts.keySet());
*/

        //*********************** Profile Image ****************************
      /*  if(jsonRespObject.has("profileImage")) {
            String userImage = jsonRespObject.getString("profileImage");
            Log.e("login: "," userImage --> "+ userImage);
            Bitmap bitmap = StringToBitMap2(userImage);
            Log.e("login: "," bitmap --> "+ bitmap);
            Globals.profileImage = StringToBitMap2(userImage);
            Log.e("login: "," Globals.profileImage --> "+ Globals.profileImage);

        }

        if(jsonRespObject.has("HasLinkedAccount")){
            Globals.hasLinkedAccount= jsonRespObject.getBoolean("HasLinkedAccount");
            Log.e("login: ", "Has linked account : " + Globals.hasLinkedAccount);

        }else {
            Globals.hasLinkedAccount = false;
            Log.e("login: ", "Has linked account : " + Globals.hasLinkedAccount);
        }*/



        /*Globals.ProxyList.clear();
        Log.e("Proxylist: ", "Before fetching data "+Globals.ProxyList);

        if(jsonRespObject.has("Proxylist")) {
            JSONArray Proxylist = jsonRespObject.getJSONArray("Proxylist");

            for (int i = 0; i < Proxylist.length(); i++) {
                Globals.ProxyList.add(new ProxyEntry(
                        Proxylist.getJSONObject(i).getString("account_number"),
                        Proxylist.getJSONObject(i).getString("proxy_ID")
                        ));
            }
        }
        Log.e("Proxylist: ", "After fetching data "+Globals.ProxyList);
        */


        //ads
     /*   Globals.Ads = new ArrayList<>();

        if(jsonRespObject.has("adsList")) {

            JSONArray ads = jsonRespObject.getJSONArray("adsList");
            Log.e( "login: ads size ","" +ads.length());
            for (int i = 0; i < ads.length(); i++) {
                JSONObject ad = ads.getJSONObject(i);
                Bitmap bitmap = StringToBitMap2(ad.getString("image"));
                Log.e("login: ", " bitmap --> " + bitmap);
                //byte[] bytes = null;
                //bytes = Base64.decode(ad.getString("image"), Base64.DEFAULT);
              //  AdsEntry newAd = new AdsEntry(ad.getString("id"), bitmap);
                //  04072022
                AdsEntry newAd = new AdsEntry(ad.getString("id"), bitmap, ad.getString("link"));
                Globals.Ads.add(newAd);
            }
            Log.d("ads list", "  ++ " + Globals.Ads);

        }
*/
        // fatim 08/04/2022
        //Globals.timeout =  jsonRespObject.getString("session_timeout");
    /*    Globals.timeout =  jsonRespObject.optString("session_timeout", (5 * 60) + "");
        //Globals.timeout = "30";
        Log.i("Tag", "Value of timer" +Globals.timeout);

        // fatim 08/04/2022

        //   26092022
        Globals.pickUpBy = new ArrayList<>();
        Globals.pickUpBy.add(Globals.Select +" Pick Up By"); // MAW20180106
        JSONArray pickUpByJA = jsonRespObject.getJSONArray("pickUpBy");
        for (int i = 0; i < pickUpByJA.length(); i++) {
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106
            Globals.pickUpBy.add(pickUpByJA.getString(i));
        }*/


        //hajer 04/02/2022 start
       /* Globals.Ads = new ArrayList<>();
        JSONArray ads = jsonRespObject.getJSONArray("adsList");
        for (int i = 0; i < ads.length(); i++) {
            JSONObject ad = ads.getJSONObject(i);
            Bitmap bitmap = StringToBitMap2(ad.getString("image"));
            Log.e("login: "," bitmap --> "+ bitmap);
            //byte[] bytes = null;
            //bytes = Base64.decode(ad.getString("image"), Base64.DEFAULT);
            AdsEntry newAd = new AdsEntry(ad.getString("id"), bitmap);
            Globals.Ads.add(newAd);
        }
        Log.d("ads list", "  ++ "+ Globals.Ads);

        */
        //hajer 04:03:2022 end

      /*  if (jsonRespObject.getString("responseCode").equals("802"))
            throw new Exception("REFRESH AUTHENTICATION <responseCode=[" + jsonRespObject.getString("responseCode") + "]>");*/

    }

    static public void changePwd(String userCode, String password, String newPwd, String newPwdConf) throws Exception {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", userCode);
        jsonObject.put("password", password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("newPassword", newPwd);
        jsonObject.put("newPasswordConf", newPwdConf);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.ChangePassword, jsonObject);

        if (jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("OPERATION REJECTED <RespCode=[" + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "]>");

        Globals.sessionId = jsonRespObject.getString("sessionId");


        return;

    }

    static public void changePin(String userCode, String pin, String newPin, String newPinConf) throws Exception {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", userCode);
        jsonObject.put("password", Globals.password);
        jsonObject.put("pin", pin);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("newPin", newPin);
        jsonObject.put("newPinConf", newPinConf);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.ChangePin, jsonObject);

        if (jsonRespObject.has("respCode")

                && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("OPERATION REJECTED <RespCode=[" + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "]>");

        //Globals.sessionId = jsonRespObject.getString("sessionId");


        return;

    }

    static public void ChangeUserActivation(String user, String status) throws Exception{
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", user);
        jsonObject.put("status", status);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceChangeUserActivation, jsonObject);

        if (jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("OPERATION REJECTED <" + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + ">");

    }

    static public void userAuthentication(String userCode, String password, String authenCode, int flag) throws Exception {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", userCode);
        jsonObject.put("password", password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", authenCode);
        jsonObject.put("flag", flag);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceUserAuthentication, jsonObject);

        if (jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("OPERATION REJECTED <" + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + ">");

        Globals.sessionId = jsonRespObject.getString("newSessionId");
        Globals.authenCode = authenCode;
        // Globals.authenToken = jsonRespObject.getString("authenToken");

        return;

    }

    //requesting for reset password


    static public void requestForResetPassword(String user, String clientCode, String mobile, String email) throws Exception {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", user);
        jsonObject.put("mobile", mobile);
        jsonObject.put("email", email);
        jsonObject.put("clientCode", clientCode);
        //jsonObject.put("sessionId", Globals.sessionId); jsonObject.put("authenCode", Globals.authenCode);


        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.servicePassReset, jsonObject);

        if (jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new
                    Exception("OPERATION REJECTED <RespCode=[" + (jsonRespObject.has("respCode")
                    ? jsonRespObject.getString("respCode") : "") + "]>");

        Globals.sessionId = jsonRespObject.getString("sessionId");
        // Log.d("before SMS  ",Globals.sessionId );

        return;

    }

    static public void requestForResetPin(String user, String account, String branch, String dob) throws Exception{
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", user);
        jsonObject.put("account", account);
        jsonObject.put("branch", branch);
        jsonObject.put("dob", dob);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.servicePinReset, jsonObject);

        if (jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new
                    Exception("OPERATION REJECTED <RespCode=[" + (jsonRespObject.has("respCode")
                    ? jsonRespObject.getString("respCode") : "") + "]>");
    }

    static public void requestForResetPinOut(String user, String account, String branch, String dob) throws Exception{
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", user);
        jsonObject.put("account", account);
        jsonObject.put("branch", branch);
        jsonObject.put("dob", dob);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.servicePinResetOut, jsonObject);

        if (jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new
                    Exception("OPERATION REJECTED <RespCode=[" + (jsonRespObject.has("respCode")
                    ? jsonRespObject.getString("respCode") : "") + "]>");
    }

    //check for a authentification code
    public static void checkAuthentificationCode(String resetCode, String pin) throws Exception { // younes uncommented function
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("resetCode", resetCode);
        jsonObject.put("user", Globals.username);
        //jsonObject.put("mobile", Globals.userModelSession.getUserMobile());
        //jsonObject.put("email", Globals.userModelSession.getUserEmail());
        //jsonObject.put("clientCode", Globals.userModelSession.getUserClientCode());
        jsonObject.put("pin", pin);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.servicePassResetCheck, jsonObject);

        if (jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")) {
            throw new Exception("CHECK OTP FAILED <RespCode=[" + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "]>");
        } else {

           /* if (jsonRespObject.has("sessionId")) {
                String sessionID = jsonRespObject.getString("sessionId");
                Globals.userModelSession.setUserSessionID(sessionID);
                Log.d("ON SMS  ",Globals.sessionId );

            } else {
                throw new JSONException("checkAuthentificationCode: Error attribute not found");
            }*/

        }

        return;
    }

    public static void checkAuthentificationCodePinOut(String resetCode, String password) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("resetCode", resetCode);
        jsonObject.put("user", Globals.username);
        jsonObject.put("password", password);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.servicePinResetCheckOut, jsonObject);
        if (jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")) {
            throw new Exception("CHECK OTP FAILED <RespCode=[" + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "]>");
        }
    }

    public static void CheckValidationCode(String code, String user) throws Exception{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("resetCode", code);
        jsonObject.put("user", user);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceCheckValidationCode, jsonObject);
        if (jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")) {
            throw new Exception("CHECK OTP FAILED <RespCode=[" + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "]>");
        }
    }

    public static boolean updateOldUser(String userCode, String pass, String pin, String defaultAcc) throws Exception {
        boolean result = false;
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", userCode);
        jsonObject.put("pass", pass);
        jsonObject.put("pin", pin);
        jsonObject.put("defaultAcc", defaultAcc);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceCheckOldAccount, jsonObject);
        if (jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")) {
            throw new Exception("CHECK OLD USER <RespCode=[" + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "]>");
        }
        else
            result = true;

        return result;
    }
    //confirmResetPassword

    static public void confirmResetPassword(String newPassword, String newPasswordConf) throws Exception {
        JSONObject jsonObject = new JSONObject();
/*"user":"iti",
		"sessionId":"XXXXXXXXXXXXXXX",
		"newPassword":"YYYYYYYY",
		"newPasswordConf":"YYYYYYYY"
		*/
// 3/28/2023       jsonObject.put("user", Globals.userModelSession.getUserCode());
//        jsonObject.put("sessionId", Globals.userModelSession.getUserSessionID());
//        jsonObject.put("newPassword", newPassword);
//        jsonObject.put("newPasswordConf", newPasswordConf);
//        jsonObject.put("resetCode", Globals.userModelSession.getResetCode());
        jsonObject.put("sessionId", Globals.sessionId);

        //  Log.d("After SMS  ",Globals.sessionId );

        //jsonObject.put("sessionId", Globals.sessionId); jsonObject.put("authenCode", Globals.authenCode);
        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.servicePassResetChange, jsonObject);
        if (jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new
                    Exception("OPERATION REJECTED <RespCode=[" + (jsonRespObject.has("respCode")
                    ? jsonRespObject.getString("respCode") : "") + "]>");
        // Globals.sessionId = jsonRespObject.getString("sessionId");
        return;
    }
    static public void confirmResetPasswordSIgnIN(String newPassword, String newPasswordConf) throws Exception {
        JSONObject jsonObject = new JSONObject();
/*"user":"iti",
		"sessionId":"XXXXXXXXXXXXXXX",
		"newPassword":"YYYYYYYY",
		"newPasswordConf":"YYYYYYYY"
		*/
        jsonObject.put("user", Globals.username);
        jsonObject.put("password", "");
        jsonObject.put("newPassword", newPassword);
        jsonObject.put("newPasswordConf", newPasswordConf);
        jsonObject.put("otp", Globals.pinEntered);


        //jsonObject.put("sessionId", Globals.sessionId); jsonObject.put("authenCode", Globals.authenCode);
        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.servicePassResetChangeSignIN, jsonObject);
        if (jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new
                    Exception("OPERATION REJECTED <RespCode=[" + (jsonRespObject.has("respCode")
                    ? jsonRespObject.getString("respCode") : "") + "]>");
        // Globals.sessionId = jsonRespObject.getString("sessionId");
        return;
    }

    static public void confirmResetPin(String pin, String confirmPin) throws Exception {
        JSONObject jsonObject = new JSONObject();

// 3/28/2023       jsonObject.put("user", Globals.userModelSession.getUserCode());
//        jsonObject.put("sessionId", Globals.userModelSession.getUserSessionID());
        jsonObject.put("newPin", pin);
        jsonObject.put("newPinConf", confirmPin);
//3/28/2023        jsonObject.put("resetCode", Globals.userModelSession.getResetCode());
        jsonObject.put("sessionId", Globals.sessionId);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.servicePinResetChange, jsonObject);

        if (jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new
                    Exception("OPERATION REJECTED <RespCode=[" + (jsonRespObject.has("respCode")
                    ? jsonRespObject.getString("respCode") : "") + "]>");
        // Globals.sessionId = jsonRespObject.getString("sessionId");
        return;

    }
    static public void confirmResetPin2(String pin, String confirmPin, String activity) throws Exception {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.username);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("newPin", pin);
        jsonObject.put("newPinConf", confirmPin);
        jsonObject.put("activity", activity);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.servicePinResetChange, jsonObject);

        if (jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new
                    Exception("OPERATION REJECTED <RespCode=[" + (jsonRespObject.has("respCode")
                    ? jsonRespObject.getString("respCode") : "") + "]>");
        // Globals.sessionId = jsonRespObject.getString("sessionId");
        return;

    }

    // Younes AB

    static public boolean signUP(String customerID, int localNumberOfTries) throws Exception {
        JSONObject jsonObject = new JSONObject();
/*"user":"iti",
		"sessionId":"XXXXXXXXXXXXXXX",
		"newPassword":"YYYYYYYY",
		"newPasswordConf":"YYYYYYYY"
		*/
//younes
        //    Log.d("localNumberOfTries", "this num+++++++++++++" + localNumberOfTries);
        //Log.d("localNumberOfTries", ""+localNumberOfTries+"");
        //System.out.println( localNumberOfTries +" ");
        //System.out.println( localNumberOfTries +" ");

        if (localNumberOfTries == 3) {
            //younes
            //Globals.globalNumberOfTries=0;
            System.out.println("Customer should be blocked ");
            System.out.println("Customer should be blocked ");
            System.out.println("Customer should be blocked ");
            System.out.println("Customer should be blocked ");
            return false;
        }

        jsonObject.put("customerID", customerID);
        jsonObject.put("localNumberOfTries", localNumberOfTries);
        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceSignUp, jsonObject);
        Globals.errorCustId = jsonRespObject.getString("respCode");
        String msg = jsonRespObject.getString("respCode");
        System.out.println("showit   **********   " + msg);
        if (jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")) {
            //Globals.globalNumberOfTries++;
            Globals.nameCustomer = jsonRespObject.getString("name");
            // String msg = jsonRespObject.getString("respCode"); // younes to display error msg after SignUp
            //Globals.errorCustId = jsonRespObject.getString("respCode");
            //System.out.println("showit   **********   "+msg);

            throw new
                    Exception("OPERATION REJECTED <RespCode=[" + (jsonRespObject.has("respCode")
                    ? jsonRespObject.getString("respCode") : "") + "]>");
//            Log.d("localNumberOfTries", "this num+++++++++++++"+localNumberOfTries);
        } else {
// younes
            Globals.ClientId = customerID;
            Globals.user = "user";
            Globals.password = "";
            Globals.sessionId = "";
            Globals.authenCode = "";

            Globals.domicile_branch = jsonRespObject.getString("domicile_branch"); // younes for new signup
            Globals.useOTPSelfSignUp = true;

            Globals.globalNumberOfTries = 0;
            Globals.errorCustId = "signup sucessful";

            Globals.nameCustomer = jsonRespObject.getString("name");
            Globals.fnameCustomer = jsonRespObject.getString("fname");
            Globals.idCustomer = jsonRespObject.getString("id");

            //Globals.accountnbr=jsonRespObject.getString("account");
            Globals.emailCustomer = jsonRespObject.getString("email");
            Globals.telephone = jsonRespObject.getString("telephone");
            JSONArray accountnbr = jsonRespObject.getJSONArray("listAccount");

            Globals.accountTBSent = new ArrayList<>();
            //Globals.accountTBSent.add(Globals.Select); // MAW20180106
            JSONArray arrayAccounts = jsonRespObject.getJSONArray("listAccount");
            for (int i = 0; i < arrayAccounts.length(); i++) {
                // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

                Globals.accountTBSent.add(arrayAccounts.getString(i));
            }
            Globals.currencyLst = new ArrayList<>();
            JSONArray arrayCurrency = jsonRespObject.getJSONArray("CurrencyList");
            for (int i = 0; i < arrayCurrency.length(); i++) {
                // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

                Globals.currencyLst.add(arrayCurrency.getString(i));
            }

            Globals.acountString = jsonRespObject.getString("acountString");
            for (int ii = 0; ii < accountnbr.length(); ii++) {
                Globals.accountnbr.add(String.valueOf(accountnbr.get(ii))); // younes to see why string
            }

            //Globals.
            // JSONObject wsClient = jsonRespObject.getJSONObject("custumer");
            System.out.println(jsonRespObject.getString("acountString"));
            System.out.println(Globals.nameCustomer + " ");
            System.out.println(Globals.accountnbr + " ");
            System.out.println(Globals.emailCustomer + " ");
            System.out.println(Globals.telephone + " ");
            // younes test idea
            //Globals.user= Globals.nameCustomer;
            return true;

        }
    }

    static public void createUser(String userCode, String newPwd, String newPwdConf, String clientData, List<String> accountNumber, List<String> currencyList) throws Exception {

        JSONObject jsonObject = new JSONObject();
        //int lastIndx = lastIndexOf(accountNumber);
        jsonObject.put("user", userCode);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("newPassword", newPwd);
        jsonObject.put("newPasswordConf", newPwdConf);
        jsonObject.put("clientData", clientData);
        // jsonObject.put("accountNumber",accountNumber.substring(1, accountNumber.lastIndexOf(']')));
        jsonObject.put("accountNumber", accountNumber);
        jsonObject.put("currencyList", currencyList);
        //younes
        // jsonObject.put("secretQues",secretQues);
        //jsonObject.put("answer",answer);
        jsonObject.put("id", Globals.ClientId); // Younes
        jsonObject.put("fname", Globals.fnameCustomer); // Younes
        jsonObject.put("name", Globals.nameCustomer); // Younes
        jsonObject.put("tel", Globals.telephone); // Younes
        jsonObject.put("email", Globals.emailCustomer); // Younes


        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.createUser, jsonObject);
        Globals.errorUserId = jsonRespObject.getString("respCode"); // younes
        System.out.println("showit   **********   " + Globals.errorUserId);

        if (jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")) {
            //         throw new Exception("OPERATION REJECTED <RespCode=[" + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "]>");
            throw new Exception("" + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "");

        }
        Globals.sessionId = jsonRespObject.getString("sessionId");


        return;

    }


    static public void NewSignUp(int flag , String mobile, String account, String branch, String dob, String pin, String pinConf, String pass, String passConf,String RefCode) throws Exception {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("mobile", mobile);
        jsonObject.put("account", account);
        jsonObject.put("branch", branch);
        jsonObject.put("dob", dob);
        jsonObject.put("pin", pin);
        jsonObject.put("pinConf", pinConf);
        jsonObject.put("pass", pass);
        jsonObject.put("passConf", passConf);
        jsonObject.put("flag", flag);
        jsonObject.put("refrealCode", RefCode);
        jsonObject.put("otp", Globals.otp);


        //Globals.pin = pin;
        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.newSignUp, jsonObject);

        Globals.errorOnBoard = jsonRespObject.getString("respCode");

        if (jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")) {
            throw new Exception("" + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "");
        }
        if(jsonRespObject.getString("respCode").equals("000") && jsonRespObject.has("userValide"))
        {
            Log.e("NewSignUp: ","222222222222222222222222" );
            if(jsonRespObject.getBoolean("userValide")) {
                Log.e("NewSignUp: ","333333333333333333333" );
                Globals.userValid = true;
                return;
            }
        }
        Log.e("NewSignUp: ","44444444444444444444444" );

        Globals.errorCustId = "signup successful";
        Globals.username = jsonRespObject.getString("fname");
        Globals.firstName = jsonRespObject.getString("firstName");
        Log.i("NewSingUp FirstName : " , Globals.firstName );


    }

    static public void NewSignUpBiz(int flag ,String mobile, String account, String branch, String bizcert, String dob, String pin, String pinConf, String pass, String passConf , String RefCode) throws Exception {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("mobile", mobile);
        jsonObject.put("account", account);
        jsonObject.put("branch", branch);
        jsonObject.put("bizcert", bizcert);
        jsonObject.put("dob", dob);
        jsonObject.put("pin", pin);
        jsonObject.put("pinConf", pinConf);
        jsonObject.put("flag", flag);
        jsonObject.put("pass", pass);
        jsonObject.put("passConf", passConf);
        jsonObject.put("otp", Globals.otp);
        jsonObject.put("refrealCode", RefCode);


        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.newSignUpBiz, jsonObject);

        Globals.errorOnBoard = jsonRespObject.getString("respCode");

        if (jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")) {
            throw new Exception("" + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "");
        }
        if(jsonRespObject.getString("respCode").equals("000") && jsonRespObject.has("userValide"))
        {
            Log.e("NewSignUp: ","222222222222222222222222" );
            if(jsonRespObject.getBoolean("userValide")) {
                Log.e("NewSignUp: ","333333333333333333333" );
                Globals.userValid = true;
                return;
            }
        }
        Globals.errorCustId = "signup successful";
        Globals.username = jsonRespObject.getString("fname");
        //   Log.d("new Self Onboarding", "" + Globals.username);
        Globals.firstName = jsonRespObject.getString("firstName");
    }

    public static void AccountCheck(String phone, String titleType) throws Exception{
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("phone", phone);
        jsonObject.put("titleType", titleType);
        Globals.phone = phone;

        Globals.errBenef="";

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.AccCheckService, jsonObject);

        if (jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")) {
            // throw new Exception("" + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "");
            if (jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("100")) {
                Globals.errBenef = jsonRespObject.getString("respCode");
            }
            Globals.resultAccCheck = false;

        }
        else{
            if(jsonRespObject.has("resep") && jsonRespObject.getString("resep").equals("999")) {
                Globals.resultAccCheck = true;
                Globals.username = jsonRespObject.getString("userCode");
                Globals.firstName =  jsonRespObject.getString("firstName");
                Log.i("checkAcount FirstName: " , Globals.firstName );
                Globals.firstNameC =   Globals.firstName ;
                Globals.oldAccount = true;
            }
            else{
                Globals.username = jsonRespObject.getString("userCode");
                Globals.resultAccCheck = true;
                String data[] = Globals.username.trim().split("\\s+");
                Globals.user = "user";
                Globals.password = "";
                Globals.sessionId = "";
                Globals.authenCode = "";
                Globals.firstName = jsonRespObject.getString("firstName");
                Log.i("else1 FirstName : " , Globals.firstName );
                Globals.firstNameC = Globals.firstName.trim();
                Globals.oldAccount = false;
            }
        }

    }


    static public void login2(String userCode, String password, String authenCode, int flag, String pin) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", userCode);
        jsonObject.put("password", password);
        jsonObject.put("authenCode", authenCode == null ? "" : authenCode);
        jsonObject.put("flag", flag);
        jsonObject.put("pin", pin);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceSignIn, jsonObject);
        //hajer 24/06/2022
        if (jsonRespObject.has("sessionId")){
            Globals.sessionId = jsonRespObject.getString("sessionId");
        }
        Log.e( "login2: ",""+jsonRespObject.getString("respCode") );
        if (jsonRespObject.getString("respCode").equals("803")) {
             Globals.sessionId = jsonRespObject.getString("sessionId");
            throw new Exception(jsonRespObject.getString("respCode"));
        }
       /* if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000") && !jsonRespObject.getString("respCode").equals("802"))
            throw new Exception("LOGIN REJECTED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");
*/
        Globals.notificationViewed = false;
        Globals.ERmsg = jsonRespObject.getString("respCode");
        //     Log.d("", "" + Globals.ERmsg);
        if (jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000")) {
            throw new Exception("" + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "");
        }
        Globals.sessionId = jsonRespObject.getString("sessionId");
        Globals.fname = jsonRespObject.getString("fname"); // younes
        Globals.ClientId = jsonRespObject.getString("clientID");
        Globals.statusBiom = jsonRespObject.getString("statusBiom"); // hajer bio
        Globals.userType = jsonRespObject.getString("userType");

        Globals.accountsList = new ArrayList<String>();
        Globals.accountsList.add(Globals.Select); // MAW20180106
        JSONArray arrayAccounts = jsonRespObject.getJSONArray("accountsList");
        for (int i = 0; i < arrayAccounts.length(); i++) {
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

            Globals.accountsList.add(arrayAccounts.getString(i));
        }

        Globals.accountsHabList = new ArrayList<AccountHabEntry>();
        JSONArray arrayAccountsHab = jsonRespObject.getJSONArray("accountsHabList");
        for (int i = 0; i < arrayAccountsHab.length(); i++) {
            Globals.accountsHabList.add(new AccountHabEntry(arrayAccountsHab.getJSONObject(i).getString("accountNumber"), arrayAccountsHab.getJSONObject(i).getString("operationId")));
        }

        Globals.cardsList = new ArrayList<String>();
        Globals.maskedCardsList = new ArrayList<String>();
        JSONArray arrayCards = jsonRespObject.getJSONArray("cardsList");
        for (int i = 0; i < arrayCards.length(); i++) {
            Globals.cardsList.add(arrayCards.getString(i));
            Globals.maskedCardsList.add("***********" + arrayCards.getString(i).substring(11));
        }

        Globals.banksList = new ArrayList<BankEntry>();
        Globals.achBanksList = new ArrayList<BankEntry>();
        Globals.gipBanksList = new ArrayList<BankEntry>();
        JSONArray arrayBanks = jsonRespObject.getJSONArray("banksList");
        for (int i = 0; i < arrayBanks.length(); i++) {
            // if(i==0) Globals.banksList.add(new BankEntry(Globals.Select,Globals.Select,Globals.Select,Globals.Select));

            Globals.banksList.add(new BankEntry(
                    arrayBanks.getJSONObject(i).getString("bankCode"),
                    arrayBanks.getJSONObject(i).getString("wording"),
                    arrayBanks.getJSONObject(i).getString("achCode"),
                    arrayBanks.getJSONObject(i).getString("gipCode")));
        }

        if (Globals.banksList != null) {
            Collections.sort(Globals.banksList, new Comparator<BankEntry>() {
                public int compare(BankEntry c1, BankEntry c2) {
                    return c1.getWording().compareTo(c2.getWording());
                }
            });

            Globals.banksList.add(0, new BankEntry(Globals.Select, Globals.Select, Globals.Select, Globals.Select));

            for (BankEntry bank : Globals.banksList)
                if (!bank.getBankCode().equals("00100") && bank.getAchCode() != null && bank.getAchCode().length() > 0)
                    Globals.achBanksList.add(bank);

            for (BankEntry bank : Globals.banksList)
                if (!bank.getBankCode().equals("00100") && bank.getGipCode() != null && bank.getGipCode().length() > 0)
                    Globals.gipBanksList.add(bank);
        }

        Globals.branchesList = new ArrayList<BranchEntry>();
        JSONArray arrayBranches = jsonRespObject.getJSONArray("branchesList");
        for (int i = 0; i < arrayBranches.length(); i++) {
            // if(i==0) Globals.branchesList.add(new BranchEntry(Globals.Select,Globals.Select,Globals.Select,Globals.Select,Globals.Select,Globals.Select,Globals.Select));

            Globals.branchesList.add(new BranchEntry(
                    arrayBranches.getJSONObject(i).getString("branchCode"),
                    arrayBranches.getJSONObject(i).getString("bankCode"),
                    arrayBranches.getJSONObject(i).getString("branchName"),
                    arrayBranches.getJSONObject(i).getString("achBankCode"),
                    arrayBranches.getJSONObject(i).getString("gipBankCode"),
                    arrayBranches.getJSONObject(i).getString("achCode"),
                    arrayBranches.getJSONObject(i).getString("gipCode")
            ));
        }



        if (Globals.branchesList != null) {
            Collections.sort(Globals.branchesList, new Comparator<BranchEntry>() {
                public int compare(BranchEntry c1, BranchEntry c2) {
                    return c1.getBranchName().compareTo(c2.getBranchName());
                }
            });
            Globals.branchesList.add(0, new BranchEntry(Globals.Select, Globals.Select, Globals.Select, Globals.Select, Globals.Select, Globals.Select, Globals.Select));
        }

        // fatim 08/04/2022
        // Globals.timeout =  jsonRespObject.getString("session_timeout");
        Globals.timeout =  jsonRespObject.optString("session_timeout", (5 * 60) + "");
        //Globals.timeout = "30";
        Log.i("Tag", "Value of timer" +Globals.timeout);

        // fatim 08/04/2022

        // purpose and payment place

        // ---------------- purpose Prepaid Card Topup ------------------
        Globals.PurposeLstCT = new ArrayList<>();
        Globals.PurposeLstCT.add(Globals.Select); // MAW20180106
        JSONArray arrayPurposeCT = jsonRespObject.getJSONArray("PURPOSE_CT");
        for (int i = 0; i < arrayPurposeCT.length(); i++) {
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

            Globals.PurposeLstCT.add(arrayPurposeCT.getString(i));
        }

        // ---------------- Othman Loading Notifications ------------------
        JSONArray unviewedNotificationsList = jsonRespObject.getJSONArray("unviewedNotificationsList");
        Globals.unviewdNotificationsList.clear();
        for (int i = 0; i < unviewedNotificationsList.length(); i++) {
            NotificationEntry notificationEntry =  new NotificationEntry(
                    unviewedNotificationsList.getJSONObject(i).getString("id"),
                    unviewedNotificationsList.getJSONObject(i).getString("title"),
                    unviewedNotificationsList.getJSONObject(i).getString("body"),
                    unviewedNotificationsList.getJSONObject(i).getString("operation_date"),
                    unviewedNotificationsList.getJSONObject(i).getString("user")
            );
            Globals.unviewdNotificationsList.add(
                    notificationEntry
            );
            Log.d("notifications ::::: ", ""+notificationEntry.toString());
            Log.e("notifications size", ""+Globals.unviewdNotificationsList.size());

        }
        Globals.NOTIFICATION_LIST = Globals.unviewdNotificationsList.size();


        // ---------------- purpose   Mobile Transfer ------------------
        Globals.PurposeLstA2M = new ArrayList<>();
        Globals.PurposeLstA2M.add(Globals.Select); // MAW20180106
        JSONArray arrayPurposeA2M = jsonRespObject.getJSONArray("PURPOSE_A2M");
        for (int i = 0; i < arrayPurposeA2M.length(); i++) {
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

            Globals.PurposeLstA2M.add(arrayPurposeA2M.getString(i));
        }

        // ---------------- purpose Money Voucher ------------------
        Globals.PurposeLstVT = new ArrayList<>();
        Globals.PurposeLstVT.add(Globals.Select); // MAW20180106
        JSONArray arrayPurposeLstVT = jsonRespObject.getJSONArray("PURPOSE_VT");
        for (int i = 0; i < arrayPurposeLstVT.length(); i++) {
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

            Globals.PurposeLstVT.add(arrayPurposeLstVT.getString(i));
        }



        // ---------------- purpose Instant Pay Transfer ------------------
        Globals.PurposeLstGIP = new ArrayList<>();
        Globals.PurposeLstGIP.add(Globals.Select); // MAW20180106
        JSONArray arrayPurposeLstGIP = jsonRespObject.getJSONArray("PURPOSE_GIP");
        for (int i = 0; i < arrayPurposeLstGIP.length(); i++) {
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

            Globals.PurposeLstGIP.add(arrayPurposeLstGIP.getString(i));
        }

        // ---------------- purpose Between Accounts Transfer ------------------
        Globals.PurposeLstBA = new ArrayList<>();
        Globals.PurposeLstBA.add(Globals.Select); // MAW20180106
        JSONArray arrayPurposeBA = jsonRespObject.getJSONArray("PURPOSE_BA");
        for (int i = 0; i < arrayPurposeBA.length(); i++) {
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

            Globals.PurposeLstBA.add(arrayPurposeBA.getString(i));
        }

        // ---------------- purpose Two Days Transfer - ACH ------------------
        Globals.PurposeLstACH = new ArrayList<>();
        Globals.PurposeLstACH.add(Globals.Select); // MAW20180106
        JSONArray arrayPurposeACH = jsonRespObject.getJSONArray("PURPOSE_ACH");
        for (int i = 0; i < arrayPurposeACH.length(); i++) {
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

            Globals.PurposeLstACH.add(arrayPurposeACH.getString(i));
        }

        // ---------------- purpose  Account to Account Transfer ------------------
        Globals.PurposeLstA2A = new ArrayList<>();
        Globals.PurposeLstA2A.add(Globals.Select); // MAW20180106
        JSONArray arrayPurposeA2A = jsonRespObject.getJSONArray("PURPOSE_A2A");
        for (int i = 0; i < arrayPurposeA2A.length(); i++) {
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

            Globals.PurposeLstA2A.add(arrayPurposeA2A.getString(i));
        }

        // purpose transfer

        Globals.PurposeLstTR = new ArrayList<>();
        Globals.PurposeLstTR.add(Globals.Select); // MAW20180106
        JSONArray arrayPurposeTR = jsonRespObject.getJSONArray("PurposeListTR");
        for (int i = 0; i < arrayPurposeTR.length(); i++) {
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

            Globals.PurposeLstTR.add(arrayPurposeTR.getString(i));
        }
        // purpose and payment place



        Globals.PurposeLstAG = new ArrayList<>();
        Globals.PurposeLstAG.add(Globals.Select); // MAW20180106
        JSONArray arrayPurposeAG = jsonRespObject.getJSONArray("PurposeListAG");
        for (int i = 0; i < arrayPurposeAG.length(); i++) {
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

            Globals.PurposeLstAG.add(arrayPurposeAG.getString(i));
        }

        Globals.agentsList = new ArrayList<>();
        Globals.agentsList.add(Globals.Select); // MAW20180106
        JSONArray arrayAgents = jsonRespObject.getJSONArray("agentList");
        for (int i = 0; i < arrayAgents.length(); i++) {
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

            Globals.agentsList.add(arrayAgents.getString(i));
        }
        // HOTEL
        Globals.PurposeLstH = new ArrayList<>();
        Globals.PurposeLstH.add(Globals.Select); // MAW20180106
        JSONArray arrayPurposeH = jsonRespObject.getJSONArray("PurposeListH");
        for (int i = 0; i < arrayPurposeH.length(); i++) {
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

            Globals.PurposeLstH.add(arrayPurposeH.getString(i));
        }

        Globals.hotelList = new ArrayList<>();
        Globals.hotelList.add(Globals.Select); // MAW20180106
        JSONArray arrayHotels = jsonRespObject.getJSONArray("hotelList");
        for (int i = 0; i < arrayHotels.length(); i++) {
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

            Globals.hotelList.add(arrayHotels.getString(i));
        }

        // UNIVERSITY
        Globals.PurposeLstU = new ArrayList<>();
        Globals.PurposeLstU.add(Globals.Select); // MAW20180106
        JSONArray arrayPurposeU = jsonRespObject.getJSONArray("PurposeListU");
        for (int i = 0; i < arrayPurposeU.length(); i++) {
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

            Globals.PurposeLstU.add(arrayPurposeU.getString(i));
        }

        Globals.universityList = new ArrayList<>();
        Globals.universityList.add(Globals.Select); // MAW20180106
        JSONArray arrayUniversity = jsonRespObject.getJSONArray("universityList");
        for (int i = 0; i < arrayUniversity.length(); i++) {
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

            Globals.universityList.add(arrayUniversity.getString(i));
        }

        // SCHOOLS

        Globals.schoolsList = new ArrayList<>();
        Globals.schoolsList.add(Globals.Select); // MAW20180106
        JSONArray arraySchool = jsonRespObject.getJSONArray("schoolList");
        for (int i = 0; i < arraySchool.length(); i++) {
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

            Globals.schoolsList.add(arraySchool.getString(i));
        }

        // CHURCH

        Globals.PurposeLstC = new ArrayList<>();
        Globals.PurposeLstC.add(Globals.Select); // MAW20180106
        JSONArray arrayPurposeC = jsonRespObject.getJSONArray("PurposeListC");
        for (int i = 0; i < arrayPurposeC.length(); i++) {
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

            Globals.PurposeLstC.add(arrayPurposeC.getString(i));
        }

        Globals.churchList = new ArrayList<>();
        Globals.churchList.add(Globals.Select); // MAW20180106
        JSONArray arrayChurch = jsonRespObject.getJSONArray("churchList");
        for (int i = 0; i < arrayChurch.length(); i++) {
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106

            Globals.churchList.add(arrayChurch.getString(i));
        }


        // MM purposes : mobile money
        Globals.PurposeMM = new ArrayList<>();
        Globals.PurposeMM.add(Globals.Select); // MAW20180106
        JSONArray arrayPurposeMM = jsonRespObject.getJSONArray("PurposeListMM");
        for (int i = 0; i < arrayPurposeMM.length(); i++) {
            Globals.PurposeMM.add(arrayPurposeMM.getString(i));
        }

        Log.e( "login: PurposeListMM : ", ""+ Globals.PurposeMM );

        // QR purposes // Qr payements
        Globals.PurposeQR = new ArrayList<>();
        Globals.PurposeQR.add(Globals.Select); // MAW20180106
        JSONArray arrayPurposeQr = jsonRespObject.getJSONArray("PURPOSE_QR_PAYMENT");
        for (int i = 0; i < arrayPurposeQr.length(); i++) {
            Globals.PurposeQR.add(arrayPurposeQr.getString(i));
        }

        Log.e( "login: PurposeListQR : ", ""+ Globals.PurposeQR );


        //PurposeListAD : Airtime & data

        Globals.PurposeAD = new ArrayList<>();
        Globals.PurposeAD.add(Globals.Select); // MAW20180106
        JSONArray arrayPurposeAD = jsonRespObject.getJSONArray("PurposeListAD");
        for (int i = 0; i < arrayPurposeAD.length(); i++) {
            Globals.PurposeAD.add(arrayPurposeAD.getString(i));
        }

        Log.e( "login: PurposeListAD : ", ""+ Globals.PurposeAD );

        //PurposeListVD : vodafone

        Globals.PurposeVODAFONE = new ArrayList<>();
        Globals.PurposeVODAFONE.add(Globals.Select); // MAW20180106
        JSONArray PurposeVODAFONE = jsonRespObject.getJSONArray("PurposeListVD");
        for (int i = 0; i < PurposeVODAFONE.length(); i++) {
            Globals.PurposeVODAFONE.add(PurposeVODAFONE.getString(i));
        }

        Log.e( "login: PurposeListAD : ", ""+ Globals.PurposeVODAFONE );



        //PurposeListVD : vodafone

        Globals.PurposeDstv = new ArrayList<>();
        Globals.PurposeDstv.add(Globals.Select); // MAW20180106
        JSONArray Purposeds = jsonRespObject.getJSONArray("PurposeListDS");
        for (int i = 0; i < Purposeds.length(); i++) {
            Globals.PurposeDstv.add(Purposeds.getString(i));
        }

        Log.e( "login: PurposeListAD : ", ""+ Globals.PurposeDstv );



        // beneficiaries list

        JSONArray array = jsonRespObject.getJSONArray("Beneflist");
        if (!array.equals(null)) {
            Globals.BeneficyList.clear();
            // Globals.BeneficyList.add(new BeneficiaryEntry("Select","Select","Select","Select","Select","Select","Select"));


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

        // branches names
        Globals.branchOn = new ArrayList<String>();
        Globals.branchOn.add(Globals.Select);
        JSONArray array1Branches = jsonRespObject.getJSONArray("BranchList");
        for (int i = 0; i < array1Branches.length(); i++) {
            Globals.branchOn.add(array1Branches.get(i).toString());
        }
        if(jsonRespObject.has("CC")){
            Globals.CC = jsonRespObject.getString("CC");
        }
        Log.d("USER", "id["+Globals.statusBiom+"]");

        Globals.Ads = new ArrayList<>();


        if(jsonRespObject.has("adsList")) {

            JSONArray ads = jsonRespObject.getJSONArray("adsList");
            for (int i = 0; i < ads.length(); i++) {
                JSONObject ad = ads.getJSONObject(i);
                Bitmap bitmap = StringToBitMap2(ad.getString("image"));
                Log.e("login: ", " bitmap --> " + bitmap);
                //byte[] bytes = null;
                //bytes = Base64.decode(ad.getString("image"), Base64.DEFAULT);
             //   AdsEntry newAd = new AdsEntry(ad.getString("id"), bitmap);
                //  04072022
                AdsEntry newAd = new AdsEntry(ad.getString("id"), bitmap, ad.getString("link"));
                Globals.Ads.add(newAd);
            }
            Log.d("ads list", "  ++ " + Globals.Ads);

        }


        //**************** Notification List ********************
        JSONArray arrayNotifications = jsonRespObject.getJSONArray("notificationsList");
        Globals.notificationsList.clear();
        for (int i = 0; i < arrayNotifications.length(); i++) {
            NotificationEntry notificationEntry =  new NotificationEntry(
                    arrayNotifications.getJSONObject(i).getString("id"),
                    arrayNotifications.getJSONObject(i).getString("title"),
                    arrayNotifications.getJSONObject(i).getString("body"),
                    arrayNotifications.getJSONObject(i).getString("operation_date")
            );
            Globals.notificationsList.add(
                    notificationEntry
            );
            Log.d("notifications INSIDE IF", ""+arrayNotifications.get(i));
            Log.d("notifications ::::: ", ""+notificationEntry.toString());

        }

        //**************** Get Notification List ********************
  /*      JSONArray arrayAllNotifications = jsonRespObject.getJSONArray("getAllNotifications");
        Globals.getAllNotificationList.clear();
        for (int i = 0; i < arrayAllNotifications.length(); i++) {
            NotificationEntry notificationEntry =  new NotificationEntry(
                    arrayAllNotifications.getJSONObject(i).getString("id"),
                    arrayAllNotifications.getJSONObject(i).getString("title"),
                    arrayAllNotifications.getJSONObject(i).getString("body"),
                    arrayAllNotifications.getJSONObject(i).getString("operation_date")
            );
            Globals.getAllNotificationList.add(
                    notificationEntry
            );
            Log.d("notifications INSIDE IF", ""+arrayAllNotifications.get(i));
            Log.d("notifications ::::: ", ""+arrayAllNotifications.toString());

        }*/
        /**

         //**************** limitedOperations List ********************
         Globals.limitedOperationsAccounts.clear();
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
         Log.e("login: "," limitedOperationsAccounts --> "+ Globals.limitedOperationsAccounts);
         }
         Globals.limitedOperations.addAll(Globals.limitedOperationsAccounts.keySet());

         **/
        //*********************** Profile Image ****************************
        if(jsonRespObject.has("profileImage")) {
            String userImage = jsonRespObject.getString("profileImage");
            Log.e("login: "," userImage --> "+ userImage);
            Bitmap bitmap = StringToBitMap2(userImage);
            Log.e("login: "," bitmap --> "+ bitmap);
            Globals.profileImage = StringToBitMap2(userImage);
            Log.e("login: "," Globals.profileImage --> "+ Globals.profileImage);

        }

        //*********************** Must used operations **************************

        //*********************** Must used operations **************************

        JSONArray operationsList = jsonRespObject.getJSONArray("MostUsedServices");
        Globals.MustUsedOperations.clear();
        List<String> mustUserdOperations = new ArrayList<>();

        for (int i = 0; i < operationsList.length(); i++) {
            if (operationsList.getJSONObject(i).getString("service").equals("Proxy")) {
                mustUserdOperations.add(operationsList.getJSONObject(i).getString("service"));
            } else {
                mustUserdOperations.add(operationsList.getJSONObject(i).getString("operation_type"));
            }
        }
        Globals.MustUsedOperations.addAll(mustUserdOperations);
        Log.e("login: ", " MustUsedOperations --> " + Globals.MustUsedOperations);

        //hajer 13/06/2022 start
        Globals.momoProvidersList = new ArrayList<MomoProviderEntry>();
        JSONArray arrayMomoProviders = jsonRespObject.getJSONArray("MomoProviders");
        for(int i = 0 ; i < arrayMomoProviders.length() ; i++){
            Log.d("MOMO ",arrayMomoProviders.getJSONObject(i).getString("wording"));

            Globals.momoProvidersList.add(new MomoProviderEntry(
                    arrayMomoProviders.getJSONObject(i).getString("providerCode"),
                    arrayMomoProviders.getJSONObject(i).getString("wording"),
                    arrayMomoProviders.getJSONObject(i).getString("routingCode")));

            Log.d("MOMO GLOBAL LIST",Globals.momoProvidersList.get(i).getWording());

        }

        if(Globals.momoProvidersList!=null) {
            Collections.sort(Globals.momoProvidersList, new Comparator<MomoProviderEntry>() {
                public int compare(MomoProviderEntry c1, MomoProviderEntry c2) {
                    return c1.getWording().compareTo(c2.getWording());
                }
            });
            Globals.momoProvidersList.add(0, new MomoProviderEntry(Globals.Select,Globals.Select,Globals.Select));
        }
        Log.e("","MOMO GLOBAL LIST after tri"+ Globals.momoProvidersList);

        //hajer 13/06/2022 end


        //   26092022
        Globals.pickUpBy = new ArrayList<>();
        Globals.pickUpBy.add(Globals.Select +" Pick Up By"); // MAW20180106
        JSONArray pickUpByJA = jsonRespObject.getJSONArray("pickUpBy");
        for (int i = 0; i < pickUpByJA.length(); i++) {
            // if(i==0) Globals.accountsList.add(Globals.Select); // MAW20180106
            Globals.pickUpBy.add(pickUpByJA.getString(i));
        }



        /*JSONArray limitedOperations = jsonRespObject.getJSONArray("limitedOperations");
        Globals.limitedOperations.clear();
        for (int i = 0; i < limitedOperations.length(); i++) {
            Globals.limitedOperations.add(
                    limitedOperations.getString(i)
            );
        }*/


        /* ads

        Globals.Ads = new ArrayList<>();
        JSONArray ads = jsonRespObject.getJSONArray("adsList");
        for (int i = 0; i < ads.length(); i++) {
            JSONObject ad = ads.getJSONObject(i);
            byte[] bytes = null;
            bytes = Base64.decode(ad.getString("image"), Base64.DEFAULT);
            AdsEntry newAd = new AdsEntry(ad.getString("id"), bytes);
            Globals.Ads.add(newAd);
        }
        Log.d("ads list", "  ++ "+ Globals.Ads.get(0).getImage().toString());


        */

        if (jsonRespObject.getString("respCode").equals("802"))
            throw new Exception("REFRESH AUTHENTICATION <RespCode=[" + jsonRespObject.getString("respCode") + "]>");

        return;
    }

    //hajer 19/03/2021
    static public void setFingerPrint(String operation,String code) throws Exception {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId); jsonObject.put("authenCode", Globals.authenCode);
        jsonObject.put("otp", Globals.pinEntered);
        jsonObject.put("operation", operation); // MAW20190918
        jsonObject.put("code", code); // MAW20190918

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceSetfingerPrint, jsonObject);

        if (jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("OPERATION REJECTED <RespCode=[" + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "]>");

        Globals.sessionId = jsonRespObject.getString("sessionId");
        Globals.statusBiom =jsonRespObject.getString("statusBiom");

        return;

    }

    public static void SendImageToServer(String ProfileImage)  throws Exception {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        jsonObject.put("password", Globals.password);
        jsonObject.put("sessionId", Globals.sessionId);
        jsonObject.put("ProfileImage", ProfileImage);
        Log.e("SendImageToServer: ", "+ "+ProfileImage);
        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.UploadProfileImage, jsonObject);

        if (jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("OPERATION REJECTED <RespCode=[" + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "]>");


        return;
    }
/*
   public static void Logout()  throws Exception {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);
        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.doLogOut, jsonObject);

        if (jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("OPERATION REJECTED <RespCode=[" + (jsonRespObject.has("respCode") ? jsonRespObject.getString("respCode") : "") + "]>");


        return;
    }

*/
     //   13062022
    public static void Logout() throws Exception{

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", Globals.user);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.doLogOut, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("OPERATION FAILED WITH CODE: "+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):""));


    }

    public static Bitmap decodeToBase64(String input) {
        byte[] decodedByte = Base64.decode(input, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public static Bitmap decodeFile(String encod){
        Bitmap b = null;
        byte[] temp=null;
        temp = Base64.decode(encod, Base64.DEFAULT);
        ByteArrayInputStream imageStream = new ByteArrayInputStream(
                temp);
        //Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;

        BitmapFactory.decodeStream(imageStream, null, o);
        int scale = 1;
        if (o.outHeight > 500 || o.outWidth > 500) {
            scale = (int)Math.pow(2, (int) Math.ceil(Math.log(500 /
                    (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
        }
        BitmapFactory.Options o1 = new BitmapFactory.Options();
        o1.inSampleSize = scale;
        imageStream.reset();
        b = BitmapFactory.decodeStream(imageStream, null, o1);
        return b;
    }


    /**
     * @param encodedString
     * @return bitmap (from given string)
     */
    public static Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            Log.e( "StringToBitMap: ", ""+bitmap);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static Bitmap StringToBitMap2(String image){
        try{
            byte [] encodeByte=Base64.decode(image,Base64.DEFAULT);
            Log.e("StringToBitMap2: "," encodeByte --> "+ encodeByte);

            // InputStream inputStream  = new ByteArrayInputStream(encodeByte);
            // Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            Log.e("StringToBitMap2: "," bitmap --> "+ bitmap);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);


            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }
}
