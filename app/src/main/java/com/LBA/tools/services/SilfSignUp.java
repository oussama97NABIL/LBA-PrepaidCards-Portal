package com.LBA.tools.services;

import com.LBA.tools.assets.Globals;
import com.LBA.tools.connection.HTTPClient;

import org.json.JSONObject;



public class SilfSignUp {


    static public String GetClientData(String accountNumber) throws Exception{

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("accountNumber", accountNumber);

        JSONObject jsonRespObject = HTTPClient.sendPostJSON(Globals.serviceSelfSignUp, jsonObject);

        if(jsonRespObject.has("respCode") && !jsonRespObject.getString("respCode").equals("000"))
            throw new Exception("GET CLIENT INFORMATION FAILED <RespCode=["+(jsonRespObject.has("respCode")?jsonRespObject.getString("respCode"):"")+"]>");

        String custInfo = jsonRespObject.getString("custInfo");


        return custInfo;
    }
}
