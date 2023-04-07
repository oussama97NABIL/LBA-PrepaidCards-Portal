package com.LBA.prepaidPortal.widgets.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.LBA.prepaidPortal.R;
import com.LBA.prepaidPortal.activity.HomeActivity;
import com.LBA.tools.assets.Globals;
import com.LBA.tools.misc.LastTransactionDetail;
import com.LBA.tools.misc.MySpinnerAdapter;
import com.LBA.tools.services.Card;
import com.LBA.tools.services.General;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class CardInformationHin extends BaseFragment implements AdapterView.OnItemSelectedListener {
    Spinner spincard;

    private EditText startDate,endDate;
    private View mRootView;

    private EditText AmountAtm,AmountPos;
    //TextView s
    Button nxtBtn;
    ImageButton canlBtn;

    private SimpleDateFormat dateFormatter;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    private int nCounter = 0;
    private int nStep = 0;
    private TextView textView_heading;
    private TextView textView;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;
    private TextView textView6;
    private EditText bankCode;
    private EditText bankName;
    private EditText clientCode;
    private EditText Branch;
    private EditText ClientType;

    static private final String TAG = CardInformationHin.class.getSimpleName();


    String selectedCard;

    ImageButton back;

    final String operationGetList="GET_LIST";
    final String operationActivateCard="ACTIVATE_CARD";
    String operation="";
    java.util.Date DateSys = new java.util.Date();
    java.util.Date DateUsrS= new java.util.Date();
    java.util.Date DateUsrE= new Date();

    String DefaultUnameValue = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.card_information_fragment1, container, false);

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

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        //findViewsById();
        setDateTimeField();

        textView_heading = (TextView) mRootView.findViewById(R.id.textView14);

        spincard = (Spinner) mRootView.findViewById(R.id.spincard);
        bankCode = (EditText) mRootView.findViewById(R.id.bankCode);
        bankName = (EditText) mRootView.findViewById(R.id.bankName);


        AmountAtm = (EditText) mRootView.findViewById(R.id.TxtAmount);
        AmountPos = (EditText) mRootView.findViewById(R.id.TxtAmount1);




        // Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        /*textView_heading.setTypeface(tf);
        AmountAtm.setTypeface(tf);
        AmountPos.setTypeface(tf);*/

        /*textView_heading.setTypeface(tf);
        textView.setTypeface(tf);
        textView2.setTypeface(tf);
        textView3.setTypeface(tf);
        textView4.setTypeface(tf);
        textView5.setTypeface(tf);
        textView6.setTypeface(tf);*/

        nxtBtn = (Button) mRootView.findViewById(R.id.imageButton23);
        canlBtn = (ImageButton) mRootView.findViewById(R.id.imageButton24);



        canlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initProgrees();
                HomeTask task = new HomeTask(HomeActivity.class);
                task.execute();
            }
        });


        MySpinnerAdapter spinnerArrayAdapter = new MySpinnerAdapter(getActivity().getApplicationContext(), R.layout.spinner_item, Globals.maskedCardsList);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spincard.setAdapter(spinnerArrayAdapter);
        spincard.setOnItemSelectedListener(this);

        //btnStopCard = (Button) findViewById(R.id.btnStopCard);
        // loadActivatedCard();

        OpenTime();


        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.e(TAG, "onClick: "+ " spincard.getSelectedItemPosition() == 0 "+spincard.getSelectedItemPosition() );

                    if (selectedCard == null || selectedCard.length() == 0 || spincard.getSelectedItemPosition() == 0) {
                        //Toast.makeText(CardLimitActivity.this, "Card" + " " + getResources().getString(R.string.isMandator), Toast.LENGTH_LONG).show();
                        Log.e(TAG, "onClick: "+ " select a card popup" );
                        AlertDialog alertDialog = new AlertDialog.Builder(getActivity().getApplicationContext()).create();
                        alertDialog.setMessage("Card" + " " + getResources().getString(R.string.isMandator));

                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                        return;
                    }

                    initProgrees();
                    CustomTask task = new CustomTask(getActivity().getApplicationContext());
                    task.execute();
                    // GoToValidation();
                } catch (Exception e) {
                    Log.d("check input", "btnLoad.setOnClickListener()", e);
                    //  Toast.makeText(CardLimitActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity().getApplicationContext()).create();
                    alertDialog.setMessage(e.getMessage());
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
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
   /* @Override
    public void onClick(View view) {
        final int id = 0;
        Log.e(TAG, "onClick: 111111111111111111111111111111111111111111111111");
        if (view.getId() == R.id.startDate) {

            Log.e(TAG, "onClick: 222222222222222222222222222222222222222");
            initProgrees();
            CustomTask task = new CustomTask(getActivity().getApplicationContext());
            task.execute();

        }
    }*/

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
                //   Toast.makeText(CardLimitActivity.this, param, Toast.LENGTH_SHORT).show();
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



    public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long id) {
        Log.e(TAG, "onItemSelected: -------------------------------------- " );
        if (parent instanceof Spinner && pos > 0) {
            switch (parent.getId()) {
                case R.id.spincard:
                    Log.e(TAG, "onItemSelected :  inside the switch -------------------------------------- " );
                    this.selectedCard = Globals.maskedCardsList.get(pos);

                    initProgrees();
                    CustomTask task = new CustomTask(getActivity().getApplicationContext());
                    task.execute();

                    break;

            }
        }
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    /////////////////// VAL + PIN

    @SuppressLint("NewApi")
    public void GoToValidation() {
        try {
            final Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            String date = dateFormatter.format(new Date());
            String dateY = dateFormatter.format(cal.getTime());
            DateSys = dateFormatter.parse(dateY);
            DateUsrS = dateFormatter.parse(startDate.getText().toString());
            DateUsrE = dateFormatter.parse(endDate.getText().toString());
            if (!DateUsrS.after(DateSys)) {
                // Toast.makeText(CardLimitActivity.this, "Select a Date Starting from Today", Toast.LENGTH_LONG).show();
                androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(getActivity().getApplicationContext()).create();
                alertDialog.setMessage("Select a Date Starting from Today");
                alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        });
                alertDialog.show();
                return;
            }
            if (!DateUsrE.after(DateUsrS)) {
                // Toast.makeText(CardLimitActivity.this, "End Date should be After Start Date", Toast.LENGTH_LONG).show();
                androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(getActivity().getApplicationContext()).create();
                alertDialog.setMessage("End Date should be After Start Date");
                alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        });
                alertDialog.show();
                return;
            }
            if (selectedCard.isEmpty()) {
                //    Toast.makeText(CardLimitActivity.this, "Card" + " " + getResources().getString(R.string.isMandator), Toast.LENGTH_LONG).show();
                androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(getActivity().getApplicationContext()).create();
                alertDialog.setMessage("Card" + " " + getResources().getString(R.string.isMandator));
                alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();

                return;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

       /* final Dialog dialog = new Dialog(CardLimitActivity.this,android.R.style.Theme_Material_Light_NoActionBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.validation_activity);
        // set title
        TextView validation_title = (TextView) dialog.findViewById(R.id.validation_title);


        dialog.findViewById(R.id.rowCard).setVisibility(View.VISIBLE);
        ( (TextView) dialog.findViewById(R.id.TxtCardNumber)).setText(selectedCard);

        if(!startDate.getText().toString().isEmpty() ) {
            dialog.findViewById(R.id.rowStartDate).setVisibility(View.VISIBLE);
            ((TextView) dialog.findViewById(R.id.TxtStartDate)).setText(startDate.getText().toString());
        }
        if(!endDate.getText().toString().isEmpty() ){
            dialog.findViewById(R.id.rowEndDate).setVisibility(View.VISIBLE);
            ( (TextView) dialog.findViewById(R.id.TxtEndDate)).setText(endDate.getText().toString());
        }



        if(!AmountAtm.getText().toString().isEmpty()){
            dialog.findViewById(R.id.rowAtmAmount).setVisibility(View.VISIBLE);
            ( (TextView) dialog.findViewById(R.id.TxtAtmAmount)).setText(AmountAtm.getText());
        }
        if(!AmountPos.getText().toString().isEmpty()){
            dialog.findViewById(R.id.rowPosAmount).setVisibility(View.VISIBLE);
            ( (TextView) dialog.findViewById(R.id.TxtPosAmount)).setText(AmountPos.getText());
        }


        validation_title.setText(R.string.Validation);

        final EditText txtCode = (EditText) dialog.findViewById(R.id.TxtCode);
        final TextView forget_pin = (TextView) dialog.findViewById(R.id.forget_pin);*/

        /*forget_pin.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent myAccountServicesAct = new Intent(CardLimitActivity.this, ResetPinActivity.class);
                    startActivity(myAccountServicesAct);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }));*/


       /* dialog.findViewById(R.id.btnNOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dialog.dismiss();
                } catch (Exception e) {
                    Log.d("otp", "btnLoad.setOnClickListener()", e);
                    // Toast.makeText(CardLimitActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(CardLimitActivity.this).create();
                    alertDialog.setMessage( e.getMessage());
                    alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            });
                    alertDialog.show();
                }
            }
        });*/
      /*  dialog.findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    operation = operationActivateCard;
                    dialog.dismiss();
                    if(startDate.getText().toString().isEmpty() ) {
                        startDate.setText("");
                    }
                    if(endDate.getText().toString().isEmpty() ){
                        endDate.setText("");
                    }
                    if(AmountAtm.getText().toString().isEmpty()){
                        AmountAtm.setText("0.0");
                    }
                    if(AmountPos.getText().toString().isEmpty()){
                        AmountPos.setText("0.0");
                    }
                    initProgrees();

                    if(Globals.useOTP) {
                        //General.sendOTP();
                        OTPVerification();
                       // new CustomTask().execute();

                    }
                    else{
                        new CustomTask().execute();
                    }
                } catch (Exception e) {
                    Log.d("otp", "btnLoad.setOnClickListener()", e);
                  //  Toast.makeText(CardLimitActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(CardLimitActivity.this).create();
                    alertDialog.setMessage( e.getMessage());
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
        dialog.show();
        */

    /* dialog.findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             try {
                 operation = operationActivateCard;
                 dialog.dismiss();
                 if(startDate.getText().toString().isEmpty() ) {
                     startDate.setText("");
                 }
                 if(endDate.getText().toString().isEmpty() ){
                     endDate.setText("");
                 }
                 if(AmountAtm.getText().toString().isEmpty()){
                     AmountAtm.setText("0.0");
                 }
                 if(AmountPos.getText().toString().isEmpty()){
                     AmountPos.setText("0.0");
                 }
                 initProgrees();

                 if(Globals.useOTP) {
                     //General.sendOTP();
                     try {
                         Globals.pinEntered=txtCode.getText().toString();
                         General.checkOTP(txtCode.getText().toString());
                         dialog.dismiss();

                         new CustomTask().execute();
                     } catch (Exception e) {
                         Log.d(TAG, "btnLoad.setOnClickListener()", e);
                         if(Globals.ERpin.equals("USER BLOCKED CONTACT BRANCH")){
                             doLogout(null);
                         }
                         else if(Globals.ERpin.contains("801")){
                             //   Toast.makeText(CardLimitActivity.this, "SESSION EXPIRED", Toast.LENGTH_LONG).show();
                             androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(CardLimitActivity.this).create();
                             alertDialog.setMessage( "SESSION EXPIRED");
                             alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                                     new DialogInterface.OnClickListener() {
                                         public void onClick(DialogInterface dialog, int which) {
                                             dialog.dismiss();
                                             doLogout(null);


                                         }
                                     });
                             alertDialog.show();
                         }
                         else {
                             //    Toast.makeText(CardLimitActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                             androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(CardLimitActivity.this).create();
                             alertDialog.setMessage(e.getMessage());
                             alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                                     new DialogInterface.OnClickListener() {
                                         public void onClick(DialogInterface dialog, int which) {
                                             dialog.dismiss();

                                         }
                                     });
                             alertDialog.show();
                         }
                     }                        // new CustomTask().execute();

                 }
                 else{
                     new CustomTask().execute();
                 }
             } catch (Exception e) {
                 Log.d("otp", "btnLoad.setOnClickListener()", e);
                 //  Toast.makeText(CardLimitActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                 androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(CardLimitActivity.this).create();
                 alertDialog.setMessage( e.getMessage());
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
     dialog.show();
 }*/
    @SuppressLint("NewApi")
    public void OTPVerification()
    {
        final Dialog dialog = new Dialog(getActivity().getApplicationContext(),android.R.style.Theme_Material_Light_NoActionBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.code_verification_activity);
        // set title
        TextView validation_title = (TextView) dialog.findViewById(R.id.validation_title);
        validation_title.setText("PIN VERIFICATION");
        final EditText txtCode = (EditText) dialog.findViewById(R.id.TxtCode);
        final TextView forget_pin = (TextView) dialog.findViewById(R.id.forget_pin);

       /* forget_pin.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent myAccountServicesAct = new Intent(CardLimitActivity.this, ResetPinActivity.class);
                    startActivity(myAccountServicesAct);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }));*/

        dialog.findViewById(R.id.btnNOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setCancelable(false);
                    dialog.dismiss();
                    dismissProgress();
                } catch (Exception e) {
                    Log.d("", "btnLoad.setOnClickListener()", e);
                    //   Toast.makeText(CardLimitActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(getActivity().getApplicationContext()).create();
                    alertDialog.setMessage( e.getMessage());
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
        dialog.findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Globals.pinEntered=txtCode.getText().toString();
                    General.checkOTP(txtCode.getText().toString());
                    dialog.dismiss();

                    new CustomTask(getActivity().getApplicationContext()).execute();
                } catch (Exception e) {
                    Log.d(TAG, "btnLoad.setOnClickListener()", e);
                   /* if(Globals.ERpin.equals("USER BLOCKED CONTACT BRANCH")){
                        doLogout(null);
                    }*/
                    if (Globals.ERpin.contains("801")){
                        //   Toast.makeText(CardLimitActivity.this, "SESSION EXPIRED", Toast.LENGTH_LONG).show();
                        androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(getActivity().getApplicationContext()).create();
                        alertDialog.setMessage( "SESSION EXPIRED");
                        /*alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        doLogout(null);


                                    }
                                });
                        alertDialog.show();*/
                    }
                    else {
                        //    Toast.makeText(CardLimitActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(getActivity().getApplicationContext()).create();
                        alertDialog.setMessage(e.getMessage());
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
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }
    @SuppressLint("NewApi")
    public void GoToConfirmation()
    {
        final Dialog dialog = new Dialog(getActivity().getApplicationContext(), android.R.style.Theme_Material_Light_NoActionBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.request_conf_act);
        // set title
        TextView validation_title = (TextView) dialog.findViewById(R.id.validation_title);
        validation_title.setText("Confirmation");
        TextView validation_Content = (TextView) dialog.findViewById(R.id.textView);
        validation_Content.setText("Card limit changed successfully");
        //final TextView txtCode = (TextView) dialog.findViewById(R.id.transactionId);
        //txtCode.setText("Your Request has been sent Successfully");
        dialog.findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dialog.dismiss();
                    // CardLimitActivity.this.finish();
                } catch (Exception e) {
                    Log.d("otp", "btnLoad.setOnClickListener()", e);
                    // Toast.makeText(CardLimitActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(getActivity().getApplicationContext()).create();
                    alertDialog.setMessage(e.getMessage());
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
        /*dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            public void onDismiss(final DialogInterface dialog) {
                CardInformation1.this.finish();
            }
        });*/


        /*Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        validation_title.setTypeface(tf);
        //txtCode.setTypeface(tf);
        final TextView textView = (TextView) dialog.findViewById(R.id.textView);
        textView.setTypeface(tf);
        /*final TextView textView2 = (TextView) dialog.findViewById(R.id.textView2);
        textView2.setTypeface(tf);
        final Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
        btnOk.setTypeface(tf);

        dialog.show();
    }*/




    /////////

    /** private class CustomTask extends AsyncTask<String, String, String> {
     String accountSelected=null;

     protected String doInBackground(String... param) {

     try {
     /*if(accountSelected==null) {
     if (selectedATM.isEmpty() || spinAtm.getSelectedItemPosition()==0) {
     if (selectedPOS.isEmpty() || spinPos.getSelectedItemPosition()==0)
     Card.RequestCardLimit(selectedAccount, selectedCardType, ReasonForRequest.getText().toString(), CardNumber6.getText().toString(), CardNumber4.getText().toString(), "", 0.0,"",0.0);
     else
     Card.RequestCardLimit(selectedAccount, selectedCardType, ReasonForRequest.getText().toString(), CardNumber6.getText().toString(), CardNumber4.getText().toString(), "",0.0,selectedPOS, Double.parseDouble(AmountPos.getText().toString())); // younes
     }
     else if (selectedPOS.isEmpty() || spinPos.getSelectedItemPosition()==0) {
     if (selectedATM.isEmpty() || spinAtm.getSelectedItemPosition()==0)
     Card.RequestCardLimit(selectedAccount, selectedCardType, ReasonForRequest.getText().toString(), CardNumber6.getText().toString(), CardNumber4.getText().toString(), "", 0.0,"",0.0);
     else
     Card.RequestCardLimit(selectedAccount, selectedCardType, ReasonForRequest.getText().toString(), CardNumber6.getText().toString(), CardNumber4.getText().toString(),selectedATM, Double.parseDouble(AmountAtm.getText().toString()),"",0.0); // younes
     } else
     Card.RequestCardLimit(selectedAccount, selectedCardType, ReasonForRequest.getText().toString(), CardNumber6.getText().toString(), CardNumber4.getText().toString(), selectedATM, Double.parseDouble(AmountAtm.getText().toString()),selectedPOS,Double.parseDouble(AmountPos.getText().toString())); // younes
     }*/
    /**               return null;
     } catch (Exception e) {
     e.printStackTrace();
     return e.getMessage();
     }
     }
     protected void onPostExecute(String param) {
     dismissProgress();
     super.onPostExecute(param);
     if(param!=null && param.contains("801")){
     Toast.makeText(CardLimitActivity.this, "SESSION EXPIRED", Toast.LENGTH_LONG).show();
     doLogout(null);
     }

     if(param!=null)
     Toast.makeText(CardLimitActivity.this, param, Toast.LENGTH_LONG).show();
     else {
     if(accountSelected==null)
     GoToConfirmation();
     }
     }
     }
     **/
    // handle dates
    private void findViewsById() {
        startDate = (EditText) mRootView.findViewById(R.id.startDate);
        /*startDate.setInputType(InputType.TYPE_NULL);
        startDate.requestFocus();*/


    }
    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(getActivity().getApplicationContext(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                startDate.setText(dateFormatter.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        toDatePickerDialog = new DatePickerDialog(getActivity().getApplicationContext(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                endDate.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
    /*public void onClick(View view) {
        if (view == startDate) {
            fromDatePickerDialog.show();
        } else if (view == endDate) {
            toDatePickerDialog.show();
        }
    }*/




    private class CustomTask extends AsyncTask<String, String, String> implements com.LBA.prepaidPortal.widgets.fragment.CustomTask {
        String accountSelected = null;
        String balanceData;

        public CustomTask(Context applicationContext) {
            Log.e(TAG, "CustomTask(): " );
        }

        protected String doInBackground(String... param) {

            Log.e(TAG, "Do InBackground: " );
            //Card.GetCardsToActivateList();
            try {
                Card.CardDetails();
                dismissProgress();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return null;
        }
        protected void onPostExecute(String result) {
            dismissProgress();
            super.onPostExecute(result);
            LastTransactionDetail c = new LastTransactionDetail();

            if(spincard!=null ){
                Log.e(TAG, "bankcode:*********************************************" );

                bankCode.setText("null");
                bankCode.setTextSize(18);
                bankName.setText("null");
                bankName.setTextSize(18);
            }
            if(result!=null && result.contains("801")){
                // Toast.makeText(AccountBalanceActivity.this, "SESSION EXPIRED", Toast.LENGTH_LONG).show();
                androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(getActivity().getApplicationContext()).create();
                alertDialog.setMessage("SESSION EXPIRED");
                alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }

            if(result!=null && !result.contains("801")) {
                // Toast.makeText(AccountBalanceActivity.this, result, Toast.LENGTH_LONG).show();

                androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(getActivity().getApplicationContext()).create();
                alertDialog.setMessage(result);
                alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        }
       /* protected void onPostExecute(String param) {
            dismissProgress();
            super.onPostExecute(param);


            if(param!=null && param.contains("801")){
                //  Toast.makeText(CardLimitActivity.this, "SESSION EXPIRED", Toast.LENGTH_LONG).show();
                androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(getActivity().getApplicationContext()).create();
                alertDialog.setMessage("SESSION EXPIRED");
                alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                //doLogout(null);
                            }
                        });
                alertDialog.show();

            }

            if(param!=null && !param.contains("801")) {
                //  Toast.makeText(CardLimitActivity.this, param, Toast.LENGTH_LONG).show();
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
            else {
                if(operation.equals(operationActivateCard))
                    GoToConfirmation();
                else{
                    if(Globals.maskedCardsList!=null && Globals.maskedCardsList.size()>0) {
                        MySpinnerAdapter spinnerArrayAdapter = new MySpinnerAdapter(getActivity().getApplicationContext(), R.layout.spinner_item, Globals.maskedCardsList);
                        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spincard.setAdapter(spinnerArrayAdapter);
                    }
                }
            }


    }*/
    }
    public void OpenTime() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        String msg = "";

        if (timeOfDay >= 0 && timeOfDay < 12) {
            msg = "Good Morning";
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            msg = "Good Afternoon";
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            msg = "Good Evening";
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            msg = "Good Evening";
        }
        //    Greetmsg.setText(msg);
    }
    public static Bitmap decodeToBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}