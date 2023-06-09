package com.LBA.prepaidPortal.widgets.fragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;

import com.LBA.prepaidPortal.R;
import com.LBA.prepaidPortal.activity.HomeActivity;
import com.LBA.tools.assets.Globals;
import com.LBA.tools.services.Card;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class CardInformation1 extends BaseFragment implements AdapterView.OnItemSelectedListener{
    Spinner spinCardNumber;
    TextView txtBalance;
    TextView txtCurrency;
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
    ImageButton canlBtn;

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
        mRootView = inflater.inflate(R.layout.card_detail, container, false);
        setupOnBackPressed();
        SpannableString s = new SpannableString("Les informations de la carte");
        s.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getActivity().setTitle(s);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        BankCode = (TextView) mRootView.findViewById(R.id.bankCode);
        BankName = (TextView) mRootView.findViewById(R.id.bankname);
        canlBtn = (ImageButton) mRootView.findViewById(R.id.imageButton24);


        OpenTime();
        getCardInformations();

        canlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initProgrees();
                HomeTask task = new HomeTask(HomeActivity.class);
                task.execute();
            }
        });




        /**
         TextView txtBalance;
         TextView txtCurrency;
         private EditText fromDateEtxt;
         private EditText toDateEtxt;
         private Button btnLoad;
         private DatePickerDialog fromDatePickerDialog;
         private DatePickerDialog toDatePickerDialog;
         **/


        return mRootView;
    }

    public void onClick(View view) {
        if (view == fromDateEtxt) {
            fromDatePickerDialog.show();
        } else if (view == toDateEtxt) {
            toDatePickerDialog.show();
        }
    }
    private void setupOnBackPressed(){
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
               if(isEnabled()){
                   Toast.makeText(requireContext(), "Go back", Toast.LENGTH_SHORT).show();
                   setEnabled(false);
                   requireActivity().onBackPressed();
               }
            }
        });
    }

    public void getCardInformations(){
        try {
            //Account.GetTransactionList(selectedAccount, fromDateEtxt.getText().toString().trim(), toDateEtxt.getText().toString().trim());
            initProgrees();
            new CustomTask().execute();
        } catch (Exception e) {
            //Log.d(TAG, "btnLoad.setOnClickListener()", e);
            Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
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

            BankName = (EditText) mRootView.findViewById(R.id.bankname);
            BankName.setText(Globals.bankName);
            EditText clientCode = (EditText) mRootView.findViewById(R.id.clientcode);
            clientCode.setText(Globals.clientCode);

            EditText branch = (EditText) mRootView.findViewById(R.id.branch);
            branch.setText(Globals.Branch);

            EditText client_type = (EditText) mRootView.findViewById(R.id.client_type);
            client_type.setText(Globals.clientType+"ndividual");

            EditText cardNumber = (EditText) mRootView.findViewById(R.id.cardNumber);
            cardNumber.setText(Globals.cardNumber);
            EditText statusCard = (EditText) mRootView.findViewById(R.id.status_Card);
            if(Globals.statusCard.equals("N")){
            statusCard.setText(Globals.statusCard+"ormal");}
            else{
                statusCard.setText(Globals.statusCard+"ocked");}
            EditText titleCardHolder = (EditText) mRootView.findViewById(R.id.title_card_holder);
            titleCardHolder.setText(Globals.titleCardHolder);
            EditText familyName = (EditText) mRootView.findViewById(R.id.family_name);
            familyName.setText(Globals.familyName);
            EditText DocumentCode = (EditText) mRootView.findViewById(R.id.document_code);
            DocumentCode.setText(Globals.documentCode);
            EditText DocumentId = (EditText) mRootView.findViewById(R.id.document_id);
            DocumentId.setText(Globals.documentId);
            EditText Address = (EditText) mRootView.findViewById(R.id.address);
            Address.setText(Globals.address);
            EditText ExpiryDate = (EditText) mRootView.findViewById(R.id.expiry_date);
            ExpiryDate.setText(Globals.expiryDate);
            EditText MobileNumber = (EditText) mRootView.findViewById(R.id.mobile_number);
            MobileNumber.setText("00 233 78000882");
            EditText City = (EditText) mRootView.findViewById(R.id.city);
            City.setText(Globals.city);
            EditText Country = (EditText) mRootView.findViewById(R.id.country);
            Country.setText(Globals.country);
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