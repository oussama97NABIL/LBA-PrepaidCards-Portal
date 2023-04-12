package com.LBA.prepaidPortal.widgets.fragment;

import static com.LBA.tools.assets.Globals.transactionType;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

import androidx.annotation.Nullable;

import com.LBA.prepaidPortal.R;
import com.LBA.prepaidPortal.activity.HomeActivity;
import com.LBA.prepaidPortal.activity.LastTransactionResult;
import com.LBA.tools.assets.Globals;
import com.LBA.tools.misc.LastTransactionDetail;
import com.LBA.tools.services.Card;
import com.LBA.tools.services.Transactions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class Last10Transactions extends BaseFragment implements AdapterView.OnItemSelectedListener {

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
    EditText TransactionType;
    EditText Amount;
    EditText Currency;
    EditText ReferenceNumber;
    EditText Location;

    static private final String TAG = CardInformation1.class.getSimpleName();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.get_last_transaction_new, container, false);
        LastTransactionDetail l = new LastTransactionDetail();

        getActivity().setTitle("Get last 10 transactions\n");
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        btnLoad=(Button) mRootView.findViewById(R.id.btnLoad);
        TransactionType = (EditText) mRootView.findViewById(R.id.transactiontype);
        Amount = (EditText) mRootView.findViewById(R.id.amount);
        Currency = (EditText) mRootView.findViewById(R.id.currency);
        ReferenceNumber = (EditText) mRootView.findViewById(R.id.refrence_number);
        Location = (EditText) mRootView.findViewById(R.id.location);


        BankCode = (TextView) mRootView.findViewById(R.id.bankCode);
        BankName = (TextView) mRootView.findViewById(R.id.bankname);
        // ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Globals.accountsList);

        //spinCardNumber.setAdapter(spinnerArrayAdapter);


        textView_heading = (TextView) mRootView.findViewById(R.id.textView_heading);
        textView = (TextView) mRootView.findViewById(R.id.textView);
        textView2 = (TextView) mRootView.findViewById(R.id.textView2);
        textView3 = (TextView) mRootView.findViewById(R.id.textView3);
        canlBtn = (ImageButton) mRootView.findViewById(R.id.imageButton24);
        OpenTime();

        canlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initProgrees();
                HomeTask task = new HomeTask(HomeActivity.class);
                task.execute();
            }
        });


        if(Globals.transactionList!=null && Globals.transactionList.size()>0)
            Globals.transactionList.clear();

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if(spinCardNumber.getSelectedItemPosition()==0){
                    Toast.makeText(getActivity().getApplicationContext(), "Source account" + " " + getResources().getString(R.string.isMandator), Toast.LENGTH_LONG).show();
                    spinCardNumber.requestFocus();
                    return;
                }

                /*if (fromDateEtxt.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity().getApplicationContext(), "From Date is Mandatory", Toast.LENGTH_LONG).show();
                    return;
                }
                if (toDateEtxt.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity().getApplicationContext(), "To Date is Mandatory", Toast.LENGTH_LONG).show();
                    return;
                }*/
                try {
                    //Account.GetTransactionList(selectedAccount, fromDateEtxt.getText().toString().trim(), toDateEtxt.getText().toString().trim());
                    initProgrees();
                    new CustomTask().execute();
                } catch (Exception e) {
                    //Log.d(TAG, "btnLoad.setOnClickListener()", e);
                    Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }

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
                Transactions.GetLast10Transactions();
                Intent myWelcomeAct = new Intent(getActivity().getApplicationContext(), LastTransactionResult.class);
                startActivity(myWelcomeAct);
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

            TransactionType.setText(transactionType);
            Amount.setText(Globals.balance);
            Currency.setText(Globals.currency);
            ReferenceNumber.setText(Globals.referenceNumber);
            Location.setText(Globals.location);
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