package com.LBA.prepaidPortal.activity;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static String SHARED_PREF_NAME="thecodingshef";
    private SharedPreferences sharedPreferences;
    Context context;
    private SharedPreferences.Editor editor;

    public SharedPrefManager(Context context){
        this.context=context;
    }
    public void logout(){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();;
    }
}
