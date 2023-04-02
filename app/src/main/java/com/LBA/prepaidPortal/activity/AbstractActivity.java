package com.LBA.prepaidPortal.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.LBA.prepaidPortal.R;
import com.LBA.tools.assets.Globals;
import com.LBA.tools.services.User;



/**
 * Created by User on 11/11/2015.
 */
public abstract class AbstractActivity extends AppCompatActivity {
    ProgressDialog pDialog;
    //start fatim 08042022
    protected  int delay=0;
    protected ApptimeoutManager apptimeoutManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        Log.i("tag", "call oncreate");
        super.onCreate(savedInstanceState);
        apptimeoutManager = new ApptimeoutManager();
        apptimeoutManager.setAbstractActivity(this);
        apptimeoutManager.resetSession();

    }
    //end fatim

    public void initProgrees() {
        // pDialog = new ProgressDialog(AbstractActivity.this);
        pDialog = new ProgressDialog(new ContextThemeWrapper(AbstractActivity.this, R.style.CustomFontDialog));
        pDialog.setMessage(getResources().getString(R.string.pleaseWait));
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setIndeterminate(true);
        pDialog.setOnShowListener(new DialogInterface.OnShowListener() {


            @Override
            public void onShow(DialogInterface dialog) {
                // TODO Auto-generated method stub

                final int idAlertTitle = getApplicationContext().getResources().getIdentifier("alertTitle", "id", "android");
                TextView textDialog = (TextView) ((AlertDialog) dialog).findViewById(idAlertTitle);

                textDialog.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/gilroy_bold.ttf"));
            }
        });

        pDialog.setCancelable(false);

        pDialog.setCanceledOnTouchOutside(false);
        delay=0;
        pDialog.show();
        getProgress();
    }

    public void dismissProgress() {
        if (pDialog != null && pDialog.isShowing()){
            pDialog.dismiss();}
        if(Globals.timeout != null)
            delay= Integer.parseInt(Globals.timeout);


    }
/*
    public void doLogoutConfirmation(){
        Globals.user = "";
        Globals.password ="";

        Intent intent = new Intent(getApplicationContext(), NewMainPage.class);
        //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
*/
    public void doLogout(View v){
        Globals.user = "";
        Globals.password ="";
        // start fatim 08042022
        delay = 0;
        Globals.timeout = "0";
        //end fatim
        Intent intent = new Intent(getApplicationContext(), NewMainPage.class);
        //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);


    }


    public void doLogout2(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(AbstractActivity.this);
        builder.setMessage("Are you sure you want to logout ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        doLogoutConfirmation();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }





// start fatim 08042022
    @Override
    public synchronized void onUserInteraction() {
    super.onUserInteraction();
    apptimeoutManager.resetSession();
    Log.i("TAG", "User interacting with screen");


}
    public synchronized void getProgress(){
        apptimeoutManager.progreesDialog();
    }
// end fatim

   //   13062022
    public void doLogoutConfirmation(){

      try {
            User.Logout();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Globals.password ="";
        Globals.user = "";

        Intent intent = new Intent(getApplicationContext(), NewMainPage.class);
        //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void get(){


    }
    @Override
    public void onBackPressed () {

    }

    protected void onActivityCreated(Bundle savedInstanceState) {
    }
}