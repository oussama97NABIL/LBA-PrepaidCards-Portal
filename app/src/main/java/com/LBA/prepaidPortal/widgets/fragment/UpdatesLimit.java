package com.LBA.prepaidPortal.widgets.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.LBA.prepaidPortal.R;
import com.LBA.prepaidPortal.activity.HomeActivity;
import com.LBA.tools.assets.Globals;
import com.LBA.tools.services.Card;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.shuhart.stepview.StepView;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class UpdatesLimit extends BaseFragment implements AdapterView.OnItemSelectedListener {
    Spinner spinCardNumber;
    TextView txtBalance;
    TextView txtCurrency;
    private StepView mStepView;
    protected final int MAX_HEIGHT = 500;
    private EditText fromDateEtxt;
    private EditText toDateEtxt;
    private Button btnLoad;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private int nCounter=0;
    private String selectedAccount;
    TextView BankCode;
    TextView BankName;
    ImageButton canBtn;
    MaterialButton nexBtn;
    TextInputEditText memo;
    TextInputEditText reférenceCarte;
    TextInputEditText atmLimit;
    TextInputEditText posLimit;
    TextInputEditText onlineLimit;

    TextView textView_heading;
    TextView textView;
    TextView textView2;
    TextView textView3;
    View mRootView;

    static private final String TAG = CardInformation1.class.getSimpleName();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.update_limit, container, false);

        SpannableString s = new SpannableString("Plafonds");
        s.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getActivity().setTitle(s);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        BankCode = (TextView) mRootView.findViewById(R.id.bankCode);
        BankName = (TextView) mRootView.findViewById(R.id.bankname);
        canBtn = (ImageButton) mRootView.findViewById(R.id.imageButton24);
        nexBtn = (MaterialButton) mRootView.findViewById(R.id.imageButton23);
        atmLimit = (TextInputEditText) mRootView.findViewById(R.id.limitCash);
        posLimit = (TextInputEditText) mRootView.findViewById(R.id.limitPurchase);
        onlineLimit = (TextInputEditText) mRootView.findViewById(R.id.limitEcom);
        mStepView = (StepView) mRootView. findViewById(R.id.step_view);
        reférenceCarte = (TextInputEditText) mRootView.findViewById(R.id.reférence_carte);
        memo = (TextInputEditText) mRootView.findViewById(R.id.memo);
        Log.e(TAG, "onCreateView: HC -----  mStepView.getState()");
        mStepView.done(false);

        List<String> steps = Arrays.asList(new String[]{"SAISIE", "VALIDATION", "CONFIRMATION"});
        mStepView.setSteps(steps);


        //getCardInformations();
        OpenTime();
        getCardLimit();

        canBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initProgrees();
                HomeTask task = new HomeTask(HomeActivity.class);
                task.execute();
            }
        });
        nexBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (atmLimit.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity().getApplicationContext(), "Veuillez entrer Atm Limit", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (posLimit.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity().getApplicationContext(), "Veuiller entrer Pos Limit", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (onlineLimit.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity().getApplicationContext(), "Veuiller entrer Online Limit", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    DialogPlafondsLimit();
                }
            }
        });
        onlineLimit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                } else {
                    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                }
            }
        });

        mStepView.getState()
                .selectedTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.white))
                .animationType(StepView.ANIMATION_CIRCLE)
                .selectedCircleColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.colorAccent))
                .selectedCircleRadius(getResources().getDimensionPixelSize(R.dimen._14sdp))
                .selectedStepNumberColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.colorPrimary))
                .stepsNumber(3)
                .animationType(StepView.ANIMATION_LINE)
                .doneStepLineColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.black))
                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .stepLineWidth(getResources().getDimensionPixelSize(R.dimen._1sdp))
                .textSize(getResources().getDimensionPixelSize(R.dimen._14sdp))
                .stepNumberTextSize(getResources().getDimensionPixelSize(R.dimen._16sdp))
                .typeface(ResourcesCompat.getFont(getContext(), R.font.roboto_light))
                // other state methods are equal to the corresponding xml attributes
                .commit();



        /**
         TextView txtBalance;
         TextView txtCurrency;
         private EditText fromDateEtxt;
         private EditText toDateEtxt;
         private Button btnLoad;
         private DatePickerDialog fromDatePickerDialog;
         private DatePickerDialog toDatePickerDialog;
         **/
        /*Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/miloot_bold.otf"); omar
        btnLoad.setTypeface(tf);
        textView_heading.setTypeface(tf);
        textView.setTypeface(tf);
        textView2.setTypeface(tf);
        textView3.setTypeface(tf);

        Button button_sign_out = (Button) findViewById(R.id.button_sign_out);
        button_sign_out.setTypeface(tf);*/

        return mRootView;
    }
    /*private void findViewsById() {
        fromDateEtxt = (EditText) findViewById(R.id.etxt_fromdate);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();
        toDateEtxt = (EditText) findViewById(R.id.etxt_todate);
        toDateEtxt.setInputType(InputType.TYPE_NULL);
    }*/
    /*private void setDateTimeField() {
        fromDateEtxt.setOnClickListener(this);
        toDateEtxt.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                toDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }*/
    public void onClick(View view) {
        if (view == fromDateEtxt) {
            fromDatePickerDialog.show();
        } else if (view == toDateEtxt) {
            toDatePickerDialog.show();
        }
    }

    public void  getCardLimit(){
        try {
            //Account.GetTransactionList(selectedAccount, fromDateEtxt.getText().toString().trim(), toDateEtxt.getText().toString().trim());
            initProgrees();
            new CustomTaskCardLimit().execute();
        } catch (Exception e) {
            //Log.d(TAG, "btnLoad.setOnClickListener()", e);
            Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /*public void getCardInformations(){
        try {
            //Account.GetTransactionList(selectedAccount, fromDateEtxt.getText().toString().trim(), toDateEtxt.getText().toString().trim());
            initProgrees();
            new CustomTask().execute();
        } catch (Exception e) {
            //Log.d(TAG, "btnLoad.setOnClickListener()", e);
            Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
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
    public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long id) {
        if(parent instanceof Spinner && pos>0) {
            this.selectedAccount = (String) parent.getItemAtPosition(pos);
            /*try {
                if(nCounter>0) {
                    String balanceData = Account.GetBalance(selectedAccount);
                    txtBalance.setText(balanceData.substring(0, 0 + 20));
                    txtCurrency.setText(balanceData.substring(0 + 20, 0 + 20 + 3));
                    //txtValueDate.setText(balanceData.substring(0 + 20 + 3, 0 + 20 + 3 + 8));
                }
                nCounter++;
            } catch (Exception e) {
                Toast.makeText(TransactionListActivity.this, "Balance Retrieval Failed", Toast.LENGTH_LONG).show();
            }*/
        }
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    private class CustomTask extends AsyncTask<String, String, String> {
        protected String doInBackground(String... param) {
            try {
                Card.UpdateLimit();
                // Intent myWelcomeAct = new Intent(getActivity().getApplicationContext(), CardInformationResult.class);
                //startActivity(myWelcomeAct);
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }
        protected void onPostExecute(String param) {
            dismissProgress();
            super.onPostExecute(param);
            Log.d(TAG, "Param" +param);

            if(param!=null && param.contains("801")){
                Toast.makeText(getActivity().getApplicationContext(), "Session expired", Toast.LENGTH_LONG).show();
                try {
                    // doLogout(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
            if(param!=null) {

                Toast.makeText(getActivity().getApplicationContext(), param, Toast.LENGTH_LONG).show();
                Log.e(TAG, "doInBackground: 4" );
            }else{
                if(Globals.message.contains("Successfull")){
                    DialogToValidation(true,Globals.message);
                }
                else {
                    DialogToValidation(false,Globals.message);
                }
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void DialogPlafondsLimit(){
        {

            final Dialog dialog = new Dialog(getActivity());
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            int dialogWidth = lp.width;
            int dialogHeight = lp.height;

            if (dialogWidth < MAX_HEIGHT) {
                dialog.getWindow().setLayout(dialogWidth, MAX_HEIGHT);
            }

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(getActivity().getDrawable(R.drawable.white_rect));
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.new_benef_conf_plafond_limite);
            // set title
            TextView validation_title = (TextView) dialog.findViewById(R.id.validation_title);
            validation_title.setText(R.string.Validation);
            /*final TextView txtCode = (TextView) dialog.findViewById(R.id.transactionId);
            txtCode.setText(Globals.transactionId);*/
            // mStepView.done(false);
            TextView cardNumber = (TextView) dialog.findViewById(R.id.cardNumber);
            cardNumber.setText(Globals.cardNumber);
            mStepView.go(1, true);

            dialog.findViewById(R.id.btnNOk).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        dialog.dismiss();
                    } catch (Exception e) {
                        Log.d(TAG, "btnLoad.setOnClickListener()", e);
                        //  Toast.makeText(DSTVActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
                        dialog.dismiss();
                        initProgrees();
                        new CustomTask().execute();
                    } catch (Exception e) {
                        Log.d(TAG, "btnLoad.setOnClickListener()", e);
                        // Toast.makeText(DSTVActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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

            dialog.show();
        }
    }
    private void DialogToValidation(boolean isSuccessful , String message){
        final Dialog dialog = new Dialog(getActivity());
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        int dialogWidth = lp.width;
        int dialogHeight = lp.height;
        if (dialogWidth < MAX_HEIGHT) {
            dialog.getWindow().setLayout(dialogWidth, MAX_HEIGHT);
        }

        dialog.setContentView(R.layout.confirm_dialog_plafond);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;


        mStepView.go(2,true);
        Button okey = dialog.findViewById(R.id.btn_okay);
        Button cancel = dialog.findViewById(R.id.btn_cancel);
        if(!isSuccessful){
            ImageView image =  (ImageView) dialog.findViewById(R.id.imageView);
            image.setImageResource(R.drawable.error_icon);
            TextView success = (TextView) dialog.findViewById(R.id.textView);
            success.setText("Failure");
            TextView Felicitation = (TextView) dialog.findViewById(R.id.textView2);
            Felicitation.setText(message);
        }
        okey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStepView.done(true);
                Toast.makeText(getActivity().getApplicationContext(), "Okay", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(), "Annuler", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    private class CustomTaskCardLimit extends AsyncTask<String, String, String> {
        protected String doInBackground(String... param) {
            try {
                Card.UpdateLimit();
                // Intent myWelcomeAct = new Intent(getActivity().getApplicationContext(), CardInformationResult.class);
                //startActivity(myWelcomeAct);
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }
        protected void onPostExecute(String param) {
            dismissProgress();
            super.onPostExecute(param);

            if(param!=null && param.contains("801")){
                Toast.makeText(getActivity().getApplicationContext(), "Session expired", Toast.LENGTH_LONG).show();
                try {
                    // doLogout(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
            if(param!=null)
                Toast.makeText(getActivity().getApplicationContext(), param, Toast.LENGTH_LONG).show();
            TextInputEditText CardNumber = (TextInputEditText) mRootView.findViewById(R.id.cardNumber);
            CardNumber.setText(Globals.cardNumber);
            TextInputEditText LimitCash = (TextInputEditText) mRootView.findViewById(R.id.limitCash);
            LimitCash.setText(Globals.limitCash);
            TextInputEditText LimitPurchase = (TextInputEditText) mRootView.findViewById(R.id.limitPurchase);
            LimitPurchase.setText(Globals.limitPurchase);
            TextInputEditText LimitEcom = (TextInputEditText) mRootView.findViewById(R.id.limitEcom);
            LimitEcom.setText(Globals.limitEcom);
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
}