package com.LBA.prepaidPortal.widgets.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.LBA.prepaidPortal.R;
import com.LBA.prepaidPortal.activity.ConfirmResetPinActivity;
import com.LBA.prepaidPortal.activity.HomeActivity;
import com.LBA.tools.assets.Globals;
import com.LBA.tools.misc.MySpinnerAdapter;
import com.LBA.tools.services.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class NewPin extends BaseFragment implements View.OnClickListener {
    Intent in;
    Class<?> clazz;
    Class activity= NewPin.class;
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
    View mRootView;
    ImageButton back;
    ImageButton homeBtn;
    RelativeLayout header;

    private SimpleDateFormat dateFormatter;
    private DatePickerDialog DOBDatePickerDialog;
    String DefaultUnameValue = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.newui_activity_resetpin, container, false);
        homeBtn = (ImageButton) mRootView.findViewById(R.id.home_button);

        //btnOk = (Button) findViewById(R.id.btnOk);
       /* textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView4);
        textView3 = (TextView) findViewById(R.id.textView7);
        textView4 = (TextView) findViewById(R.id.textView2);
        textView5 = (TextView) findViewById(R.id.textView8);
        textView6 = (TextView) findViewById(R.id.textView10);*/

        RelativeLayout yourBackgroundView = (RelativeLayout) mRootView.findViewById(R.id.root);

            /*SharedPreferences settings = getSharedPreferences("appBack",
                    Context.MODE_PRIVATE);*/

        /*String imageS = settings.getString("background", DefaultUnameValue);
        Log.d("Retr image from device", imageS);
        Bitmap imageB;
        if (!imageS.equals("")) {
            imageB = decodeToBase64(imageS);
            Drawable d = new BitmapDrawable(getResources(), imageB);

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                yourBackgroundView.setBackgroundDrawable(d);
            } else {
                yourBackgroundView.setBackground(d);
            }
        } else {
            //keep it black
        }*/

        /*SharedPreferences settings = getSharedPreferences("appBack",
                Context.MODE_PRIVATE);*/

        /*String imageS  = settings.getString("background", DefaultUnameValue);
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
        }*/



        // edtUser=(EditText) findViewById(R.id.user);
        edtAcc=(EditText) mRootView.findViewById(R.id.accountNbr);
        nxtBtn = (Button) mRootView.findViewById(R.id.imageButton23);
        canlBtn = (ImageButton) mRootView.findViewById(R.id.imageButton24);
        homeBtn = (ImageButton) mRootView.findViewById(R.id.home_button);

        /*header = (RelativeLayout) mRootView.findViewById(R.id.header_layout);
        if(!Globals.user.isEmpty()){
            Log.e("ResetPin sessionId : ",Globals.user);
            header.setVisibility(View.VISIBLE);
        }*/
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        findViewsById();
        setDateTimeField();

        canlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initProgrees();
                HomeTask task = new HomeTask(Globals.activity);
                task.execute();
            }
        });
        back = (ImageButton) mRootView.findViewById(R.id.Back);



       // Log.d("text", ""+textVieww.getText().toString());

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
                        androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(getActivity().getApplicationContext()).create();
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
                        androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(getActivity().getApplicationContext()).create();
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
                        androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(getActivity().getApplicationContext()).create();
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
                    if(!Globals.branchOn.contains(textVieww.getText().toString())) {
                        androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(getActivity().getApplicationContext()).create();
                        alertDialog.setMessage("SELECT A VALID BRANCH");
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
                    NewPin.CustomTask task = new NewPin.CustomTask(getActivity().getApplicationContext());
                    task.execute();

                } catch (Exception e) {
                    e.printStackTrace();
                    //  Toast.makeText(ResetPinActivity.this,"Error call requestForResetPassword : "+e.getMessage(),Toast.LENGTH_LONG).show();
                    androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(getActivity().getApplicationContext()).create();
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
        /*SharedPreferences settings = getSharedPreferences("appBack",
                Context.MODE_PRIVATE);*/

        /*String imageS  = settings.getString("background", DefaultUnameValue);
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
        }*/



        // edtUser=(EditText) findViewById(R.id.user);
        edtAcc=(EditText) mRootView.findViewById(R.id.accountNbr);
        nxtBtn = (Button) mRootView.findViewById(R.id.imageButton23);
        canlBtn = (ImageButton) mRootView.findViewById(R.id.imageButton24);
        homeBtn = (ImageButton) mRootView.findViewById(R.id.home_button);

        /*header = (RelativeLayout) mRootView.findViewById(R.id.header_layout);
        if(!Globals.user.isEmpty()){
            Log.e("ResetPin sessionId : ",Globals.user);
            header.setVisibility(View.VISIBLE);
        }*/
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        findViewsById();
        setDateTimeField();

        canlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initProgrees();
                HomeTask task = new HomeTask(Globals.activity);
                task.execute();
            }
        });
        back = (ImageButton) mRootView.findViewById(R.id.Back);


        MySpinnerAdapter spinner3ArrayAdapter = new MySpinnerAdapter(getActivity().getApplicationContext(), R.layout.spinner, Globals.branchOn);
        spinner3ArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        textVieww = (AutoCompleteTextView) mRootView.findViewById(R.id.txtViewNames);
        //textVieww.setThreshold(2);
        textVieww.setAdapter(spinner3ArrayAdapter);
        //textVieww.setOnItemSelectedListener(this);
        textVieww.setTextColor(getResources().getColor(R.color.black));
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
                        androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(getActivity().getApplicationContext()).create();
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
                        androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(getActivity().getApplicationContext()).create();
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
                        androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(getActivity().getApplicationContext()).create();
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
                    if(!Globals.branchOn.contains(textVieww.getText().toString())) {
                        androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(getActivity().getApplicationContext()).create();
                        alertDialog.setMessage("SELECT A VALID BRANCH");
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

                    /*initProgrees();
                    NewPin.CustomTask task = new NewPin.CustomTask();
                    task.execute();*/

                } catch (Exception e) {
                    e.printStackTrace();
                    //  Toast.makeText(ResetPinActivity.this,"Error call requestForResetPassword : "+e.getMessage(),Toast.LENGTH_LONG).show();
                    androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(getActivity().getApplicationContext()).create();
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
     return mRootView;

    }
    @Override
    public void onClick(View view) {
        final int id = 0;
        Log.e("TAG", "onClick: 111111111111111111111111111111111111111111111111");
        if (view.getId() == R.id.spincard) {

            Log.e("TAG", "onClick: 222222222222222222222222222222222222222");
            initProgrees();
            CustomTask task = new CustomTask(getActivity().getApplicationContext());
            task.execute();

        }
    }

   /* private void loadActivatedCard() {

        try {
            operation = operationGetList;
            initProgrees();
            new CardInformation1().CustomTask().execute();
            spincard.setOnItemSelectedListener(this);

        } catch (Exception e) {
            // Log.d(TAG, "btnLoad.setOnClickListener()", e);
            // Toast.makeText(CardLimitActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

            androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(CardInformation1.this).create();
            alertDialog.setMessage(e.getMessage());
            alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });
            alertDialog.show();
        }
    }*/


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_account_balance, menu);
        return true;
    }*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }







    /*public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long id) {
        if (parent instanceof Spinner && pos > 0) {
            switch (parent.getId()) {
                case R.id.spincard:
                    this.selectedCard = Globals.maskedCardsList.get(pos);
                    break;

            }
        }
    }*/
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    /////////////////// VAL + PIN

    ProgressDialog pDialog;

    public void initProgrees() {
        // pDialog = new ProgressDialog(AbstractActivity.this);
        pDialog = new ProgressDialog(new ContextThemeWrapper(getActivity(), R.style.CustomFontDialog));
        pDialog.setMessage(getResources().getString(R.string.pleaseWait));
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setIndeterminate(true);
        pDialog.setOnShowListener(new DialogInterface.OnShowListener() {


            @Override
            public void onShow(DialogInterface dialog) {
                // TODO Auto-generated method stub

                final int idAlertTitle = getActivity().getApplicationContext().getResources().getIdentifier("alertTitle", "id", "android");
                TextView textDialog = (TextView) ((AlertDialog) dialog).findViewById(idAlertTitle);

                textDialog.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/gilroy_bold.ttf"));
            }
        });

        pDialog.setCancelable(false);

        pDialog.setCanceledOnTouchOutside(false);
        delay=0;
        pDialog.show();
        getProgress();
    }

    public void dismissProgress() {
        if (pDialog != null && pDialog.isShowing())
            pDialog.dismiss();
    }


    private class CustomTask extends AsyncTask<String, String, String> {
        public CustomTask(Context applicationContext) {
            super();

        }

        protected String doInBackground(String... param) {
            try {

                User.requestForResetPin(user,account,branch,dob);

                Intent intentAuthentificationPasswordActivity=new Intent(getActivity().getApplicationContext(), ConfirmResetPinActivity.class);
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
                androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(getActivity().getApplicationContext()).create();
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
                Intent myAccountServicesAct = new Intent(getActivity().getApplicationContext(), activity);
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
                androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(getActivity().getApplicationContext()).create();
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
        edtDob = (EditText) mRootView.findViewById(R.id.dob);
        edtDob.setInputType(InputType.TYPE_NULL);
        edtDob.requestFocus();

    }
    private void setDateTimeField() {
        edtDob.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        DOBDatePickerDialog = new DatePickerDialog(getActivity().getApplicationContext(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                edtDob.setText(dateFormatter.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    public static Bitmap decodeToBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}