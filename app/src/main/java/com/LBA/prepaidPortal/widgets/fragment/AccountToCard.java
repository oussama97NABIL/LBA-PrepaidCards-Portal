package com.LBA.prepaidPortal.widgets.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.LBA.MainActivity;
import com.LBA.prepaidPortal.R;
import com.LBA.prepaidPortal.activity.AbstractActivity;
import com.LBA.prepaidPortal.activity.ForgotPassword;
import com.LBA.prepaidPortal.activity.HomeActivity;
import com.LBA.tools.assets.Globals;
import com.LBA.tools.services.Card;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.shuhart.stepview.StepView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class AccountToCard extends BaseFragment implements AdapterView.OnItemSelectedListener {
    Spinner spinCardNumber;
    TextView txtBalance;
    TextView txtCurrency;
    private EditText fromDateEtxt;

    private int position = 0;
    private StepView mStepView;
    TextInputEditText accountNumber;
    TextInputEditText montant;
    TextInputEditText memo;
    TextInputEditText reférenceCarte;

    private EditText toDateEtxt;
    private Button btnLoad;
    protected final int MAX_HEIGHT = 500;

    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private int nCounter=0;
    private String selectedAccount;
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
        mRootView = inflater.inflate(R.layout.account_to_card, container, false);

        SpannableString s = new SpannableString("Transfert de Compte à Carte");
        s.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getActivity().setTitle(s);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        BankCode = (TextView) mRootView.findViewById(R.id.bankCode);
        BankName = (TextView) mRootView.findViewById(R.id.bankname);
        canBtn = (ImageButton) mRootView.findViewById(R.id.imageButton24);
        nexBtn = (MaterialButton) mRootView.findViewById(R.id.imageButton23);
        mStepView = (StepView) mRootView. findViewById(R.id.step_view);
        accountNumber = (TextInputEditText) mRootView.findViewById(R.id.account_number);
        montant = (TextInputEditText) mRootView.findViewById(R.id.amount);
        reférenceCarte = (TextInputEditText) mRootView.findViewById(R.id.reférence_carte);
        memo = (TextInputEditText) mRootView.findViewById(R.id.memo);
        Log.e(TAG, "onCreateView: HC -----  mStepView.getState()");
        mStepView.done(false);
        List<String> steps = Arrays.asList(new String[]{"SAISIE", "VALIDATION", "CONFIRMATION"});
        mStepView.setSteps(steps);
        //getCardInformations();
        OpenTime();
        getCardNumber();

        memo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                } else {
                    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                }
            }
        });

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
                if (accountNumber.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity().getApplicationContext(), "Veuillez entrer le numéro du compte", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (montant.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity().getApplicationContext(), "Veuiller entrer le montant", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (memo.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity().getApplicationContext(), "Veuiller entrer le motif", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (reférenceCarte.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity().getApplicationContext(), "Veuiller entrer la référence de carte bénéficiaire", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Bundle args = new Bundle();
                    args.putString("texte", reférenceCarte.getText().toString());

                    DialogAccountToCard();
                }
            }
        });
        mStepView.getState()
                .selectedTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.white))
                .animationType(StepView.ANIMATION_CIRCLE)
                .selectedCircleColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.colorAccent))
                .selectedCircleRadius(getResources().getDimensionPixelSize(R.dimen._14sdp))
                .selectedStepNumberColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.colorPrimary))
                // You should specify only stepsNumber or steps array of strings.
                // In case you specify both steps array is chosen.

                // You should specify only steps number or steps array of strings.
                // In case you specify both steps array is chosen.
                .stepsNumber(3)
                .animationType(StepView.ANIMATION_LINE)
                .doneStepLineColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.black))
                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .stepLineWidth(getResources().getDimensionPixelSize(R.dimen._1sdp))
                .textSize(getResources().getDimensionPixelSize(R.dimen._14sdp))
                .stepNumberTextSize(getResources().getDimensionPixelSize(R.dimen._16sdp))
                .typeface(ResourcesCompat.getFont(getContext(), R.font.roboto_light))
                // other state methods are equal to the corresponding xml attr ibutes
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

    @Override
    public void onResume() {
        super.onResume();

        if(getView() == null){
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    // handle back button's click listener
                    return true;
                }
                return false;
            }
        });
    }
    @Override
    public void onBackPressed() {
        Log.e(TAG, "popBack " );
        getActivity().getFragmentManager().popBackStack();
    }
    public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long id) {
        if(parent instanceof Spinner && pos>0) {
            this.selectedAccount = (String) parent.getItemAtPosition(pos);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void DialogAccountToCard(){
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
            dialog.setContentView(R.layout.new_benef_conf_account_to_card);
            // set title
            TextView validation_title = (TextView) dialog.findViewById(R.id.validation_title);
            validation_title.setText("Validation et Otp");


            TextView user = (TextView) dialog.findViewById(R.id.nom_carte);
            user.setText(Globals.userWelcome);

            TextView account_numberTxt = (TextView) dialog.findViewById(R.id.num_compte_txt);
            account_numberTxt.setText(accountNumber.getText());

            TextView reférence_carteTxt = (TextView) dialog.findViewById(R.id.reférence_carte_txt);
            reférence_carteTxt.setText(reférenceCarte.getText());

            TextView montant_text = (TextView) dialog.findViewById(R.id.montant_text);
            montant_text.setText(montant.getText());

            TextView motif_text = (TextView) dialog.findViewById(R.id.motif_text);
            motif_text.setText(memo.getText());


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
            EditText code_verification = (EditText) dialog.findViewById(R.id.codeVerification);
            dialog.findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (code_verification.getText().toString().isEmpty()) {
                        Toast.makeText(getActivity().getApplicationContext(), "Veuiller entrer le code de vérification", Toast.LENGTH_SHORT).show();
                        return;
                    }
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
            dialog.setContentView(R.layout.confirm_dialog_account_to_card);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(false);
            dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
            TextView dateTransaction = (TextView) dialog.findViewById(R.id.date);
            Calendar calendar = Calendar.getInstance();
            Date currentDate = calendar.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String formattedDate = sdf.format(currentDate);
            dateTransaction.setText(formattedDate);
            TextView reférence_carteTxt = (TextView) dialog.findViewById(R.id.reférence_carte_txt);
            reférence_carteTxt.setText(reférenceCarte.getText());
            TextView montant_text = (TextView) dialog.findViewById(R.id.montant_text);
            montant_text.setText(montant.getText());

            TextView motif_text = (TextView) dialog.findViewById(R.id.motif_text);
            motif_text.setText(memo.getText());


            TextView user = (TextView) dialog.findViewById(R.id.nom_carte);
            user.setText(Globals.userWelcome);

            mStepView.go(2,true);
            Button okey = dialog.findViewById(R.id.btn_okay);
            Button cancel = dialog.findViewById(R.id.btn_cancel);
            if(!isSuccessful){
                ImageView image =  (ImageView) dialog.findViewById(R.id.imageView);
                image.setImageResource(R.drawable.error_icon);
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
                Card.AccountToCard();
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
            if(param!=null){
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

            TextInputEditText NomCarte = (TextInputEditText) mRootView.findViewById(R.id.user);
            NomCarte.setText(Globals.userWelcome);
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