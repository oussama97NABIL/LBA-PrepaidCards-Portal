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
import android.icu.text.DateFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.textclassifier.TextClassification;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.LBA.prepaidPortal.R;
import com.LBA.prepaidPortal.activity.HomeActivity;
import com.LBA.tools.assets.Globals;
import com.LBA.tools.misc.BranchEntry;
import com.LBA.tools.services.Card;
import com.LBA.tools.services.General;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class GMoneyWalletToCard extends BaseFragment implements View.OnClickListener {
    Spinner spindebitAccount, spinBenef;
    EditText spinBranch;
    EditText  txtPaymentDetails, amount, txtPayeeName;
    TextView textView, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textViewHeading;
    TextView txtCreditAccount, ACHBank;
    TextView txtBalance;

    String selectedAccount, selectedBenef, selectedBranch;
    List<String> BenefName = new ArrayList<String>();
    List<BranchEntry> branchesByBankList = new ArrayList<BranchEntry>();

    int posBr = 0;

    List<String> balanceData = new ArrayList<String>();;

    ImageButton back;
    //TextView showUser;
    ImageButton homeBtn;
    Button nxtBtn;
    ImageButton canlBtn;
    View mRootView;
    //Button balances;
    //TextView Greetmsg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.gmoneywallet_to_card_fragment, container, false);
        textViewHeading = (TextView) mRootView.findViewById(R.id.textView14);
        textView = (TextView) mRootView.findViewById(R.id.textView);
        textView2 = (TextView) mRootView.findViewById(R.id.textView2);
        textView3 = (TextView) mRootView.findViewById(R.id.textView3);
        textView4 = (TextView) mRootView.findViewById(R.id.textView4);
        textView5 = (TextView) mRootView.findViewById(R.id.textView5);
        textView6 = (TextView) mRootView.findViewById(R.id.textView6);
        textView7 = (TextView) mRootView.findViewById(R.id.textView7);
        textView8 = (TextView) mRootView.findViewById(R.id.textView8);
        txtBalance = (TextView) mRootView.findViewById(R.id.TxtBalance);

        spindebitAccount = (Spinner) mRootView.findViewById(R.id.spindebitAccount);
        spinBenef = (Spinner) mRootView.findViewById(R.id.spinBenef);
        spinBranch = (EditText) mRootView.findViewById(R.id.spinACHBranch);

        ACHBank = (TextView) mRootView.findViewById(R.id.ACHBank);
        txtPayeeName = (EditText) mRootView.findViewById(R.id.TxtPayeeName);
        txtCreditAccount = (TextView) mRootView.findViewById(R.id.TxtCreditAccount);
        amount = (EditText) mRootView.findViewById(R.id.TxtAmount);
        txtPaymentDetails = (EditText) mRootView.findViewById(R.id.TxtPaymentDetails);

        //      showUser = (TextView) findViewById(R.id.showUser);
        homeBtn = (ImageButton) mRootView.findViewById(R.id.home_button);
        nxtBtn = (Button) mRootView.findViewById(R.id.imageButton23);
        canlBtn = (ImageButton) mRootView.findViewById(R.id.imageButton24);
        //     balances = (Button) findViewById(R.id.btnBalances);
        //    Greetmsg = (TextView) findViewById(R.id.Greetmsg);

        /*Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        textViewHeading.setTypeface(tf);
        textView.setTypeface(tf);
        textView2.setTypeface(tf);
        textView3.setTypeface(tf);
        textView4.setTypeface(tf);
        textView5.setTypeface(tf);
        textView6.setTypeface(tf);
        textView7.setTypeface(tf);
        textView8.setTypeface(tf);*/
        //     showUser.setTypeface(tf);
        //     Greetmsg.setTypeface(tf);

        //     showUser.setText(Globals.fname);
        OpenTime();
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
                HomeTask task = new HomeTask(HomeActivity.class);
                task.execute();
            }
        });

        this.back = (ImageButton) mRootView.findViewById(R.id.Back);
        this.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initProgrees();
                HomeTask task = new HomeTask(HomeActivity.class);
                task.execute();
            }
        });

       /* MySpinnerAdapter spinnerArrayAdapter = new MySpinnerAdapter(getActivity().getApplicationContext(), R.layout.spinner_item, Globals.accountsList);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spindebitAccount.setAdapter(spinnerArrayAdapter);*/
        //spindebitAccount.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) getActivity().getApplicationContext());


        BenefName.add(Globals.Select);
        for(int i =0; i<Globals.BeneficyList.size(); i++){
            if(Globals.BeneficyList.get(i).getBeneficiary_operation().equals("HT"))
                BenefName.add(Globals.BeneficyList.get(i).getBeneficiary_name());
        }

        /*MySpinnerAdapter spinner2ArrayAdapter = new MySpinnerAdapter(getActivity().getApplicationContext(), R.layout.spinner_item, BenefName);
        spinner2ArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinBenef.setAdapter(spinner2ArrayAdapter);*/
        //spinBenef.setOnItemSelectedListener(getActivity().getApplicationContext());

        /*MyBranchesSpinnerAdapter spinner3ArrayAdapter = new MyBranchesSpinnerAdapter(this, R.layout.spinner_item, branchesByBankList);
        spinner3ArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinBranch.setAdapter(spinner3ArrayAdapter);*/


        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(spindebitAccount.getSelectedItemPosition()==0){
                        // Toast.makeText(ACHActivityBeneficiary.this, "Account" + " " + getResources().getString(R.string.isMandator), Toast.LENGTH_LONG).show();
                        AlertDialog alertDialog = new AlertDialog.Builder(getActivity().getApplicationContext()).create();
                        alertDialog.setMessage("Account" + " " + getResources().getString(R.string.isMandator));
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                        return;
                    }

                    if (spinBenef.getSelectedItemPosition() == 0) {
                        //Toast.makeText(ACHActivityBeneficiary.this, "Beneficiary "+ " " + getResources().getString(R.string.isMandator), Toast.LENGTH_LONG).show();

                        AlertDialog alertDialog = new AlertDialog.Builder(getActivity().getApplicationContext()).create();
                        alertDialog.setMessage("Beneficiary "+ " " + getResources().getString(R.string.isMandator));
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                        return;
                    }

                    if (spinBranch.getText().toString().isEmpty()) {
                        //  Toast.makeText(ACHActivityBeneficiary.this, getResources().getString(R.string.ACHBranch) + " " + getResources().getString(R.string.isMandator), Toast.LENGTH_LONG).show();
                        AlertDialog alertDialog = new AlertDialog.Builder(getActivity().getApplicationContext()).create();
                        alertDialog.setMessage(getResources().getString(R.string.ACHBranch) + " " + getResources().getString(R.string.isMandator));
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                        return;
                    }

                    if (txtCreditAccount.getText().toString().isEmpty()) {
                        // Toast.makeText(ACHActivityBeneficiary.this, getResources().getString(R.string.CreditAccount) + " " + getResources().getString(R.string.isMandator), Toast.LENGTH_LONG).show();

                        AlertDialog alertDialog = new AlertDialog.Builder(getActivity().getApplicationContext()).create();
                        alertDialog.setMessage(getResources().getString(R.string.CreditAccount) + " " + getResources().getString(R.string.isMandator));
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();

                        return;
                    }
                    if (txtCreditAccount.getText().toString().length()!=13) {
                        //Toast.makeText(ACHActivityBeneficiary.this, getResources().getString(R.string.CreditAccount) + " " + "Length should be 13", Toast.LENGTH_LONG).show();
                        AlertDialog alertDialog = new AlertDialog.Builder(getActivity().getApplicationContext()).create();
                        alertDialog.setMessage(getResources().getString(R.string.CreditAccount) + " " + "Length should be 13");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                        return;
                    }
                    if (amount.getText().toString().isEmpty()) {
                        // Toast.makeText(ACHActivityBeneficiary.this, getResources().getString(R.string.Amount) + " " + getResources().getString(R.string.isMandator), Toast.LENGTH_LONG).show();

                        AlertDialog alertDialog = new AlertDialog.Builder(getActivity().getApplicationContext()).create();
                        alertDialog.setMessage(getResources().getString(R.string.Amount) + " " + getResources().getString(R.string.isMandator));
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();

                        return;
                    }
                    if (txtPaymentDetails.getText().toString().isEmpty()) {
                        //Toast.makeText(ACHActivityBeneficiary.this, "Payment Details " + " " + getResources().getString(R.string.isMandator), Toast.LENGTH_LONG).show();

                        AlertDialog alertDialog = new AlertDialog.Builder(getActivity().getApplicationContext()).create();
                        alertDialog.setMessage( "Payment Details " + " " + getResources().getString(R.string.isMandator));
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();

                        return;
                    }
                    if (ACHBank.getText().toString().isEmpty()) {
                        //Toast.makeText(ACHActivityBeneficiary.this, "Bank " + " " + getResources().getString(R.string.isMandator), Toast.LENGTH_LONG).show();

                        AlertDialog alertDialog = new AlertDialog.Builder(getActivity().getApplicationContext()).create();
                        alertDialog.setMessage("Bank " + " " + getResources().getString(R.string.isMandator));
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();

                        return;
                    }
                    if (txtPayeeName.getText().toString().isEmpty()) {
                        // Toast.makeText(ACHActivityBeneficiary.this, "Payee Name " + " " + getResources().getString(R.string.isMandator), Toast.LENGTH_LONG).show();

                        AlertDialog alertDialog = new AlertDialog.Builder(getActivity().getApplicationContext()).create();
                        alertDialog.setMessage( "Payee Name " + " " + getResources().getString(R.string.isMandator));
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();

                        return;
                    }
                    if (Double.parseDouble(amount.getText().toString())>Double.parseDouble(txtBalance.getText().toString())) {
                        //  Toast.makeText(ACHActivityBeneficiary.this, "INSUFFICIENT FUNDS", Toast.LENGTH_LONG).show();

                        AlertDialog alertDialog = new AlertDialog.Builder(getActivity().getApplicationContext()).create();
                        alertDialog.setMessage("INSUFFICIENT FUNDS");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();

                        return;
                    }

                    //GoToValidation();


                } catch (Exception e) {
                    Log.d("", "btnLoad.setOnClickListener()", e);
                    //Toast.makeText(ACHActivityBeneficiary.this, e.getMessage(), Toast.LENGTH_LONG).show();
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

    /*@SuppressLint("NewApi")
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
    }*/

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
                    Log.d("TAG", "btnLoad.setOnClickListener()", e);
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
        EditText startDate = (EditText) mRootView.findViewById(R.id.startDate);
        startDate.setInputType(InputType.TYPE_NULL);
        startDate.requestFocus();
        EditText endDate = (EditText) mRootView.findViewById(R.id.endDate);
        endDate.setInputType(InputType.TYPE_NULL);
    }
    private void setDateTimeField() {
        TextClassification.Builder startDate = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startDate.setOnClickListener(this);
        }
        TextClassification.Builder endDate = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            endDate.setOnClickListener(this);
        }
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog fromDatePickerDialog = new DatePickerDialog(getActivity().getApplicationContext(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                DateFormat dateFormatter = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    startDate.setText(dateFormatter.format(newDate.getTime()));
                }
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        DatePickerDialog toDatePickerDialog = new DatePickerDialog(getActivity().getApplicationContext(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                DateFormat dateFormatter = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    endDate.setText(dateFormatter.format(newDate.getTime()));
                }
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
        }

        protected String doInBackground(String... param) {


            //Card.GetCardsToActivateList();
            try {
                Card.CardDetails();
                dismissProgress();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return null;
        }
        /*protected void onPostExecute(String param) {
            dismissProgress();
            super.onPostExecute(param);


            if(param!=null && param.contains("801")){
                //  Toast.makeText(CardLimitActivity.this, "SESSION EXPIRED", Toast.LENGTH_LONG).show();
                androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(CardLimitActivity.this).create();
                alertDialog.setMessage("SESSION EXPIRED");
                alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                doLogout(null);
                            }
                        });
                alertDialog.show();

            }

            if(param!=null && !param.contains("801")) {
                //  Toast.makeText(CardLimitActivity.this, param, Toast.LENGTH_LONG).show();
                androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(CardLimitActivity.this).create();
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
                        MySpinnerAdapter spinnerArrayAdapter = new MySpinnerAdapter(CardLimitActivity.this, R.layout.spinner_item, Globals.maskedCardsList);
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