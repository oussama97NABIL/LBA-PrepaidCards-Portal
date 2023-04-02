package com.LBA.prepaidPortal.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.LBA.prepaidPortal.R;
import com.LBA.tools.assets.Globals;
import com.LBA.tools.misc.MySpinnerAdapter;
import com.LBA.tools.services.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;



public class ResetPinActivity extends AbstractActivity implements View.OnClickListener {

    Intent in;
    Class<?> clazz;
    Class activity= Z_WelcomeActivity.class;
    private String user;
    private String account;
    private String branch;
    private String dob;
    //Button  btnRequestResetPassword;
    EditText edtAcc;
    EditText edtDob;
    AutoCompleteTextView textVieww;
    Button nxtBtn;
    ImageButton canlBtn;
    ImageButton back;
    ImageButton homeBtn;
    RelativeLayout header;

    private SimpleDateFormat dateFormatter;
    private DatePickerDialog DOBDatePickerDialog;
    String DefaultUnameValue = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newui_activity_resetpin);

        RelativeLayout yourBackgroundView = (RelativeLayout) findViewById(R.id.root);

        SharedPreferences settings = getSharedPreferences("appBack",
                Context.MODE_PRIVATE);

        String imageS  = settings.getString("background", DefaultUnameValue);
        Log.d("Retr image from device", imageS);
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



        // edtUser=(EditText) findViewById(R.id.user);
        edtAcc=(EditText) findViewById(R.id.accountNbr);
        nxtBtn = (Button) findViewById(R.id.imageButton23);
        canlBtn = (ImageButton) findViewById(R.id.imageButton24);
        homeBtn = (ImageButton) findViewById(R.id.home_button);

        header = (RelativeLayout) findViewById(R.id.header_layout);
        if(!Globals.user.isEmpty()){
           Log.e("ResetPin sessionId : ",Globals.user);
            header.setVisibility(View.VISIBLE);
        }
                dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        findViewsById();
        setDateTimeField();
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initProgrees();
                HomeTask task = new HomeTask(HomeActivity.class);
                task.execute();
            }
        });
        canlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initProgrees();
                HomeTask task = new HomeTask(Globals.activity);
                task.execute();
            }
        });
        back = (ImageButton) findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initProgrees();
                HomeTask task = new HomeTask(HomeActivity.class);
                task.execute();
            }
        });

        MySpinnerAdapter spinner3ArrayAdapter = new MySpinnerAdapter(this, R.layout.spinner, Globals.branchOn);
        spinner3ArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        textVieww = (AutoCompleteTextView) findViewById(R.id.txtViewNames);
        //textVieww.setThreshold(2);
        textVieww.setAdapter(spinner3ArrayAdapter);
        //textVieww.setOnItemSelectedListener(this);
        textVieww.setTextColor(getResources().getColor(R.color.white));
        textVieww.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                textVieww.showDropDown();
                textVieww.setDropDownHeight(502);
            }
        });
        Log.d("text", ""+textVieww.getText().toString());

        /*try {
            if(in.hasExtra("class")) {
                String tv1 = in.getExtras().getString("class");
                if(tv1.equals("Settings"))
                    activity = SettingsActivity.class;

                canlBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent myAccountServicesAct = new Intent(ResetPinActivity.this, activity);
                        startActivity(myAccountServicesAct);
                    }
                });
            }else{

            }
        }catch (Exception e){
            e.printStackTrace();
        }*/
        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {




                    user = Globals.username;
                    if (edtAcc.getText().toString().length() == 13) {
                        account = edtAcc.getText().toString();
                    }
                    else{
                       // Toast.makeText(ResetPinActivity.this, "ACCOUNT NUMBER" + " MUST BE 13 DIGITS", Toast.LENGTH_LONG).show();
                        androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(ResetPinActivity.this).create();
                        alertDialog.setMessage("ACCOUNT NUMBER" + " MUST BE 13 DIGITS");
                        alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                        return;
                    }
                    if (edtDob.getText().toString().isEmpty()) {
                     //   Toast.makeText(ResetPinActivity.this, "DATE OF BIRTH IS MANDATORY", Toast.LENGTH_LONG).show();
                        androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(ResetPinActivity.this).create();
                        alertDialog.setMessage("DATE OF BIRTH IS MANDATORY");
                        alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                        return;
                    }
                    if (textVieww.getText().toString().isEmpty()) {
                      //  Toast.makeText(ResetPinActivity.this, "BRANCH IS MANDATORY", Toast.LENGTH_LONG).show();
                        androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(ResetPinActivity.this).create();
                        alertDialog.setMessage("BRANCH IS MANDATORY");
                        alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                        return;
                    }
                    branch = textVieww.getText().toString().trim();
                    dob = edtDob.getText().toString();

                    initProgrees();
                    CustomTask task = new CustomTask();
                    task.execute();

                } catch (Exception e) {
                    e.printStackTrace();
                  //  Toast.makeText(ResetPinActivity.this,"Error call requestForResetPassword : "+e.getMessage(),Toast.LENGTH_LONG).show();
                    androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(ResetPinActivity.this).create();
                    alertDialog.setMessage("Error call requestForResetPassword : "+e.getMessage());
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

    ProgressDialog pDialog;

    public void initProgrees() {
        // pDialog = new ProgressDialog(AbstractActivity.this);
        pDialog = new ProgressDialog(new ContextThemeWrapper(ResetPinActivity.this, R.style.CustomFontDialog));
        pDialog.setMessage(getResources().getString(R.string.pleaseWait));
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setIndeterminate(true);
        pDialog.setOnShowListener(new DialogInterface.OnShowListener() {


            @Override
            public void onShow(DialogInterface dialog) {
                // TODO Auto-generated method stub

                final int idAlertTitle = getApplicationContext().getResources().getIdentifier("alertTitle", "id", "android");
                TextView textDialog = (TextView) ((AlertDialog) dialog).findViewById(idAlertTitle);

                textDialog.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf"));
            }
        });

        pDialog.setCancelable(false);

        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
    }

    public void dismissProgress() {
        if (pDialog != null && pDialog.isShowing())
            pDialog.dismiss();
    }


    private class CustomTask extends AsyncTask<String, String, String> {

        protected String doInBackground(String... param) {
            try {

                User.requestForResetPin(user,account,branch,dob);

                Intent intentAuthentificationPasswordActivity=new Intent(ResetPinActivity.this,ConfirmResetPinActivity.class);
                intentAuthentificationPasswordActivity.putExtra("activity","ResetPinActivity");
                 startActivity(intentAuthentificationPasswordActivity);

                return null;
            } catch (Exception e) {

                return "RESET FAILED <"+e.getMessage()+">";
            }
        }
        protected void onPostExecute(String param) {
            dismissProgress();
            super.onPostExecute(param);
            if(param!=null) {
             //   Toast.makeText(ResetPinActivity.this, param, Toast.LENGTH_SHORT).show();
                androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(ResetPinActivity.this).create();
                alertDialog.setMessage(param);
                alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }

        }
    }

    private class HomeTask extends AsyncTask<String, String, String> {
        Class activity;
        public HomeTask(Class pActivity) {
            super();
            activity=pActivity;
        }
        protected String doInBackground(String... param) {
            try {
                Intent myAccountServicesAct = new Intent(ResetPinActivity.this, activity);
                startActivity(myAccountServicesAct);
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }
        protected void onPostExecute(String param) {
            dismissProgress();
            super.onPostExecute(param);
            if(param!=null) {
             //   Toast.makeText(ResetPinActivity.this, param, Toast.LENGTH_SHORT).show();
                androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(ResetPinActivity.this).create();
                alertDialog.setMessage(param);
                alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        }
    }

    // handle dates
    private void findViewsById() {
        edtDob = (EditText) findViewById(R.id.dob);
        edtDob.setInputType(InputType.TYPE_NULL);
        edtDob.requestFocus();

    }
    private void setDateTimeField() {
        edtDob.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        DOBDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                edtDob.setText(dateFormatter.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }
    public void onClick(View view) {
        if (view == edtDob) {
            DOBDatePickerDialog.show();

        }
    }
    public static Bitmap decodeToBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}

