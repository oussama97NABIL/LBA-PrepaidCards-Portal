package com.LBA;

import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.LBA.prepaidPortal.activity.AbstractActivity;
import com.LBA.prepaidPortal.activity.Z_WelcomeActivity;
import com.LBA.tools.assets.Globals;
import com.LBA.tools.services.Account;
import com.LBA.tools.services.User;
import com.google.android.material.button.MaterialButton;
import com.LBA.prepaidPortal.R;
import com.LBA.prepaidPortal.activity.HomeActivity;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AbstractActivity  {
    String userCode;
    String password;
    EditText username;
    EditText pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.loginbtn);

         username = (EditText) findViewById(R.id.username);
         pass = (EditText) findViewById(R.id.password);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "User is Mandatory", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pass.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Password is Mandatory", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    initProgrees();
                    userCode = username.getText().toString();
                    password = pass.getText().toString();
                   /* if((userCode.equals("hajer")) && password.equals("hajer")) {
                        Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                        startActivity(intent);
                    }*/
                    CustomTask2 task = new CustomTask2();
                    task.execute();
                    /**/
                } catch (Exception e) {
                    Log.d("btn", "btnSignIn.setOnClickListener()", e);
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private class CustomTask extends AsyncTask<String, String, String> {

        Boolean finger;
        boolean alternate = false;
        String type;

        /*CustomTask(){
            this.finger = MainActivity.this.finger;
        }*/

        protected String doInBackground(String... param) {
            try {


                // User.loginTest(userCode, password, Globals.authenCode);
                System.out.println("inside custom task");
                // Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                Globals.user = userCode;
                Globals.password = password;
                //Log.d(TAG, "1. CustomTask()" + "> ACCOUNT FOUND authToken["+Globals.authenCode+"]");

                //omar
                //oussama 2/15/2032
                //User.login(userCode, password, Globals.authenCode,1);
                //User.GetToken();

                Intent login = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(login);
                Intent destinationAct;

                //hajer 07/06/2021

                //destinationAct = new Intent(MainActivity.this, AuthenActivity.class);
                destinationAct = new Intent(MainActivity.this, HomeActivity.class);

                startActivity(destinationAct);
                finish();
                return null;

            } catch (Exception e) {
                if(e.getMessage()!=null && e.getMessage().contains("802")){
                    Globals.user = userCode;
                    Globals.password = password;
                    //  gibSdk.setAttribute("user_id",userCode);
                    try {
                        /*Globals.gibSdk.setLogin(userCode);
                        Globals.gibSdk.setSessionId(Globals.sessionId);*/
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    finish();
                    return null;
                }
                else if(e.getMessage()!=null && e.getMessage().contains("803")){ // Force Password change
                    Globals.user = userCode;
                    Globals.password = password;
                    Globals.authenCode = "";
                    /*Intent myPasswordChange = new Intent(MainActivity.this, PasswordChangeActivity.class);
                    startActivity(myPasswordChange);*/
                    return null;
                }
                e.printStackTrace();
                return e.getMessage();
            }
        }
    }

    private class CustomTask2 extends AsyncTask<String, String, String>  {

        Boolean finger;
        boolean alternate = false;
        String type;



        protected String doInBackground(String... param) {
            try {
                System.out.println("inside custom task 2");;
                Globals.user = userCode;
                Globals.password = password;
                //oussama 2/15/2023
                User.login(userCode, password, Globals.authenCode,1);
            } catch (Exception e) {
                dismissProgress();
                e.printStackTrace();
                return e.getMessage();
            }

            return null;
        }

        protected void onPostExecute(String param) {
            super.onPostExecute(param);
            dismissProgress();
            if (param == null) {
                Log.d("testLog","this is param " + param);
                Intent destinationAct;
                destinationAct = new Intent(MainActivity.this, HomeActivity.class);

                startActivity(destinationAct);
                finish();

                return;
        }else {
                Toast.makeText(MainActivity.this, "invalid login Credentials", Toast.LENGTH_SHORT).show();
            }
    }

}}