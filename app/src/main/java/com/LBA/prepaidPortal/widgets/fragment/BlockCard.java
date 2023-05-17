package com.LBA.prepaidPortal.widgets.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentManager;

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


public class BlockCard extends BaseFragment implements AdapterView.OnItemSelectedListener {
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
    FragmentManager fm;
    private SimpleDateFormat dateFormatter;
    private int nCounter=0;
    private String selectedAccount;
    private String selectedOperation ;
    TextView BankCode;
    TextView BankName;
    ImageButton canBtn;
    MaterialButton nexBtn;

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

        mRootView = inflater.inflate(R.layout.block_card_new, container, false);
        SpannableString spannableString = new SpannableString("Bloquer/Débloquer une carte");
        spannableString.setSpan(new ForegroundColorSpan(Color.WHITE), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getActivity().setTitle(spannableString);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        BankCode = (TextView) mRootView.findViewById(R.id.bankCode);
        BankName = (TextView) mRootView.findViewById(R.id.bankname);
        canBtn = (ImageButton) mRootView.findViewById(R.id.imageButton24);
        nexBtn = (MaterialButton) mRootView.findViewById(R.id.imageButton23);
        mStepView = (StepView) mRootView. findViewById(R.id.step_view);
        Log.e(TAG, "onCreateView: HC -----  mStepView.getState()");
        mStepView.done(false);
        List<String> steps = Arrays.asList(new String[]{"SAISIE", "VALIDATION", "CONFIRMATION"});
        mStepView.setSteps(steps);
        //getCardInformations();
        OpenTime();
        getCardNumber();

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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    DialogBlockCard();
                }
            }
        });
        mStepView.getState()
                .selectedTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.white))
                .animationType(StepView.ANIMATION_CIRCLE)
                .selectedCircleColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.yellow))
                .selectedCircleRadius(getResources().getDimensionPixelSize(R.dimen._14sdp))
                .selectedStepNumberColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.Black))
                .stepsNumber(3)
                .animationType(StepView.ANIMATION_LINE)
                .doneStepLineColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.black))
                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .stepLineWidth(getResources().getDimensionPixelSize(R.dimen._1sdp))
                .textSize(getResources().getDimensionPixelSize(R.dimen._14sdp))
                .stepNumberTextSize(getResources().getDimensionPixelSize(R.dimen._16sdp))
                .typeface(ResourcesCompat.getFont(getContext(), R.font.poppinsmedium))
                // other state methods are equal to the corresponding xml attributes
                .commit();
        String[] arraySpinner = new String[] {
                "Bloquer","Débloquer"
        };
        spannableString.setSpan(new TypefaceSpan("sans-serif"), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan(16, true), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

// Définition du premier élément du tableau avec la police et la taille du texte personnalisées

        selectedOperation = "Bloquer";
        Spinner s = (Spinner) mRootView.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        s.setOnItemSelectedListener(this);
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

    public void onClick(View view) {
        if (view == fromDateEtxt) {
            fromDatePickerDialog.show();
        } else if (view == toDateEtxt) {
            toDatePickerDialog.show();
        }
    }
    public void getCardNumber(){
        try {
            //Account.GetTransactionList(selectedAccount, fromDateEtxt.getText().toString().trim(), toDateEtxt.getText().toString().trim());
            initProgrees();
            new CustomTaskCardNumber().execute();
        } catch (Exception e) {
            //Log.d(TAG, "btnLoad.setOnClickListener()", e);
            Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        fm = getFragmentManager();
    }
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
            Log.i(TAG, "onItemSelected pos: "+pos);
            this.selectedOperation = (String) parent.getItemAtPosition(pos);
            Log.i(TAG, "onItemSelected selectedOperation: "+selectedOperation);

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void DialogBlockCard(){
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
            dialog.setContentView(R.layout.new_benef_conf);
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
        {
            final Dialog dialog = new Dialog(getActivity());
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            int dialogWidth = lp.width;
            int dialogHeight = lp.height;
            if (dialogWidth < MAX_HEIGHT) {
                dialog.getWindow().setLayout(dialogWidth, MAX_HEIGHT);
            }

            dialog.setContentView(R.layout.confirm_dialog_block_card_new);
            //dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(getActivity().getDrawable(R.drawable.white_rect));

            dialog.setCancelable(false);
            dialog.getWindow().getAttributes().windowAnimations = R.style.animation;


            mStepView.go(2,true);
            Button okey = dialog.findViewById(R.id.btn_okay);
            Button cancel = dialog.findViewById(R.id.btn_cancel);
            if(!isSuccessful){
                ImageView image =  (ImageView) dialog.findViewById(R.id.imageView);
                image.setImageResource(R.drawable.failed_1);
                TextView success = (TextView) dialog.findViewById(R.id.textView);
                success.setText("Echoué");
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
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    private class CustomTask extends AsyncTask<String, String, String> {

        protected String doInBackground(String... param) {
            try {
                  if(selectedOperation.equals("Bloquer")){
                      selectedOperation = "B";
                  }
                  else {
                      selectedOperation = "U";
                  }
                    Log.e(TAG, "selectedOperation: "+selectedOperation);
                    Card.BlockCard(selectedOperation);
                Log.e(TAG, "doInBackground: 1" );
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }
        protected void onPostExecute(String param) {
            dismissProgress();
            super.onPostExecute(param);
            Log.e(TAG, "doInBackground: 2" );
            Log.e(TAG, "param " +param);
            if(param!=null && param.contains("801")){
                Toast.makeText(getActivity().getApplicationContext(), "Session expired", Toast.LENGTH_LONG).show();
                Log.e(TAG, "doInBackground: 3" );
                try {
                    // doLogout(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
            if(param.contains("Successfull")){
                  DialogToValidation(true,param);
            }
            else {
                DialogToValidation(false,param);
            }
            if(param!=null) {
                Toast.makeText(getActivity().getApplicationContext(), param, Toast.LENGTH_LONG).show();
                Log.e(TAG, "doInBackground: 4" );
            }
        }
    }
    private class CustomTaskCardNumber extends AsyncTask<String, String, String> {
        protected String doInBackground(String... param) {
            try {
                Card.CardDetails();
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

            TextInputEditText cardNumber = (TextInputEditText) mRootView.findViewById(R.id.cardNumber);
            cardNumber.setText(Globals.cardNumber);
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