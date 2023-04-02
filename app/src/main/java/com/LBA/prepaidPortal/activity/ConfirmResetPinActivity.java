package com.LBA.prepaidPortal.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.LBA.prepaidPortal.R;
import com.LBA.tools.assets.Globals;
import com.LBA.tools.services.User;



public class ConfirmResetPinActivity extends AppCompatActivity {

    Intent in;
    Class<?> clazz;
    private String pin;
    private String confirmPin;
    private Button btnResetPin;
    private EditText edtPin;
    private EditText edtConfirmPin;
    String DefaultUnameValue = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newui_activity_confirmresetpin);

        RelativeLayout yourBackgroundView = (RelativeLayout) findViewById(R.id.root);

        SharedPreferences settings = getSharedPreferences("appBack",
                Context.MODE_PRIVATE);

        String imageS  = settings.getString("background", DefaultUnameValue);
        //Log.d("Retr image from device", imageS);
        Bitmap imageB;
        if(!imageS.equals("")) {
            imageB = decodeToBase64(imageS);
            Drawable d = new BitmapDrawable(getResources(),imageB);

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
                yourBackgroundView.setBackgroundDrawable(d);
            } else {
                yourBackgroundView.setBackground(d);
            }
        }
        else{
            //keep it black
        }


        btnResetPin=(Button)findViewById(R.id.btn_confirm_reset_pin);

        btnResetPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent intent = getIntent();
                    String activity = intent.getStringExtra("activity");


                    edtPin=(EditText)findViewById(R.id.pin);
                    edtConfirmPin=(EditText)findViewById(R.id.confirm_pin);
                    pin=edtPin.getText().toString();
                    confirmPin=edtConfirmPin.getText().toString();
                    if((!pin.isEmpty() && !confirmPin.isEmpty())&& (pin.equals(confirmPin))){
                        //call of confirmResetPassword method

                        User.confirmResetPin2(pin,confirmPin,activity);
                        //Forward to MainActivity
                        Intent intentMainActivity=new Intent(ConfirmResetPinActivity.this, Globals.activity);
                      //  Toast.makeText(ConfirmResetPinActivity.this,"Success ",Toast.LENGTH_LONG).show();
                        androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(ConfirmResetPinActivity.this).create();
                        alertDialog.setMessage("Success ");
                        alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();

                                    }
                                });
                        alertDialog.show();
                        startActivity(intentMainActivity);
                    }else {
                       // Toast.makeText(ConfirmResetPinActivity.this,"Error PIN ",Toast.LENGTH_LONG).show();
                        androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(ConfirmResetPinActivity.this).create();
                        alertDialog.setMessage("Error PIN ");
                        alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();

                                    }
                                });
                        alertDialog.show();

                    }

                }catch (Exception e){
                    e.printStackTrace();
                  //  Toast.makeText(ConfirmResetPinActivity.this,"Error : call confirmResetPIN "+ e.getMessage(),Toast.LENGTH_LONG).show();
                    androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(ConfirmResetPinActivity.this).create();
                    alertDialog.setMessage("Error : call confirmResetPIN "+ e.getMessage());
                    alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            });
                    alertDialog.show();

                }

            }
        });

    }

//Zouhair
    @Override
    public void onBackPressed() {


        Intent intent = getIntent();
        String activity = intent.getStringExtra("activity");

       // Log.d("ConfirmResetPinActivity", "Activity --->"+activity);
        if (activity.equalsIgnoreCase("ResetPinActivity"))
        {
            super.onBackPressed();

        } else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmResetPinActivity.this);
            builder.setMessage("Are you sure you want to go back ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(ConfirmResetPinActivity.this , SplashScreen.class);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);



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
        }

    public static Bitmap decodeToBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}


